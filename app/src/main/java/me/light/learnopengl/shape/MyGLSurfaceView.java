package me.light.learnopengl.shape;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by shangjie on 2018/11/1.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    public MyGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(2);

        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
