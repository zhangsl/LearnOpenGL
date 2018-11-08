package me.light.learnopengl.model;

import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by shangjie on 2018/11/7.
 */

public class ModelRender implements GLSurfaceView.Renderer {
    private static final String TAG = "ModelRender";

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];
    private ModelData mModelData;
    private List<Drawable> mDrawables;
    private float mScaleX = 0.1f;
    private float mScaleY = 0;
    private float mScaleZ = 0.1f;



    public void setPaint(ModelData data) {
        mModelData = data;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        //启用深度测试
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        mDrawables = DrawableCreator.createDrawables(mModelData.objFile, mModelData.mtlFile);
        for (Drawable drawable : mDrawables) {
            drawable.onCreate();
        }
        Log.d(TAG, "surface created");
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float)width / height;
        Log.d(TAG, "surface changed");
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        mScaleY = 0.1f * width / height;

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        float[] scratch = new float[16];
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        //
        Log.d(TAG, "draw frame");
        Matrix.setLookAtM(mViewMatrix, 0, 3, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int)time);
        Matrix.setRotateM(mRotationMatrix, 0, angle, 0.0f, 1.0f, 0.0f);
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
        Matrix.scaleM(scratch, 0, mScaleX, mScaleY, mScaleZ);
        for (Drawable drawable : mDrawables) {
            drawable.onDraw(scratch);
        }
    }
}
