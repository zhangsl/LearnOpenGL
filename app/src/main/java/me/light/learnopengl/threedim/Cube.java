package me.light.learnopengl.threedim;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.GLUtils;
import me.light.learnopengl.Utils;
import me.light.learnopengl.shape.Shape;

/**
 * Created by shangjie on 2018/11/5.
 */

public class Cube extends Shape {
    private final float vertexCoords[] = {
        // 顶点坐标             //纹理坐标   //顶点法向量，用于计算光照漫反射
        -0.5f, -0.5f, -0.5f,  0.0f, 0.0f, 0.0f,  0.0f, -1.0f,
        0.5f, -0.5f, -0.5f,  1.0f, 0.0f, 0.0f,  0.0f, -1.0f,
        0.5f,  0.5f, -0.5f,  1.0f, 1.0f, 0.0f,  0.0f, -1.0f,
        0.5f,  0.5f, -0.5f,  1.0f, 1.0f, 0.0f,  0.0f, -1.0f,
        -0.5f,  0.5f, -0.5f,  0.0f, 1.0f, 0.0f,  0.0f, -1.0f,
        -0.5f, -0.5f, -0.5f,  0.0f, 0.0f, 0.0f,  0.0f, -1.0f,

        -0.5f, -0.5f,  0.5f,  0.0f, 0.0f, 0.0f,  0.0f, 1.0f,
        0.5f, -0.5f,  0.5f,  1.0f, 0.0f, 0.0f,  0.0f, 1.0f,
        0.5f,  0.5f,  0.5f,  1.0f, 1.0f, 0.0f,  0.0f, 1.0f,
        0.5f,  0.5f,  0.5f,  1.0f, 1.0f, 0.0f,  0.0f, 1.0f,
        -0.5f,  0.5f,  0.5f,  0.0f, 1.0f, 0.0f,  0.0f, 1.0f,
        -0.5f, -0.5f,  0.5f,  0.0f, 0.0f, 0.0f,  0.0f, 1.0f,

        -0.5f,  0.5f,  0.5f,  1.0f, 0.0f, -1.0f,  0.0f,  0.0f,
        -0.5f,  0.5f, -0.5f,  1.0f, 1.0f, -1.0f,  0.0f,  0.0f,
        -0.5f, -0.5f, -0.5f,  0.0f, 1.0f, -1.0f,  0.0f,  0.0f,
        -0.5f, -0.5f, -0.5f,  0.0f, 1.0f, -1.0f,  0.0f,  0.0f,
        -0.5f, -0.5f,  0.5f,  0.0f, 0.0f, -1.0f,  0.0f,  0.0f,
        -0.5f,  0.5f,  0.5f,  1.0f, 0.0f, -1.0f,  0.0f,  0.0f,

        0.5f,  0.5f,  0.5f,  1.0f, 0.0f, 1.0f,  0.0f,  0.0f,
        0.5f,  0.5f, -0.5f,  1.0f, 1.0f, 1.0f,  0.0f,  0.0f,
        0.5f, -0.5f, -0.5f,  0.0f, 1.0f, 1.0f,  0.0f,  0.0f,
        0.5f, -0.5f, -0.5f,  0.0f, 1.0f, 1.0f,  0.0f,  0.0f,
        0.5f, -0.5f,  0.5f,  0.0f, 0.0f, 1.0f,  0.0f,  0.0f,
        0.5f,  0.5f,  0.5f,  1.0f, 0.0f, 1.0f,  0.0f,  0.0f,

        -0.5f, -0.5f, -0.5f,  0.0f, 1.0f, 0.0f, -1.0f,  0.0f,
        0.5f, -0.5f, -0.5f,  1.0f, 1.0f, 0.0f, -1.0f,  0.0f,
        0.5f, -0.5f,  0.5f,  1.0f, 0.0f, 0.0f, -1.0f,  0.0f,
        0.5f, -0.5f,  0.5f,  1.0f, 0.0f, 0.0f, -1.0f,  0.0f,
        -0.5f, -0.5f,  0.5f,  0.0f, 0.0f, 0.0f, -1.0f,  0.0f,
        -0.5f, -0.5f, -0.5f,  0.0f, 1.0f, 0.0f, -1.0f,  0.0f,

        -0.5f,  0.5f, -0.5f,  0.0f, 1.0f, 0.0f,  1.0f,  0.0f,
        0.5f,  0.5f, -0.5f,  1.0f, 1.0f, 0.0f,  1.0f,  0.0f,
        0.5f,  0.5f,  0.5f,  1.0f, 0.0f, 0.0f,  1.0f,  0.0f,
        0.5f,  0.5f,  0.5f,  1.0f, 0.0f, 0.0f,  1.0f,  0.0f,
        -0.5f,  0.5f,  0.5f,  0.0f, 0.0f, 0.0f,  1.0f,  0.0f,
        -0.5f,  0.5f, -0.5f,  0.0f, 1.0f, 0.0f,  1.0f,  0.0f
    };


    private final FloatBuffer vertexBuffers;
    private static final int COORDS_PER_VERTEX = 3;
    private static final int COORDS_PER_TEXTURE = 2;
    private static final int COORDS_PER_NORMAL = 3;
    private static final int VERTEX_STRIDE = 8 * 4;
    private static final int VERTEX_COUNT = 36;
    private static final int POSITION_OFFSET = 0;
    private static final int TEXTURE_OFFSET = 3;
    private static final int NORMAL_OFFSET = 5;
    private int mProgram;
    private int mPositionHandler;
    private int mTextureCoordsHandler;
    private int mTextureHandler;
    private int mMVPMatrixHandle;
    private int mNormalHandler;
    private int mLightPosHandler;
    private int mLightColorHandler;
    private int mObjectColorHandler;
    private int mAmbientStrength;
    private int mDiffuseStrength;
    private int mSpecularStrength;

    public Cube() {
        vertexBuffers = ByteBuffer.allocateDirect(vertexCoords.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffers.put(vertexCoords);
        vertexBuffers.position(0);

        int vertexShaderHandler = Utils.loadShaderFromAssets(GLES20.GL_VERTEX_SHADER, "threedim/cube.vsh");
        int fragmentShaderHandler = Utils.loadShaderFromAssets(GLES20.GL_FRAGMENT_SHADER, "threedim/cube.fsh");

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShaderHandler);
        GLES20.glAttachShader(mProgram, fragmentShaderHandler);
        GLES20.glLinkProgram(mProgram);

        final int[] textureHandle = new int[1];
        GLES20.glGenTextures(1, textureHandle, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

        // Set filtering
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
            GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
            GLES20.GL_LINEAR);

        // Set wrapping mode
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
            GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
            GLES20.GL_CLAMP_TO_EDGE);

        // Load the bitmap into the bound texture.
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, Utils.loadBitmapFromAssets("flowers.png"), 0);

        if (textureHandle[0] == 0)
        {
            throw new RuntimeException("Error loading texture.");
        }

        GLES20.glDeleteShader(vertexShaderHandler);
        GLES20.glDeleteShader(fragmentShaderHandler);
    }

    @Override
    public void draw(float[] matrix) {
        GLES20.glUseProgram(mProgram);
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, matrix, 0);

        vertexBuffers.position(POSITION_OFFSET);
        mPositionHandler = GLES20.glGetAttribLocation(mProgram, "aPosition");
        GLES20.glVertexAttribPointer(mPositionHandler, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, vertexBuffers);
        GLES20.glEnableVertexAttribArray(mPositionHandler);

        vertexBuffers.position(TEXTURE_OFFSET);
        mTextureCoordsHandler = GLES20.glGetAttribLocation(mProgram, "aTextureCoords");
        Utils.checkGlError("get texture coords");
        GLES20.glVertexAttribPointer(mTextureCoordsHandler, COORDS_PER_TEXTURE, GLES20.GL_FLOAT, false, VERTEX_STRIDE, vertexBuffers);
        GLES20.glEnableVertexAttribArray(mTextureCoordsHandler);

        vertexBuffers.position(NORMAL_OFFSET);
        mNormalHandler = GLES20.glGetAttribLocation(mProgram, "aNormal");
        Utils.checkGlError("get normal");
        GLES20.glVertexAttribPointer(mNormalHandler, COORDS_PER_NORMAL, GLES20.GL_FLOAT, false, VERTEX_STRIDE, vertexBuffers);
        GLES20.glEnableVertexAttribArray(mNormalHandler);

        mLightPosHandler = GLES20.glGetUniformLocation(mProgram, "uLightPos");
        Utils.checkGlError("get light pos");
        GLES20.glUniform3f(mLightPosHandler, 4f, 4.0f, 3.0f);

        mLightColorHandler = GLES20.glGetUniformLocation(mProgram, "uLightColor");
        Utils.checkGlError("get light color");
        GLES20.glUniform3f(mLightColorHandler, 1.0f, 1.0f, 1.0f);

        mObjectColorHandler = GLES20.glGetUniformLocation(mProgram, "uObjectColor");
        Utils.checkGlError("get object color");
        GLES20.glUniform4f(mObjectColorHandler, 1.0f, 0.5f, 0.31f, 1.0f);

        mAmbientStrength = GLES20.glGetUniformLocation(mProgram, "uAmbientStrength");
        Utils.checkGlError("get ambient strength");
        GLES20.glUniform1f(mAmbientStrength, 0.2f);

        mDiffuseStrength = GLES20.glGetUniformLocation(mProgram, "uDiffuseStrength");
        Utils.checkGlError("get diffuse strength");
        GLES20.glUniform1f(mDiffuseStrength, 1.0f);

        mSpecularStrength = GLES20.glGetAttribLocation(mProgram, "uSpecularStrength");
        Utils.checkGlError("get specular strength");
        GLES20.glUniform1f(mSpecularStrength, 0.5f);

        mTextureHandler = GLES20.glGetUniformLocation(mProgram, "uTexture");
        Utils.checkGlError("get texture location");
        GLES20.glUniform1i ( mTextureHandler, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, VERTEX_COUNT);
        GLES20.glDisableVertexAttribArray(mPositionHandler);
        GLES20.glDisableVertexAttribArray(mTextureCoordsHandler);
    }

    @Override
    protected float[] getVertexCoords() {
        return new float[0];
    }

    @Override
    protected float[] getColor() {
        return new float[0];
    }
}
