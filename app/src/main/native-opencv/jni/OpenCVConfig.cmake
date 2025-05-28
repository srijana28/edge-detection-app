# ===================================================================================
#  The OpenCV CMake configuration file
#
#             ** File generated automatically, do not modify **
#
#  Usage from an external project:
#    In your CMakeLists.txt, add these lines:
#
#    find_package(OpenCV REQUIRED)
#    target_include_directories(MY_TARGET_NAME PRIVATE ${OpenCV_INCLUDE_DIRS})
#    target_link_libraries(MY_TARGET_NAME ${OpenCV_LIBS})
#
# ===================================================================================

# Get the current directory
get_filename_component(OpenCV_CONFIG_PATH "${CMAKE_CURRENT_LIST_FILE}" PATH)

# Set OpenCV paths
set(OpenCV_INSTALL_PATH "${OpenCV_CONFIG_PATH}/..")
set(OpenCV_INCLUDE_DIRS "${OpenCV_INSTALL_PATH}/include")
set(OpenCV_LIB_DIRS "${OpenCV_INSTALL_PATH}/libs/${ANDROID_ABI}")
set(OpenCV_3RDPARTY_LIB_DIRS "${OpenCV_INSTALL_PATH}/3rdparty/libs/${ANDROID_ABI}")

# Set OpenCV version
set(OpenCV_VERSION 4.11.0)
set(OpenCV_VERSION_MAJOR 4)
set(OpenCV_VERSION_MINOR 11)
set(OpenCV_VERSION_PATCH 0)

# Ensure cpufeatures is available
if(NOT TARGET cpufeatures)
    message(FATAL_ERROR "cpufeatures target must be defined before including OpenCV")
endif()

# Include architecture-specific configuration
if(ANDROID_ABI STREQUAL "armeabi-v7a")
    include("${OpenCV_CONFIG_PATH}/abi-armeabi-v7a/OpenCVModules.cmake")
elseif(ANDROID_ABI STREQUAL "arm64-v8a")
    include("${OpenCV_CONFIG_PATH}/abi-arm64-v8a/OpenCVModules.cmake")
elseif(ANDROID_ABI STREQUAL "x86")
    include("${OpenCV_CONFIG_PATH}/abi-x86/OpenCVModules.cmake")
elseif(ANDROID_ABI STREQUAL "x86_64")
    include("${OpenCV_CONFIG_PATH}/abi-x86_64/OpenCVModules.cmake")
endif()

# Set OpenCV libraries
set(OpenCV_LIBS opencv_core opencv_imgproc opencv_features2d opencv_java4)
