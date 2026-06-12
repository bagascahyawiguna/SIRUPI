package com.faizal.deteksiuang.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.faizal.deteksiuang.R
import com.faizal.deteksiuang.databinding.FragmentCameraBinding
import com.faizal.deteksiuang.ml.MoneyYoloDetector
import com.faizal.deteksiuang.view.MoneyDetailActivity
import java.util.Locale
import java.util.concurrent.Executors

class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding
    private lateinit var detector: MoneyYoloDetector
    private val cameraExecutor = Executors.newSingleThreadExecutor()
    private var camera: Camera? = null

    // 🔹 VARIABLE BARU: Default pakai kamera belakang
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    // 🔹 Data deteksi
    private var currentDetectedLabel: String? = null

    // 🛡️ LOGIKA ANTI-JITTER
    private var tempLabel: String? = null
    private var detectionCounter = 0
    private val REQUIRED_FRAMES = 3
    private val MIN_CONFIDENCE = 0.60f

    // 🔊 Audio & Controls
    private var tts: TextToSpeech? = null
    private var mediaPlayer: MediaPlayer? = null
    private var lastSpokenLabel: String? = null
    private var isSoundOn = true
    private var isFlashOn = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detector = MoneyYoloDetector(requireContext())

        // Init TTS & Sound
        tts = TextToSpeech(requireContext()) {
            if (it == TextToSpeech.SUCCESS) {
                tts?.language = Locale("id", "ID")
            }
        }
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.detect)

        // ----------------------------------------------------
        // 🆕 LOGIKA GANTI KAMERA (SWITCH CAMERA)
        // ----------------------------------------------------
        binding.btnSwitchCamera.setOnClickListener {
            // Ubah selector: Jika Belakang -> Depan, Jika Depan -> Belakang
            cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else {
                CameraSelector.DEFAULT_BACK_CAMERA
            }

            // Matikan flash jika pindah kamera agar tidak error visual
            isFlashOn = false
            binding.btnFlash.setImageResource(R.drawable.ic_flash_off)

            // Restart kamera dengan selector baru
            startCamera()
        }

        // Tombol Shutter
        binding.btnShutter.setOnClickListener {
            if (currentDetectedLabel != null) {
                val intent = Intent(requireContext(), MoneyDetailActivity::class.java)
                intent.putExtra("LABEL_UANG", mapLabelToNominal(currentDetectedLabel!!))
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Tahan kamera stabil di depan uang...", Toast.LENGTH_SHORT).show()
            }
        }

        // Tombol Suara
        binding.btnSound.setOnClickListener {
            isSoundOn = !isSoundOn
            val icon = if (isSoundOn) R.drawable.ic_volume_up else R.drawable.ic_volume_off
            binding.btnSound.setImageResource(icon)
            if (!isSoundOn) tts?.stop()
        }

        // Tombol Flash
        binding.btnFlash.setOnClickListener {
            // Cek dulu apakah kamera yang aktif punya flash
            if (camera != null && camera!!.cameraInfo.hasFlashUnit()) {
                isFlashOn = !isFlashOn
                camera!!.cameraControl.enableTorch(isFlashOn)
                val icon = if (isFlashOn) R.drawable.ic_flash_on else R.drawable.ic_flash_off
                binding.btnFlash.setImageResource(icon)
            } else {
                Toast.makeText(requireContext(), "Flash tidak tersedia di kamera ini", Toast.LENGTH_SHORT).show()
            }
        }

        if (allPermissionsGranted()) startCamera()
        else ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), 10)
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val provider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            }

            // Image Analysis
            val analyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor) { imageProxy -> processFrame(imageProxy) }
                }

            try {
                // Unbind use cases sebelumnya sebelum bind yang baru
                provider.unbindAll()

                // 🆕 Bind menggunakan variable `cameraSelector` (bukan hardcode BACK_CAMERA)
                camera = provider.bindToLifecycle(
                    viewLifecycleOwner,
                    cameraSelector, // <-- Ini yang berubah
                    preview,
                    analyzer
                )

                // 🆕 Cek Flash: Jika kamera depan, sembunyikan tombol Flash
                updateFlashButtonVisibility()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    // Fungsi kecil untuk menyembunyikan tombol flash saat kamera depan aktif
    private fun updateFlashButtonVisibility() {
        if (cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA) {
            binding.btnFlash.visibility = View.GONE
        } else {
            binding.btnFlash.visibility = View.VISIBLE
        }
    }

    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    private fun processFrame(imageProxy: ImageProxy) {
        val act = activity
        val bitmap = imageProxy.toBitmap()

        if (act == null || bitmap == null) {
            imageProxy.close()
            return
        }

        val rotated = rotateBitmap(bitmap, imageProxy.imageInfo.rotationDegrees.toFloat())

        // Deteksi
        val result = detector.detect(rotated)

        act.runOnUiThread {
            if (!isAdded) return@runOnUiThread

            // Filter Anti-Jitter (Wajib stabil 5 frame & confidence > 75%)
            if (result != null && result.confidence > MIN_CONFIDENCE) {

                if (result.label == tempLabel) {
                    detectionCounter++
                } else {
                    detectionCounter = 0
                    tempLabel = result.label
                }

                if (detectionCounter >= REQUIRED_FRAMES) {
                    currentDetectedLabel = result.label

                    val textAngka = formatToRupiah(result.label)
                    val accuracy = (result.confidence * 100).toInt()
                    val textTerbilang = labelToSpeech(result.label)

                    binding.txtResult.text = "$textAngka ($accuracy%)\n$textTerbilang"
                    binding.txtResult.setTextColor(Color.GREEN)

                    // Mainkan Suara
                    if (isSoundOn && result.label != lastSpokenLabel) {
                        mediaPlayer?.start()
                        tts?.speak("Uang $textTerbilang", TextToSpeech.QUEUE_FLUSH, null, "money_id")
                        lastSpokenLabel = result.label
                    }
                }

            } else {
                detectionCounter = 0
            }

            if (result == null || result.confidence < MIN_CONFIDENCE) {
                detectionCounter = 0
                binding.txtResult.text = "Arahkan ke uang..."
                binding.txtResult.setTextColor(Color.WHITE)

                if (currentDetectedLabel != null) {
                    lastSpokenLabel = null
                    currentDetectedLabel = null
                }
            }
        }
        imageProxy.close()
    }

    // ... (Fungsi Helper di bawah sini sama persis seperti sebelumnya, tidak perlu diubah) ...

    private fun formatToRupiah(label: String): String = when (label) {
        "1k" -> "Rp 1.000"
        "2k" -> "Rp 2.000"
        "5k" -> "Rp 5.000"
        "10k" -> "Rp 10.000"
        "20k" -> "Rp 20.000"
        "50k" -> "Rp 50.000"
        "100k" -> "Rp 100.000"
        else -> label
    }

    private fun labelToSpeech(label: String): String = when (label) {
        "1k" -> "Seribu Rupiah"
        "2k" -> "Dua Ribu Rupiah"
        "5k" -> "Lima Ribu Rupiah"
        "10k" -> "Sepuluh Ribu Rupiah"
        "20k" -> "Dua Puluh Ribu Rupiah"
        "50k" -> "Lima Puluh Ribu Rupiah"
        "100k" -> "Seratus Ribu Rupiah"
        else -> label
    }

    private fun mapLabelToNominal(label: String): String = when (label) {
        "1k" -> "1000"
        "2k" -> "2000"
        "5k" -> "5000"
        "10k" -> "10000"
        "20k" -> "20000"
        "50k" -> "50000"
        "100k" -> "100000"
        else -> label
    }

    private fun rotateBitmap(bitmap: android.graphics.Bitmap, degrees: Float): android.graphics.Bitmap {
        if (degrees == 0f) return bitmap
        val matrix = android.graphics.Matrix()
        matrix.postRotate(degrees)
        // 🆕 Tambahan Opsional: Jika Kamera Depan, Mirror gambarnya biar natural
        if (cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA) {
            matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
        }
        return android.graphics.Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun allPermissionsGranted(): Boolean =
        ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    override fun onDestroyView() {
        super.onDestroyView()
        tts?.stop()
        tts?.shutdown()
        mediaPlayer?.release()
        try { camera?.cameraControl?.enableTorch(false) } catch (e: Exception) {}
        cameraExecutor.shutdown()
    }
}