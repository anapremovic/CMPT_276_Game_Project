package org.Display;

import org.Input.*;
import org.Logic.CollisionDetector;
import org.GameObjects.*;
import org.Logic.MenuLogic;

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
    private KeyHandler playerInput = new KeyHandler();
    private TileManager gameTiles = new TileManager(this);
    private CollisionDetector collisionDetector = new CollisionDetector(this, gameTiles);
    private Thread gameThread;


    // game objects
    private MainCharacter player = new MainCharacter(this, playerInput, collisionDetector);
    private Reward rewards[] = new Reward[11]; // display 10 carrots and 1 mystical ocean fruit at one time
    private RewardDisplay objDisplayer = new RewardDisplay(this, gameTiles);

    private MenuLogic menuLogic;

    public Screen() {
        // set screen size
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(74, 75, 195));
        this.setDoubleBuffered(true); // improves rendering performance
        this.addKeyListener(playerInput); // allow Screen to recognize key input
        this.setFocusable(true); // allow Screen to be focused on taking key input
        // display menu at start of program
        menuLogic = new MenuLogic(this);
        menuLogic.displayMenu();
    }

    public void gameSetup() {
        objDisplayer.displayRewards();
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

        // TILES
        gameTiles.draw(g2); // important to draw tiles before game objects

        // IMMOVABLE OBJECTS
        for(Reward cur : rewards) {
            if(cur != null) {
                cur.draw(g2, this);
            }
        }

        // MAIN CHARACTER
        player.draw(g2);

        g2.dispose();
    }

    // getters
    public int getTileSize() {
        return tileSize;
    }
    public int getNumColumns() { return screenColumns; }
    public int getNumRows() { return screenRows; }
    public int getWidth() { return screenWidth; }
    public int getHeight() { return screenHeight; }

    public Reward[] getRewards() { return rewards; }

    // setters
    public void setReward(int index, Reward reward) {
        rewards[index] = reward;
    }
    public void setRewardCollidableAreaX(int index, int x) {
        rewards[index].setCollidableAreaX(x);
    }
    public void setRewardCollidableAreaY(int index, int y) {
        rewards[index].setCollidableAreaY(y);
    }
}
