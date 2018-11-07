package me.light.learnopengl.model;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ModelDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Paint paint = new Paint("nanosuit/nanosuit.obj", "nanosuit/nanosuit.mtl");
        ModelRender render = new ModelRender();
        render.setPaint(paint);
        ModelRenderSurface surface = new ModelRenderSurface(getApplicationContext());
        surface.setRenderer(render);
        setContentView(surface);
    }
}
