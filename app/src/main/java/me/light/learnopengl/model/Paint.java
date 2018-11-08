package me.light.learnopengl.model;

import java.nio.FloatBuffer;
import java.util.List;

import android.opengl.GLES20;
import android.opengl.GLUtils;
import me.light.learnopengl.Utils;

/**
 * Created by shangjie on 2018/11/7.
 */

public class Paint {
    private String mObjFile;
    private String mMtlFile;
    private List<Drawable> mDrawables;
    private static final int COORDS_PER_VERTEX = 3;
    private static final int COORDS_PER_TEXTURE = 2;
    private static final int COORDS_PER_NORMAL = 3;
    private static final int VERTEX_STRIDE = 3 * 4;
    private static final int NORMAL_STRIDE = 3 * 4;
    private static final int TEXTURE_STRIDE = 2 * 4;
    private static final int VERTEX_COUNT = 36;
    private int mProgram;
    private int mPositionHandler;
    private int mTextureCoordsHandler;
    private int mTextureHandler;
    private int mMVPMatrixHandle;
    private int mNormalHandler;
    private int mLightPosHandler;
    private int mNsHandler;
    private int mKaHandler;
    private int mKsHandler;
    private int mKdHandler;


    public Paint(String objFile, String mtlFile) {
        mObjFile = objFile;
        mMtlFile = mtlFile;
    }

    public void onCreate() {

        mDrawables = DrawableCreator.createDrawables(mObjFile, mMtlFile);

        int vertexShaderHandler = Utils.loadShaderFromAssets(GLES20.GL_VERTEX_SHADER, "threedim/model.vsh");
        int fragmentShaderHandler = Utils.loadShaderFromAssets(GLES20.GL_FRAGMENT_SHADER, "threedim/model.fsh");

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShaderHandler);
        GLES20.glAttachShader(mProgram, fragmentShaderHandler);
        GLES20.glLinkProgram(mProgram);

        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0)
        {
            throw new RuntimeException("link program error");
        }

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

        GLES20.glDeleteShader(vertexShaderHandler);
        GLES20.glDeleteShader(fragmentShaderHandler);

    }

    public void onDraw(float[] matrix) {
        GLES20.glUseProgram(mProgram);
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, matrix, 0);
        Drawable drawable = mDrawables.get(0);
        //for (Drawable drawable : mDrawables) {
            FloatBuffer vertexBuffers = drawable.getVertexCoords();
            vertexBuffers.position(0);
            FloatBuffer textureCoords = drawable.getTextureCoords();
            textureCoords.position(0);
            FloatBuffer normalBuffer = drawable.getNormals();
            normalBuffer.position(0);

            final int[] textureHandle = new int[1];
            GLES20.glGenTextures(1, textureHandle, 0);
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);
            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, Utils.loadBitmapFromAssets("nanosuit/" + drawable.getDiffuseColorTexture()), 0);
            if (textureHandle[0] == 0)
            {
                throw new RuntimeException("Error loading texture.");
            }

            mPositionHandler = GLES20.glGetAttribLocation(mProgram, "aPosition");
            GLES20.glVertexAttribPointer(mPositionHandler, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, vertexBuffers);
            GLES20.glEnableVertexAttribArray(mPositionHandler);

            mTextureCoordsHandler = GLES20.glGetAttribLocation(mProgram, "aTextureCoords");
            Utils.checkGlError("get texture coords");
            GLES20.glVertexAttribPointer(mTextureCoordsHandler, COORDS_PER_TEXTURE, GLES20.GL_FLOAT, false, TEXTURE_STRIDE, textureCoords);
            GLES20.glEnableVertexAttribArray(mTextureCoordsHandler);

            mNormalHandler = GLES20.glGetAttribLocation(mProgram, "aNormal");
            Utils.checkGlError("get normal");
            GLES20.glVertexAttribPointer(mNormalHandler, COORDS_PER_NORMAL, GLES20.GL_FLOAT, false, NORMAL_STRIDE, normalBuffer);
            GLES20.glEnableVertexAttribArray(mNormalHandler);

            mLightPosHandler = GLES20.glGetUniformLocation(mProgram, "uLightPos");
            Utils.checkGlError("get light pos");
            GLES20.glUniform3f(mLightPosHandler, 3.0f, 3.0f, -6.0f);

            mKaHandler = GLES20.glGetUniformLocation(mProgram, "uKa");
            Utils.checkGlError("get ka");
            float[] amient = drawable.getAmientColor();
            GLES20.glUniform3f(mKaHandler, amient[0], amient[1], amient[2]);

            mKsHandler = GLES20.glGetUniformLocation(mProgram, "uKs");
            Utils.checkGlError("get ks");
            float[] specular = drawable.getSpecularColor();
            GLES20.glUniform3f(mKsHandler, specular[0], specular[1], specular[2]);

            mKdHandler = GLES20.glGetUniformLocation(mProgram, "uKd");
            Utils.checkGlError("get kd");
            float[] diffuse = drawable.getDiffuseColor();
            GLES20.glUniform3f(mKdHandler, diffuse[0], diffuse[1], diffuse[2]);

            mNsHandler = GLES20.glGetUniformLocation(mProgram, "uNs");
            Utils.checkGlError("get ns");
            GLES20.glUniform1f(mNsHandler, drawable.getSpecularExponent());

            mTextureHandler = GLES20.glGetUniformLocation(mProgram, "uTexture");
            Utils.checkGlError("get texture location");
            GLES20.glUniform1i ( mTextureHandler, 0);

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, VERTEX_COUNT);
            GLES20.glDisableVertexAttribArray(mPositionHandler);
            GLES20.glDisableVertexAttribArray(mTextureCoordsHandler);
        //}


    }

    public void onSurfaceChange() {

    }
}
