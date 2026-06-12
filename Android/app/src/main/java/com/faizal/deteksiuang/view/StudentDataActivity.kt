package com.faizal.deteksiuang.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.faizal.deteksiuang.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class StudentDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_data)

        val etName = findViewById<TextInputEditText>(R.id.etName)
        val etClass = findViewById<TextInputEditText>(R.id.etClass)
        val btnStart = findViewById<MaterialButton>(R.id.btnStartGame)
        val btnBack = findViewById<ImageView>(R.id.btnBackInput)

        // Tombol Kembali
        btnBack.setOnClickListener {
            finish()
        }

        // Tombol Mulai
        btnStart.setOnClickListener {
            val name = etName.text.toString().trim()
            val className = etClass.text.toString().trim()

            if (name.isEmpty() || className.isEmpty()) {
                Toast.makeText(this, "Nama dan Kelas harus diisi ya!", Toast.LENGTH_SHORT).show()
            } else {
                // Kirim data ke QuizActivity
                val intent = Intent(this, QuizActivity::class.java)
                intent.putExtra("EXTRA_NAME", name)
                intent.putExtra("EXTRA_CLASS", className)

                startActivity(intent)
                finish() // Tutup halaman input biar gak bisa balik kesini kalau ditekan back
            }
        }
    }
}