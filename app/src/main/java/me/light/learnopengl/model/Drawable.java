package me.light.learnopengl.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import me.light.learnopengl.Utils;

/**
 * Created by shangjie on 2018/11/7.
 */

public class Drawable {
    private FloatBuffer mVertexCoords;
    private FloatBuffer mTextureCoords;
    private FloatBuffer mNormals;
    private String mName;
    private float[] mAmientColor;
    private float[] mDiffuseColor;
    private float[] mSpecularColor;
    private float mSpecularExponent;
    private float mIndexOfRefraction;
    private float mIlluminationModel;
    private String mAmbientColorTexture;
    private String mDiffuseColorTexture;
    private String mBumpTexture;
    private String mSpecularColorTexutre;
    private int mTextureHandler;
    private int mVertexCount;

    private static final int COORDS_PER_VERTEX = 3;
    private static final int COORDS_PER_TEXTURE = 2;
    private static final int COORDS_PER_NORMAL = 3;
    private static final int VERTEX_STRIDE = 3 * 4;
    private static final int NORMAL_STRIDE = 3 * 4;
    private static final int TEXTURE_STRIDE = 2 * 4;
    private int mProgram;
    private int mPositionHandler;
    private int mTextureCoordsHandler;
    private int mTextureValueHandler;
    private int mMVPMatrixHandle;
    private int mNormalHandler;
    private int mLightPosHandler;
    private int mNsHandler;
    private int mKaHandler;
    private int mKsHandler;
    private int mKdHandler;


    public FloatBuffer getTextureCoords() {
        return mTextureCoords;
    }

    public void onCreate() {

        int vertexShaderHandler = Utils.loadShaderFromAssets(GLES20.GL_VERTEX_SHADER, "threedim/model.vsh");
        int fragmentShaderHandler = Utils.loadShaderFromAssets(GLES20.GL_FRAGMENT_SHADER, "threedim/model.fsh");

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShaderHandler);
        GLES20.glAttachShader(mProgram, fragmentShaderHandler);
        GLES20.glLinkProgram(mProgram);
        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            throw new RuntimeException("link program error");
        }


        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        mPositionHandler = GLES20.glGetAttribLocation(mProgram, "aPosition");
        mTextureCoordsHandler = GLES20.glGetAttribLocation(mProgram, "aTextureCoords");
        mNormalHandler = GLES20.glGetAttribLocation(mProgram, "aNormal");
        mLightPosHandler = GLES20.glGetUniformLocation(mProgram, "uLightPos");
        mTextureValueHandler = GLES20.glGetUniformLocation(mProgram, "uTexture");
        mKaHandler = GLES20.glGetUniformLocation(mProgram, "uKa");
        mKsHandler = GLES20.glGetUniformLocation(mProgram, "uKs");
        mKdHandler = GLES20.glGetUniformLocation(mProgram, "uKd");
        mNsHandler = GLES20.glGetUniformLocation(mProgram, "uNs");

        mTextureHandler = createTexture("nanosuit/" + getDiffuseColorTexture());

        GLES20.glDeleteShader(vertexShaderHandler);
        GLES20.glDeleteShader(fragmentShaderHandler);

    }

    public void onDraw(float[] matrix) {
        Utils.printMatrix("Drawable", "matrix", matrix);
        GLES20.glUseProgram(mProgram);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, matrix, 0);
        mVertexCoords.position(0);
        mTextureCoords.position(0);

        if (mNormals != null) {
            mNormals.position(0);
        }

        GLES20.glVertexAttribPointer(mPositionHandler, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, mVertexCoords);
        GLES20.glEnableVertexAttribArray(mPositionHandler);

        GLES20.glVertexAttribPointer(mTextureCoordsHandler, COORDS_PER_TEXTURE, GLES20.GL_FLOAT, false, TEXTURE_STRIDE, mTextureCoords);
        GLES20.glEnableVertexAttribArray(mTextureCoordsHandler);

        if (mNormals != null) {
            GLES20.glVertexAttribPointer(mNormalHandler, COORDS_PER_NORMAL, GLES20.GL_FLOAT, false, NORMAL_STRIDE, mNormals);
            GLES20.glEnableVertexAttribArray(mNormalHandler);
        }

        GLES20.glUniform3f(mLightPosHandler, 0.0f, -200.0f, -500.0f);

        float[] amient = getAmientColor();
        GLES20.glUniform3fv(mKaHandler, 1, amient, 0);

        float[] specular = getSpecularColor();
        GLES20.glUniform3fv(mKsHandler, 1, specular, 0);

        float[] diffuse = getDiffuseColor();
        GLES20.glUniform3fv(mKdHandler, 1, diffuse, 0);

        GLES20.glUniform1f(mNsHandler, getSpecularExponent());

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureHandler);
        GLES20.glUniform1i (mTextureValueHandler, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, mVertexCount);
        GLES20.glDisableVertexAttribArray(mPositionHandler);
        GLES20.glDisableVertexAttribArray(mTextureCoordsHandler);
    }

    private int createTexture(String imgPath){
        Bitmap bitmap = Utils.loadBitmapFromAssets(imgPath);
        int[] texture=new int[1];
        if(bitmap!=null&&!bitmap.isRecycled()){
            //生成纹理
            GLES20.glGenTextures(1,texture,0);
            //生成纹理
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,texture[0]);
            //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
            //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
            //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
            //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);
            //根据以上指定的参数，生成一个2D纹理
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
            return texture[0];
        }
        return 0;
    }

    public void setTextureCoords(float[] textureCoords) {
        mTextureCoords = ByteBuffer.allocateDirect(textureCoords.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mTextureCoords.position(0);
    }
    public void setTextureCoords(FloatBuffer textureCoords) {
        mTextureCoords = textureCoords;
    }

    public FloatBuffer getNormals() {
        return mNormals;
    }

    public void setNormals(float[] normals) {
        mNormals = ByteBuffer.allocateDirect(normals.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mNormals.position(0);
    }

    public void setNormals(FloatBuffer normals) {
        mNormals = normals;
    }

    public void setVertexCount(int count) {
        mVertexCount = count;
    }

    public int getVertexCount() {
        return mVertexCount;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public FloatBuffer getVertexCoords() {

        return mVertexCoords;
    }

    public void setVertexCoords(float[] vertexCoords) {
        mVertexCoords = ByteBuffer.allocateDirect(vertexCoords.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertexCoords.position(0);
    }

    public void setVertexCoords(FloatBuffer vertexCoords) {
        mVertexCoords = vertexCoords;
    }

    public float[] getAmientColor() {
        return mAmientColor;
    }

    public void setAmientColor(float[] amientColor) {
        mAmientColor = amientColor;
    }

    public float[] getDiffuseColor() {
        return mDiffuseColor;
    }

    public void setDiffuseColor(float[] diffuseColor) {
        mDiffuseColor = diffuseColor;
    }

    public float[] getSpecularColor() {
        return mSpecularColor;
    }

    public void setSpecularColor(float[] specularColor) {
        mSpecularColor = specularColor;
    }

    public float getSpecularExponent() {
        return mSpecularExponent;
    }

    public void setSpecularExponent(float specularExponent) {
        mSpecularExponent = specularExponent;
    }

    public float getIndexOfRefraction() {
        return mIndexOfRefraction;
    }

    public void setIndexOfRefraction(float indexOfRefraction) {
        mIndexOfRefraction = indexOfRefraction;
    }

    public float getIlluminationModel() {
        return mIlluminationModel;
    }

    public void setIlluminationModel(float illuminationModel) {
        mIlluminationModel = illuminationModel;
    }

    public String getAmbientColorTexture() {
        return mAmbientColorTexture;
    }

    public void setAmbientColorTexture(String ambientColorTexture) {
        mAmbientColorTexture = ambientColorTexture;
    }

    public String getDiffuseColorTexture() {
        return mDiffuseColorTexture;
    }

    public void setDiffuseColorTexture(String diffuseColorTexture) {
        mDiffuseColorTexture = diffuseColorTexture;
    }

    public String getBumpTexture() {
        return mBumpTexture;
    }

    public void setBumpTexture(String bumpTexture) {
        mBumpTexture = bumpTexture;
    }

    public String getSpecularColorTexutre() {
        return mSpecularColorTexutre;
    }

    public void setSpecularColorTexutre(String specularColorTexutre) {
        mSpecularColorTexutre = specularColorTexutre;
    }
}
