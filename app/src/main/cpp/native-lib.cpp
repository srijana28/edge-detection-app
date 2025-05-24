#include <jni.h>
#include <opencv2/opencv.hpp>
using namespace cv;

extern "C"
JNIEXPORT void JNICALL
Java_com_example_edgedetection_NativeLib_processEdge(JNIEnv *env, jobject thiz,
                                                     jbyteArray input_frame, jint width, jint height,
                                                     jobject surface) {
    jbyte *frame_data = env->GetByteArrayElements(input_frame, nullptr);
    Mat yuv(height + height / 2, width, CV_8UC1, (unsigned char *) frame_data);
    Mat bgr, gray, edge;

    cvtColor(yuv, bgr, COLOR_YUV2BGR_NV21);
    cvtColor(bgr, gray, COLOR_BGR2GRAY);
    Canny(gray, edge, 50, 150);

    Mat rgba;
    cvtColor(edge, rgba, COLOR_GRAY2RGBA);

    ANativeWindow *window = ANativeWindow_fromSurface(env, surface);
    ANativeWindow_setBuffersGeometry(window, width, height, WINDOW_FORMAT_RGBA_8888);

    ANativeWindow_Buffer buffer;
    if (ANativeWindow_lock(window, &buffer, nullptr) == 0) {
        Mat dst(buffer.height, buffer.width, CV_8UC4, buffer.bits);
        rgba.copyTo(dst);
        ANativeWindow_unlockAndPost(window);
    }
    ANativeWindow_release(window);

    env->ReleaseByteArrayElements(input_frame, frame_data, 0);
}