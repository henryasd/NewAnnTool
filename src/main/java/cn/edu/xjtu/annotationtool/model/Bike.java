package cn.edu.xjtu.annotationtool.model;

import java.awt.*;

/**
 * Created by Henry on 2017/6/9.
 */
public class Bike extends Annotation {
    private String type;
    private Point p1;
    private Point p2;
    private Point p3;
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

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public Point getP3() {
        return p3;
    }

    public void setP3(Point p3) {
        this.p3 = p3;
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

    public Bike(String type, Point p1, Point p2, Point p3, String label1, String label2, String label3, String label4) {
        this.type = type;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.label1 = label1;
        this.label2 = label2;
        this.label3 = label3;
        this.label4 = label4;
        this.scaling = 1;
    }
    public Bike(){
        this.scaling = 1;

    }
    @Override
    public String toString() {
        return  type + " " +
                (int)(p1.getX()/scaling) + " " +
                (int)(p1.getY()/scaling) + " " +
                (int)(p2.getX()/scaling) + " " +
                (int)(p2.getY()/scaling) + " " +
                (int)(p3.getX()/scaling) + " " +
                (int)(p3.getY()/scaling) + " " +
                label1 + " " +
                label2 + " " +
                label3 + " " +
                label4 + "\r\n";
    }
}
