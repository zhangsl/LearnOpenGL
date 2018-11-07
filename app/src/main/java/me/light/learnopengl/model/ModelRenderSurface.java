package me.light.learnopengl.model;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by shangjie on 2018/11/7.
 */

public class ModelRenderSurface extends GLSurfaceView {
    public ModelRenderSurface(Context context) {
        super(context);
        setEGLContextClientVersion(2);

        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
