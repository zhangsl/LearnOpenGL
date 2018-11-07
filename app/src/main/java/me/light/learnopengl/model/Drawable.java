package me.light.learnopengl.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

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


    public FloatBuffer getTextureCoords() {
        return mTextureCoords;
    }

    public void setTextureCoords(float[] textureCoords) {
        mTextureCoords = ByteBuffer.allocateDirect(textureCoords.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mTextureCoords.position(0);
    }

    public FloatBuffer getNormals() {
        return mNormals;
    }

    public void setNormals(float[] normals) {
        mNormals = ByteBuffer.allocateDirect(normals.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mNormals.position(0);
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
