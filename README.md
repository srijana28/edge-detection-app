# EdgeDetection - Android OpenCV + OpenGL Real-Time Edge Detection Viewer

## Overview

This is a minimal Android app that captures real-time camera frames, processes them using OpenCV C++ (via JNI), and renders the processed output with OpenGL ES 2.0. The main processing includes edge detection (Canny) applied in native C++ code, demonstrating smooth real-time performance.

## Features

- 📸 **Real-time Camera Feed Integration**  
  Captures camera frames using Android's `TextureView` and Camera2 API.

- 🔁 **Frame Processing via OpenCV (C++)**  
  Frames are sent to native C++ code through JNI. Canny edge detection is applied using OpenCV.

- 🎨 **OpenGL ES 2.0 Rendering**  
  The processed frames are rendered as textures in OpenGL ES for efficient display.

- Toggle between raw camera feed and edge detection
- FPS counter
- Smooth performance optimization

## Technical Stack

- Android SDK (Java/Kotlin)
- NDK (Native Development Kit)
- OpenCV 4.x
- OpenGL ES 2.0
- Camera2 API
- JNI (Java ↔ C++ communication)

## Project Structure

```
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
```

## Setup Instructions

### Prerequisites

- Android Studio (Arctic Fox or newer recommended)  
- Android NDK (side-by-side version 21.4.7075529 recommended)  
- OpenCV Android SDK (placed in `app/src/main/native-opencv/` folder)  
- Gradle 7+ and Android Gradle Plugin compatible with your Android Studio

### Steps

1. **Clone the repository:**
   ```bash
   git clone https://github.com/srijana28/edge-detection-app.git
   cd edge-detection-app
   ```

2. **Download and place OpenCV Android SDK:**
   - Download OpenCV Android SDK from https://opencv.org/releases/
   - Copy `sdk/native/libs/*` to `app/src/main/jniLibs/`
   - Copy `sdk/native/jni/*` to `app/src/main/native-opencv/jni/`

3. **Open the project in Android Studio**

4. **Build and run on a device or emulator with camera support**

## Architecture

The app follows a modular architecture:

1. Camera Module (Camera2 API)
   - Handles camera preview and frame capture
   - Provides frames to the native processing module

2. Native Processing (OpenCV)
   - Processes frames using OpenCV in C++
   - Implements Canny edge detection
   - Communicates with Java through JNI

3. Rendering Module (OpenGL ES)
   - Displays processed frames using OpenGL ES
   - Handles texture updates and rendering
   - Provides smooth display performance

## Performance

The app is optimized for real-time processing:
- Uses OpenCV's native implementation for edge detection
- Efficient texture handling in OpenGL ES
- Maintains frame rate through proper resource management
- Displays current FPS in the UI

## Requirements

- Android Studio Arctic Fox or newer
- Android SDK 21 or higher
- NDK 21 or higher
- OpenCV 4.x Android SDK
- Device with OpenGL ES 2.0 support

## License

MIT License © Srijana

## Contact Information

You can contact me at srijanagautam595@gmail.com for any feedback.
