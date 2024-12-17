package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class App {

    public static void create() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Student Manager");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(854, 480);
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        App.create();
    }
}