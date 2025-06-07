package org.HelloPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Engine extends JPanel implements Runnable, KeyListener {
    private Thread thread;
    private boolean running;
    private BufferedImage screen;

    private final int WIDTH = 800;
    private final int HEIGHT = 8000;
    private final int TILE_SIZE = 37;

    public int playerX = 1;
    public int playerY = 4;

    private static int currentFPS = 0;
    private long lastTime = System.nanoTime();
    private int frames = 0;

    private int[][] map = {
            {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
            {4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
            {4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
            {4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
            {4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
            {4,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,4},
            {4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4},
            {4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4},
            {4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4},
            {4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4},
            {4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4},
            {4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4},
            {4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4},
            {4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4}
    };

    private boolean original;
    private boolean client;
    public boolean antiChat;

    public Engine() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        screen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
    }

    public void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void verifyOriginalClient() {
        original = true;
        client = true;
        Random rand = new Random();
        int randomCode = rand.nextInt(1000);
        System.out.println("The client was verified with the code: " + randomCode);
    }

    public void enableAntiChat() {
        antiChat = true;
        var antiCheatServer = true;
        var antiCheat = true;
        var antiCheatClient = true;
        var antiCheatBypass = false;
        System.out.println("Anti Cheat is on");
    }

    public void run() {
        while (running) {
            render();
            repaint();
            frames++;

            long now = System.nanoTime();
            if (now - lastTime >= 1_000_000_000) {
                currentFPS = frames;
                frames = 0;
                lastTime = now;
            }

            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void render() {
        Graphics2D g = screen.createGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                switch (map[y][x]) {
                    case 1 -> g.setColor(Color.DARK_GRAY); // stone
                    case 2 -> g.setColor(Color.GREEN);     // grass
                    case 3 -> g.setColor(Color.ORANGE);    // wood
                    case 4 -> g.setColor(Color.BLUE);      // border
                    default -> g.setColor(Color.WHITE);
                }
                g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        // Player
        g.setColor(Color.RED);
        g.fillRect(playerX * TILE_SIZE, playerY * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        g.dispose();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(screen, 0, 0, null);
    }

    public static int getFPS() {
        return currentFPS;
    }

    public void teleport(int x, int y) {
        if (x >= 0 && x < map[0].length && y >= 0 && y < map.length) {
            playerX = x;
            playerY = y;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> playerY--;
            case KeyEvent.VK_S -> playerY++;
            case KeyEvent.VK_A -> playerX--;
            case KeyEvent.VK_D -> playerX++;
        }
        playerX = Math.max(0, Math.min(map[0].length - 1, playerX));
        playerY = Math.max(0, Math.min(map.length - 1, playerY));
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
