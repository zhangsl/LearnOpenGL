package me.light.learnopengl.blur;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.light.learnopengl.R;

public class BlurActivity extends AppCompatActivity {

    @BindView(R.id.filter_preview)
    public GLSurfaceView mGLSurfaceView;

    @BindView(R.id.sb_blur)
    public SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        ButterKnife.bind(this);

        final BlurRender render = new BlurRender();
        mGLSurfaceView.setEGLContextClientVersion(2);
        mGLSurfaceView.setRenderer(render);

        mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                render.setRate(progress/100.0f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}
