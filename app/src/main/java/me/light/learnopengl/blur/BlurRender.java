package me.light.learnopengl.blur;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import me.light.learnopengl.R;
import me.light.learnopengl.Utils;

/**
 * Created by shangjie on 2018/11/8.
 */

public class BlurRender implements GLSurfaceView.Renderer {
    private final float[] mMVPMatrix = new float[16];

    private int mPositionHandler;
    private int mTextureCoordsHandler;
    private int mTextureHandler;
    private int mMatrixHandler;
    private int mWidthOffsetHandle;
    private int mHeightOffsetHandle;
    private int mProgram;
    private FloatBuffer mPositionBuffer;
    private FloatBuffer mTextureCoodrBuffer;
    private Bitmap mBitmap;
    private int mWidth;
    private int mHeight;
    private float mWidthOffset;
    private float mHeightOffset;


    public static final float CUBE[] = {
        -1.0f, -1.0f,
        1.0f, -1.0f,
        -1.0f, 1.0f,
        1.0f, 1.0f,
    };

    public static final float TEXTURE_NO_ROTATION[] = {
        0.0f, 1.0f,
        1.0f, 1.0f,
        0.0f, 0.0f,
        1.0f, 0.0f,
    };


    public static final String COLOR_INVERT_FRAGMENT_SHADER = "" +
        "precision highp float;\n" +
        "varying vec2 textureCoordinate;\n" +
        "\n" +
        "uniform sampler2D inputImageTexture;\n" +
        "\n" +
        "void main()\n" +
        "{\n" +
        "    vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n" +
        "    \n" +
        "    gl_FragColor = vec4((1.0 - textureColor.rgb), textureColor.w);\n" +
        "}";


    public static final String NO_FILTER_VERTEX_SHADER = "" +
        "attribute vec4 position;\n" +
        "attribute vec4 inputTextureCoordinate;\n" +
        " \n" +
        "varying vec2 textureCoordinate;\n" +
        " \n" +
        "void main()\n" +
        "{\n" +
        "    gl_Position = position;\n" +
        "    textureCoordinate = inputTextureCoordinate.xy;\n" +
        "}";

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        int vertexShander = Utils.loadShaderFromAssets(GLES20.GL_VERTEX_SHADER, "blur/blur.vsh");
        int fragShader = Utils.loadShaderFromAssets(GLES20.GL_FRAGMENT_SHADER, "blur/blur.fsh");
        //int vertexShander = Utils.loadShader(GLES20.GL_VERTEX_SHADER, NO_FILTER_VERTEX_SHADER);
        //int fragShader = Utils.loadShader(GLES20.GL_FRAGMENT_SHADER, COLOR_INVERT_FRAGMENT_SHADER);

        mPositionBuffer = ByteBuffer.allocateDirect(CUBE.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mPositionBuffer.put(CUBE).position(0);

        mTextureCoodrBuffer = ByteBuffer.allocateDirect(TEXTURE_NO_ROTATION.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mTextureCoodrBuffer.put(TEXTURE_NO_ROTATION).position(0);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShander);
        GLES20.glAttachShader(mProgram, fragShader);
        GLES20.glLinkProgram(mProgram);
        int[] lstatus = new int[1];
        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, lstatus, 0);
        if (lstatus[0] == GL11.GL_FALSE){
            System.err.println("Could not link program.\nlink status: " + lstatus[0]);
            System.err.println("Program-Info-Log: " + GLES20.glGetProgramInfoLog(mProgram));
        }else{
            System.out.println("program linked: " + mProgram);
        }

        mPositionHandler = GLES20.glGetAttribLocation(mProgram, "position");
        Utils.checkGlError("position");
        mTextureCoordsHandler = GLES20.glGetAttribLocation(mProgram, "inputTextureCoordinate");
        Utils.checkGlError("text");
        mTextureHandler = GLES20.glGetAttribLocation(mProgram, "inputImageTexture");
        Utils.checkGlError("image");
        mMatrixHandler = GLES20.glGetAttribLocation(mProgram, "vMatrix");
        Utils.checkGlError("matrix");

        mWidthOffsetHandle = GLES20.glGetUniformLocation(mProgram, "texelWidthOffset");
        mHeightOffsetHandle = GLES20.glGetUniformLocation(mProgram, "texelHeightOffset");

        final int[] textureHandle = new int[1];
        GLES20.glGenTextures(1, textureHandle, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

        // Set filtering
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
            GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
            GLES20.GL_LINEAR);

        // Set wrapping mode
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
            GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
            GLES20.GL_CLAMP_TO_EDGE);

        // Load the bitmap into the bound texture.
        mBitmap = BitmapFactory.decodeResource(Utils.sContext.getResources(), R.mipmap.pic_lena);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0);
        Utils.checkGlError("bind image");
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        GLES20.glUseProgram(mProgram);
        Utils.checkGlError("use program");

        GLES20.glViewport(0, 0, width, height);

        Matrix.setIdentityM(mMVPMatrix, 0);
        mWidth = width;
        mHeight = height;
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        mPositionBuffer.position(0);

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
        Utils.printMatrix("blur", "matrix", mMVPMatrix);
        GLES20.glUniformMatrix4fv(mMatrixHandler,1,false, mMVPMatrix,0);
        GLES20.glVertexAttribPointer(mPositionHandler,2,GLES20.GL_FLOAT,false,0,mPositionBuffer);
        GLES20.glEnableVertexAttribArray(mPositionHandler);

        GLES20.glVertexAttribPointer(mTextureCoordsHandler,2,GLES20.GL_FLOAT,false,0,mTextureCoodrBuffer);
        GLES20.glEnableVertexAttribArray(mTextureCoordsHandler);

        GLES20.glUniform1f(mWidthOffsetHandle, mWidthOffset);
        GLES20.glUniform1f(mHeightOffsetHandle, mHeightOffset);

        GLES20.glUniform1i(mTextureHandler, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4);
    }

    public void setRate(float rate) {
        mWidthOffset = rate / (mWidth / 8);
        mHeightOffset = rate / (mHeight / 8);
    }

}