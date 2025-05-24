package com.example.edgedetectionapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.ImageReader
import android.os.Handler
import android.os.HandlerThread
import android.util.Size
import android.view.Surface

class CameraHelper(
    private val context: Context,
    private val size: Size,
    private val texture: SurfaceTexture,
    private val onFrame: (ByteArray, Int, Int) -> Unit
) {
    private lateinit var cameraDevice: CameraDevice
    private lateinit var captureSession: CameraCaptureSession
    private lateinit var reader: ImageReader
    private var handler: Handler

    init {
        val thread = HandlerThread("CameraThread").apply { start() }
        handler = Handler(thread.looper)
    }

    @SuppressLint("MissingPermission")
    fun start() {
        val manager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val id = manager.cameraIdList.first()
        manager.openCamera(id, object : CameraDevice.StateCallback() {
            override fun onOpened(device: CameraDevice) {
                cameraDevice = device
                startPreview()
            }

            override fun onDisconnected(device: CameraDevice) {
                device.close()
            }

            override fun onError(device: CameraDevice, error: Int) {
                device.close()
            }
        }, handler)
    }

    private fun startPreview() {
        reader = ImageReader.newInstance(size.width, size.height, ImageFormat.YUV_420_888, 2)
        reader.setOnImageAvailableListener({ reader ->
            val image = reader.acquireLatestImage() ?: return@setOnImageAvailableListener
            val buffer = image.planes[0].buffer
            val data = ByteArray(buffer.remaining())
            buffer.get(data)
            onFrame(data, image.width, image.height)
            image.close()
        }, handler)

        val surface = Surface(texture)
        val targets = listOf(surface, reader.surface)

        val requestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW).apply {
            addTarget(surface)
            addTarget(reader.surface)
        }

        cameraDevice.createCaptureSession(targets, object : CameraCaptureSession.StateCallback() {
            override fun onConfigured(session: CameraCaptureSession) {
                captureSession = session
                captureSession.setRepeatingRequest(requestBuilder.build(), null, handler)
            }

            override fun onConfigureFailed(session: CameraCaptureSession) {}
        }, handler)
    }
}
