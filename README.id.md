<!-- Penukar bahasa -->
[English](README.md) | **Bahasa Indonesia**

# 💵 SIRUPI — Sahabat Inklusif Rupiah Pintar

![Status](https://img.shields.io/badge/status-active-success)
![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)
![TensorFlow](https://img.shields.io/badge/TensorFlow-FF6F00?logo=tensorflow&logoColor=white)
![License](https://img.shields.io/badge/license-MIT-blue)

> Pengenalan uang kertas Rupiah berbasis AI untuk membantu penyandang tunanetra mengidentifikasi nominal uang secara mandiri dan akurat.

## ✨ Ringkasan
SIRUPI (Sahabat Inklusif Rupiah Pintar) adalah aplikasi berbasis kecerdasan buatan yang mendeteksi dan mengenali uang kertas Rupiah secara real-time, lalu menyampaikan nominalnya melalui keluaran suara. Dibuat untuk membantu penyandang disabilitas netra mengenali uang secara mandiri.

Dikembangkan selama Kerja Praktik di SLB Negeri Taruna Mandiri Kuningan.

## 🧩 Struktur Repository
    SIRUPI/
    ├─ Android/          # Aplikasi mobile (Kotlin) yang menjalankan model di perangkat
    └─ MachineLearning/  # Dataset, notebook pelatihan & model hasil ekspor

## 🚀 Fitur
- 📸 Deteksi uang kertas real-time melalui kamera
- 🔊 Umpan balik suara untuk nominal yang terdeteksi
- ♿ Desain mengutamakan aksesibilitas bagi tunanetra
- 📱 Inferensi di perangkat (berfungsi offline)

## 🛠️ Teknologi
- **Mobile:** Kotlin, Android, CameraX, TensorFlow Lite
- **Machine Learning:** Python, Ultralytics YOLOv8n, PyTorch, OpenCV

## 📦 Cara Memulai
Klon repository:

    git clone https://github.com/bagascahyawiguna/SIRUPI.git

Lalu ikuti panduan di tiap folder:
- [Aplikasi Android](Android/README.id.md)
- [Machine Learning](MachineLearning/README.id.md)

## 👤 Penulis
**Bagas Cahyawiguna** — [LinkedIn](https://www.linkedin.com/in/bagas-cahyawiguna-539715285)

## 📄 Lisensi
Dirilis di bawah Lisensi MIT.
