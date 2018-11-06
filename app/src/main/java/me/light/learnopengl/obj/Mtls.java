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
import me.light.learnopengl.obj.DefaultMtl;

/**
 * Methods to create {@link me.light.learnopengl.obj.Mtl} instances
 */
public class Mtls
{
    /**
     * Creates a new default {@link me.light.learnopengl.obj.Mtl}
     * 
     * @param name The name of the material
     * @return The {@link me.light.learnopengl.obj.Mtl}
     */
    public static me.light.learnopengl.obj.Mtl create(String name)
    {
        return new me.light.learnopengl.obj.DefaultMtl(name);
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private Mtls()
    {
        // Private constructor to prevent instantiation
    }

}
