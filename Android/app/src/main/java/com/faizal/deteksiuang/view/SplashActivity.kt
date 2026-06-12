package com.faizal.deteksiuang.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.faizal.deteksiuang.MainActivity
import com.faizal.deteksiuang.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    companion object {
        // Waktu total splash screen (3 detik cukup biar puas liat animasinya)
        const val SPLASH_SCREEN_DELAY_MILLIS = 3000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cek supaya splash screen gak muncul terus kalau di-minimize
        if (!isTaskRoot && intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intent.action != null && intent.action == Intent.ACTION_MAIN) {
            finish()
            return
        }

        setContentView(R.layout.activity_splash)

        // ==========================================
        // 🎬 BAGIAN ANIMASI
        // ==========================================

        // 1. Kenalan dulu sama komponen di layout
        val cardLogo = findViewById<CardView>(R.id.cardLogo)
        val tvAppName = findViewById<TextView>(R.id.tvAppName)
        val tvDesc = findViewById<TextView>(R.id.tvDesc)

        // 2. Siapkan Animasi
        // Animasi untuk Logo (Pop Up Membal)
        val logoAnim = AnimationUtils.loadAnimation(this, R.anim.pop_up_animation)

        // Animasi untuk Teks (Muncul pelan biasa)
        val textAnim = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        textAnim.duration = 1500 // Durasi teks muncul pelan

        // 3. JALANKAN ANIMASI!
        cardLogo.startAnimation(logoAnim)

        // Teks munculnya telat dikit biar keren (Gak barengan sama logo)
        tvAppName.alpha = 0f // Sembunyi dulu
        tvDesc.alpha = 0f

        tvAppName.animate().alpha(1f).setDuration(1000).setStartDelay(800).start()
        tvDesc.animate().alpha(1f).setDuration(1000).setStartDelay(1000).start()


        // 4. Pindah ke Menu Utama setelah selesai
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_DELAY_MILLIS)
    }
}