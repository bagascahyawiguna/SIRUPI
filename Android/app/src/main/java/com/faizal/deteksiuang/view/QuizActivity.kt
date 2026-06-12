package com.faizal.deteksiuang.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.media.SoundPool
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.faizal.deteksiuang.R
import com.faizal.deteksiuang.db.QuizHistoryDatabaseHelper
import com.google.android.material.button.MaterialButton
import kotlin.random.Random

class QuizActivity : AppCompatActivity() {

    // ... (Variabel yang lama tetap sama) ...
    private val nominals = listOf(1000, 2000, 5000, 10000, 20000)
    private val nominalToDrawable = mapOf(
        1000 to R.drawable.uang_1000,
        2000 to R.drawable.uang_2000,
        5000 to R.drawable.uang_5000,
        10000 to R.drawable.uang_10000,
        20000 to R.drawable.uang_20000
    )

    private var correctAnswer = 0
    private var currentScore = 0
    private var questionCount = 0
    private val maxQuestions = 10
    private val pointsPerQuestion = 10
    private val maxTotalScore = maxQuestions * pointsPerQuestion

    // Views
    private lateinit var tvScore: TextView
    private lateinit var tvQuestionText: TextView
    private lateinit var tvQuestionNumeric: TextView
    private lateinit var tvOperator: TextView
    private lateinit var imgMoneyLeft: ImageView
    private lateinit var imgMoneyRight: ImageView
    private lateinit var imgFeedback: ImageView
    private lateinit var btnOptionA: MaterialButton
    private lateinit var btnOptionB: MaterialButton
    private lateinit var btnOptionC: MaterialButton
    private lateinit var btnOptionD: MaterialButton
    private lateinit var btnRetry: MaterialButton
    private lateinit var btnBack: MaterialButton

    private lateinit var soundPool: SoundPool
    private var soundClick = 0
    private var soundCorrect = 0
    private var soundWrong = 0

    // VARIABLE UNTUK DATA SISWA
    private var studentName: String = ""
    private var studentClass: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // AMBIL DATA DARI MENU ACTIVITY
        studentName = intent.getStringExtra("EXTRA_NAME") ?: "Tanpa Nama"
        studentClass = intent.getStringExtra("EXTRA_CLASS") ?: "-"

        // ... Inisialisasi View (Sama seperti sebelumnya) ...
        soundPool = SoundPool.Builder().setMaxStreams(3).build()
        soundClick = soundPool.load(this, R.raw.click, 1)
        soundCorrect = soundPool.load(this, R.raw.correct, 1)
        soundWrong = soundPool.load(this, R.raw.wrong, 1)

        tvScore = findViewById(R.id.tvScore)
        tvQuestionText = findViewById(R.id.tvQuestionText)
        tvQuestionNumeric = findViewById(R.id.tvQuestionNumeric)
        tvOperator = findViewById(R.id.tvOperator)
        imgMoneyLeft = findViewById(R.id.imgMoneyLeft)
        imgMoneyRight = findViewById(R.id.imgMoneyRight)
        imgFeedback = findViewById(R.id.imgFeedback)

        btnOptionA = findViewById(R.id.btnOptionA)
        btnOptionB = findViewById(R.id.btnOptionB)
        btnOptionC = findViewById(R.id.btnOptionC)
        btnOptionD = findViewById(R.id.btnOptionD)
        btnRetry = findViewById(R.id.btnRetry)
        btnBack = findViewById(R.id.btnBack)

        val options = listOf(btnOptionA, btnOptionB, btnOptionC, btnOptionD)
        options.forEach { button ->
            button.setOnClickListener {
                soundPool.play(soundClick, 1f, 1f, 0, 0, 1f)
                checkAnswer(it as MaterialButton)
            }
        }

        btnRetry.setOnClickListener {
            soundPool.play(soundClick, 1f, 1f, 0, 0, 1f)
            resetQuiz()
        }

        btnBack.setOnClickListener {
            soundPool.play(soundClick, 1f, 1f, 0, 0, 1f)
            finish()
        }

        generateNewQuestion()
    }

    private fun generateNewQuestion() {
        if (questionCount >= maxQuestions) {
            showQuizFinished()
            return
        }

        questionCount++
        tvScore.text = "Skor: $currentScore / $maxTotalScore"

        val operator = if (Random.nextBoolean()) "+" else "-"
        var num1 = nominals.random()
        var num2 = nominals.random()

        if (operator == "-" && num1 < num2) {
            val temp = num1
            num1 = num2
            num2 = temp
        }

        correctAnswer = if (operator == "+") num1 + num2 else num1 - num2

        imgMoneyLeft.setImageResource(nominalToDrawable[num1]!!)
        imgMoneyRight.setImageResource(nominalToDrawable[num2]!!)

        tvOperator.text = operator
        tvQuestionText.text = "Pilih jawaban yang benar"
        tvQuestionNumeric.text = "Rp ${format(num1)} $operator Rp ${format(num2)} = ?"

        val answers = generateChoices(correctAnswer)
        val buttons = listOf(btnOptionA, btnOptionB, btnOptionC, btnOptionD).shuffled()

        buttons.forEachIndexed { index, button ->
            button.text = "Rp ${format(answers[index])}"
            button.tag = answers[index]
            button.isEnabled = true
            button.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#2196F3"))
        }
    }

    private fun generateChoices(correct: Int): List<Int> {
        val set = mutableSetOf(correct)
        while (set.size < 4) {
            val offset = Random.nextInt(1, 6) * 1000
            val wrong = correct + if (Random.nextBoolean()) offset else -offset
            if (wrong > 0) set.add(wrong)
        }
        return set.toList().shuffled()
    }

    private fun checkAnswer(button: MaterialButton) {
        val selected = button.tag as Int
        imgFeedback.visibility = View.VISIBLE
        val allButtons = listOf(btnOptionA, btnOptionB, btnOptionC, btnOptionD)
        allButtons.forEach { it.isEnabled = false }

        if (selected == correctAnswer) {
            currentScore += pointsPerQuestion
            imgFeedback.setImageResource(R.drawable.ic_check_circle)
            soundPool.play(soundCorrect, 1f, 1f, 0, 0, 1f)
            button.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#4CAF50"))
        } else {
            imgFeedback.setImageResource(R.drawable.ic_close_circle)
            soundPool.play(soundWrong, 1f, 1f, 0, 0, 1f)
            button.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F44336"))
            allButtons.forEach { btn ->
                if (btn.tag as Int == correctAnswer) {
                    btn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#4CAF50"))
                }
            }
        }

        tvScore.text = "Skor: $currentScore / $maxTotalScore"
        button.postDelayed({
            imgFeedback.visibility = View.GONE
            generateNewQuestion()
        }, 1500)
    }

    private fun showQuizFinished() {
        tvQuestionText.text = "KUIS SELESAI 🎉"
        tvQuestionNumeric.text = "Skor akhir: $currentScore / $maxTotalScore"
        tvScore.text = "Skor: $currentScore / $maxTotalScore"

        tvOperator.visibility = View.GONE
        imgMoneyLeft.visibility = View.GONE
        imgMoneyRight.visibility = View.GONE
        btnOptionA.visibility = View.GONE
        btnOptionB.visibility = View.GONE
        btnOptionC.visibility = View.GONE
        btnOptionD.visibility = View.GONE
        btnRetry.visibility = View.VISIBLE
        btnBack.visibility = View.VISIBLE

        val db = QuizHistoryDatabaseHelper(this)

        val date = java.text.SimpleDateFormat(
            "dd-MM-yyyy HH:mm",
            java.util.Locale.getDefault()
        ).format(java.util.Date())

        // UPDATE: SIMPAN NAMA, KELAS, SKOR, TANGGAL
        db.insertHistory(
            studentName,
            studentClass,
            "Skor: $currentScore / $maxTotalScore",
            date
        )
    }

    private fun resetQuiz() {
        currentScore = 0
        questionCount = 0
        tvOperator.visibility = View.VISIBLE
        imgMoneyLeft.visibility = View.VISIBLE
        imgMoneyRight.visibility = View.VISIBLE
        btnOptionA.visibility = View.VISIBLE
        btnOptionB.visibility = View.VISIBLE
        btnOptionC.visibility = View.VISIBLE
        btnOptionD.visibility = View.VISIBLE
        btnRetry.visibility = View.GONE
        btnBack.visibility = View.GONE
        generateNewQuestion()
    }

    private fun format(value: Int) = String.format("%,d", value).replace(",", ".")

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}