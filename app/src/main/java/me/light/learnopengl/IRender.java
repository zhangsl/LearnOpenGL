package me.light.learnopengl;

import android.opengl.GLSurfaceView.Renderer;
import me.light.learnopengl.shape.ShapeCreator;

/**
 * Created by shangjie on 2018/11/5.
 */

public interface IRender extends Renderer {
    public void setShapeCreator(ShapeCreator creator);
}
