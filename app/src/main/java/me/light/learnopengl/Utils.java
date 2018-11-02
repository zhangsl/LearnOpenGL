package me.light.learnopengl;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by shangjie on 2018/11/1.
 */

public class Utils {
    public static Context sContext;
    public static void printMatrix(String tag, String prefer, float[] matrix) {
        StringBuilder mvpMatrixStr = new StringBuilder();
        for (int i = 0, len = matrix.length; i < len; i++) {
            mvpMatrixStr.append(matrix[i]).append("   ");
            if ( (i+1) % 4 == 0) {
                mvpMatrixStr.append("\n");
            }
        }
        Log.d(tag, prefer + ":\n  " + mvpMatrixStr.toString());
    }

    public static int loadShader(int type, String shaderCode) {

        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

        // If the compilation failed, delete the shader.
        if (compileStatus[0] == 0)
        {
            Log.e("ColorTriangle", "loadShader error   " + shaderCode);
        }
        return shader;
    }

    public static Bitmap loadBitmapFromAssets(String filePath) {
        AssetManager assetManager = sContext.getAssets();

        InputStream istr = null;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (istr != null) {
                    istr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bitmap;
    }

    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e("LearnOpenGL", glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
}
