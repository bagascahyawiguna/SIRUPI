package com.faizal.deteksiuang.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.faizal.deteksiuang.R
import com.faizal.deteksiuang.view.HistoryQuizActivity
import com.faizal.deteksiuang.view.MoneyListActivity
import com.faizal.deteksiuang.view.StudentDataActivity // Import Activity baru

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val cardBelajar: CardView = view.findViewById(R.id.cardBelajar)
        val cardGame: CardView = view.findViewById(R.id.cardGame)
        val cardHistory: CardView = view.findViewById(R.id.cardHistory)

        // 1. Menu Belajar
        cardBelajar.setOnClickListener {
            startActivity(Intent(requireContext(), MoneyListActivity::class.java))
        }

        // 2. Menu Kuis -> Ke Halaman Input Data (StudentDataActivity)
        cardGame.setOnClickListener {
            // TIDAK PAKAI DIALOG LAGI, TAPI PINDAH HALAMAN
            startActivity(Intent(requireContext(), StudentDataActivity::class.java))
        }

        // 3. Menu History
        cardHistory.setOnClickListener {
            startActivity(Intent(requireContext(), HistoryQuizActivity::class.java))
        }

        return view
    }
}