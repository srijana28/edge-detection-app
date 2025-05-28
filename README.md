# Edge Detection App

A real-time edge detection Android application that demonstrates the integration of OpenCV, OpenGL ES, and Android Camera API.

## Features

- Real-time camera preview
- OpenCV-based Canny edge detection
- OpenGL ES rendering
- Toggle between raw camera feed and edge detection
- FPS counter
- Smooth performance optimization

## Technical Stack

- Android SDK (Java/Kotlin)
- NDK (Native Development Kit)
- OpenCV 4.x
- OpenGL ES 2.0
- CameraX API
- JNI (Java ↔ C++ communication)

## Project Structure

```
/app
  /src
    /main
      /cpp           # Native C++ code
      /java         # Kotlin/Java code
      /res          # Resources
      /jniLibs      # Native libraries
      /native-opencv # OpenCV SDK files
```

## Setup Instructions

1. Clone the repository
2. Download OpenCV Android SDK from https://opencv.org/releases/
3. Copy the following files from the OpenCV SDK:
   - Copy `sdk/native/libs/*` to `app/src/main/jniLibs/`
   - Copy `sdk/native/jni/*` to `app/src/main/native-opencv/jni/`
4. Open the project in Android Studio
5. Build and run the project

## Architecture

The app follows a modular architecture:

1. Camera Module (CameraX)
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

This project is licensed under the MIT License - see the LICENSE file for details.