package cn.edu.xjtu.annotationtool.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Henry on 2017/3/30.
 */
public class ShowWindow {
    public static void run(final JFrame f){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                f.setTitle("Annotation Tool");
                f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                f.setBounds(0, 0, dimension.width, dimension.height-50);
//                f.setSize(Toolkit.getDefaultToolkit().getScreenSize());
                f.setVisible(true);
            }
        });
    }
}
