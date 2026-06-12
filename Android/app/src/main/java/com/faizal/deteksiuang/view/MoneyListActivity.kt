package com.faizal.deteksiuang.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.faizal.deteksiuang.R
import com.google.android.material.card.MaterialCardView

class MoneyListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Pastikan layout yang dipanggil benar: activity_money_list
        setContentView(R.layout.activity_money_list)

        setupCardListeners()
    }
    private fun setupCardListeners() {
        val cardIds = mapOf(
            R.id.card_1000 to "1k",
            R.id.card_2000 to "2k",
            R.id.card_5000 to "5k",
            R.id.card_10000 to "10k",
            R.id.card_20000 to "20k",
            R.id.card_50000 to "50k",
            R.id.card_100000 to "100k"
        )

        for ((id, label) in cardIds) {
            val cardView = findViewById<MaterialCardView>(id)
            cardView.setOnClickListener {
                navigateToDetail(label)
            }
        }
    }

    private fun navigateToDetail(label: String) {
        val intent = Intent(this, MoneyDetailActivity::class.java)
        intent.putExtra("LABEL_UANG", label)
        startActivity(intent)
    }
}