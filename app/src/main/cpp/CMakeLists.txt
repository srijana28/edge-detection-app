# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html.
# For more examples on how to use CMake, see https://github.com/android/ndk-samples.

# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

# Declares the project name. The project name can be accessed via ${ PROJECT_NAME},
# Since this is the top level CMakeLists.txt, the project name is also accessible
# with ${CMAKE_PROJECT_NAME} (both CMake variables are in-sync within the top level
# build script scope).
project("edgedetectionapp")

# Path to OpenCVConfig.cmake relative to this CMakeLists.txt (app/src/main/cpp)
set(OpenCV_DIR "${CMAKE_SOURCE_DIR}/../native-opencv/jni")

# Find OpenCV package
find_package(OpenCV REQUIRED)

# Include OpenCV headers
include_directories(${OpenCV_INCLUDE_DIRS})

# Declare your native library and source files
add_library(native-lib SHARED native-lib.cpp)

# Link libraries: OpenCV + Android log and android system libs
target_link_libraries(native-lib
        ${OpenCV_LIBS}
        log
        android
)

