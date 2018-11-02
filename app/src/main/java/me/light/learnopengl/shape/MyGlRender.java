package me.light.learnopengl.shape;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import me.light.learnopengl.Utils;

/**
 * Created by shangjie on 2018/11/1.
 */

public class MyGlRender implements GLSurfaceView.Renderer {
    private static final String TAG = "StartRender";

    private Triangle mTriangle;
    private Square mSquare;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];


    private String mShape;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Log.d(TAG, "surface created");
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float)width / height;
        Log.d(TAG, "surface changed");
        mTriangle = new Triangle();
        mSquare = new Square();
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1,1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        float[] scratch = new float[16];
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        Log.d(TAG, "draw frame");
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        Utils.printMatrix(TAG, "View Matrix", mViewMatrix);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        Utils.printMatrix(TAG, "MVP Matrix", mMVPMatrix);

        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int)time);
        Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1.0f);
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        if (TextUtils.equals(mShape, "triangle")) {
            mTriangle.draw(scratch);
        } else if (TextUtils.equals(mShape, "square")) {
            mSquare.draw(scratch);
        }
    }

    public void setShape(String shape) {
        mShape = shape;
    }

}
