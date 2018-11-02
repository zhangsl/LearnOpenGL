package me.light.learnopengl.shape;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class StartOpenGLActivity extends Activity {
    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String shape = getIntent().getStringExtra("shape");

        MyGlRender mRender = new MyGlRender();
        mRender.setShape(shape);
        mGLSurfaceView = new MyGLSurfaceView(this);
        mGLSurfaceView.setRenderer(mRender);
        setContentView(mGLSurfaceView);

    }
}
