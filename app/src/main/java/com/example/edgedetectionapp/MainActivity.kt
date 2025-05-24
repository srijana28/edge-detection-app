package com.example.edgedetectionapp

import android.graphics.SurfaceTexture
import android.os.Bundle
import android.util.Size
import android.view.TextureView
import android.view.Surface
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var textureView: TextureView
    private lateinit var cameraHelper: CameraHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textureView = TextureView(this)
        setContentView(textureView)

        textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
                cameraHelper = CameraHelper(this@MainActivity, Size(width, height), surface) { frame, w, h ->
                    NativeLib.processEdge(frame, w, h, Surface(surface))
                }
                cameraHelper.start()
            }

            override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}
            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean = true
            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
        }
    }
}
