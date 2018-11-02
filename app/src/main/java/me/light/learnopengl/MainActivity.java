package me.light.learnopengl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.light.learnopengl.shape.StartOpenGLActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
