package me.light.learnopengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.light.learnopengl.glcanvas.BitmapTexture;
import me.light.learnopengl.glcanvas.GLCanvas;
import me.light.learnopengl.glcanvas.GLES20Canvas;
import me.light.learnopengl.glcanvas.GLPaint;

public class GlCanvasActivity extends AppCompatActivity {
    private static final String TAG = "GlCanvasActivity";

    @BindView(R.id.gl_surface_view)
    GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gl_canvas);
        ButterKnife.bind(this);

        //Version必须要设置，如不设置，后续创建shader之类都会有问题，且无法通过glGetError查到状态。
        mGLSurfaceView.setEGLContextClientVersion(2);
        mGLSurfaceView.setRenderer(new MyRender());
    }

    private static class MyRender implements Renderer{

        private GLCanvas mGLCanvas;
        private GLPaint mGLPaint = new GLPaint();
        private float[] mBgColor = new float[] {1.0f, 1.0f, 1.0f, 1.0f};
        private BitmapTexture mTulip;
        private BitmapTexture mLena;
        public MyRender() {
            mGLPaint.setColor(Color.BLACK);
            mGLPaint.setLineWidth(100.0f);
        }

        @Override
        public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
            mGLCanvas = new GLES20Canvas();
            mTulip = new BitmapTexture(Utils.loadBitmapFromAssets("tulip.jpg"));
            mLena = new BitmapTexture(Utils.loadBitmapFromAssets("lena.png"));
            Log.d(TAG, "  onSurface  Created:   ");

        }

        @Override
        public void onSurfaceChanged(GL10 gl10, int width, int height) {
            mGLCanvas.setSize(width, height);
            Log.d(TAG, "onSurface  ---> Changed");
        }

        @Override
        public void onDrawFrame(GL10 gl10) {
            mGLCanvas.clearBuffer(mBgColor);
            mGLCanvas.drawTexture(mTulip, 10, 10, mTulip.getWidth(), mTulip.getHeight());
            mGLCanvas.drawLine(0f, 1100f, 1600f, 1100, mGLPaint);
            mGLCanvas.drawMixed(mLena, Color.BLUE, 0.3f, 20, 1200, mLena.getWidth(), mLena.getHeight());

            mGLCanvas.save();
            mGLCanvas.rotate(1, 300, 1200, 0);
            mGLCanvas.drawMixed(mLena, Color.RED, 0.3f, 300, 1200, mLena.getWidth(), mLena.getHeight());
            mGLCanvas.restore();

            
            mGLCanvas.drawRect(600, 1200, 300f, 300f, mGLPaint);

        }
    }
}
