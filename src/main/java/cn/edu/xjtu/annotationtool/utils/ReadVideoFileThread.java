//package cn.edu.xjtu.annotationtool.utils;
//
//import org.bytedeco.javacv.FFmpegFrameGrabber;
//import org.bytedeco.javacv.FrameGrabber;
//import org.bytedeco.javacv.Java2DFrameConverter;
//
//import java.io.File;
//
///**
// * Created by Henry on 2017/6/12.
// */
//public class ReadVideoFileThread implements Runnable {
//    public Thread t;
//    private boolean suspended = false;
//    private File videoFile;
//
//    public ReadVideoFileThread(File videoFile){
//        this.videoFile = videoFile;
//    }
//    @Override
//    public void run() {
//        FFmpegFrameGrabber ffGrabber = new FFmpegFrameGrabber(videoFile);
//        try {
//            ffGrabber.start();
//            int i = 0;
//            while (i < ffGrabber.getLengthInFrames()){
////                frameIndex = i;
//                org.bytedeco.javacv.Frame frame = ffGrabber.grabFrame();
//                bufferedImage = new Java2DFrameConverter().convert(frame);
//                annotationArea.setImage(bufferedImage);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                i++;
////                    System.out.println("show frame :" + frameIndex);
//                logger.debug("show frame :" + frameIndex);
//
//            }
//        } catch (FrameGrabber.Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
