package cn.edu.xjtu.annotationtool.view;


import cn.edu.xjtu.annotationtool.model.*;
import cn.edu.xjtu.annotationtool.model.Rectangle;
//import org.w3c.dom.css.Rect;

//import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;
//import java.util.Timer;

import static cn.edu.xjtu.annotationtool.view.Main.logger;

/**
 * Created by Henry on 2017/3/30.
 */

/**
 * annotation area
 */
public class AnnotationArea extends JPanel {
    //    private Image image;
    private long mouseNum;
    //    private long mouseRectNum;
//    private long mouseDrag;
//    private long mouseRelease;
    private List<Annotation> list = new LinkedList<Annotation>();
    private File imageFile;
    private Boolean isLeft; //左键为0，右键为1
    //    private String carPart;
    private Color color;
    private int point = 0;
    private Annotation tmpAnnotation;
    private Annotation draggAnnotation;
    //    private boolean isRect;
    private int imgWidth;
    private int imgHeight;
    private Model model;
    private int xsLabel;
    private int carLabel;
    //    private List<Lane> laneList;
    private int laneLabel;
    private boolean work = true;
    private boolean stop = false;
    private List<IndexImage> imageList;
    private BufferedImage currentImage;
    private int delay = 300;
    private int imageIndex = 0;
    private int frameIndex;
    private ReadThread readThread = new ReadThread();
    private cn.edu.xjtu.annotationtool.model.Type annType;
    private Map<Integer, List<Annotation>> annTreeMap;
    private double scaling;
    private int frameNum;


    public void setScaling(double scaling) {
        this.scaling = scaling;
    }
    //    public double getScaling(){
//        double scale =1;
//        double xScale = 1;
//        double yScale = 1;
//        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
//        int width = dimension.width;
//        int height = (int)(dimension.height * 0.8);
//        if(imgWidth > 0 && imgHeight > 0){
//            xScale = ((double) width)/imgWidth;
//            yScale = ((double)height)/imgHeight;
//        }
//        scale = Math.min(xScale, yScale);
//        scale = Math.min(scale, 1);
//        logger.debug("getScalling return " + scale);
//        return scale;
//
//    }

    public int getImageIndex() {
        return imageIndex;
    }

    /**
     * set image index for show
     * @param imageIndex
     */
    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
        currentImage = imageList.get(imageIndex).getImage();
        frameIndex = imageList.get(imageIndex).getFrameIndex();
        list = annTreeMap.get(frameIndex);
        if(list == null){
            list = new LinkedList<Annotation>();
        }
        repaint();
        if(imageIndex == 0){
            goOn();
        }
    }

    public Map<Integer, List<Annotation>> getAnnTreeMap() {
        return annTreeMap;
    }

    public void setAnnTreeMap(Map<Integer, List<Annotation>> annTreeMap) {
        this.annTreeMap = annTreeMap;
    }

    public int getFrameIndex() {
        return frameIndex;
    }

//    public void setFrameIndex(int frameIndex) {
//        this.frameIndex = frameIndex;
//    }
//
//    public void setWork(boolean work) {
//        this.work = work;
//    }

    public void setStop(boolean stop) {
        this.stop = stop;
//        readThread.stop();

    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setImageList(List<IndexImage> imageList) {
        this.imageList = imageList;
        new Thread(new ReadThread()).start();
//        walkImageList();


    }

    /**
     * set lane label
     * @param laneLabel
     */
    public void setLaneLabel(int laneLabel) {
//        this.laneLabel = laneLabel;
        if (list != null && !list.isEmpty()) {
            Annotation annotation = list.get(list.size() - 1);
            if (annotation.getType().equals(Type.getLane())) {
                ((Lane) annotation).setLabel1(laneLabel + "");
            }
        }
        repaint();
    }

    /**
     * set person label
     * @param personLabel
     */
    public void setPersonLabel(String personLabel) {
        if (list != null && !list.isEmpty()) {
            Annotation annotation = list.get(list.size() - 1);
            if (annotation.getType().equals(Type.getPerson())) {
                ((Rectangle) annotation).setLabel1(personLabel);
            }
        }
        repaint();
//        this.personLabel = personLabel;
    }

    /**
     * set car label
     * @param carLabel
     */

    public void setCarLabel(int carLabel) {
//        this.carLabel = carLabel;
        if (!list.isEmpty()) {
            Annotation annotation = list.get(list.size() - 1);

            if (annotation.getType().equals(annType.getCarHead()) || annotation.getType().equals(annType.getCarTail())
                    || annotation.getType().equals(annType.getCarHeadSide())
                || annotation.getType().equals(annType.getCarTailSide())) {
//                System.out.println("Car Label:"+carLabel);
                ((Car)annotation).setLabel1(carLabel + "");
            }
//            else if (annotation.getType().equals(annType.getCarTail())) {
//                ((CarTail) annotation).setLabel1(carLabel + "");
//            }
            repaint();

        }
    }

    /**
     * set traffic sign label
     * @param xsLabel
     */
    public void setxsLabel(int xsLabel) {
//        this.xsLabel = xsLabel;
        if (!list.isEmpty()) {
            tmpAnnotation = list.get(list.size() - 1);
            if (tmpAnnotation.getType().equals(Type.getTrafficSign())) {
//                System.out.println("XSLabel: "+xsLabel);
                ((Rectangle)tmpAnnotation).setLabel1(xsLabel + "");

            }
//            else if(tmpCoordinates.getPart().equals("head") || tmpCoordinates.getPart().equals("tail")){
//                tmpCoordinates.setLabel1(xsLabel);
//            }
            repaint();

        }
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        logger.debug("set model: " + model.toString());
        this.model = model;
        mouseNum = 0;
    }


//    public void setMouseNum(long mouseNum) {
//        this.mouseNum = mouseNum;
//    }
//
//    public File getImageFile() {
//        return imageFile;
//    }


    public List getList() {
        return list;
    }

    public void setList(List<Annotation> list) {
        this.list = list;
        repaint();
    }

    /**
     * show image and annotation
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
//        logger.debug("paint image");
        super.paintComponent(g);

        if (currentImage != null) {
//                scaling = getScaling();
//            logger.debug("scaling is "+ scaling);
            g.drawImage(currentImage, 0, 0, (int)(currentImage.getWidth()*scaling), (int)(currentImage.getHeight()*scaling), this);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g.setColor(Color.RED);
            g.drawString("Frame: " + frameIndex + " / " + frameNum, 20 , 20);
        }
//        }
        g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(2.0f));
        if (list != null && list.size() > 0) {
            for (Annotation annotation : list) {
                String type = annotation.getType();
                color = switchColor(type);
//            color = (tmpCarPart.equals("head")) ? Color.blue : Color.red;
                g2D.setColor(color);
//            g2D.setFont();

                if (type.equals(annType.getCarHead()) || type.equals(annType.getCarTail()) || type.equals(annType.getCarHeadSide())
                        || type.equals(annType.getCarTailSide())) {
                    logger.debug("paint car annotation");
                    Car car = (Car) annotation;
//                    Point p1 = car.getP1();
                    int x1 = (int) car.getP1().getX();
                    int y1 = (int) car.getP1().getY();

                    int x2 = (int) car.getP2().getX();
                    int y2 = (int) car.getP2().getY();
                    int x3 = (int) car.getP3().getX();
                    int y3 = (int) car.getP3().getY();
                    int x4 = (int) car.getP4().getX();
                    int y4 = (int) car.getP4().getY();


//                Point p1 = car.getP1();
//                Point p2 = car.getP2();
//                Point p3 = car.getP3();
//                Point p4 = car.getP4();
                    g2D.drawString("VT: " + car.getLabel1(), x1 + 2, y1 - 2);
                    if (x2 >= x1 && y2 >= y1) {
//                    if (Point2D.distance(x1, y1, x2, y2) <= 1) {
//                        x2 = x2 + 10;
//                        y2 = y2 + 10;
//                    }
                        if (x1 > 0 || y1 > 0) {

                            g2D.drawRect(x1, y1, x2 - x1, y2 - y1);
                        }

                    }
                    if (x3 > 0 || y3 > 0) {
                        if (x3 >= (x2 + x1) / 2) {

                            g2D.drawLine(x2, y2, x3, y3);
                            if (y4 > 0 && x4 > 0) {

                                g2D.drawLine(x3, y3, x4, y4);
                                g2D.drawLine(x2, y1, x4, y4);
                            }
                        }
                        if (x3 < (x1 + x2) / 2) {
                            g2D.drawLine(x1, y2, x3, y3);
                            if (y4 > 0) {

                                g2D.drawLine(x3, y3, x4, y4);
                                g2D.drawLine(x1, y1, x4, y4);
                            }
                        }


                    }
                    g2D.setColor(Color.green);
                    g2D.drawOval(x1 - 5, y1 - 5, 10, 10);
                    g2D.drawOval(x2 - 5, y2 - 5, 10, 10);
                    if (x3 > 0 || y3 > 0 || x4 > 0 || y4 > 0) {
                        g2D.drawOval(x3 - 5, y3 - 5, 10, 10);
                        g2D.drawOval(x4 - 5, y4 - 5, 10, 10);
                    }
                } else if (type.equals(annType.getPerson()) || type.equals(annType.getTricycle()) ||
                        type.equals(annType.getTrafficSign())) {
                    Rectangle rect = (Rectangle) annotation;

                    int x1 = (int) rect.getP1().getX();
                    int y1 = (int) rect.getP1().getY();
                    int x2 = (int) rect.getP2().getX();
                    int y2 = (int) rect.getP2().getY();
                    if(type.equals(Type.getPerson())){
                        g2D.drawString("PT: " + rect.getLabel1(), x1 + 2, y1 -2);
                    }
                    if (x2 >= x1 && y2 >= y1) {
//                    if (Point2D.distance(x1, y1, x2, y2) <= 1) {
//                        x2 = x2 + 10;
//                        y2 = y2 + 10;
//                    }
                        g2D.drawRect(x1, y1, x2 - x1, y2 - y1);
                        if (type.equals(annType.getTrafficSign())) {
                            g2D.drawString("TS: " + rect.getLabel1(), x1 + 2, y1 - 2);
                        }
                        g2D.setColor(Color.green);
                        g2D.drawOval(x1 - 5, y1 - 5, 10, 10);
                        g2D.drawOval(x2 - 5, y2 - 5, 10, 10);

                    }
                } else if (type.equals(annType.getBike())) {
                    Bike bike = (Bike) annotation;
                    int x1 = (int) bike.getP1().getX();
                    int y1 = (int) bike.getP1().getY();
                    int x2 = (int) bike.getP2().getX();
                    int y2 = (int) bike.getP2().getY();
                    int x3 = (int) bike.getP3().getX();
                    int y3 = (int) bike.getP3().getY();
                    if(x1 > 0 || y1 > 0){
                        g2D.drawOval(x1 - 5, y1 - 5, 10, 10);
                    }
                    if(x2 > 0 || y2 > 0){
                        g2D.drawOval(x2 - 5, y2 - 5, 10, 10);
                    }
                    if(x3 > 0 || y3 > 0){
                        g2D.drawOval(x3 - 5, y3 - 5, 10, 10);
                    }
                    if ((x1 > 0 || y1 > 0) && (x2 > 0 || y2 > 0) && x2 == x1 && y2 > y1) {

                        g2D.drawLine(x1, y1, x2, y2);
                        if ((x3 > 0 || y3 > 0) && x3 > x2) {
                            g2D.drawLine(x2, y2, x3, y3);
                            int x = x3 + (x1 - x2);
                            int y = y3 + (y1 - y2);
                            if (x > 0 && y > 0 && x < imgWidth * scaling && y < imgHeight * scaling) {
                                g2D.drawLine(x3, y3, x, y);
                                g2D.drawLine(x1, y1, x, y);
                            }
                        }
//                        g2D.setColor(Color.green);
//                        g2D.drawOval(x1 - 5, y1 - 5, 10, 10);
//                        g2D.drawOval(x2 - 5, y2 - 5, 10, 10);
//                        g2D.drawOval(x3 - 5, y3 - 5, 10, 10);
                    }
                } else if (type.equals(annType.getLane())) {
                    Lane lane = (Lane) annotation;
                    List<Point> pointList = lane.getPointList();
//                g2D.setColor(Color.BLACK);

                    if (!pointList.isEmpty()) {
                        for (int i = 0; i < pointList.size(); i++) {
//                        System.out.println("draw point "+ i);
                            g2D.setColor(Color.GREEN);
                            g2D.drawOval((int) pointList.get(i).getX() - 5, (int) pointList.get(i).getY()
                                    - 5, 10, 10);
                            g2D.setColor(Color.BLACK);
                            if (i < pointList.size() - 1) {
                                g2D.drawLine((int)pointList.get(i).getX(), (int)pointList.get(i).getY(),
                                        (int)pointList.get(i + 1).getX(), (int)pointList.get(i + 1).getY());
                            }
                        }

                    }
                    g2D.drawString("Lane: " + lane.getLabel1(), (int) pointList.get(0).getX() - 6, (int) pointList.get(0).getY() - 6);

                }

            }
        }
    }

    /**
     * switch color
     * @param type
     * @return
     */
    private Color switchColor(String type) {
        color = Color.red;
        if (type.equals(annType.getCarHead()) || type.equals(annType.getCarHeadSide())) {
            color = Color.BLUE;
        } else if (type.equals(annType.getCarTail()) || type.equals(annType.getCarTailSide())) {
            color = Color.RED;
        } else if (type.equals(annType.getPerson())) {
            color = Color.YELLOW;
        } else if (type.equals(annType.getTricycle())) {
            color = Color.MAGENTA;

        } else if (type.equals(annType.getTrafficSign())) {
            color = Color.CYAN;
        } else if (type.equals(annType.getBike())) {
            color = Color.GREEN;
        } else if (type.equals(annType.getLane())) {
            color = Color.BLACK;
        }
        return color;
    }

    public AnnotationArea(Main mainFrame) {

        mouseNum = 0;

        isLeft = false;
//        carPart = "tail";
        color = Color.red;
//        isRect = false;
        imageFile = null;
        model = Model.CAR_TAIL_SIDE;
        annType = new Type();
//        scaling = 1;
        list = new LinkedList<Annotation>();
        annTreeMap = new TreeMap<Integer, List<Annotation>>();
        isLeft = false;
        frameNum = 0;

        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
//        timer = new Timer(MULTI_CLICK_INTERVAL,this);

        addMouseListener(new MouseListenerA());
        addMouseMotionListener(new MouseMotionListenerB());


    }

    /**
     * remove the last annotation
     */
    public void deleteLastCoordinates() {
        mouseNum = 0;
//        if(model != Model.LANE){
        if (list != null && list.size() > 0) {
            list.remove(list.size() - 1);
        }
        repaint();
    }

    /**
     * go on show image
     */
    public void goOn() {
        stop = false;
        readThread.setSuspend(false);

//        walkImageList();
    }

    public void setFrameNum(int frameNum) {
        this.frameNum = frameNum;
    }


    /**
     * show image thread
     */
    class ReadThread implements Runnable {
        private boolean suspend = false;
        private String control = "";

        public void setSuspend(boolean suspend) {
            if (!suspend) {
                synchronized (control) {
                    control.notify();
                }
            }
            this.suspend = suspend;
        }

        @Override
        public void run() {
            for (imageIndex = 0; imageIndex < imageList.size(); imageIndex++) {
//                imageIndex = i;
                currentImage = imageList.get(imageIndex).getImage();
                imgWidth = currentImage.getWidth();
                imgHeight = currentImage.getHeight();
                frameIndex = imageList.get(imageIndex).getFrameIndex();
                list = annTreeMap.get(frameIndex);
                if(list == null){
                    list = new LinkedList<Annotation>();
                }

                logger.debug("show image " + imageIndex);
                repaint();
                try {
                    Thread.sleep(500);
                    synchronized (control) {
                        if (stop) {
                            control.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "视频已标记完", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;

        }

    }

    /**
     * operate mouse click, press, release
     */

    public class MouseListenerA extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = prepareXY((int)(e.getX()), (int)(imgWidth*scaling));
            int y = prepareXY((int)(e.getY()), (int)(imgHeight*scaling));
            if (model == Model.BIKE && e.getButton() == MouseEvent.BUTTON1) {
                mouseNum++;
                if (mouseNum % 3 == 1) {
                    Bike bike = new Bike(annType.getBike(), new Point(0, 0), new Point(0, 0), new Point(0, 0),
                            "0", "0", "0", "0");
//                    bike.setType(annType.getBike());
                    bike.getP1().setLocation(x, y);
                    list.add(bike);
//                    generateP1("bike", x, y);
                } else if (mouseNum % 3 == 2) {
                    if (!list.isEmpty()) {
                        Bike bike = (Bike) list.get(list.size() - 1);
                        int x2 = (int) bike.getP1().getX();
                        int y2 = y > (int) bike.getP1().getY() ? y : (int) bike.getP1().getY();
                        bike.setP2(new Point(x2, y2));

                    }
                } else if (mouseNum % 3 == 0) {
                    if (!list.isEmpty()) {
                        Bike bike = (Bike) list.get(list.size() - 1);
                        int x3 = x > (int) bike.getP3().getX() ? x : (int) bike.getP3().getX();
//                        int xtmp = x > tmpAnnotation.getX3() ? x : (tmpAnnotation.getX3());
                        bike.setP3(new Point(x3, y));


                    }
                }
            } else if (model == Model.LANE && e.getButton() == MouseEvent.BUTTON1) {

                if (e.getClickCount() == 1) {
                    mouseNum++;
                    if (mouseNum == 1) {
                        Lane lane = new Lane(Type.getLane(), new LinkedList<Point>(),"0", "0", "0", "0");
//                        lane.setType(annType.getLane());
//                        tmpLa.setObject("lane");
//                        List<Point> pointList = new LinkedList<Point>();

//                        pointList.add(new Point(x, y));
//                        lane.setPointList(pointList);
                        lane.getPointList().add(new Point(x, y));
                        list.add(lane);
//                        laneList.add(tmpLane);

                    } else if (mouseNum > 1) {
                        if (!list.isEmpty()) {
                            Lane lane = (Lane) list.get(list.size() - 1);


                            lane.getPointList().add(new Point(x, y));
                        }
                    }


                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
//            logger.debug("mouse pressed at: " + "(" + e.getX()  + "," +  e.getY()+")");
            int x = prepareXY(e.getX(), (int)(imgWidth*scaling));
            int y = prepareXY(e.getY(), (int)(imgHeight*scaling));
            logger.debug("mouse pressed at: " + new Point(x, y).toString());

            if (e.getButton() == MouseEvent.BUTTON1) {
                isLeft = true;
                if (model == Model.PERSON) {
                    logger.debug("label person");
                    Rectangle person = new Rectangle(annType.getPerson(), new Point(0, 0), new Point(0, 0),
                            "0", "0", "0", "0");
//                    person.setType(annType.getPerson());
                    person.getP1().setLocation(x, y);
                    list.add(person);
//                    generateP1("person", x, y);
                } else if (model == Model.TRICYCLE) {
                    logger.debug("label tricycle");
                    Rectangle tricycle = new Rectangle(annType.getTricycle(), new Point(0, 0), new Point(0, 0),
                            "0", "0", "0", "0");
//                    tricycle.setType("tricycle",);
                    tricycle.getP1().setLocation(x, y);
                    list.add(tricycle);
//                    generateP1("tricycle", x, y);
                } else if (model == Model.TRAFFIC_SIGN) {
                    logger.debug("label traffic sign");
                    Rectangle trafficSign = new Rectangle(annType.getTrafficSign(), new Point(0, 0), new Point(0, 0),
                            "0", "0", "0", "0");
//                    trafficSign.setType("traffic_sign");
                    trafficSign.getP1().setLocation(x, y);
                    list.add(trafficSign);
//                    generateP1("traffic_sign", x, y);
                } else if (model == Model.CAR_TAIL) {
                    logger.debug("label car tail");
                    Car car = new Car(annType.getCarTail(), new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0),
                            "0", "0", "0", "0");
                    car.getP1().setLocation(x, y);
                    list.add(car);
//                    generateP1("tail",x,y);
                } else if (model == Model.CAR_HEAD) {
                    logger.debug("label car head");
                    Car car = new Car(annType.getCarHead(), new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0),
                            "0", "0", "0", "0");
//                    car.setType("head");
                    car.getP1().setLocation(x, y);
                    list.add(car);
//                    generateP1("head",x,y);
                } else if (model == Model.CAR_TAIL_SIDE) {
                    mouseNum++;
                    logger.debug("label car tail and side " + mouseNum);
                    generateCoor(mouseNum, annType.getCarTailSide(), x, y);
//                    System.out.println("tail side mousePress "+mouseNum);

                } else if (model == Model.CAR_HEAD_SIDE) {
                    mouseNum++;
                    logger.debug("label car head and side " + mouseNum);
                    generateCoor(mouseNum, annType.getCarHeadSide(), x, y);

                }


                repaint();
            }
            else if(e.getButton() == MouseEvent.BUTTON3){
                isLeft = false;
            }


        }

        private void generateCoor(long mouseNum, String label, int x, int y) {

            if (mouseNum % 3 == 1) {
                Car car = new Car(label, new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0),
                        "0", "0", "0", "0");

//                car.setType(label);
                car.getP1().setLocation(x, y);
                logger.debug(car.toString());
                list.add(car);

            } else if (mouseNum % 3 == 2) {
                Car car = (Car) list.get(list.size() - 1);
                car.getP3().setLocation(x, y);


            } else if (mouseNum % 3 == 0) {
                Car car = (Car) list.get(list.size() - 1);
                car.getP4().setLocation(x, y);

            }
        }


        @Override
        public void mouseReleased(MouseEvent e) {
            int x = prepareXY(e.getX(), (int)(imgWidth*scaling));
            int y = prepareXY(e.getY(), (int)(imgHeight*scaling));

            if (e.getButton() == MouseEvent.BUTTON1 && !list.isEmpty()) {
                isLeft = true;

                tmpAnnotation = list.get(list.size() - 1);
                if (model == Model.PERSON || model == Model.TRAFFIC_SIGN || model == Model.TRICYCLE) {
                    logger.debug("label rect mouse release");
                    Rectangle rect = (Rectangle) tmpAnnotation;
                    if (Point2D.distance(rect.getP1().getX(), rect.getP1().getY(), x, y) <= 1) {
                        rect.getP2().setLocation(x + 10, y + 10);
                    } else {
                        rect.getP2().setLocation(x, y);
                    }
                } else if (model == Model.CAR_HEAD || model == Model.CAR_TAIL || (mouseNum % 3 == 1 && (model == Model.CAR_HEAD_SIDE ||
                        model == Model.CAR_TAIL_SIDE))) {
//                    if(model == Model.CAR_HEAD || model == Model.CAR_TAIL || (mouseNum%3 == 1 && (model == Model.CAR_HEAD_SIDE ||
//                    model == Model.CAR_TAIL_SIDE))){
//                        System.out.println("release .......");
                    logger.debug("car label p2");
                    Car car = (Car) tmpAnnotation;
                    if (Point2D.distance(car.getP1().getX(), car.getP1().getY(), x, y) <= 1) {
                        car.getP2().setLocation(x + 10, y + 10);
//                            tmpAnnotation.setX2(x+10);
//                            tmpAnnotation.setY2(y+10);
                    } else {
                        car.getP2().setLocation(x, y);
                    }
//                    }
                }


                repaint();
            }
        }


    }

    /**
     * deal with mouse drag, mouse move
     */
    public class MouseMotionListenerB extends MouseMotionAdapter {

//        private Coordinates tmpAnnotation;

        @Override
        public void mouseDragged(MouseEvent e) {

//            System.out.println("mouseDragged 1");
//            logger.debug("mouse drag");
            System.out.println("" + e.getButton());
            int x = prepareXY(e.getX(), (int)(imgWidth*scaling));
            int y = prepareXY(e.getY(), (int)(imgHeight*scaling));
            if (isLeft) {
//                System.out.println("mouseDragged left");
                logger.debug("mouse left drag at " + new Point(x, y).toString());
                if (!list.isEmpty()) {
                    tmpAnnotation = list.get(list.size() - 1);
                    if (model == Model.PERSON || model == Model.TRAFFIC_SIGN || model == Model.TRICYCLE) {
                        Rectangle rect = (Rectangle) tmpAnnotation;
                        rect.getP2().setLocation(x, y);
//                        tmpAnnotation.setX2(x);
//                        tmpAnnotation.setY2(y);

                    } else if (model == Model.CAR_HEAD || model == Model.CAR_TAIL || mouseNum % 3 == 1 &&
                            (model == Model.CAR_HEAD_SIDE || model == Model.CAR_TAIL_SIDE)) {
                        Car car = (Car) tmpAnnotation;
                        car.getP2().setLocation(x, y);

                    }

                }

            } else if (!isLeft) {
//                System.out.println("mouseDragged right");
                logger.debug("mouse right drag");
                if (list.size() > 0) {
                    for (Annotation ann : list) {
                        String type = ann.getType();
                        if (type.equals(annType.getPerson()) || type.equals(annType.getTrafficSign()) || type.equals(annType.getTrafficSign())) {
                            logger.debug("person || traffic_sign || tricycle  adjustment");
                            Rectangle rect = (Rectangle) ann;
                            if (rect.getP1().distance(x, y) < 10) {
                                rect.getP1().setLocation(x, y);
                            } else if (rect.getP2().distance(x, y) < 10) {
                                rect.getP2().setLocation(x, y);
                            }

                        } else if (type.equals(annType.getCarHead()) || type.equals(annType.getCarTail()) ||
                                type.equals(Type.getCarHeadSide()) || type.equals(Type.getCarTailSide())) {
                            logger.debug("car adjustment");
                            Car car = (Car) ann;
                            if (car.getP1().distance(x, y) < 10) {
                                car.getP1().setLocation(x, y);
                            } else if (car.getP2().distance(x, y) < 10) {
                                car.getP2().setLocation(x, y);
                            } else if (car.getP3().distance(x, y) < 10) {
                                car.getP3().setLocation(x, y);
                            } else if (car.getP4().distance(x, y) < 10) {
                                car.getP4().setLocation(x, y);
                            }
                        } else if (type.equals(annType.getBike())) {
                            logger.debug("bike adjustment");
                            Bike bike = (Bike) ann;
                            if (bike.getP1().distance(x, y) < 10) {
                                bike.getP1().setLocation(x, y);
                                bike.getP2().setLocation(x, bike.getP2().getY());
                            } else if (bike.getP2().distance(x, y) < 10) {
                                bike.getP1().setLocation(x, bike.getP1().getY());
                                bike.getP2().setLocation(x, y);
                            } else if (bike.getP3().distance(x, y) < 10) {
                                bike.getP3().setLocation(x, y);
                            }
                        } else if (type.equals(annType.getLane())) {
                            logger.debug("lane adjustment");
                            Lane lane = (Lane) ann;
                            for (Point p : lane.getPointList()) {
                                if (p.distance(x, y) < 10) {
                                    p.setLocation(x, y);
                                }
                            }
                        }
                    }


                }


            }
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = prepareXY(e.getX(), (int)(imgWidth*scaling));
            int y = prepareXY(e.getY(), (int)(imgHeight*scaling));

            if (model == Model.CAR_TAIL_SIDE || model == Model.CAR_HEAD_SIDE) {
//                    logger.debug("car mouse move");
                if (list.size() > 0) {
                    Car car = (Car) list.get(list.size() - 1);
                    if (mouseNum % 3 == 1) {
                        car.getP3().setLocation(x, y);

                    } else if (mouseNum % 3 == 2) {
                        car.getP4().setLocation(x, y);

                    }
                }
            } else if (model == Model.BIKE) {
//                    logger.debug("bike mouse Move");
                if (list.size() > 0) {
                    Bike bike = (Bike) list.get(list.size() - 1);
                    if (mouseNum % 3 == 1) {
                        int ytmp = y > bike.getP1().getY() ? y : (int) bike.getP1().getY();
                        bike.getP2().setLocation(bike.getP1().getX(), ytmp);

                    } else if (mouseNum % 3 == 2) {
                        if (x > bike.getP2().getX()) {
                            bike.getP3().setLocation(x, y);

                        }
                    }
                }
            }


            repaint();
//            }


        }


    }

    /**
     * guarantee xy is in 0 ~ boud
     * @param xy
     * @param bound
     * @return
     */
    public int prepareXY(int xy, int bound) {

        if (xy < 0) {
            xy = 0;
        }
        if (xy > bound) {
            xy = bound;
        }
        return (int)xy;
    }

}
