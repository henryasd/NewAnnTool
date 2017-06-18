package cn.edu.xjtu.annotationtool.model;

import java.awt.image.BufferedImage;

/**
 * Created by Henry on 2017/6/12.
 */
public class IndexImage {
    private int frameIndex;
    private BufferedImage image;

    public IndexImage() {
    }

    public IndexImage(int frameIndex, BufferedImage image) {
        this.frameIndex = frameIndex;
        this.image = image;
    }

    public int getFrameIndex() {
        return frameIndex;
    }

    public void setFrameIndex(int frameIndex) {
        this.frameIndex = frameIndex;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
