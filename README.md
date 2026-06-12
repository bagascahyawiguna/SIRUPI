<!-- Language switch -->
**English** | [Bahasa Indonesia](README.id.md)

# 💵 SIRUPI — Sahabat Inklusif Rupiah Pintar

![Status](https://img.shields.io/badge/status-active-success)
![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)
![TensorFlow](https://img.shields.io/badge/TensorFlow-FF6F00?logo=tensorflow&logoColor=white)
![License](https://img.shields.io/badge/license-MIT-blue)

> AI-powered Indonesian Rupiah banknote recognition that helps blind and visually impaired users identify money independently and accurately.

## ✨ Overview
SIRUPI (Sahabat Inklusif Rupiah Pintar) is an AI-based application that detects and recognizes Indonesian paper currency in real time and announces the nominal value through audio output. It was built to help people with visual disabilities identify money on their own.

Developed during an internship (Kerja Praktik) at SLB Negeri Taruna Mandiri Kuningan.

## 🧩 Repository Structure
    SIRUPI/
    ├─ Android/          # Mobile app (Kotlin) that runs the model on-device
    └─ MachineLearning/  # Dataset, training notebooks & exported model

## 🚀 Features
- 📸 Real-time banknote detection via camera
- 🔊 Audio feedback of the detected nominal value
- ♿ Accessibility-first design for visually impaired users
- 📱 On-device inference (works offline)

## 🛠️ Tech Stack
- **Mobile:** Kotlin, Android, CameraX, TensorFlow Lite
- **Machine Learning:** Python, Ultralytics YOLOv8n, PyTorch, OpenCV

## 📦 Getting Started
Clone the repository:

    git clone https://github.com/bagascahyawiguna/SIRUPI.git

Then follow the setup guide inside each folder:
- [Android app](Android/README.md)
- [Machine Learning](MachineLearning/README.md)

## 👤 Author
**Bagas Cahyawiguna** — [LinkedIn](https://www.linkedin.com/in/bagas-cahyawiguna-539715285)

## 📄 License
Released under the MIT License.
