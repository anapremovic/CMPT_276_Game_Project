package org.Display;

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

    // thread
    Thread gameThread;

    public Screen() {
        // set screen size
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.gray);
        this.setDoubleBuffered(true); // improves rendering performance
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // call run() method
    }
    
    // GAME LOOP
    @Override
    public void run() {
        while(gameThread != null) {
            System.out.println("game loop running");

            // UPDATE GAME OBJECTS
            update();

            // DRAW UPDATES
            repaint(); // call paintComponent() method
        }
    }

    public void update() {

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.white);
        g2.fillRect(100, 100, tileSize, tileSize);
        g2.dispose();
    }
}
