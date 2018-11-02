package me.light.learnopengl.shape;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import me.light.learnopengl.Utils;

/**
 * Created by shangjie on 2018/11/2.
 */

public class ColorTriangle extends Shape {

    private final float vertexCoords[] = {
        //位置                    //颜色
        0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
        -0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
        0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f
    };

    private final String vertexShader =
        "uniform mat4 uMVPMatrix;"
            + "attribute vec4 aPosition;"
            + "attribute vec4 aColor;"
            + "varying vec4 vColor;"
            + "void main(){"
            + "     vColor = aColor;"
            + "     gl_Position = uMVPMatrix * vPosition;"
            + "}";

    private final String fragmentShader =
         "varying vec4 vColor;"
            + "void main(){"
            + "     gl_FragColor = vColor;"
            + "}";

    private FloatBuffer vertexBuffers;
    private static final int COORDS_PER_VERTEX = 3;
    private static final int COORDS_PER_COLOR = 4;
    private static final int VERTEX_STRIDE = 7 * 4;
    private static final int VERTEX_COUNT = 3;
    private static final int POSITION_OFFSET = 0;
    private static final int COLOR_OFFSET = 3;
    private int mProgram;
    private int mPositionHandler;
    private int mColorHandler;
    private int mMVPMatrixHandle;

    public ColorTriangle() {
        vertexBuffers = ByteBuffer.allocateDirect(vertexCoords.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffers.put(vertexCoords);
        vertexBuffers.position(0);

        int vertexShaderHandler = Utils.loadShader(GLES20.GL_VERTEX_SHADER, vertexShader);
        int fragmentShaderHandler = Utils.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShaderHandler);
        GLES20.glAttachShader(mProgram, fragmentShaderHandler);
        GLES20.glLinkProgram(mProgram);
        GLES20.glDeleteShader(vertexShaderHandler);
        GLES20.glDeleteShader(fragmentShaderHandler);
    }

    @Override
    public void draw(float[] matrix) {
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, matrix, 0);

        GLES20.glBindAttribLocation(mPositionHandler, 0, "aPosition");
        GLES20.glBindAttribLocation(mPositionHandler, 1, "aColor");

        vertexBuffers.position(POSITION_OFFSET);
        mPositionHandler = GLES20.glGetAttribLocation(mProgram, "aPosition");
        GLES20.glVertexAttribPointer(mPositionHandler, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, vertexBuffers);
        GLES20.glEnableVertexAttribArray(mPositionHandler);

        mColorHandler = GLES20.glGetAttribLocation(mProgram, "aColor");
        vertexBuffers.position(COLOR_OFFSET);
        GLES20.glVertexAttribPointer(mColorHandler, COORDS_PER_COLOR, GLES20.GL_FLOAT, false, VERTEX_STRIDE, vertexBuffers);
        GLES20.glEnableVertexAttribArray(mColorHandler);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, VERTEX_COUNT);
        GLES20.glDisableVertexAttribArray(mPositionHandler);

    }

    @Override
    protected float[] getVertexCoords() {
        return new float[0];
    }

    @Override
    protected float[] getColor() {
        return new float[0];
    }
}
