# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html.
# For more examples on how to use CMake, see https://github.com/android/ndk-samples.

# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

# Declares the project name
project("edgedetection")

# Find Android NDK
if(NOT DEFINED ANDROID_NDK)
    if(DEFINED ENV{ANDROID_NDK_HOME})
        set(ANDROID_NDK $ENV{ANDROID_NDK_HOME})
    else()
        set(ANDROID_NDK $ENV{ANDROID_NDK})
    endif()
endif()

# Add cpufeatures library first (required by OpenCV)
add_library(cpufeatures STATIC
    ${ANDROID_NDK}/sources/android/cpufeatures/cpu-features.c)
target_include_directories(cpufeatures PUBLIC
    ${ANDROID_NDK}/sources/android/cpufeatures)

# Set OpenCV paths
set(OpenCV_DIR "${CMAKE_CURRENT_SOURCE_DIR}/../native-opencv/jni")
set(OpenCV_INCLUDE_DIRS "${OpenCV_DIR}/include")
set(OpenCV_LIBS_DIR "${OpenCV_DIR}/libs/${ANDROID_ABI}")
set(OpenCV_3RDPARTY_LIBS_DIR "${CMAKE_CURRENT_SOURCE_DIR}/../native-opencv/3rdparty/libs/${ANDROID_ABI}")
set(OpenCV_STATIC_LIBS_DIR "${CMAKE_CURRENT_SOURCE_DIR}/../native-opencv/staticlibs/${ANDROID_ABI}")

# Create the native library
add_library(native-lib SHARED
    native-lib.cpp)

# Include directories
target_include_directories(native-lib PRIVATE
    ${OpenCV_INCLUDE_DIRS}
    ${ANDROID_NDK}/sources/android/cpufeatures)

# Link libraries in correct order
target_link_libraries(native-lib
    # OpenCV main libraries
    "${OpenCV_STATIC_LIBS_DIR}/libopencv_features2d.a"
    "${OpenCV_STATIC_LIBS_DIR}/libopencv_imgproc.a"
    "${OpenCV_STATIC_LIBS_DIR}/libopencv_core.a"
    
    # OpenCV HAL and optimization libraries
    "${OpenCV_3RDPARTY_LIBS_DIR}/libkleidicv.a"
    "${OpenCV_3RDPARTY_LIBS_DIR}/libkleidicv_thread.a"
    "${OpenCV_3RDPARTY_LIBS_DIR}/libkleidicv_hal.a"
    "${OpenCV_3RDPARTY_LIBS_DIR}/libtegra_hal.a"
    "${OpenCV_3RDPARTY_LIBS_DIR}/libtbb.a"
    "${OpenCV_3RDPARTY_LIBS_DIR}/libittnotify.a"
    "${OpenCV_3RDPARTY_LIBS_DIR}/libade.a"
    
    # System libraries
    cpufeatures
    android
    log
    z
    dl
    m
    atomic)

