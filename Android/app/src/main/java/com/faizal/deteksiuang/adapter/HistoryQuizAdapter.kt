package com.faizal.deteksiuang.adapter

import QuizHistoryModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faizal.deteksiuang.R
import com.faizal.deteksiuang.db.QuizHistoryDatabaseHelper

class HistoryQuizAdapter(
    private val list: MutableList<QuizHistoryModel>,
    private val db: QuizHistoryDatabaseHelper
) : RecyclerView.Adapter<HistoryQuizAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // 1. Definisikan ID TextView sesuai layout XML yang baru
        val tvName: TextView = view.findViewById(R.id.tvStudentName)
        val tvClass: TextView = view.findViewById(R.id.tvStudentClass) // <-- INI BARU
        val tvScore: TextView = view.findViewById(R.id.tvScore)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_quiz, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]

        // 2. Masukkan data ke masing-masing TextView

        // Nama (Tebal & Besar)
        holder.tvName.text = data.name

        // Kelas (Lebih kecil dengan label "Kelas :")
        holder.tvClass.text = "Kelas : ${data.className}"

        // Skor & Tanggal
        holder.tvScore.text = data.score
        holder.tvDate.text = data.date

        // Tombol Hapus
        holder.btnDelete.setOnClickListener {
            val pos = holder.adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                db.deleteHistory(data.id)
                list.removeAt(pos)
                notifyItemRemoved(pos)
            }
        }
    }
}