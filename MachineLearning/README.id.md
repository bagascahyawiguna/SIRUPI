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
- **7 kelas** uang kertas Rupiah: `1k`, `2k`, `5k`, `10k`, `20k`, `50k`, `100k` (Rp1.000 – Rp100.000).
- Pembagian: **8.369 latih / 1.097 validasi / 988 uji** gambar.
- Diberi label format YOLO — tervalidasi dengan **0 Class ID tidak valid** dan **0 BBox tidak valid**.
- Augmentasi: mosaic, rotasi, kecerahan, zoom.

## 🧩 Model
- Model deteksi objek Ultralytics **YOLOv8n** — ringan dan cepat untuk perangkat.
- Arsitektur: 72 layer (fused), **~3,0 juta parameter**, **8,1 GFLOPs**.
- Keluaran: bounding box + kelas untuk tiap nominal uang yang terdeteksi.
- Diekspor menjadi `model.tflite` (atau ONNX) untuk modul Android.

### 🖥️ Lingkungan Pelatihan
- Ultralytics 8.3.240 · Python 3.12.12 · PyTorch 2.9.0 (CUDA)
- GPU: NVIDIA Tesla T4 (15 GB)

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
    ├─ SIRUPI_YOLOV8N.ipynb # Colab Notebook Setup
    ├─ runs/            # Hasil pelatihan & bobot (best.pt)
    └─ model/           # Model hasil ekspor (.tflite / .onnx)

## 📈 Hasil (Data Uji)
Dievaluasi pada **988 gambar uji**:

| Kelas | Precision | Recall | mAP@50 | mAP@50-95 |
| --- | --- | --- | --- | --- |
| **Semua** | 0.994 | 0.992 | 0.995 | 0.959 |
| Rp1.000 | 0.996 | 1.000 | 0.995 | 0.963 |
| Rp2.000 | 0.990 | 0.977 | 0.994 | 0.956 |
| Rp5.000 | 1.000 | 0.994 | 0.995 | 0.970 |
| Rp10.000 | 1.000 | 0.993 | 0.995 | 0.967 |
| Rp20.000 | 0.998 | 0.993 | 0.995 | 0.934 |
| Rp50.000 | 0.986 | 0.985 | 0.995 | 0.947 |
| Rp100.000 | 0.989 | 1.000 | 0.995 | 0.972 |

**Kecepatan inferensi:** ~3,9 ms/gambar di Tesla T4 (preprocess 1,7 ms · inferensi 3,9 ms · postprocess 1,5 ms).

## 📄 Lisensi
Lisensi MIT.
