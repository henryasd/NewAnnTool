package cn.edu.xjtu.annotationtool.utils;

import cn.edu.xjtu.annotationtool.model.*;
import cn.edu.xjtu.annotationtool.model.Rectangle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.edu.xjtu.annotationtool.view.Main.logger;

/**
 * Created by Henry on 2017/6/10.
 */

/**
 *  file operate class
 */
public class FileUtils {
    /**
     * save annotation file
     * @param oneAnnTreeMap annotation data
     * @param videoFile video
     * @param annotator
     * @param scaling
     */
    private static String annotator;

    public static String getAnnotator() {
        return annotator;
    }

    public static void saveFile(TreeMap<Integer, List<Annotation>> oneAnnTreeMap, File videoFile,
                                String annotator, double scaling){
        String videoName = videoFile.getName().substring(0, videoFile.getName().lastIndexOf("."));
        String annotationPath = videoFile.getParent() + "\\" + videoName +"\\Annotation";
//        String imgPath = videoParentPath + "\\"+videoName+"\\image";
        File annDir = new File(annotationPath);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        File imgDir = new File(imgPath);
        if(!annDir.exists()){
            annDir.mkdirs();
        }

        if(oneAnnTreeMap != null && (!oneAnnTreeMap.isEmpty())) {
            File txtFile = new File(annotationPath + "\\" + videoFile.getName() + "_" + "OA" + ".txt");
            try {
                PrintWriter pw = new PrintWriter(txtFile);
                pw.println("Video_Name:" + videoFile.getName());
                pw.println("Annoator:" + annotator);
                pw.println("Date:" + sdf.format(new Date()));
//                pw.println(frameIndex+"");
                for(Map.Entry<Integer, List<Annotation>> entry : oneAnnTreeMap.entrySet()){
                    pw.println(entry.getKey());
                    for(Annotation annotation : entry.getValue()){
//                        Annotation newAnn = scal(annotation, scaling);
                        annotation.setScaling(scaling);
                        pw.print(annotation.toString());
                    }
                }
//                for(OneAnnotation oneAnnotation : oneAnnTreeMap){
//                    pw.print(oneAnnotation.toString());
//                }
                pw.flush();
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }


    }



    /**
     * read annotation from file
     * @param videoFile
     * @return 对应视屏文件的标注列表
     */
    public static TreeMap<Integer, List<Annotation>> readFile(File videoFile, double scaling){
        TreeMap<Integer, List<Annotation>> treeMap = new TreeMap<Integer, List<Annotation>>();
        String videoName = videoFile.getName().substring(0, videoFile.getName().lastIndexOf("."));

        String annotationPath = videoFile.getParent() + "\\" + videoName +"\\Annotation";

        File annFile = new File(annotationPath + "\\" + videoFile.getName() + "_" + "OA" + ".txt");
        if(scaling <= 0){
            scaling = 1;
        }
        if(annFile.exists()){
            try {
                BufferedReader br = new BufferedReader(new FileReader(annFile));
                String str = "";
                int lineNum = 0;
                int frameIndex = 0;
                List<Annotation> annList = new LinkedList<Annotation>();

//                OneAnnotation oneAnnotation = null;
                while ((str = br.readLine()) != null){
                    lineNum ++;
                    if(lineNum == 2){
                        String strs[] = str.split(":");
                        annotator = strs[1];
                    }
                    else if(lineNum > 3) {
//                        OneAnnotation oneAnnotation = new OneAnnotation();
                        Matcher matcher = Pattern.compile("^[0-9]+$").matcher(str);
                        if(matcher.find()){
                            frameIndex = Integer.valueOf(str);
                            logger.debug(lineNum + " read frameIndex is " + frameIndex);
                            annList = new LinkedList<Annotation>();
                            treeMap.put(frameIndex, annList);

//                            if(oneAnnotation != null){
//                                list.add(oneAnnotation);
//                            }
//                            oneAnnotation = new OneAnnotation();
//                            oneAnnotation.setFrameIndex(Integer.valueOf(str));
                        }
                        else {
                            Annotation ann = null;
                            String[] strs = str.split(" ");
                            if (strs[0].equals(Type.getCarHead()) || strs[0].equals(Type.getCarTail()) ||
                                    strs[0].equals(Type.getCarHeadSide()) || strs[0].equals(Type.getCarTailSide())) {
                                logger.debug(lineNum + " read car");
                                ann = new Car(strs[0], new Point((int)(Integer.parseInt(strs[1])*scaling),(int) (Integer.parseInt(strs[2])*scaling)),
                                        new Point((int)(Integer.parseInt(strs[3])*scaling), (int)(Integer.parseInt(strs[4])*scaling)),
                                        new Point((int)(Integer.parseInt(strs[5])*scaling), (int)(Integer.parseInt(strs[6])*scaling)),
                                        new Point((int)(Integer.parseInt(strs[7])*scaling), (int)(Integer.parseInt(strs[8])*scaling)),
                                        strs[9], strs[10], strs[11], strs[12]);
                            }

                            else if (strs[0].equals(Type.getBike())) {
                                logger.debug("read bike");
                                ann = new Bike(strs[0], new Point((int)(Integer.parseInt(strs[1])*scaling), (int)(Integer.parseInt(strs[2])*scaling)),
                                        new Point((int)(Integer.parseInt(strs[3])*scaling), (int)(Integer.parseInt(strs[4])*scaling)),
                                        new Point((int)(Integer.parseInt(strs[5])*scaling), (int)(Integer.parseInt(strs[6])*scaling)),
                                        strs[7], strs[8], strs[9], strs[10]);
                            } else if (strs[0].equals(Type.getPerson()) || strs[0].equals(Type.getTrafficSign()) ||
                                    strs[0].equals(Type.getTricycle())) {
                                logger.debug("read person || traffic_sign || tricycle");
                                ann = new Rectangle(strs[0], new Point((int)(Integer.parseInt(strs[1])*scaling), (int)(Integer.parseInt(strs[2])*scaling)),
                                        new Point((int) (Integer.parseInt(strs[3])*scaling), (int)(Integer.parseInt(strs[4])*scaling)),
                                        strs[5], strs[6], strs[7], strs[8]);
                            }

                            else if (strs[0].equals(Type.getLane())) {
                                logger.debug("read lane");
                                List<Point> laneList = new LinkedList<Point>();
                                for (int i = 1; i < strs.length - 5; i=i+2) {
                                    laneList.add(new Point((int)(Integer.parseInt(strs[i])*scaling), (int)(Integer.parseInt(strs[i + 1])*scaling)));
                                }
                                ann = new Lane(strs[0], laneList, strs[strs.length - 4], strs[strs.length - 3],
                                        strs[strs.length - 2], strs[strs.length - 1]);

                            }
//                            oneAnnotation.getList().add(ann);
                            if(ann != null) {
                                treeMap.get(frameIndex).add(ann);
                            }
                        }
//                        list.add(ann);
                    }
                }
//                list.add(oneAnnotation);
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return treeMap;


    }

    /**
     * save image
     * @param bufferedImage
     * @param frameIndex
     * @param videoFile
     */
    public static void saveImg(BufferedImage bufferedImage, Integer frameIndex, File videoFile) {
        String videoName = videoFile.getName().substring(0, videoFile.getName().lastIndexOf("."));
        String imgDirPath = videoFile.getParent() + "\\" + videoName +"\\Images";
        File imgDir = new File(imgDirPath);
        if(!imgDir.exists()){
            imgDir.mkdirs();
        }
        File imgFile = new File(imgDirPath + "\\" + videoFile.getName() + "_" +String.format("%05d", frameIndex) + ".jpg");
        try {
            ImageIO.write(bufferedImage, "jpg", imgFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
