[English](README.md) | **Bahasa Indonesia**

# 📱 SIRUPI — Aplikasi Android

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=white)
![TensorFlow Lite](https://img.shields.io/badge/TFLite-FF6F00?logo=tensorflow&logoColor=white)

> Klien mobile SIRUPI yang menjalankan model terlatih di perangkat dan membacakan nominal Rupiah yang terdeteksi.

## ✨ Ringkasan
Modul ini adalah aplikasi Android dari SIRUPI. Aplikasi menangkap aliran kamera, menjalankan model YOLOv8n (hasil ekspor ke TensorFlow Lite) di perangkat, lalu menyampaikan nominal uang yang dikenali melalui Text-to-Speech — sepenuhnya dapat digunakan secara offline.

## 🚀 Fitur
- Deteksi kamera real-time dengan CameraX
- Inferensi di perangkat menggunakan TensorFlow Lite
- Keluaran suara Text-to-Speech
- Antarmuka aksesibel: kontras tinggi & area sentuh besar

## 🛠️ Teknologi
- Kotlin
- Android SDK, CameraX
- TensorFlow Lite
- Android Text-to-Speech (TTS)

## ▶️ Cara Menjalankan
1. Buka folder `Android/` di **Android Studio**.
2. Tunggu Gradle menyinkronkan dependensi.
3. Hubungkan perangkat atau jalankan emulator (perlu kamera).
4. Klik **Run**.

## 📁 Struktur Proyek
    Android/
    ├─ app/src/main/java/   # Kode sumber Kotlin
    ├─ app/src/main/assets/ # model.tflite & label
    └─ app/src/main/res/    # Layout & resource

## 📄 Lisensi
Lisensi MIT.
