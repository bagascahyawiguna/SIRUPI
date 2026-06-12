package com.faizal.deteksiuang.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faizal.deteksiuang.R
import com.faizal.deteksiuang.adapter.HistoryQuizAdapter
import com.faizal.deteksiuang.db.QuizHistoryDatabaseHelper

class HistoryQuizActivity : AppCompatActivity() {

    private lateinit var db: QuizHistoryDatabaseHelper
    private lateinit var adapter: HistoryQuizAdapter
    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_quiz)

        db = QuizHistoryDatabaseHelper(this)

        rv = findViewById(R.id.rvHistory)
        rv.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()

        // 🔥 AMBIL ULANG DATA SETIAP HALAMAN DIBUKA
        adapter = HistoryQuizAdapter(db.getAllHistory(), db)
        rv.adapter = adapter
    }
}
