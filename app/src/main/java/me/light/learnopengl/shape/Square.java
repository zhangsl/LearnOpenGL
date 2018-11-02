package me.light.learnopengl.shape;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import android.opengl.GLES20;

/**
 * Created by shangjie on 2018/11/1.
 */

public class Square extends Shape {
    private short drawOrder[] = { 0, 1, 2, 0, 2, 3 };
    private ShortBuffer drawListBuffer;

    public Square() {
        super();
        ByteBuffer dlb = ByteBuffer.allocateDirect(
            drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
    }

    @Override
    protected void onDraw(int positionHandle) {
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

    @Override
    protected float[] getVertexCoords() {
        float squareCoords[] = {
            -0.5f,  0.5f, 0.0f,   // top left
            -0.5f, -0.5f, 0.0f,   // bottom left
            0.5f, -0.5f, 0.0f,   // bottom right
            0.5f,  0.5f, 0.0f };  //top right
        return squareCoords;
    }

    @Override
    protected float[] getColor() {
        return new float[]{ 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
    }

}

