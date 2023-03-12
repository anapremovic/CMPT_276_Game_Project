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
    final int screenColumns = 21;
    final int screenRows = 16;
    final int screenWidth = tileSize * screenColumns;
    final int screenHeight = tileSize * screenRows;

    // FPS
    final int FPS = 60;

    // game logic
    KeyHandler playerInput = new KeyHandler();
    Thread gameThread;

    // game objects
    MainCharacter player = new MainCharacter(this, playerInput);
    TileManager gameTiles = new TileManager(this);

    public Screen() {
        // set screen size
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(74, 75, 195));
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
                // UPDATE GAME OBJECTS
                update();
                // DRAW UPDATES
                repaint(); // call paintComponent() method
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        gameTiles.draw(g2); // draw tiles before player
        player.draw(g2);

        g2.dispose();
    }

    public int getTileSize() {
        return tileSize;
    }
    public int getNumColumns() { return screenColumns; }
    public int getNumRows() { return screenRows; }
    public int getWidth() { return screenWidth; }
    public int getHeight() { return screenHeight; }
}
