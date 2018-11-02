package me.light.learnopengl.shape;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.text.TextUtils;

public class StartOpenGLActivity extends Activity {
    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String shape = getIntent().getStringExtra("shape");

        MyGlRender mRender = new MyGlRender();
        ShapeCreator shapeCreator = new ShapeCreator();
        if (TextUtils.equals(shape, "triangle")) {
            shapeCreator.setShapeClass(Triangle.class);
        } else if (TextUtils.equals(shape, "square")) {
            shapeCreator.setShapeClass(Square.class);
        } else if (TextUtils.equals(shape, "colorTriangle")){
            shapeCreator.setShapeClass(ColorTriangle.class);
        }
        mRender.setShapeCreator(shapeCreator);
        mGLSurfaceView = new MyGLSurfaceView(this);
        mGLSurfaceView.setRenderer(mRender);
        setContentView(mGLSurfaceView);

    }
}
