//package cn.edu.xjtu.annotationtool.model;
//
//import java.util.List;
//
///**
// * Created by Henry on 2017/6/11.
// */
//public class OneAnnotation {
//    private int frameIndex;
//    private List<Annotation> list;
//    public OneAnnotation(){
//
//    }
//    public OneAnnotation(int frameIndex, List<Annotation> list) {
//        this.frameIndex = frameIndex;
//        this.list = list;
//    }
//
//    public int getFrameIndex() {
//        return frameIndex;
//    }
//
//    public void setFrameIndex(int frameIndex) {
//        this.frameIndex = frameIndex;
//    }
//
//    public List<Annotation> getList() {
//        return list;
//    }
//
//    public void setList(List<Annotation> list) {
//        this.list = list;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(frameIndex).append("\r\n");
//        for(Annotation ann : list){
//            sb.append(ann.toString());
//        }
//        return sb.toString();
//    }
//}
