package me.light.learnopengl.model;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ModelDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ModelRenderSurface surface = new ModelRenderSurface(this);
        setContentView(surface);
        ModelRender render = new ModelRender();
        render.setPaint(new ModelData("nanosuit/nanosuit.obj", "nanosuit/nanosuit.mtl"));
        surface.setRenderer(render);
    }
}
