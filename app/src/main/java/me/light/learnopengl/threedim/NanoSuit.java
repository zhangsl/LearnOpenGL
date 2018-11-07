package me.light.learnopengl.threedim;

import java.io.IOException;
import java.io.InputStreamReader;

import android.util.Log;
import me.light.learnopengl.Utils;
import me.light.learnopengl.obj.Obj;
import me.light.learnopengl.obj.ObjReader;

/**
 * Created by shangjie on 2018/11/6.
 */

public class NanoSuit {


    public void loadObj() {
        try (InputStreamReader reader = new InputStreamReader(Utils.sContext.getAssets().open("nanosuit/nanosuit.obj"))) {
            Obj obj = ObjReader.read(reader);
            Log.d("OBJ", obj.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
