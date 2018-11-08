package me.light.learnopengl.model;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ModelDisplayActivity extends AppCompatActivity {
    private GLSurfaceView mGLView;
    private List<AFilter> filters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ModelRender render = new ModelRender();
        //render.setPaint(new ModelData("nanosuit/nanosuit.obj", "nanosuit/nanosuit.mtl"));
        //ModelRenderSurface surface = new ModelRenderSurface(this);
        //surface.setRenderer(render);
        //setContentView(surface);

        mGLView = new GLSurfaceView(this);
        setContentView(mGLView);
        mGLView.setEGLContextClientVersion(2);
        List<Obj3D> model=ObjReader.readMultiObj(this,"assets/nanosuit/nanosuit.obj");
        filters=new ArrayList<>();
        for (int i=0;i<model.size();i++){
            AFilter f=new AFilter(getResources());
            f.setObj3D(model.get(i));
            filters.add(f);
        }
        mGLView.setRenderer(new GLSurfaceView.Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                for (AFilter f:filters){
                    f.create();
                }
            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
                for (AFilter f:filters){
                    f.onSizeChanged(width, height);
                    float[] matrix= new float[]{
                        1,0,0,0,
                        0,1,0,0,
                        0,0,1,0,
                        0,0,0,1
                    };
                    Matrix.translateM(matrix,0,0,-0.3f,0);
                    Matrix.scaleM(matrix,0,0.1f,0.1f*width/height,0.1f);
                    f.setMatrix(matrix);
                }
            }

            @Override
            public void onDrawFrame(GL10 gl) {
                GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
                GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
                for (AFilter f:filters){
                    Matrix.rotateM(f.getMatrix(),0,0.3f,0,1,0);
                    f.draw();
                }
            }
        });
        mGLView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
