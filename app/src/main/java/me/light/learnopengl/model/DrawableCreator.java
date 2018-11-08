package me.light.learnopengl.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;
import me.light.learnopengl.Utils;
import me.light.learnopengl.obj.FloatTuple;
import me.light.learnopengl.obj.Mtl;
import me.light.learnopengl.obj.MtlReader;
import me.light.learnopengl.obj.Obj;
import me.light.learnopengl.obj.ObjFace;
import me.light.learnopengl.obj.ObjGroup;
import me.light.learnopengl.obj.ObjReader;

/**
 * Created by shangjie on 2018/11/7.
 */

public class DrawableCreator {
    public static List<Drawable> createDrawables(String objAssetsName, String mtlAssetsName) {
        List<Drawable> drawables = new ArrayList<>();
        //try (InputStreamReader objReader = new InputStreamReader(Utils.sContext.getAssets().open(objAssetsName));
        //InputStreamReader mtlReader = new InputStreamReader(Utils.sContext.getAssets().open(mtlAssetsName))) {
        //    Obj obj = ObjReader.read(objReader);
        //    List<Mtl> mtl = MtlReader.read(mtlReader);
        //    int materialNum = obj.getNumMaterialGroups();
        //
        //    for (int i = 0; i < materialNum; i++) {
        //        ObjGroup group = obj.getMaterialGroup(i);
        //        Drawable drawable = group2Drawable(obj, group);
        //        drawables.add(drawable);
        //    }
        //
        //    addMtlInfo(drawables, mtl);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}



        List<Obj3D> model= me.light.learnopengl.model.ObjReader.readMultiObj(Utils.sContext,"assets/nanosuit/nanosuit.obj");
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
        }

        return drawables;
    }

    private static void addMtlInfo(List<Drawable> drawables, List<Mtl> mtls) {
        for (Drawable drawable : drawables) {
            Mtl mtl = findMtl(mtls, drawable.getName());
            drawable.setAmbientColorTexture(mtl.getMapKa());
            FloatTuple ambientColor = mtl.getKa();
            drawable.setAmientColor(new float[] {ambientColor.get(0), ambientColor.get(1), ambientColor.get(2)});
            FloatTuple diffuseColor = mtl.getKd();
            drawable.setDiffuseColor(new float[] {diffuseColor.get(0), diffuseColor.get(1), diffuseColor.get(2)});
            FloatTuple specularColor = mtl.getKs();
            drawable.setSpecularColor(new float[] {specularColor.get(0), specularColor.get(1), specularColor.get(2)});
            drawable.setBumpTexture(mtl.getMapKd());
            drawable.setIlluminationModel(mtl.getIllum());
            drawable.setDiffuseColorTexture(mtl.getMapKd());
            drawable.setSpecularColorTexutre(mtl.getMapKs());
            drawable.setSpecularExponent(mtl.getNs());
            drawable.setIndexOfRefraction(mtl.getNi());
        }
    }

    private static Mtl findMtl(List<Mtl> mtls, String name) {
        for (Mtl mtl : mtls) {
            if (TextUtils.equals(mtl.getName(), name)) {
                return mtl;
            }
        }

        return null;
    }

    private static Drawable group2Drawable(Obj obj, ObjGroup group) {
        Drawable drawable = new Drawable();
        int faceNum = group.getNumFaces();

        float[] vertexs = new float[faceNum * 9];
        float[] normals = new float[faceNum * 9];
        float[] textureVertexs = new float[faceNum * 6];
        drawable.setVertexCount(faceNum * 3);

        for (int i = 0; i < faceNum; i++) {
            ObjFace face = group.getFace(i);
            for (int j = 0; j < 3; j ++) {
                int vertexIndex = face.getVertexIndex(j);
                FloatTuple vertex = obj.getVertex(vertexIndex);
                vertexs[i * 9 + j * 3] = vertex.get(0);
                vertexs[i * 9 + j * 3 + 1] = vertex.get(1);
                vertexs[i * 9 + j * 3 + 2] = vertex.get(2);

                int normalIndex = face.getNormalIndex(j);
                FloatTuple normal = obj.getNormal(normalIndex);
                normals[i * 9 + j * 3] = normal.get(0);
                normals[i * 9 + j * 3 + 1] = normal.get(1);
                normals[i * 9 + j * 3 + 2] = normal.get(2);

                int textureCoordsIndex = face.getTexCoordIndex(j);
                FloatTuple textureCoords = obj.getTexCoord(textureCoordsIndex);
                textureVertexs[i * 6 + j * 2] = textureCoords.get(0);
                textureVertexs[i * 6 + j * 2 + 1] = textureCoords.get(1);
            }
        }

        drawable.setName(group.getName());
        drawable.setNormals(normals);
        drawable.setTextureCoords(textureVertexs);
        drawable.setVertexCoords(vertexs);

        return drawable;
    }
}
