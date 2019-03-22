package me.light.learnopengl;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHazeFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageLookupFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSharpenFilter;

public class SmartActivity extends Activity {
    @BindView(R.id.gl_surface_view)
    public GLSurfaceView mSurfaceView;

    private GPUImageSharpenFilter mSharpenFilter;
    private GPUImageHazeFilter mHazeFilter;
    private GPUImageLookupFilter mLookupFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart);
        ButterKnife.bind(this);

        GPUImage gpuimage = new GPUImage(this);
        gpuimage.setGLSurfaceView(mSurfaceView);
        gpuimage.setImage(BitmapFactory.decodeResource(getResources(), R.mipmap.piazza_san_marco));

        GPUImageFilterGroup group = new GPUImageFilterGroup();
        mHazeFilter = new GPUImageHazeFilter();
        mHazeFilter.setDistance(0.1f);
        mHazeFilter.setSlope(0.1f);

        mSharpenFilter = new GPUImageSharpenFilter();
        mSharpenFilter.setSharpness(0.2f);
        mLookupFilter = new GPUImageLookupFilter();
        mLookupFilter.setIntensity(1.0f);
        mLookupFilter.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lookup_amatorka));

        //group.addFilter(mHazeFilter);
        //group.addFilter(mSharpenFilter);
        group.addFilter(mLookupFilter);
        gpuimage.setFilter(group);
    }

    @OnLongClick(R.id.gl_surface_view)
    public boolean change(GLSurfaceView view) {
        mSharpenFilter.setSharpness(0);
        mHazeFilter.setSlope(0);
        mHazeFilter.setDistance(0);
        return true;
    }
}
