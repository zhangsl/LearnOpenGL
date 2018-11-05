package me.light.learnopengl.shape;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.text.TextUtils;
import me.light.learnopengl.IRender;
import me.light.learnopengl.threedim.Cube;
import me.light.learnopengl.texture.TextureSquare;
import me.light.learnopengl.threedim.ThreeDimensionRender;

public class StartOpenGLActivity extends Activity {
    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String shape = getIntent().getStringExtra("shape");

        IRender mRender = new MyGlRender();
        ShapeCreator shapeCreator = new ShapeCreator();
        if (TextUtils.equals(shape, "triangle")) {
            shapeCreator.setShapeClass(Triangle.class);
        } else if (TextUtils.equals(shape, "square")) {
            shapeCreator.setShapeClass(Square.class);
        } else if (TextUtils.equals(shape, "colorTriangle")){
            shapeCreator.setShapeClass(ColorTriangle.class);
        } else if (TextUtils.equals(shape, "textureSquare")) {
            shapeCreator.setShapeClass(TextureSquare.class);
        } else if (TextUtils.equals(shape, "cube")) {
            shapeCreator.setShapeClass(Cube.class);
            mRender = new ThreeDimensionRender();
        }
        mRender.setShapeCreator(shapeCreator);
        mGLSurfaceView = new MyGLSurfaceView(this);
        mGLSurfaceView.setRenderer(mRender);
        setContentView(mGLSurfaceView);

    }
}
