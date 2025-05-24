 ## EdgeDetection - Android OpenCV + OpenGL Real-Time Edge Detection Viewer

## Overview

This is a minimal Android app that captures real-time camera frames, processes them using OpenCV C++ (via JNI), and renders the processed output with OpenGL ES 2.0. The main processing includes edge detection (Canny) applied in native C++ code, demonstrating smooth real-time performance.

## Features Implemented

- 📸 **Real-time Camera Feed Integration**  
  Captures camera frames using Android's `TextureView` and Camera2 API.

- 🔁 **Frame Processing via OpenCV (C++)**  
  Frames are sent to native C++ code through JNI. Canny edge detection is applied using OpenCV.

- 🎨 **OpenGL ES 2.0 Rendering**  
  The processed frames are rendered as textures in OpenGL ES for efficient display.

- 🧩 **Modular Architecture**  
  - `/app/src/main/java` – Kotlin code for camera setup and UI  
  - `/app/src/main/cpp` – Native C++ code for OpenCV processing  
  - `/app/src/main/java/com/example/edgedetection/gl` – OpenGL rendering classes


## Setup Instructions

### Prerequisites

- Android Studio (Arctic Fox or newer recommended)  
- Android NDK (side-by-side version 21.4.7075529 recommended)  
- OpenCV Android SDK (placed in `app/src/main/native-opencv/` folder)  
- Gradle 7+ and Android Gradle Plugin compatible with your Android Studio

### Steps

1. **Clone the repo:**
   ```bash
   git clone https://github.com/srijana28/edge-detection-app.git
   cd edge-detection-app
Download and place OpenCV Android SDK:

Download OpenCV Android SDK from OpenCV official site

Unzip and copy the sdk/native folder content into:

app/src/main/native-opencv/
Copy OpenCV native libs:

Copy .so files from:

app/src/main/native-opencv/libs/<ABI>/
to:

app/src/main/jniLibs/<ABI>/
for each ABI you want to support (e.g., armeabi-v7a, arm64-v8a).

2. **Open the project in Android Studio.**

3. **Build and run on a device or emulator with camera support.**

### Architecture Overview
JNI and Frame Flow
Camera frames captured in Kotlin (MainActivity.kt) using TextureView and Camera2 API.

Each frame is converted to a suitable format and passed to native C++ via JNI.

Native code (native-lib.cpp) uses OpenCV to apply Canny edge detection.

The processed image is converted to an OpenGL texture.

OpenGL ES renderer (OpenGLRenderer.kt) renders the processed texture on screen in real-time.

### Module Structure
app/
 ├─ src/
 │   ├─ main/
 │   │   ├─ java/com/example/edgedetection/      # Kotlin app and GL rendering code
 │   │   ├─ cpp/                                # Native C++ OpenCV processing
 │   │   ├─ native-opencv/                      # OpenCV Android SDK
 │   │   └─ jniLibs/                            # Native .so libs for packaging
 │   ├─ androidTest/                            # Instrumentation tests
 │   └─ test/                                   # Unit tests
 ├─ build.gradle                                # Module build config
 └─ CMakeLists.txt                             # Native build config
### License
MIT License © Srijana

### Contact Information
You can cantact me on my email address srijanagautam595@gmail.com for any feedback.
