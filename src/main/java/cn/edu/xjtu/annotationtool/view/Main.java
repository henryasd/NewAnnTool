package cn.edu.xjtu.annotationtool.view;



import cn.edu.xjtu.annotationtool.model.*;
import cn.edu.xjtu.annotationtool.utils.FileUtils;
import org.apache.log4j.Logger;
import org.bytedeco.javacv.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import static cn.edu.xjtu.annotationtool.utils.ShowWindow.run;


/**
 * Created by Henry on 2017/3/30.
 */

/**
 * 主界面
 */
public class Main extends JFrame implements ActionListener{
    private JMenuBar jmenuBar;
    private JMenu fileMenu;
    private JMenu deleteMenu;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem deleteMenuItem;
    private JButton nextButton;
    private JButton previousButton;
    private JButton deleteButton;
    private AnnotationArea annotationArea;
    //private static ExecutorService exec = Executors.newCachedThreadPool();
//    private File imageDir;
    private List<File> fileList;
//    private File imageFile;
    private File videoFile;
//    private File nextImageFile;
    private int nextButtonNum = 0;
    private JToolBar jToolBar;
    private JButton labelTailSide;
    private JButton labelHeadSide;
    private JButton labelTail;
    private JButton labelHead;

    private JButton labelPerson;
    private JButton labelTricycle;
    private JButton labelSign;
    private JButton labelBike;
    private JButton fengeButton1;
    private JButton fengeButton2;
    private JButton fengeButton3;

    private Image iconImage;
    private JButton jButton;
    private JMenu help;
    private JMenuItem aboutMenuItem;

    private JTextField xsTextField;
    private JLabel xsLabel;
    private JButton okButton;
    private JPanel xsPanel;
    private JPanel carTypePanel;
    private JLabel carTypeLabel;
    private JTextField carTypeTextField;
    private JButton carTypeOkButton;
    private JPanel statusPanel;

    private JButton laneButton;
    private Icon laneIcon;
    private JPanel lanePanel;
    private JLabel laneLabel;
    private JTextField laneTextField;
    private JButton laneOKButton;
    private Boolean stop;
    private File videoDir;
    private int frameIndex;
    private BufferedImage bufferedImage;
    private JButton stopButton;
    private TreeMap<Integer, List<Annotation>> oneAnnTreeMap;
    private String annotator;
    private List<IndexImage> imageList;
    private String tmpPath;
    private File tmpFile;
    private int frameNum;
    private JLabel annotaorLabel;
    private JTextField annotatorTextField;
    private JButton annotatorOKButton;
    private JPanel annotatorPanel;
    private JButton reviewButton;
    private int imgWidth;
    private int imgHeight;
    private double scaling;
    private JLabel personLabel;
    private JTextField personTF;
    private JButton personOKButton;
    private JPanel personPanel;
    private JButton btSaveImg;
    private JMenuItem miSaveImg;
    private JPanel frameRatePanel;
    private JLabel frameRateLabel;
    private JLabel fpsLabel;
    private JComboBox frameRateCB;
//    private JCheckBox saveImgCB;
//    private boolean saveImgFlag;

//    private cn.edu.xjtu.annotationtool.model.Type type;
//    private String videoName;

    private Icon tailSideIcon, headSideIcon, deleteIcon,nextIcon, previousIcon, tailIcon, headIcon, personIcon,
            tricycleIcon, signIcon, bikeIcon, fengeIcon, stopIcon, reviewIcon, saveImgIcon;
    public static Logger logger = Logger.getLogger(Main.class);
    public Main() {
//        saveImgFlag = false;
//        saveImgCB = new JCheckBox("Save Image", false);
//        frameRatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        frameRateLabel = new JLabel("Frame Rate: ");
//        fpsLabel = new JLabel("FPS");
//        frameRateCB = new JComboBox();
//        frameRatePanel.add(frameRateLabel);
//        frameRatePanel.add(frameRateCB);
//        frameRateCB.addItem("5");
//        frameRateCB.addItem("10");
//        frameRateCB.add
        stop = false;
        scaling = 1;
        jmenuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        deleteMenu = new JMenu("Delete");
        openMenuItem = new JMenuItem("Open");
        saveMenuItem = new JMenuItem("Save");
        deleteMenuItem = new JMenuItem("Delete Last");
        miSaveImg = new JMenuItem("Export Images");
//        imageDir = new File("");
        fileList = new ArrayList<File>();
//        imageFile = new File("");
//        nextImageFile = new File("");
        jToolBar = new JToolBar();
        help = new JMenu("Help");
        aboutMenuItem = new JMenuItem("About");

        tailSideIcon = new ImageIcon(getClass().getResource("/icon/tailSideIcon.png"));
        headSideIcon = new ImageIcon(getClass().getResource("/icon/headSideIcon.png"));
        deleteIcon = new ImageIcon(getClass().getResource("/icon/deleteIcon.png"));
        nextIcon = new ImageIcon(getClass().getResource("/icon/nextIcon.png"));
        previousIcon = new ImageIcon(getClass().getResource("/icon/previousIcon.png"));
        tailIcon = new ImageIcon(getClass().getResource("/icon/tailIcon.png"));
        headIcon = new ImageIcon(getClass().getResource("/icon/headIcon.png"));
        personIcon = new ImageIcon(getClass().getResource("/icon/personIcon.png"));
        tricycleIcon = new ImageIcon(getClass().getResource("/icon/tricycleIcon.png"));
        signIcon = new ImageIcon(getClass().getResource("/icon/signIcon.png"));
        bikeIcon = new ImageIcon(getClass().getResource("/icon/bikeIcon.png"));
        fengeIcon = new ImageIcon(getClass().getResource("/icon/fenge.png"));
        laneIcon = new ImageIcon(getClass().getResource("/icon/lane.png"));
        stopIcon = new ImageIcon(getClass().getResource("/icon/stop.png"));
        reviewIcon = new ImageIcon(getClass().getResource("/icon/review.png"));
        saveImgIcon = new ImageIcon(getClass().getResource("/icon/saveImg.png"));

        labelTailSide = new JButton("Car Tail Side",tailSideIcon);
        labelHeadSide = new JButton("Car Head Side",headSideIcon);
        labelTail = new JButton("Car Tail",tailIcon);
//        labelTail.setPreferredSize(new Dimension(80,30));
        labelHead = new JButton("Car Head", headIcon);
//        labelHead.setPreferredSize(new Dimension(80,30));
        labelPerson = new JButton("Person",personIcon);
//        labelPerson.setPreferredSize(new Dimension(100,30));
        labelSign = new JButton("Traffic Sign",signIcon);
//        labelSign.setPreferredSize(new Dimension(150,30));
        labelTricycle = new JButton("Tricycle",tricycleIcon);
//        labelTricycle.setPreferredSize(new Dimension(100,30));
        labelBike = new JButton("Bike", bikeIcon);
//        labelBike.setPreferredSize(new Dimension(100,30));
        laneButton = new JButton("Lane",laneIcon);
//        laneButton.setPreferredSize(new Dimension(100,30));
        deleteButton = new JButton("Delete Last", deleteIcon);
//        deleteButton.setPreferredSize(new Dimension(150,30));
        nextButton = new JButton("Continue",nextIcon);
        nextButton.setPreferredSize(new Dimension(100,30));
        stopButton = new JButton("Stop",stopIcon);
        stopButton.setPreferredSize(new Dimension(80,30));
        previousButton = new JButton("Previous",previousIcon);
        fengeButton1 = new JButton("",fengeIcon);
        fengeButton2 = new JButton("",fengeIcon);
        fengeButton3 = new JButton("",fengeIcon);
        reviewButton = new JButton("Review", reviewIcon);
        personLabel = new JLabel("Person Type: ");
        personTF = new JTextField(10);
        personOKButton = new JButton("OK");
        btSaveImg = new JButton("Export Images", saveImgIcon);
//        frameIndexLabel = new JLabel("Frame: ");


        xsLabel = new JLabel("Traffic Sign:");
        xsTextField = new JTextField(10);
        okButton = new JButton("OK");
        xsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        carTypeLabel = new JLabel("Vehicle Type:");
        carTypeTextField = new JTextField(10);
        carTypeOkButton = new JButton("OK");
        laneLabel = new JLabel("Lane Type:");
        laneTextField = new JTextField(10);
        laneOKButton = new JButton("OK");
        annotaorLabel = new JLabel("Annotator:");
        annotatorTextField = new JTextField(10);
        annotatorOKButton = new JButton("OK");
        annotatorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        annotatorPanel.add(annotaorLabel);
        annotatorPanel.add(annotatorTextField);
        annotatorPanel.add(annotatorOKButton);
        lanePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lanePanel.add(laneLabel);
        lanePanel.add(laneTextField);
        lanePanel.add(laneOKButton);

        xsPanel.add(xsLabel);
        xsPanel.add(xsTextField);
        xsPanel.add(okButton);
        xsPanel.setSize(400,20);
        carTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        carTypePanel.add(carTypeLabel);
        carTypePanel.add(carTypeTextField);
        carTypePanel.add(carTypeOkButton);
        carTypePanel.setSize(400,20);
        personPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        personPanel.add(personLabel);
        personPanel.add(personTF);
        personPanel.add(personOKButton);

//        carTypePanel.add(xsPanel);
        statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        statusPanel.add(carTypePanel);
        statusPanel.add(new JLabel("    "));
        statusPanel.add(xsPanel);
        statusPanel.add(new JLabel("    "));
        statusPanel.add(lanePanel);
        statusPanel.add(new JLabel("    "));
        statusPanel.add(personPanel);
        statusPanel.add(new JLabel("    "));

        statusPanel.add(annotatorPanel);





        labelTailSide.setToolTipText("标注车尾和侧面，快捷键ALT + Q");
        labelHeadSide.setToolTipText("标标注车头和侧面，快捷键ALT + W");
        labelTail.setToolTipText("标注车尾，快捷键 ALT + E");
        labelHead.setToolTipText("标注车头，快捷键 ALT + R");

        labelPerson.setToolTipText("标注行人，快捷键 ALT + T");
        labelTricycle.setToolTipText("标注三轮车，快捷键 ALT + F");
        labelSign.setToolTipText("标注交通标志，快捷键 ALT + G");
        labelBike.setToolTipText("标注自行车，快捷键 ALT + C");

        deleteButton.setToolTipText("删除上一个标注, ALT + A");
        nextButton.setToolTipText("继续, 快捷键 ALT + D");
        stopButton.setToolTipText("暂停, 快捷键 ALT + S");
        laneButton.setToolTipText("标注道路边线，快捷键 ALT + V");

        jToolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        jToolBar.add(labelTailSide);
        jToolBar.add(labelHeadSide);
        jToolBar.add(labelTail);
        jToolBar.add(labelHead);
        jToolBar.add(fengeButton1);
        jToolBar.add(labelPerson);
        jToolBar.add(labelTricycle);
        jToolBar.add(labelBike);
        jToolBar.add(fengeButton2);
        jToolBar.add(labelSign);
        jToolBar.add(laneButton);
        jToolBar.add(fengeButton3);
        jToolBar.add(deleteButton);
        jToolBar.add(previousButton);
        jToolBar.add(stopButton);
        jToolBar.add(nextButton);
        jToolBar.add(reviewButton);
        jToolBar.add(btSaveImg);

        labelTail.addActionListener(this);
        labelHead.addActionListener(this);
        labelHeadSide.addActionListener(this);
        labelTailSide.addActionListener(this);
        labelPerson.addActionListener(this);
        labelTricycle.addActionListener(this);
        labelSign.addActionListener(this);
        labelBike.addActionListener(this);
        laneButton.addActionListener(this);

        fileMenu.add(openMenuItem);
        openMenuItem.addActionListener(this);
        fileMenu.add(saveMenuItem);
        saveMenuItem.addActionListener(this);
        deleteMenu.add(deleteMenuItem);
        deleteMenuItem.addActionListener(this);
        help.add(aboutMenuItem);
        aboutMenuItem.addActionListener(this);
        jmenuBar.add(fileMenu);
        jmenuBar.add(deleteMenu);
        jmenuBar.add(help);
        help.addActionListener(this);
        deleteMenu.addActionListener(this);
        nextButton.addActionListener(this);
        previousButton.addActionListener(this);
        deleteButton.addActionListener(this);
        okButton.addActionListener(this);
        xsTextField.addActionListener(this);
        carTypeOkButton.addActionListener(this);
        carTypeTextField.addActionListener(this);
        laneTextField.addActionListener(this);
        laneOKButton.addActionListener(this);
        stopButton.addActionListener(this);
        annotatorTextField.addActionListener(this);
        annotatorOKButton.addActionListener(this);
        reviewButton.addActionListener(this);
        personTF.addActionListener(this);
        personOKButton.addActionListener(this);
        btSaveImg.addActionListener(this);
        miSaveImg.addActionListener(this);
        fileMenu.add(miSaveImg);
//        saveImgCB.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(saveImgCB.isSelected()){
//                    saveImgFlag = true;
//                }
//                else{
//                    saveImgFlag = false;
//                }
//            }
//        });

        labelTailSide.setMnemonic(KeyEvent.VK_Q);
        labelHeadSide.setMnemonic(KeyEvent.VK_W);
        labelTail.setMnemonic(KeyEvent.VK_E);
        labelHead.setMnemonic(KeyEvent.VK_R);
        deleteButton.setMnemonic(KeyEvent.VK_A);
        stopButton.setMnemonic(KeyEvent.VK_S);
        nextButton.setMnemonic(KeyEvent.VK_D);
        labelPerson.setMnemonic(KeyEvent.VK_T);
        labelTricycle.setMnemonic(KeyEvent.VK_F);
        labelSign.setMnemonic(KeyEvent.VK_G);
        labelBike.setMnemonic(KeyEvent.VK_C);
        laneButton.setMnemonic(KeyEvent.VK_V);
        imageList = new LinkedList<IndexImage>();
        setJMenuBar(jmenuBar);
        setLayout(new BorderLayout());
        annotationArea = new AnnotationArea(this);

        JScrollPane jsp = new JScrollPane(annotationArea);
        add(jsp,BorderLayout.CENTER);
        add(jToolBar,BorderLayout.NORTH);
        add(statusPanel,BorderLayout.SOUTH);
        oneAnnTreeMap = new TreeMap<Integer, List<Annotation>>();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                int option = JOptionPane.showConfirmDialog(Main.this, "是否保存标注文件？","提示",JOptionPane.YES_NO_CANCEL_OPTION);
                if(option == JOptionPane.YES_OPTION) {
                    opSave();
                    System.exit(0);
                }
                else if(option == JOptionPane.NO_OPTION){
                    System.exit(0);
                }
//                else if(option == JOptionPane.CANCEL_OPTION){
//                    return;
//                }

            }
        });
    }

    /**
     * 主函数入口
     * @param args
     */
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e){
            e.printStackTrace();
        }
//        public
        run(new Main());
    }

    /**
     * Action perform function
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == laneButton){
            switchModel(laneButton, Model.LANE);
        }
        if(e.getSource() == labelBike){
            switchModel(labelBike,Model.BIKE);
        }
        if(e.getSource() == labelPerson){
            switchModel(labelPerson,Model.PERSON);
        }
        if(e.getSource() == labelSign){
            switchModel(labelSign,Model.TRAFFIC_SIGN);
        }
        if(e.getSource() == labelTricycle){
            switchModel(labelTricycle,Model.TRICYCLE);
        }
        if(e.getSource() == aboutMenuItem){
            System.out.println("about....");
            opAbout();
        }
        if(e.getSource() == labelTail){
            switchModel(labelTail,Model.CAR_TAIL);
        }
        else if(e.getSource() == labelHead){
            switchModel(labelHead,Model.CAR_HEAD);
        }
        if(e.getSource() == labelHeadSide){
            switchModel(labelHeadSide,Model.CAR_HEAD_SIDE);
        }
        if(e.getSource() == labelTailSide){
            switchModel(labelTailSide,Model.CAR_TAIL_SIDE);
        }
        if(e.getSource() == nextButton){
            opContinue();
//            annotationArea.setStop(false);
//            new Thread(annotationArea).start();
//            operateNe();
        }
        if(e.getSource() == previousButton){
            operatePreviouseButton();
        }
        if(e.getSource() == deleteMenuItem || e.getSource() == deleteButton){
            opDelete();
        }
        if(e.getSource() == saveMenuItem) {
            opSave();

        }
        else if(e.getSource() == okButton || e.getSource() == xsTextField){
            String speed = xsTextField.getText();
            annotationArea.setxsLabel(Integer.parseInt(speed));
        }
        else if(e.getSource() == okButton || e.getSource() == xsTextField){
            String speed = xsTextField.getText();
            annotationArea.setxsLabel(Integer.parseInt(speed));
        }
        else if(e.getSource() == laneOKButton || e.getSource() == laneTextField){
            String label = laneTextField.getText();
            annotationArea.setLaneLabel(Integer.parseInt(label));
        }

        else if(e.getSource() == carTypeOkButton || e.getSource() == carTypeTextField){
            String label = carTypeTextField.getText();
            annotationArea.setCarLabel(Integer.parseInt(label));
        }

        else if(e.getSource() == openMenuItem){
            opOpen();
        }
        else if(e.getSource() == stopButton){
            annotationArea.setStop(true);
            logger.debug("set stop " + true);
        }
        else if(e.getSource() == annotatorOKButton || e.getSource() == annotatorTextField){
            annotator = annotatorTextField.getText();
        }
        else if(e.getSource() == personOKButton || e.getSource() == personTF){
            String label = personTF.getText();
            if(label != null) {
                annotationArea.setPersonLabel(label);
            }
        }
        else if(e.getSource() == btSaveImg || e.getSource() == miSaveImg){
            opSaveImg();
        }
        else if(e.getSource() == reviewButton){
            logger.debug("press review button");
            xsTextField.setText("");
            carTypeTextField.setText("");
            laneTextField.setText("");
//        if(jButton != null){
//            jButton.setForeground(Color.ORANGE);
//        }
            List<Annotation> annList = annotationArea.getList();

            int currentFrameIndex = annotationArea.getFrameIndex();

            if(annList != null && !annList.isEmpty()){
                oneAnnTreeMap.put(currentFrameIndex, annList);
//                double scaling = annotationArea.getScaling();
//                    add(new OneAnnotation(currentFrameIndex, annList));
                FileUtils.saveFile(oneAnnTreeMap, videoFile, annotator, scaling);
            }
            int currentImageIndex = annotationArea.getImageIndex();

//            if(currentImageIndex > 0){
            if(currentImageIndex < imageList.size()) {
                annotationArea.setImageIndex(0);
            }else if(currentImageIndex == imageList.size()){
                annotationArea.setImageList(imageList);
            }

//            }
        }
//        else if()

    }

    /**
     * save image if need
     */

    private void opSaveImg() {
//        Map<Integer, BufferedImage> imgMap = new TreeMap<Integer, BufferedImage>();
        if(imageList != null) {
            for (IndexImage ii : imageList) {
//            imgMap.put(ii.getFrameIndex(), ii.getImage());
                int frameIndex = ii.getFrameIndex();
                if (oneAnnTreeMap.containsKey(frameIndex)) {
//                    BufferedImage img = (BufferedImage) oneAnnTreeMap.get(frameIndex);
                    BufferedImage img = ii.getImage();
                    if (img != null) {
                        FileUtils.saveImg(img, frameIndex, videoFile);
                    }
                }
            }
        }
//        for(Integer frameIndex : oneAnnTreeMap.keySet()){
//            FileUtils.saveImg(imgMap.get(frameIndex), frameIndex, videoFile);
//        }

    }

    /**
     * previous button action perform
     */

    private void operatePreviouseButton() {
        logger.debug("press previouse button");
        xsTextField.setText("");
        carTypeTextField.setText("");
        laneTextField.setText("");
//        if(jButton != null){
//            jButton.setForeground(Color.ORANGE);
//        }
        List<Annotation> annList = annotationArea.getList();

        int currentFrameIndex = annotationArea.getFrameIndex();

        if(annList != null && !annList.isEmpty()){
            oneAnnTreeMap.put(currentFrameIndex, annList);
//            double scaling = annotationArea.getScaling();
//                    add(new OneAnnotation(currentFrameIndex, annList));
            FileUtils.saveFile(oneAnnTreeMap, videoFile, annotator, scaling);
        }
        int currentImageIndex = annotationArea.getImageIndex();
        if(currentImageIndex > 0){
            annotationArea.setImageIndex(--currentImageIndex);

        }
    }

    /**
     * cpntinue button action perform
     */
    private void opContinue() {
//        annotationArea.setStop(false);
        xsTextField.setText("");
        carTypeTextField.setText("");
        laneTextField.setText("");
//        if(jButton != null){
//            jButton.setForeground(Color.ORANGE);
//        }
        List<Annotation> annList = annotationArea.getList();

        int currentFrameIndex = annotationArea.getFrameIndex();

        if(annList != null && !annList.isEmpty()){
            oneAnnTreeMap.put(currentFrameIndex, annList);

//            double scaling = annotationArea.getScaling();
//                    add(new OneAnnotation(currentFrameIndex, annList));
            FileUtils.saveFile(oneAnnTreeMap, videoFile, annotator, scaling);
//            if(saveImgFlag == true){
//
//            }
        }

        if(currentFrameIndex <frameNum){
            annotationArea.goOn();
        }
//        else{
//            Toolkit.getDefaultToolkit().beep();
//            JOptionPane.showMessageDialog(null,"视频已标记完","提示",JOptionPane.INFORMATION_MESSAGE);
//            return;
//        }


    }

    /**
     * save menu item action perform
     */
    private void opSave() {
//        List<Annotation> list = annotationArea.getList();

        List<Annotation> annList = annotationArea.getList();

        int currentFrameIndex = annotationArea.getFrameIndex();

        if(annList != null && !annList.isEmpty()){
            oneAnnTreeMap.put(currentFrameIndex, annList);

//            double scaling = annotationArea.getScaling();
//                    add(new OneAnnotation(currentFrameIndex, annList));
            FileUtils.saveFile(oneAnnTreeMap, videoFile, annotator, scaling);
//            if(saveImgFlag == true){
//
//            }
        }
    }

    /**
     * open menu item action perform
     */
    private void opOpen() {
        JFileChooser jFileChooser = new JFileChooser();

        int rval = jFileChooser.showOpenDialog(this);
        if(rval == JFileChooser.APPROVE_OPTION) {
            videoDir = jFileChooser.getCurrentDirectory();
            videoFile = jFileChooser.getSelectedFile();
            setTitle("AnnotationTool----------" +"Loading Video "+ videoFile.getName() );
            oneAnnTreeMap.clear();
            imageList.clear();
            new ReadVideoFileThread().run();
            setTitle("AnnotationTool-" + videoFile.getName());
//            double scaling = annotationArea.getScaling();
//            oneAnnTreeMap = FileUtils.readFile(videoFile,scaling);
            oneAnnTreeMap = FileUtils.readFile(videoFile,scaling);
            annotator = FileUtils.getAnnotator();
            annotatorTextField.setText(annotator);
            annotationArea.setScaling(scaling);
            annotationArea.setAnnTreeMap(oneAnnTreeMap);
            annotationArea.setFrameNum(frameNum);
            annotationArea.setImageList(imageList);
//            double scaling = annotationArea.getScaling();


        }


    }

    /**
     * switch annotation model
     * @param button
     * @param model
     */
    private void switchModel(JButton button, Model model){
        logger.debug("switch Model: " + model.toString());
        if(jButton != null){
            jButton.setForeground(Color.BLACK);
        }

        jButton = button;
        jButton.setForeground(Color.ORANGE);
        annotationArea.setModel(model);

    }

    /**
     * about menu item action perform
     */
    private void opAbout() {
        JOptionPane.showMessageDialog(null,"Ver: 4.0.0.0\nBy Liheng Wu\nForward&XJTU\nJune.9.2017","About",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * delete last button action perform
     */

    private void opDelete() {
        annotationArea.deleteLastCoordinates();
    }

//    private void operateNextButton() {
//
//    }

    /**
     * calculate scaling
     * @param imgWidth video width
     * @param imgHeight video height
     * @return scaling
     */
    public double getScaling(int imgWidth, int imgHeight){
    double scale =1;
    double xScale = 1;
    double yScale = 1;
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    int width = dimension.width;
    int height = (int)(dimension.height * 0.75);
    if(imgWidth > 0 && imgHeight > 0){
        xScale = ((double) width)/imgWidth;
        yScale = ((double)height)/imgHeight;
    }
    scale = Math.min(xScale, yScale);
    scale = Math.min(scale, 1);
    logger.debug("getScalling return " + scale);
    return scale;

}

    /**
     * read video file thread
     */
    class ReadVideoFileThread extends Thread{

        @Override
        public void run() {
            FFmpegFrameGrabber ffGrabber = new FFmpegFrameGrabber(videoFile);
            try {
                ffGrabber.start();
                frameNum = ffGrabber.getLengthInFrames();
                imgHeight = ffGrabber.getImageHeight();
                imgWidth = ffGrabber.getImageWidth();
                scaling = getScaling(imgWidth, imgHeight);

                int i = 0;
                while (i < ffGrabber.getLengthInFrames()){

                    frameIndex = i;
                    org.bytedeco.javacv.Frame frame = ffGrabber.grabFrame();
                    if(i%5 == 0) {
                        bufferedImage = new Java2DFrameConverter().convert(frame);
                        if(bufferedImage != null){

                            imageList.add(new IndexImage(frameIndex, bufferedImage));
                        }
                    }
                    i++;
                }
//                frameNum = f;

//                new Thread(annotationArea).start();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}






