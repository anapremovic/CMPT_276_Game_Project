package org.Display;

import org.Input.*;
import org.GameObjects.*;
import org.Logic.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
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
    ArrayList<Enemy> enemies = new ArrayList<>();
    // display up to 10 carrots, 1 mystical ocean fruit, and 6 lava at one time
    private ImmovableObject objects[] = new ImmovableObject[17];
    private ImmovableObjectDisplay objDisplayer = new ImmovableObjectDisplay(this, gameTiles);
    private MainCharacter player = new MainCharacter(this, playerInput, collisionDetector, objDisplayer, gameTiles);
    private MenuLogic menuLogic;
    private WinningMenu winningMenu = new WinningMenu(this.elapsedTime, this);
    private GameOverMenu gameOverMenu = new GameOverMenu(this);

    //game state
    public int gameState;
    public final int playState = 1;
    public final int gameOver = 2;


    // timer
    private long startTime;
    private long elapsedTime;
    private Font timerFont = new Font("Arial", Font.BOLD, 20);

    // score
    private int score = 0;
    private Font scoreFont = new Font("Arial", Font.BOLD, 20);


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
        enemies.add(new Enemy(this, collisionDetector, gameTiles));
        // initialize elapsedTime to 0
        elapsedTime = 0;
    }

    public void gameSetup() {
        objDisplayer.displayObjects(objects.length);
        startTime = System.currentTimeMillis();
        elapsedTime = 0;
    }


    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // call run() method
    }

    public void endGameThread() {
        gameThread.interrupt();
    }

    // GAME LOOP
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // 1 second/60
        //double nextDrawTime = System.nanoTime() * drawInterval; // next time to draw the screen
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                // score goes negative => game over
                if (this.score < 0) {
                    endGameThread();
                    gameOverMenu.displayGameOverMenu();
                }

                // score reaches 10 => exit unlocks
                if (this.score >= 10) {
                    gameTiles.setMap("/maps/map02.txt");
                }

                // UPDATE GAME OBJECTS
                update();
                // DRAW UPDATES
                repaint(); // call paintComponent() method
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
        for (Enemy e:enemies)
        {
            e.update(player,tileSize);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // TILES
        gameTiles.draw(g2); // important to draw tiles before game objects

        // IMMOVABLE OBJECTS
        for (ImmovableObject cur : objects) {
            if (cur != null) {
                cur.draw(g2, this);
            }
        }

        // MAIN CHARACTER
        player.draw(g2);
        for (Enemy e:enemies)
        {
            e.draw(g2);
        }
        // TIMER
        g2.setFont(timerFont);
        g2.setColor(new Color(255, 255, 255, 150));
        g2.fillRect(10, 10, 120, 23);
        g2.setColor(Color.BLACK);
        elapsedTime = System.currentTimeMillis() - startTime;
        g2.drawString("Time: " + String.format("%02d:%02d", elapsedTime / 60000, (elapsedTime / 1000) % 60), 16, 28);

        // SCORE
        g2.setFont(scoreFont);
        g2.setColor(new Color(255, 255, 255, 150));
        g2.fillRect(getWidth() - 130, 10, 120, 23);
        g2.setColor(Color.BLACK);
        g2.drawString("Points: " + score, getWidth() - 124, 28);

        g2.dispose();
    }

    // getters
    public int getTileSize() {
        return tileSize;
    }

    public int getNumColumns() {
        return screenColumns;
    }

    public int getNumRows() {
        return screenRows;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public ImmovableObject[] getObjects() {
        return objects;
    }

    public WinningMenu getWinningMenu() {
        return winningMenu;
    }

    // setters
    public void setObject(int index, ImmovableObject newObject) {
        objects[index] = newObject;
    }

    public void setObjectCollidableAreaX(int index, int x) {
        objects[index].setCollidableAreaX(x);
    }

    public void setObjectCollidableAreaY(int index, int y) {
        objects[index].setCollidableAreaY(y);
    }

    public void updateScore(int addedScore) {
        score += addedScore;
    }
}
