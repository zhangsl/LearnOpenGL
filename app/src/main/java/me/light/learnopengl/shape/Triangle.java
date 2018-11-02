package me.light.learnopengl.shape;

import android.opengl.GLES20;

/**
 * Created by shangjie on 2018/11/1.
 */

public class Triangle extends Shape {

    @Override
    protected void onDraw(int positionHandle) {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(positionHandle);
    }


    @Override
    protected float[] getVertexCoords() {
        return new float[] {
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.0f, 0.5f, 0.0f
        };
    }

    @Override
    protected float[] getColor() {
        return new float[] {0.63671874f, 0.76953125f, 0.22265625f, 1.0f};
    }
}
