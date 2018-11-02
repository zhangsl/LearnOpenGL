package me.light.learnopengl.shape;

/**
 * Created by shangjie on 2018/11/2.
 */

public class ShapeCreator {
    private Class<? extends Shape> mShapeClass;

    public void setShapeClass(Class<? extends Shape> clazz) {
        mShapeClass = clazz;
    }

    public Shape create() {
        Shape shape = null;
        try {
            shape = mShapeClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shape;
    }

}
