package me.light.learnopengl.threedim;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;
import me.light.learnopengl.IRender;
import me.light.learnopengl.shape.Shape;
import me.light.learnopengl.shape.ShapeCreator;

/**
 * Created by shangjie on 2018/11/5.
 */

public class ThreeDimensionRender implements IRender {
    private static final String TAG = "StartRender";

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];
    private ShapeCreator mShapeCreator;
    private Shape mShape;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        //启用深度测试
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        Log.d(TAG, "surface created");
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float)width / height;
        Log.d(TAG, "surface changed");
        mShape = mShapeCreator.create();
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        float[] scratch = new float[16];
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        Log.d(TAG, "draw frame");
        Matrix.setLookAtM(mViewMatrix, 0, 3, 3, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int)time);
        Matrix.setRotateM(mRotationMatrix, 0, angle, 0.5f, 1.0f, 0.0f);
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
        mShape.draw(scratch);
    }

    @Override
    public void setShapeCreator(ShapeCreator shape) {
        mShapeCreator = shape;
    }
}
