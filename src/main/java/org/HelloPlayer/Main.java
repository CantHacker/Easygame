package org.HelloPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Easygame (Public test 0.1)");
        Engine engine = new Engine();

        // ⬇️ Add an icon
        try {
            Image icon = new ImageIcon(Main.class.getResource("/org/HelloPlayer/logo.png")).getImage();
            frame.setIconImage(icon);
        } catch (Exception e) {
            System.out.println("❌ Error loading icon: " + e.getMessage());
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true); // No one has the right to use this
        frame.add(engine);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); // Warning: This is a developer option

        engine.start();
        engine.enableAntiChat();

        ConsoleWindow console = new ConsoleWindow(engine);
        console.setVisible(true); // Set whether the console is on or off

        engine.verifyOriginalClient(); // engine.run(); if you are making a custom client, use engine.run(); and remove engine.orginalclient();
    }
}
