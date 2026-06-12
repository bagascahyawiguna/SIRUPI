# 📊 SIRUPI Dataset

Dataset gambar uang kertas Rupiah yang digunakan untuk melatih model deteksi **YOLOv8n** pada proyek SIRUPI.

## 🔗 Tautan Dataset

| Bagian | Tautan |
| --- | --- |
| 🖼️ Images | [Google Drive — Images](https://drive.google.com/drive/folders/1ig_wLhigCQMV4hiImo04yRG5SBpQe7_q?usp=sharing) |
| 🏷️ Labels | [Google Drive — Labels](https://drive.google.com/drive/folders/1mwfMRrpMHi4b95w1J1l-xS9xOEssTIsJ?usp=sharing) |

## 🧩 Kelas (7)

`1k` · `2k` · `5k` · `10k` · `20k` · `50k` · `100k`  
(Rp1.000 – Rp100.000)

## 📈 Pembagian Data

| Split | Jumlah Gambar |
| --- | --- |
| Train | 8.369 |
| Validation | 1.097 |
| Test | 988 |
| **Total** | **10.454** |

## 📁 Format & Struktur

Label menggunakan **format YOLO** (file `.txt` per gambar, berisi `class x_center y_center width height` ternormalisasi).

    SIRUPI_YOLO/
    ├─ images/
    │  ├─ train/
    │  ├─ val/
    │  └─ test/
    └─ labels/
       ├─ train/
       ├─ val/
       └─ test/

## ⬇️ Cara Menggunakan

1. Unduh folder **images** dan **labels** dari tautan di atas.
2. Susun mengikuti struktur folder di atas.
3. Sesuaikan path pada `data.yaml`, lalu mulai pelatihan:

       yolo detect train data=data.yaml model=yolov8n.pt epochs=50 imgsz=640
