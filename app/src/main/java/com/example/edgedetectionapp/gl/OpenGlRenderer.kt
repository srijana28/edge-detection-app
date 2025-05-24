package com.example.edgedetectionapp.gl

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class OpenGLRenderer : GLSurfaceView.Renderer {

    private val vertexShaderCode = """
        attribute vec4 vPosition;
        attribute vec2 vTexCoord;
        varying vec2 texCoord;
        uniform mat4 uMVPMatrix;
        void main() {
            gl_Position = uMVPMatrix * vPosition;
            texCoord = vTexCoord;
        }
    """

    private val fragmentShaderCode = """
        precision mediump float;
        varying vec2 texCoord;
        uniform sampler2D uTexture;
        void main() {
            gl_FragColor = texture2D(uTexture, texCoord);
        }
    """

    private val vertices = floatArrayOf(
        // X, Y,      U, V
        -1f, 1f,     0f, 0f,   // top-left
        -1f, -1f,    0f, 1f,   // bottom-left
        1f, 1f,      1f, 0f,   // top-right
        1f, -1f,     1f, 1f    // bottom-right
    )

    private var vertexBuffer = GlUtils.createFloatBuffer(vertices)

    private var program = 0
    private var positionHandle = 0
    private var texCoordHandle = 0
    private var textureUniformHandle = 0
    private var mvpMatrixHandle = 0

    private val mvpMatrix = FloatArray(16)

    var textureId: Int = 0  // Set this texture ID from your native texture

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(0f, 0f, 0f, 1f)

        val vertexShader = GlUtils.loadShader(GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = GlUtils.loadShader(GL_FRAGMENT_SHADER, fragmentShaderCode)

        program = glCreateProgram().also {
            glAttachShader(it, vertexShader)
            glAttachShader(it, fragmentShader)
            glLinkProgram(it)
        }

        Matrix.setIdentityM(mvpMatrix, 0)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)

        glUseProgram(program)

        // Prepare vertex data
        vertexBuffer.position(0)
        positionHandle = glGetAttribLocation(program, "vPosition")
        glEnableVertexAttribArray(positionHandle)
        glVertexAttribPointer(positionHandle, 2, GL_FLOAT, false, 4 * 4, vertexBuffer) // stride: 4 floats per vertex * 4 bytes

        vertexBuffer.position(2)
        texCoordHandle = glGetAttribLocation(program, "vTexCoord")
        glEnableVertexAttribArray(texCoordHandle)
        glVertexAttribPointer(texCoordHandle, 2, GL_FLOAT, false, 4 * 4, vertexBuffer)

        // Set the texture uniform
        textureUniformHandle = glGetUniformLocation(program, "uTexture")
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, textureId)
        glUniform1i(textureUniformHandle, 0)

        // Set the MVP matrix
        mvpMatrixHandle = glGetUniformLocation(program, "uMVPMatrix")
        glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0)

        // Draw two triangles (triangle strip)
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4)

        // Disable vertex arrays
        glDisableVertexAttribArray(positionHandle)
        glDisableVertexAttribArray(texCoordHandle)
    }
}
