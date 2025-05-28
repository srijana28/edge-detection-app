#include <jni.h>
#include <string>
#include <opencv2/core.hpp>
#include <opencv2/imgproc.hpp>
#include <android/log.h>

#define TAG "NativeLib"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

extern "C" {

JNIEXPORT jbyteArray JNICALL
Java_com_example_edgedetectionapp_MainActivity_processFrame(
        JNIEnv *env,
        jobject /* this */,
        jbyteArray input,
        jint width,
        jint height,
        jboolean useEdgeDetection) {

    // Convert input byte array to Mat
    jbyte *inputBuffer = env->GetByteArrayElements(input, nullptr);
    cv::Mat inputMat(height, width, CV_8UC1, inputBuffer);
    cv::Mat outputMat;

    if (useEdgeDetection) {
        // Apply Gaussian blur to reduce noise
        cv::GaussianBlur(inputMat, outputMat, cv::Size(5, 5), 1.5);

        // Apply Canny edge detection
        cv::Canny(outputMat, outputMat, 80, 100);
    } else {
        outputMat = inputMat.clone();
    }

    // Convert the processed image back to byte array
    jbyteArray output = env->NewByteArray(width * height);
    env->SetByteArrayRegion(output, 0, width * height, (jbyte *)outputMat.data);

    // Release the input buffer
    env->ReleaseByteArrayElements(input, inputBuffer, 0);

    return output;
}

} // extern "C"