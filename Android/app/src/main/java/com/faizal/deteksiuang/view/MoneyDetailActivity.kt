package com.faizal.deteksiuang.view

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.faizal.deteksiuang.R
import com.google.android.material.button.MaterialButton
import java.util.Locale

class MoneyDetailActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    // Views
    private lateinit var imgDepan: ImageView
    private lateinit var imgBelakang: ImageView
    private lateinit var tvDeskripsiDepan: TextView
    private lateinit var tvDeskripsiBelakang: TextView

    // Header
    private lateinit var tvNominalTitle: TextView
    private lateinit var tvNominalEjaan: TextView

    // Tombol Utama
    private lateinit var btnSpeakAll: MaterialButton

    // TTS & Data
    private var tts: TextToSpeech? = null
    private var textToRead: String = ""

    private fun mapLabelToNominal(label: String): String {
        return when (label) {
            "1k" -> "1000"
            "2k" -> "2000"
            "5k" -> "5000"
            "10k" -> "10000"
            "20k" -> "20000"
            "50k" -> "50000"
            "100k" -> "100000"
            else -> label
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money_detail)

        // Inisialisasi TTS
        tts = TextToSpeech(this, this)

        // Bind views
        tvNominalTitle = findViewById(R.id.tvNominalTitle)
        tvNominalEjaan = findViewById(R.id.tvNominalEjaan)
        imgDepan = findViewById(R.id.imgDepan)
        imgBelakang = findViewById(R.id.imgBelakang)
        tvDeskripsiDepan = findViewById(R.id.tvDeskripsiDepan)
        tvDeskripsiBelakang = findViewById(R.id.tvDeskripsiBelakang)

        // Bind tombol bawah
        btnSpeakAll = findViewById(R.id.btnSpeakAll)

        val label = intent.getStringExtra("LABEL_UANG")

        if (label != null) {
            val nominal = mapLabelToNominal(label)
            loadMoneyDetails(nominal)
        } else {
            Toast.makeText(this, "Data nominal tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Listener Tombol Speak All
        btnSpeakAll.setOnClickListener {
            speakOut(textToRead)
        }
    }

    private fun speakOut(text: String) {
        if (text.isNotEmpty()) {
            if (tts?.isSpeaking == true) {
                tts?.stop()
            }
            // Menggunakan params null agar default
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "money_detail_id")
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale("id", "ID")) ?: TextToSpeech.LANG_MISSING_DATA
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Fallback ke US jika Indo tidak ada, tapi idealnya user install data voice ID
                tts?.language = Locale.US
                Toast.makeText(
                    this,
                    "Bahasa Indonesia belum terinstall di TTS HP ini.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            // Otomatis baca saat masuk activity (Opsional, sangat membantu tuna netra)
            // speakOut(textToRead)
        } else {
            Toast.makeText(this, "TTS Gagal Init", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }

    private fun loadMoneyDetails(nominal: String) {
        // Format angka (1000 -> 1.000)
        val formattedNominal = try {
            String.format("%,d", nominal.toLong()).replace(',', '.')
        } catch (e: Exception) { nominal }

        tvNominalTitle.text = "Rp $formattedNominal"

        // Variabel Data Dinamis
        val ejaan: String
        val warna: String
        val tokoh: String
        val bunga: String
        val blindCode: String
        val wisataTari: String
        val tahunEmisi = "2022"

        // 1. ISI DATA BERDASARKAN NOMINAL
        when (nominal) {
            "1000" -> {
                ejaan = "SE-RI-BU RU-PI-AH"
                warna = "Hijau Muda"
                tokoh = "Cut Nyak Meutia"
                bunga = "Anggrek Larat"
                blindCode = "7 (Tujuh)"
                wisataTari = "Pemandangan Banda Neira dan Tari Tifa"
                imgDepan.setImageResource(R.drawable.uang_1000)
                imgBelakang.setImageResource(R.drawable.uang_1000_belakang)
            }
            "2000" -> {
                ejaan = "DU-A RI-BU RU-PI-AH"
                warna = "Abu-abu"
                tokoh = "Mohammad Husni Thamrin"
                bunga = "Jeumpa"
                blindCode = "6 (Enam)"
                wisataTari = "Pemandangan Ngarai Sianok dan Tari Piring"
                imgDepan.setImageResource(R.drawable.uang_2000)
                imgBelakang.setImageResource(R.drawable.uang_2000_belakang)
            }
            "5000" -> {
                ejaan = "LI-MA RI-BU RU-PI-AH"
                warna = "Cokelat"
                tokoh = "Dr. Idham Chalid"
                bunga = "Sedap Malam"
                blindCode = "5 (Lima)"
                wisataTari = "Gunung Bromo dan Tari Gambyong"
                imgDepan.setImageResource(R.drawable.uang_5000)
                imgBelakang.setImageResource(R.drawable.uang_5000_belakang)
            }
            "10000" -> {
                ejaan = "SE-PU-LUH RI-BU RU-PI-AH"
                warna = "Ungu"
                tokoh = "Frans Kaisiepo"
                bunga = "Cempaka Hutan Kasar"
                blindCode = "4 (Empat)"
                wisataTari = "Taman Nasional Wakatobi dan Tari Pakarena"
                imgDepan.setImageResource(R.drawable.uang_10000)
                imgBelakang.setImageResource(R.drawable.uang_10000_belakang)
            }
            "20000" -> {
                ejaan = "DU-A PU-LUH RI-BU RU-PI-AH"
                warna = "Hijau Terang"
                tokoh = "Sam Ratulangi"
                bunga = "Anggrek Hitam"
                blindCode = "3 (Tiga)"
                wisataTari = "Kepulauan Derawan dan Tari Gong"
                imgDepan.setImageResource(R.drawable.uang_20000)
                imgBelakang.setImageResource(R.drawable.uang_20000_belakang)
            }
            "50000" -> {
                ejaan = "LI-MA PU-LUH RI-BU RU-PI-AH"
                warna = "Biru"
                tokoh = "Ir. Djuanda Kartawidjaja"
                bunga = "Jepun Bali (Kamboja)"
                blindCode = "2 (Dua)"
                wisataTari = "Taman Nasional Komodo dan Tari Legong"
                imgDepan.setImageResource(R.drawable.uang_50000)
                imgBelakang.setImageResource(R.drawable.uang_50000_belakang)
            }
            "100000" -> {
                ejaan = "SE-RA-TUS RI-BU RU-PI-AH"
                warna = "Merah"
                tokoh = "Ir. Soekarno dan Dr. H. Mohammad Hatta"
                bunga = "Anggrek Bulan"
                blindCode = "1 (Satu)"
                wisataTari = "Raja Ampat dan Tari Topeng Betawi"
                imgDepan.setImageResource(R.drawable.uang_100000)
                imgBelakang.setImageResource(R.drawable.uang_100000_belakang)
            }
            else -> {
                ejaan = "NOMINAL TIDAK DIKENAL"
                warna = "-"
                tokoh = "-"
                bunga = "-"
                blindCode = "-"
                wisataTari = "-"
            }
        }

        // 2. TEMPLATE DESKRIPSI (HTML STYLE AGAR RAPI DI LAYAR)
        val htmlDepan = """
            <b>Warna Uang:</b><br> Dominan $warna.<br><br>
            
            <b>1. Pojok Kiri Atas:</b><br>
            Terdapat angka nominal $formattedNominal.<br><br>
            
            <b>2. Kode Tuna Netra:</b><br>
            Memiliki $blindCode pasang garis timbul di sisi kanan dan kiri uang.<br><br>
            
            <b>3. Pojok Kiri Bawah:</b><br>
            Terdapat ikon Bunga $bunga. Tepat di bawahnya, terdapat tanda tangan Gubernur Bank Indonesia.<br><br>
            
            <b>4. Tengah Uang:</b><br>
            Terdapat gambar tokoh utama, yaitu $tokoh.<br><br>
            
            <b>5. Pojok Kanan Atas:</b><br>
            Terdapat Lambang Garuda, dan Peta Kepulauan Indonesia.<br><br>
            
            <b>6. Pojok Kanan Bawah:</b><br>
            Terdapat Logo Bank Indonesia, angka $formattedNominal dan tulisan "$ejaan".
        """.trimIndent()

        val htmlBelakang = """
            <b>1. Pojok Kiri Atas:</b><br>
            Terdapat gambar Bunga $bunga dan angka $formattedNominal.<br><br>
            
            <b>2. Pojok Kiri Bawah:</b><br>
            Terdapat Logo BI, Nomor Seri Unik, dan Kalimat Hukum Uang.<br><br>
            
            <b>3. Tengah Uang:</b><br>
            Menampilkan keindahan alam $wisataTari.<br><br>
            
            <b>4. Pojok Kanan Atas:</b><br>
            Terdapat tulisan BANK INDONESIA, Nomor Seri, angka $formattedNominal, dan Tahun Emisi $tahunEmisi.
        """.trimIndent()

        // 3. SET TEXT KE LAYAR (Menggunakan HtmlCompat agar Teks Bold muncul)
        tvNominalEjaan.text = "($ejaan)"
        tvDeskripsiDepan.text = HtmlCompat.fromHtml(htmlDepan, HtmlCompat.FROM_HTML_MODE_LEGACY)
        tvDeskripsiBelakang.text = HtmlCompat.fromHtml(htmlBelakang, HtmlCompat.FROM_HTML_MODE_LEGACY)

        // 4. KONFIGURASI SUARA (TTS) - BACA SEMUA TEKS
        // Trik: Kita ambil teks HTML di atas, lalu hapus kode HTML-nya.
        // Ganti <br> dengan Titik (.) agar suara berhenti sejenak (jeda).

        val ttsDepan = htmlDepan
            .replace("<br>", ". ") // Ganti enter jadi titik biar ada jeda
            .replace("<b>", "")     // Hapus kode bold
            .replace("</b>", "")    // Hapus kode penutup bold
            .replace("\n", " ")     // Hapus enter manual

        val ttsBelakang = htmlBelakang
            .replace("<br>", ". ")
            .replace("<b>", "")
            .replace("</b>", "")
            .replace("\n", " ")

        // Gabungkan semua teks untuk dibaca
        textToRead = "Terdeteksi Uang $ejaan. " +
                "Penjelasan Bagian Depan. $ttsDepan " +
                "Penjelasan Bagian Belakang. $ttsBelakang"
    }
}