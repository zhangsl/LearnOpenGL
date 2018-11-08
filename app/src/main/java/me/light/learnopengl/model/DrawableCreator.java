package me.light.learnopengl.model;

import java.util.ArrayList;
import java.util.List;

import me.light.learnopengl.Utils;

/**
 * Created by shangjie on 2018/11/7.
 */

public class DrawableCreator {
    public static List<Drawable> createDrawables(String objAssetsName, String mtlAssetsName) {
        List<Drawable> drawables = new ArrayList<>();

        List<Obj3D> model = me.light.learnopengl.model.ObjReader.readMultiObj(Utils.sContext, "assets/nanosuit/nanosuit.obj");
        for (Obj3D obj : model) {
            Drawable drawable = new Drawable();
            drawable.setVertexCount(obj.vertCount);
            drawable.setSpecularExponent(obj.mtl.Ns);
            drawable.setSpecularColorTexutre(obj.mtl.map_Ks);
            drawable.setSpecularColor(obj.mtl.Ks);
            drawable.setDiffuseColorTexture(obj.mtl.map_Kd);
            drawable.setDiffuseColor(obj.mtl.Kd);
            drawable.setIlluminationModel(obj.mtl.illum);
            drawable.setAmientColor(obj.mtl.Ka);
            drawable.setBumpTexture(obj.mtl.map_Ka);
            drawable.setNormals(obj.vertNorl);
            drawable.setVertexCoords(obj.vert);
            drawable.setTextureCoords(obj.vertTexture);
            drawables.add(drawable);
        }

        return drawables;
    }
}
