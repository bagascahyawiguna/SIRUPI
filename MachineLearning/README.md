**English** | [Bahasa Indonesia](README.id.md)

# 🧠 SIRUPI — Machine Learning

![Python](https://img.shields.io/badge/Python-3776AB?logo=python&logoColor=white)
![TensorFlow](https://img.shields.io/badge/TensorFlow-FF6F00?logo=tensorflow&logoColor=white)
![Ultralytics](https://img.shields.io/badge/Ultralytics-YOLOv8-blueviolet)
![OpenCV](https://img.shields.io/badge/OpenCV-5C3EE8?logo=opencv&logoColor=white)

> Dataset, training pipeline and exported model used to recognize Indonesian Rupiah banknotes.

## ✨ Overview
This module contains the machine learning workflow behind SIRUPI: data preparation, model training, evaluation, and exporting the model to TensorFlow Lite for the Android app.

## 📊 Dataset
- **7 classes** of Indonesian Rupiah banknotes: `1k`, `2k`, `5k`, `10k`, `20k`, `50k`, `100k` (Rp1.000 – Rp100.000).
- Split: **8,369 train / 1,097 val / 988 test** images.
- Labeled in YOLO format — validated with **0 invalid class IDs** and **0 invalid bounding boxes**.
- Augmentation: mosaic, rotation, brightness, zoom.

## 🧩 Model
- Ultralytics **YOLOv8n** object-detection model — lightweight and fast for on-device use.
- Architecture: 72 layers (fused), **~3.0M parameters**, **8.1 GFLOPs**.
- Output: bounding box + class for each detected banknote nominal.
- Exported to `model.tflite` (or ONNX) for the Android module.

### 🖥️ Training Environment
- Ultralytics 8.3.240 · Python 3.12.12 · PyTorch 2.9.0 (CUDA)
- GPU: NVIDIA Tesla T4 (15 GB)

## ▶️ How to Run
Install Ultralytics:

    pip install ultralytics

Train the model:

    yolo detect train data=data.yaml model=yolov8n.pt epochs=100 imgsz=640

Run inference:

    yolo detect predict model=runs/detect/train/weights/best.pt source=test.jpg

## 📁 Project Structure
    MachineLearning/
    ├─ dataset/         # Images + labels (YOLO format)
    ├─ data.yaml        # Dataset config for YOLOv8
    ├─ SIRUPI_YOLOV8N.ipynb # Colab Notebook Setup
    ├─ runs/            # Training runs & weights (best.pt)
    └─ model/           # Exported model (.tflite / .onnx)

## 📈 Results (Test Set)
Evaluated on **988 test images**:

| Class | Precision | Recall | mAP@50 | mAP@50-95 |
| --- | --- | --- | --- | --- |
| **All** | 0.994 | 0.992 | 0.995 | 0.959 |
| Rp1.000 | 0.996 | 1.000 | 0.995 | 0.963 |
| Rp2.000 | 0.990 | 0.977 | 0.994 | 0.956 |
| Rp5.000 | 1.000 | 0.994 | 0.995 | 0.970 |
| Rp10.000 | 1.000 | 0.993 | 0.995 | 0.967 |
| Rp20.000 | 0.998 | 0.993 | 0.995 | 0.934 |
| Rp50.000 | 0.986 | 0.985 | 0.995 | 0.947 |
| Rp100.000 | 0.989 | 1.000 | 0.995 | 0.972 |

**Inference speed:** ~3.9 ms/image on Tesla T4 (preprocess 1.7 ms · inference 3.9 ms · postprocess 1.5 ms).

## 📄 License
MIT License.
