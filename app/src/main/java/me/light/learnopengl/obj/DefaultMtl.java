/*
 * www.javagl.de - Obj
 *
 * Copyright (c) 2008-2015 Marco Hutter - http://www.javagl.de
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package me.light.learnopengl.obj;

import me.light.learnopengl.obj.*;

/**
 * Default implementation of an Mtl (material)
 */
final class DefaultMtl implements Mtl
{
    /**
     * The name of this material
     */
    private final String name;
    
    /**
     * The ambient part of this material
     */
    private final me.light.learnopengl.obj.DefaultFloatTuple ka;
    
    /**
     * The diffuse part of this material
     */
    private final me.light.learnopengl.obj.DefaultFloatTuple kd;
    
    /**
     * The specular part of this material
     */
    private final me.light.learnopengl.obj.DefaultFloatTuple ks;
    
    /**
     * The diffuse map of this material
     */
    private String mapKd;

    private String mapKs;

    private String mapKa;

    private int illum;

    private float ni;
    
    /**
     * The shininess of this material
     */
    private float ns;
    
    /**
     * The opacity of this material
     */
    private float d;

    /**
     * Creates a new material with the given name
     * 
     * @param name The name of this material
     */
    DefaultMtl(String name)
    {
        this.name = name;
        ka = new me.light.learnopengl.obj.DefaultFloatTuple(0,0,0);
        kd = new me.light.learnopengl.obj.DefaultFloatTuple(0,0,0);
        ks = new me.light.learnopengl.obj.DefaultFloatTuple(0,0,0);
        ns = 100.0f;
        d = 1.0f;
    }

    @Override
    public String getName()
    {
        return name;
    }

    
    @Override
    public void setKa(float ka0, float ka1, float ka2)
    {
        ka.setX(ka0);
        ka.setY(ka1);
        ka.setZ(ka2);
    }

    @Override
    public FloatTuple getKa()
    {
        return ka;
    }

    @Override
    public void setKs(float ks0, float ks1, float ks2)
    {
        ks.setX(ks0);
        ks.setY(ks1);
        ks.setZ(ks2);
    }

    @Override
    public FloatTuple getKs()
    {
        return ks;
    }

    @Override
    public void setKd(float kd0, float kd1, float kd2)
    {
        kd.setX(kd0);
        kd.setY(kd1);
        kd.setZ(kd2);
    }

    @Override
    public FloatTuple getKd()
    {
        return kd;
    }

    @Override
    public void setMapKd(String mapKd)
    {
        this.mapKd = mapKd;
    }

    @Override
    public String getMapKd()
    {
        return mapKd;
    }

    @Override
    public void setNs(float ns)
    {
       this.ns = ns;
    }

    @Override
    public float getNs()
    {
        return ns;
    }

    @Override
    public void setD(float d)
    {
       this.d = d;
    }

    @Override
    public float getD()
    {
        return d;
    }

    @Override
    public String getMapKa() {
        return this.mapKa;
    }

    @Override
    public void setMapKa(String mapKa) {
        this.mapKa = mapKa;
    }

    @Override
    public int getIllum() {
        return illum;
    }

    @Override
    public void setIllum(int illum) {
        this.illum = illum;
    }

    @Override
    public float getNi() {
        return ni;
    }

    @Override
    public void setNi(float ni) {
        this.ni = ni;
    }

    @Override
    public String getMapKs() {
        return this.mapKs;

    }

    @Override
    public void setMapKs(String mapKs) {
        this.mapKs = mapKs;
    }

    @Override
    public String toString()
    {
        return "Mtl[" +
       		"name=" + name + "," +
       		"ka=" + ka + "," +
       		"kd=" + kd + "," +
            "ks=" + ks + "," +
            "mapKd=" + mapKd + "," +
            "ns=" + ns + "," +
            "d=" + d + "]";
    }

    
}
