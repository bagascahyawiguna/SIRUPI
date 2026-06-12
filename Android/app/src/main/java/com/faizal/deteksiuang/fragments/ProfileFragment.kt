package com.faizal.deteksiuang.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.faizal.deteksiuang.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Find layouts
        val layoutAddress = view.findViewById<LinearLayout>(R.id.layoutAddress)
        val layoutPhone = view.findViewById<LinearLayout>(R.id.layoutPhone)
        val layoutEmail = view.findViewById<LinearLayout>(R.id.layoutEmail)
        val layoutInstagram = view.findViewById<LinearLayout>(R.id.layoutInstagram)
        val layoutTiktok = view.findViewById<LinearLayout>(R.id.layoutTiktok)

        // === ALAMAT → Google Maps (PAKAI PLUS CODE = 100% AKURAT) ===
        layoutAddress.setOnClickListener {
            val plusCode = "4FRW+WVQ" // Plus Code sekolah
            val fullAddress = "4FRW+WVQ, Jalan Caracas Mandirancn, Sampora, Kec. Cilimus, Kabupaten Kuningan, Jawa Barat 45556"

            // Gunakan Plus Code untuk akurasi maksimal
            val gmmIntentUri = Uri.parse("geo:0,0?q=$plusCode ($fullAddress)")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            try {
                startActivity(mapIntent)
            } catch (e: Exception) {
                // Fallback: buka di browser
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/?q=$plusCode")))
            }
        }

        // === TELEPON → Dialer ===
        layoutPhone.setOnClickListener {
            val phone = getString(R.string.phone).replace(Regex("[^0-9]"), "")
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
            startActivity(intent)
        }

        // === EMAIL → Email App ===
        layoutEmail.setOnClickListener {
            val email = getString(R.string.email)
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
            startActivity(intent)
        }

        // === INSTAGRAM → IG App / Browser ===
        layoutInstagram.setOnClickListener {
            val username = getString(R.string.instagram).removePrefix("@")
            val uri = Uri.parse("http://instagram.com/_u/$username")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.instagram.android")
            try {
                startActivity(intent)
            } catch (e: Exception) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/$username")))
            }
        }

        // === TIKTOK → TikTok App / Browser ===
        layoutTiktok.setOnClickListener {
            val username = getString(R.string.tiktok).removePrefix("@")
            val uri = Uri.parse("https://www.tiktok.com/@$username")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.zhiliaoapp.musically")
            try {
                startActivity(intent)
            } catch (e: Exception) {
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }

        return view
    }
}