package org.HelloPlayer;

import javax.swing.*;
import java.awt.*;

public class ConsoleWindow extends JFrame {
    private JTextArea logArea;
    private JTextField inputField;
    private Engine engine;
    private boolean devToolsUnlocked = false; // 🔑 Stan DevTools

    public ConsoleWindow(Engine engine) {
        this.engine = engine;

        setTitle("Easygame (Console)");
        setSize(500, 300);
        setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(logArea);

        inputField = new JTextField();
        inputField.addActionListener(e -> handleCommand(inputField.getText()));

        add(scroll, BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);
    }

    private void handleCommand(String cmd) {
        log("> " + cmd);
        inputField.setText("");

        if (cmd.equalsIgnoreCase("version")) {
            log("Client version: Public test 0.1");
            log("");
            log("Java Developer Tools Version: 0.0.1");
        } else if (cmd.startsWith("say ")) {
            log("Player says: " + cmd.substring(4));
        } else if (cmd.equalsIgnoreCase("fps")) {
            log("Current FPS: " + Engine.getFPS());
        } else if (cmd.startsWith("tp ")) {
            String[] args = cmd.split(" ");
            if (args.length == 3) {
                try {
                    int x = Integer.parseInt(args[1]);
                    int y = Integer.parseInt(args[2]);
                    engine.teleport(x, y);
                    log("Teleported to: " + x + "," + y);
                } catch (NumberFormatException e) {
                    log("Invalid coordinates!");
                }
            } else {
                log("Usage: tp x y");
            }
        } else if (cmd.equalsIgnoreCase("anticheat off")) {
            log("Anti Cheat has been disabled!");
            System.out.println("Anti Cheat has been disabled!");
            engine.antiChat = false;
        } else if (cmd.equalsIgnoreCase("anticheat on")) {
            log("Anti Cheat has been enabled!");
            System.out.println("Anti Cheat has been enabled!");
            engine.antiChat = true;
        } else if (cmd.equalsIgnoreCase("time")) {
            log("Game time (nano): " + System.nanoTime());
        } else if (cmd.equalsIgnoreCase("help")) {
            log("Available commands:");
            log("> version           - Show client version");
            log("> fps               - Show FPS");
            log("> tp x y            - Teleport to position");
            log("> say <message>     - Send chat message");
            log("> anticheat on/off  - Toggle Anti Cheat");
            log("> time              - Show system time");
            log("> settitle <text>   - Change window title");
            log("> help              - Show this help message");
            log("> unlockdev <code>  - Unlock Developer Tools");
        } else if (cmd.startsWith("settitle ")) {
            String title = cmd.substring(9);
            setTitle(title);
            log("Window title set to: " + title);
        } else if (cmd.equalsIgnoreCase("JavaCeoTools")) {
            log("JavaCeoTools >> Loading...");
            JavaCeoTools ceo = new JavaCeoTools();
            ceo.Java();
        } else if (cmd.equalsIgnoreCase("devtools")) {
            if (devToolsUnlocked) {
                log("Java Developer Tools:");
                log("> memory - Show memory usage");
                log("> inspect - Inspect player data (not implemented)");
                // Dodaj więcej narzędzi tutaj
            } else {
                log("Access denied. Use: unlockdev 1032CEO");
            }
        } else if (cmd.equalsIgnoreCase("unlockdev 1032CEO")) {
            devToolsUnlocked = true;
            log("✅ Developer Tools unlocked!");
            log("Type 'devtools' to list available tools.");
        } else {
            log("Unknown command: " + cmd);
        }
    }

    private void log(String msg) {
        logArea.append(msg + "\n");
    }
}
