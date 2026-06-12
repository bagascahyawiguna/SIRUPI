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
- Images of Indonesian Rupiah banknotes across denominations (Rp1.000 – Rp100.000).
- Split into training / validation / test sets.
- Labeled in YOLO format (bounding boxes); augmentation: mosaic, rotation, brightness, zoom.

## 🧩 Model
- Ultralytics **YOLOv8n** object-detection model — lightweight and fast for on-device use.
- Output: bounding box + class for each detected banknote nominal.
- Exported to `model.tflite` (or ONNX) for the Android module.

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
    ├─ runs/            # Training runs & weights (best.pt)
    └─ model/           # Exported model (.tflite / .onnx)

## 📈 Results
- Accuracy: _add your final accuracy here_
- Add mAP@50 / mAP@50-95, plus sample detection screenshots.

## 📄 License
MIT License.
