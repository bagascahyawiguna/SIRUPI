**English** | [Bahasa Indonesia](README.id.md)

# 📱 SIRUPI — Android App

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=white)
![TensorFlow Lite](https://img.shields.io/badge/TFLite-FF6F00?logo=tensorflow&logoColor=white)

> Mobile client of SIRUPI that runs the trained model on-device and reads out the detected Rupiah nominal.

## ✨ Overview
This module is the Android application of SIRUPI. It captures the camera stream, runs a YOLOv8n model (exported to TensorFlow Lite) on-device, and announces the recognized banknote value through Text-to-Speech — fully usable offline.

## 🚀 Features
- Real-time camera detection with CameraX
- On-device inference using TensorFlow Lite
- Text-to-Speech audio output
- Accessible, high-contrast, large-tap-target UI

## 🛠️ Tech Stack
- Kotlin
- Android SDK, CameraX
- TensorFlow Lite
- Android Text-to-Speech (TTS)

## ▶️ How to Run
1. Open the `Android/` folder in **Android Studio**.
2. Let Gradle sync the dependencies.
3. Connect a device or start an emulator (camera required).
4. Click **Run**.

## 📁 Project Structure
    Android/
    ├─ app/src/main/java/   # Kotlin source code
    ├─ app/src/main/assets/ # model.tflite & labels
    └─ app/src/main/res/    # Layouts & resources

## 📄 License
MIT License.
