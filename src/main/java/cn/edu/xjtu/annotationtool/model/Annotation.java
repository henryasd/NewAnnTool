package cn.edu.xjtu.annotationtool.model;

/**
 * Created by Henry on 2017/6/9.
 */
public  abstract class Annotation {
    private String type;

    public abstract String getType();
    public abstract void setType(String type);
    public abstract String toString();
    public abstract void setScaling(double scaling);

}
