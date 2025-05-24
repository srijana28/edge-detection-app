package com.example.edgedetectionapp

import android.view.Surface

object NativeLib {
    init {
        System.loadLibrary("opencv_java4") // Match your OpenCV version
        System.loadLibrary("native-lib")
    }

    external fun processEdge(frameData: ByteArray, width: Int, height: Int, surface: Surface)
}
