package me.light.learnopengl;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by shangjie on 2018/11/1.
 */

public class Utils {
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

        return shader;
    }
}
