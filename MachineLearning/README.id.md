[English](README.md) | **Bahasa Indonesia**

# 🧠 SIRUPI — Machine Learning

![Python](https://img.shields.io/badge/Python-3776AB?logo=python&logoColor=white)
![TensorFlow](https://img.shields.io/badge/TensorFlow-FF6F00?logo=tensorflow&logoColor=white)
![Ultralytics](https://img.shields.io/badge/Ultralytics-YOLOv8-blueviolet)
![OpenCV](https://img.shields.io/badge/OpenCV-5C3EE8?logo=opencv&logoColor=white)

> Dataset, alur pelatihan, dan model hasil ekspor untuk mengenali uang kertas Rupiah.

## ✨ Ringkasan
Modul ini berisi alur kerja machine learning di balik SIRUPI: penyiapan data, pelatihan model, evaluasi, hingga ekspor model ke TensorFlow Lite untuk aplikasi Android.

## 📊 Dataset
- Gambar uang kertas Rupiah berbagai pecahan (Rp1.000 – Rp100.000).
- Dibagi menjadi data latih / validasi / uji.
- Diberi label format YOLO (bounding box); augmentasi: mosaic, rotasi, kecerahan, zoom.

## 🧩 Model
- Model deteksi objek Ultralytics **YOLOv8n** — ringan dan cepat untuk perangkat.
- Keluaran: bounding box + kelas untuk tiap nominal uang yang terdeteksi.
- Diekspor menjadi `model.tflite` (atau ONNX) untuk modul Android.

## ▶️ Cara Menjalankan
Pasang Ultralytics:

    pip install ultralytics

Latih model:

    yolo detect train data=data.yaml model=yolov8n.pt epochs=100 imgsz=640

Jalankan inferensi:

    yolo detect predict model=runs/detect/train/weights/best.pt source=test.jpg

## 📁 Struktur Proyek
    MachineLearning/
    ├─ dataset/         # Gambar + label (format YOLO)
    ├─ data.yaml        # Konfigurasi dataset untuk YOLOv8
    ├─ runs/            # Hasil pelatihan & bobot (best.pt)
    └─ model/           # Model hasil ekspor (.tflite / .onnx)

## 📈 Hasil
- Akurasi: _tambahkan akurasi akhirmu di sini_
- Tambahkan mAP@50 / mAP@50-95, serta tangkapan layar contoh deteksi.

## 📄 Lisensi
Lisensi MIT.
