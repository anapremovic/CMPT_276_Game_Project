package org.Display;

import org.Input.*;
import org.GameObjects.*;

import java.awt.*;
import javax.swing.*;

public class Screen extends JPanel implements Runnable {
    // settings
    final int baseTileSize = 16;
    final int scale = 3;

    final int tileSize = baseTileSize * scale; // 48x48 tile size
    final int screenColumns = 18;
    final int screenRows = 12;
    final int screenWidth = tileSize * screenColumns;
    final int screenHeight = tileSize * screenRows;

    // FPS
    final int FPS = 60;

    // game logic
    KeyHandler playerInput = new KeyHandler();
    Thread gameThread;

    // game objects
    MainCharacter player = new MainCharacter(100, 100, tileSize, tileSize, 4);

    public Screen() {
        // set screen size
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.gray);
        this.setDoubleBuffered(true); // improves rendering performance
        this.addKeyListener(playerInput); // allow Screen to recognize key input
        this.setFocusable(true); // allow Screen to be focused on taking key input
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // call run() method
    }
    
    // GAME LOOP
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS; // 1 second/60
        //double nextDrawTime = System.nanoTime() * drawInterval; // next time to draw the screen
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

//            // UPDATE GAME OBJECTS
//            System.out.println("Call update");
//            update();
//
//            // DRAW UPDATES
//            repaint(); // call paintComponent() method

//            // let Thread sleep until next draw time (pause game loop)
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime(); // time remaining until next draw time
//                remainingTime = remainingTime/1000000; // convert remainingTime to milliseconds, because sleep() accepts milliseconds
//
//                // if the update/repaint took the whole interval
//                if(remainingTime < 0) {
//                    remainingTime = 0;
//                }
//
//                Thread.sleep((long) remainingTime);
//
//                nextDrawTime += drawInterval;
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

    public void update() {
        if(playerInput.upPressed) {
            player.updateYPos(player.getSpeed());
        }
        else if(playerInput.downPressed) {
            player.updateYPos(-1 * player.getSpeed());
        }
        else if(playerInput.rightPressed) {
            player.updateXPos(player.getSpeed());
        }
        else if(playerInput.leftPressed) {
            player.updateXPos(-1 * player.getSpeed());
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // TEMPORARY CHARACTER - represented as a rectangle
        g2.setColor(Color.white);
        g2.fillRect(player.getXPos(), player.getYPos(), tileSize, tileSize);
        g2.dispose();
    }
}
