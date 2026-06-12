//package com.faizal.deteksiuang.view
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.widget.ImageButton
//import android.widget.Toast
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.material.button.MaterialButton
//import com.google.android.material.card.MaterialCardView
//import com.google.android.material.textfield.TextInputEditText
//import com.faizal.deteksiuang.R
//
//class QuizMenuActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_quiz_menu)
//
//        val cardStartQuiz: MaterialCardView = findViewById(R.id.cardStartQuiz)
//        val btnHistory: ImageButton = findViewById(R.id.btnHistory)
//
//        // 1. KLIK MULAI -> Buka Dialog
//        cardStartQuiz.setOnClickListener {
//            showInputDataDialog()
//        }
//
//        // 2. KLIK HISTORY
//        btnHistory.setOnClickListener {
//            startActivity(Intent(this, HistoryQuizActivity::class.java))
//        }
//    }
//
//    private fun showInputDataDialog() {
//        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_student_data, null)
//        val etName = dialogView.findViewById<TextInputEditText>(R.id.etStudentName)
//        val etClass = dialogView.findViewById<TextInputEditText>(R.id.etStudentClass)
//        val btnStart = dialogView.findViewById<MaterialButton>(R.id.btnStartQuizData)
//
//        val dialog = AlertDialog.Builder(this)
//            .setView(dialogView)
//            .create()
//
//        // Background transparan agar rounded corner terlihat rapi (opsional)
//        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//
//        btnStart.setOnClickListener {
//            val name = etName.text.toString().trim()
//            val className = etClass.text.toString().trim()
//
//            if (name.isEmpty() || className.isEmpty()) {
//                Toast.makeText(this, "Mohon isi Nama dan Kelas!", Toast.LENGTH_SHORT).show()
//            } else {
//                dialog.dismiss()
//
//                // Kirim Data Nama & Kelas ke QuizActivity via Intent
//                val intent = Intent(this, QuizActivity::class.java)
//                intent.putExtra("EXTRA_NAME", name)
//                intent.putExtra("EXTRA_CLASS", className)
//                startActivity(intent)
//            }
//        }
//
//        dialog.show()
//    }
//}