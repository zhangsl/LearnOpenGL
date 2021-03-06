package me.light.learnopengl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.light.learnopengl.blur.BlurActivity;
import me.light.learnopengl.model.ModelDisplayActivity;
import me.light.learnopengl.shape.StartOpenGLActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.sContext = getApplicationContext();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_triangle)
    public void showTriangle(Button button) {
        Intent intent = new Intent(this, StartOpenGLActivity.class);
        intent.putExtra("shape", "triangle");
        startActivity(intent);
    }

    @OnClick(R.id.btn_square)
    public void showSqaure(Button button) {
        Intent intent = new Intent(this, StartOpenGLActivity.class);
        intent.putExtra("shape", "square");
        startActivity(intent);
    }

    @OnClick(R.id.btn_colorful_triangle)
    public void showColorTriangle(Button btn) {
        Intent intent = new Intent(this, StartOpenGLActivity.class);
        intent.putExtra("shape", "colorTriangle");
        startActivity(intent);
    }

    @OnClick(R.id.btn_texture_square)
    public void showTextureSquare(Button btn) {
        Intent intent = new Intent(this, StartOpenGLActivity.class);
        intent.putExtra("shape", "textureSquare");
        startActivity(intent);
    }

    @OnClick(R.id.btn_cube)
    public void showCube(Button btn) {
        Intent intent = new Intent(this, StartOpenGLActivity.class);
        intent.putExtra("shape", "cube");
        startActivity(intent);
    }

    @OnClick(R.id.btn_nanosuit)
    public void showNanoSuit(Button btn) {
        Intent intent = new Intent(this, ModelDisplayActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_blur)
    public void showBlur(Button btn) {
        Intent intent = new Intent(this, SmartActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_gl_canvas)
    public void showGlCanvas(Button btn) {
        Intent intent = new Intent(this, GlCanvasActivity.class);
        startActivity(intent);
    }
}
