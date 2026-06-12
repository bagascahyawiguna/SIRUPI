# 🧩 SIRUPI — Model

Model deteksi objek **YOLOv8n** hasil pelatihan proyek SIRUPI yang sudah diekspor ke **TensorFlow Lite** untuk dijalankan di perangkat Android.

## 📦 File Model

| File | Format | Keterangan |
| --- | --- | --- |
| `best_float32.tflite` | TFLite (FP32) | Bobot terbaik hasil training, presisi penuh (float32) |

## ⚙️ Spesifikasi Model

- **Arsitektur:** YOLOv8n (Ultralytics) — 72 layer (fused), ~3,0 juta parameter, 8,1 GFLOPs
- **Input size:** 640 × 640
- **Jumlah kelas:** 7 — `1k`, `2k`, `5k`, `10k`, `20k`, `50k`, `100k`
- **Output:** bounding box + kelas + confidence untuk tiap nominal uang

## 📈 Performa (Data Uji)

| Metrik | Nilai |
| --- | --- |
| mAP@50 | 0.995 |
| mAP@50-95 | 0.959 |
| Precision | 0.994 |
| Recall | 0.992 |

Kecepatan inferensi ~3,9 ms/gambar (Tesla T4).

## ▶️ Cara Menggunakan

**Python (Ultralytics):**

    from ultralytics import YOLO

    model = YOLO("best_float32.tflite")
    results = model("test.jpg")
    results[0].show()

**Android:** salin `best_float32.tflite` ke `app/src/main/assets/`, lalu muat menggunakan TensorFlow Lite Interpreter (lihat modul `Android/`).

## 🔄 Mengekspor Ulang Model

Untuk menghasilkan kembali file `.tflite` dari bobot `best.pt`:

    yolo export model=runs/detect/train/weights/best.pt format=tflite imgsz=640

## 📄 Lisensi

Lisensi MIT — lihat README repo utama.
