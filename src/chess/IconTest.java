package chess;

import javax.swing.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;

public class IconTest extends JPanel {
    ImageIcon wRook;
    ImageIcon wBishop;

    public IconTest()

    {

       // createIcons();

        JButton jb = new JButton();
        jb.setPreferredSize(new Dimension(500, 500));
        jb.setIcon(new ImageIcon("mineblown.jpg"));

        add(jb);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        IconTest it = new IconTest();
        frame.getContentPane().add(it);

        frame.setResizable(true);
        frame.setPreferredSize(new Dimension(800, 637));
        frame.pack();
        frame.setVisible(true);


    }

    private void createIcons() {
        // Sets the Image for white player pieces
        wRook = new ImageIcon("wRook.png");
        wBishop = new ImageIcon("wBishop.png");

    }
}