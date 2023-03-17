package org.GameObjects;

import org.Display.Screen;
import org.Logic.AStarFindPath;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * The type Enemy.
 */
public class Enemy extends MovableObject {
    /**
     * Image icon of Snake.
     */
    Image snake;
    /**
     * The board of game background.
     */
    int[][] board;
    /**
     * The direction of enemy.
     */
    String direction;

    /**
     * Instantiates a new Enemy.
     *
     * @param screen    the screen
     * @param gameTiles the game tiles
     */
    public Enemy(Screen screen, TileManager gameTiles) {
        this.width = screen.getTileSize();
        this.height = screen.getTileSize();
        board = transposeMatrix(gameTiles.getBoard());
        setStartingValues(500, 200, 7, "UNKNOWN");
        getImage();
    }

    /**
     * find the closest grid of enemy
     *
     * @param x        enemy x grid
     * @param y        enemy y grid
     * @param tileSize the tile size
     * @return the closest grid of enemy
     */
    public int[] adjust(int x, int y, int tileSize) {
        for (int j = (int) Math.floor(x / (double) tileSize); j <= Math.ceil(x / (double) tileSize); j++) {
            for (int i = (int) Math.floor(y / (double) tileSize); i <= Math.ceil(y / (double) tileSize); i++) {
                if (canPassable(i, j)) return new int[]{j, i};
            }
        }
        return new int[]{-1, -1};
    }


    /**
     * Update the direction of enemy.
     *
     * @param tileSize      the tile size
     * @param mainCharacter the main character
     */
    public void updateDirection(int tileSize, MainCharacter mainCharacter) {
        if (xPos % tileSize != 0 && (direction.equals("LEFT") || direction.equals("RIGHT"))) return;
        if ((yPos % tileSize != 0) && (direction.equals("UP") || direction.equals("DOWN"))) return;
        AStarFindPath findPath = new AStarFindPath(adjust(xPos, yPos, tileSize), adjust(mainCharacter.xPos, mainCharacter.yPos, tileSize), board);
        findPath.solve();
        List<int[]> path = findPath.getPath();
        if (path.isEmpty()) {
            direction = "UNKNOWN";
            return;
        }
        int[] temp = path.get(0);
        if (path.size() >= 2 && yPos == temp[0] * tileSize && xPos == temp[1] * tileSize) {
            temp = path.get(1);
        }
        if (temp[0] * tileSize < yPos) {
            direction = "UP";
        }
        if (temp[0] * tileSize > yPos) {
            direction = "DOWN";
        }
        if (temp[1] * tileSize < xPos) {
            direction = "LEFT";
        }
        if (temp[1] * tileSize > xPos) {
            direction = "RIGHT";
        }
    }


    /**
     * Update position of enemy.
     *
     * @param mainCharacter the main character
     * @param tileSize      the tile size
     */
    public void update(MainCharacter mainCharacter, int tileSize) {
        updateDirection(tileSize, mainCharacter);
        switch (direction) {
            case "LEFT":
                updateXPos(-1 * speed);
                break;
            case "RIGHT":
                updateXPos(speed);
                break;
            case "DOWN":
                updateYPos(-1 * speed);
                break;
            case "UP":
                updateYPos(speed);
                break;
        }
    }

    /**
     * Draw enemey on canvas.
     *
     * @param g the g
     */
    public void draw(Graphics2D g) {
        g.drawImage(snake, xPos, yPos, width, height, null);
    }

    /**
     * Load images.
     */
    public void getImage() {
        try {
            snake = ImageIO.read(getClass().getResourceAsStream("/snake.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets starting values of enemy.
     *
     * @param initialXPos the initial x pos
     * @param initialYPos the initial y pos
     * @param speed       the speed
     * @param direction   the direction
     */
    public void setStartingValues(int initialXPos, int initialYPos, int speed, String direction) {
        this.xPos = initialXPos;
        this.yPos = initialYPos;
        this.speed = speed;
        this.direction = direction;
    }

    /**
     * Whether the grid can passable.
     *
     * @param i the
     * @param j the j
     * @return the board[i][j] can passable
     */
    public boolean canPassable(int i, int j) {
        return board[i][j] == 0 || board[i][j] == 3;
    }

    /**
     * Transpose matrix int [ ] [ ].
     *
     * @param matrix the matrix
     * @return the int [ ] [ ]
     */
    public int[][] transposeMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] transposedMatrix = new int[n][m];

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < m; y++) {
                transposedMatrix[x][y] = matrix[y][x];
            }
        }

        return transposedMatrix;
    }
}
