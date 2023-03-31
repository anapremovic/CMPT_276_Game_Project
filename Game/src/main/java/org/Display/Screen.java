package org.Display;

import org.Input.*;
import org.GameObjects.*;
import org.Logic.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Contains general game logic, including main game loop, asset drawing, and updating of movable and immovable objects.
 * Acts as the main center/culmination of our program.
 */
public class Screen extends JPanel implements Runnable {
    // settings

    /**
     * The size of the length and width of each tile of our game in pixels.
     */
    int tileSize = 48; // 48x48 tile size

    /**
     * Number of columns of our game screen.
     */
    int screenColumns = 21;

    /**
     * Number of rows of our game screen.
     */
    int screenRows = 16;

    /**
     * Width of our game screen in pixels.
     */
    final int screenWidth = tileSize * screenColumns;

    /**
     * Height of our game screen in pixels.
     */
    final int screenHeight = tileSize * screenRows;

    // FPS
    /**
     * Number of frames per second our game runs at.
     */
    final int FPS = 60;

    // game logic
    /**
     * Takes player input via WASD keys or arrow keys, in order to facilitate main character movement.
     */
    private KeyHandler playerInput = new KeyHandler();

    /**
     * Holds all information about the game tiles.
     */
    private TileManager gameTiles = new TileManager(this);

    /**
     * Handles collisions between player and tiles as well as player and immovable objects.
     */
    private CollisionDetector collisionDetector = new CollisionDetector(this, gameTiles);

    /**
     * Main game loop thread. As long as the thread is running, the game runs.
     */
    private Thread gameThread;


    // game objects

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * List containing all snake enemies.
     */
    ArrayList<Enemy> enemies = new ArrayList<>();

    /**
     * Array containing all possible immovable objects in our game. Display up to 10 carrots, 1 mystical ocean fruit,
     * and 6 lava at one time
     */
    private ImmovableObject objects[] = new ImmovableObject[17];

    /**
     * Populates objects[] with the corresponding objects.
     */
    private ImmovableObjectDisplay objDisplayer = new ImmovableObjectDisplay(this, gameTiles);

    /**
     * Main character of our game, a turtle.
     */
    private MainCharacter player = new MainCharacter(this, playerInput, collisionDetector, objDisplayer, gameTiles);

    /**
     * Contains all logic in reference to starting, winning, and losing menus.
     */
    private MenuLogic menuLogic;
    private WinningMenu winningMenu;
    private GameOverMenu gameOverMenu = new GameOverMenu(this);

    //game state
    /**
     * Toggles between game state and menu state.
     */
    public int gameState;

    /**
     * Indicates whether the player has lost the game.
     */
    public final int gameOver = 2;


    // timer
    /**
     * Time at which the game started, in milliseconds.
     */
    private long startTime;

    /**
     * How long has passed since the game started, in milliseconds.
     */
    private long elapsedTime;

    /**
     * Font for any timer information displayed on the screen.
     */
    private Font timerFont = new Font("Arial", Font.BOLD, 20);

    // score

    /**
     * Current score of player where they receive 1 points for collecting a carrot, 3 for a mystical ocean fruit, and
     * lose 3 for touching lava.
     */
    private int score = 0;

    /**
     * Font for any score information displayed on the screen.
     */
    private Font scoreFont = new Font("Arial", Font.BOLD, 20);

    /**
     * Sets basic information on the screen, initializes variables, and commences timer.
     */
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
        enemies = new ArrayList<>();
        Enemy e1 = new Enemy(this, gameTiles);
        e1.setStartingValues(tileSize * 2, tileSize * 4, 2, "UNKNOWN");
        enemies.add(e1);
        Enemy e2 = new Enemy(this, gameTiles);
        e2.setStartingValues(tileSize * 5, tileSize * 8, 2, "UNKNOWN");
        enemies.add(e2);
        // initialize elapsedTime to 0
        elapsedTime = 0;
    }

    /**
     * Initial game set up, including initializing objects[] and setting time info.
     */
    public void gameSetup() {
        objDisplayer.displayObjects(objects.length);
        startTime = System.currentTimeMillis();
        elapsedTime = 0;
    }

    /**
     * Start the game thread which allows the game to be played.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // call run() method
    }

    /**
     * End the game thread which allows the game to be played.
     */
    public void endGameThread() {
        gameThread.interrupt();
    }

    /**
     * From the winning or losing menus, the player may choose to restart the game.
     * Reinitialize all game information and variables, restart timer, as well as create a new game window.
     */
    public void restartGame() {
        endGameThread();
        closeOldFrame();
        resetGameState();
        resetScore();
        resetTimer();
        resetGameObjects();
        startNewFrame();
    }


    private void closeOldFrame() {
        // close old game window
        JFrame oldFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        oldFrame.dispose();
    }

    private void resetGameState() {
        // reset game state
        gameState = gameOver; // Change gameState to menuState
    }

    private void resetScore() {
        // reset score
        score = 0;
    }

    private void resetTimer() {
        // reset timer
        startTime = System.currentTimeMillis();
        elapsedTime = 0;
    }

    private void resetGameObjects() {
        // reset game objects
        objects = new ImmovableObject[17];
        objDisplayer = new ImmovableObjectDisplay(this, gameTiles);
        player = new MainCharacter(this, playerInput, collisionDetector, objDisplayer, gameTiles);
        enemies = new ArrayList<>();
        Enemy e1 = new Enemy(this, gameTiles);
        e1.setStartingValues(tileSize * 2, tileSize * 4, 2, "UNKNOWN");
        enemies.add(e1);
        Enemy e2 = new Enemy(this, gameTiles);
        e2.setStartingValues(tileSize * 5, tileSize * 8, 2, "UNKNOWN");
        enemies.add(e2);
    }

    private void startNewFrame() {
        // start new game window
        JFrame newFrame = new JFrame("The Legend of the Turtle: Turtle Run");
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setResizable(false);
        newFrame.add(this);
        newFrame.pack();
        newFrame.setLocationRelativeTo(null);
        newFrame.setVisible(true);
        objDisplayer.displayObjects(17);
        gameTiles.setMap("/maps/map01.txt");
        playerInput.nomovement();
    }


    /**
     * Contains main game loop. As long as the game loop runs (based on the main game thread), the game runs.
     */
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
                if (this.score < 0 || (collisionDetector.detectEnemyCatchPlayer(player, tileSize) && player.getNumCarrotsCollected() < 10)) {
                    endGameThread();
                    playerInput.nomovement();
                    gameOverMenu.displayGameOverMenu();
                }

                // player collects 10 carrots => exit unlocks
                if (player.getNumCarrotsCollected() >= 10) {
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

    /**
     * Update positions and logic of all game objects (main character, enemies, and immovable game objects).
     */
    public void update() {
        player.update();
        for (Enemy e : enemies) {
            e.update(player, tileSize);
        }

        if (objects[10] != null) {
            objects[10].updateTime(elapsedTime);
        }
    }

    /**
     * Detects collision between an enemy and the main character.
     * @return indication of whether collision has been detected
     */


    /**
     * Draw all assets and relevant player information to screen, including tiles, main character, enemies,
     * immovable objects, timer, and score.
     *
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // TILES
        gameTiles.draw(g2); // important to draw tiles before game objects

        // IMMOVABLE OBJECTS
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null) {
                if (i == 10) {
                    long curTime = objects[i].getTime() / 1000;
                    if (curTime >= 5 && curTime <= 15) {
                        objects[i].draw(g2, this);
                    }
                } else {
                    objects[i].draw(g2, this);
                }
            }
        }

        // MAIN CHARACTER
        player.draw(g2);
        for (Enemy e : enemies) {
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

    // GETTERS

    public int getTileSize() {
        return tileSize;
    }

    public int getNumColumns() {
        return screenColumns;
    }

    public int getNumRows() {
        return screenRows;
    }

    public ImmovableObject[] getObjects() {
        return objects;
    }

    public WinningMenu getWinningMenu() {
        return winningMenu;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    // SETTERS
    public void setObject(int index, ImmovableObject newObject) {
        objects[index] = newObject;
    }

    public void setObjectCollidableAreaX(int index, int x) {
        objects[index].setCollidableAreaX(x);
    }

    public void setObjectCollidableAreaY(int index, int y) {
        objects[index].setCollidableAreaY(y);
    }

    /**
     * Add a given number to the current score.
     *
     * @param addedScore number to add to score
     */
    public void updateScore(int addedScore) {
        score += addedScore;
    }

    /**
     * Initialize the winningMenu variable with a given time, screen, and current points
     *
     * @param elapsedTime current time to be passed to winningMenu
     * @param screen      screen to display winning menu on
     * @param points      current points to be used in winning menu logic
     */
    public void initializeWinningMenu(long elapsedTime, Screen screen, int points) {
        winningMenu = new WinningMenu(elapsedTime, screen, score);
    }
}
