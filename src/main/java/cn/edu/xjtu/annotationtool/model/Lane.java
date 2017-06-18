package cn.edu.xjtu.annotationtool.model;

import java.awt.*;
import java.util.List;

/**
 * Created by Henry on 2017/6/9.
 */
public class Lane extends Annotation {
    private String type;
    private List<Point> pointList;
    private String label1;
    private String label2;
    private String label3;
    private String label4;
    private double scaling;

    public double getScaling() {
        return scaling;
    }

    public void setScaling(double scaling) {
        this.scaling = scaling;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public List<Point> getPointList() {
        return pointList;
    }

    public void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }

    public String getLabel1() {
        return label1;
    }

    public void setLabel1(String label1) {
        this.label1 = label1;
    }

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String label2) {
        this.label2 = label2;
    }

    public String getLabel3() {
        return label3;
    }

    public void setLabel3(String label3) {
        this.label3 = label3;
    }

    public String getLabel4() {
        return label4;
    }

    public void setLabel4(String label4) {
        this.label4 = label4;
    }

    public Lane(String type, List<Point> pointList, String label1, String label2, String label3, String label4) {
        this.type = type;
        this.pointList = pointList;
        this.label1 = label1;
        this.label2 = label2;
        this.label3 = label3;
        this.label4 = label4;
        this.scaling = 1;
    }
    public Lane(){
        this.scaling = 1;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Point p: pointList){
            sb.append((int)(p.getX()/scaling)).append(" ").append((int)(p.getY()/scaling)).append(" ");
        }

        return  type + " " +
                sb.toString()+
                label1 + " " +
                label2 + " " +
                label3 + " " +
                label4 + "\r\n";
    }
}
