package org.GameObjects;

import org.Display.Screen;
import org.Logic.PathFinder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * The type Enemy.
 */
public class Enemy extends MovableObject {
    /**
     * The Snake.
     */
    Image snake;
    /**
     * The Board.
     */
    int[][] board;
    /**
     * The Direction.
     */
    String direction;
    /**
     * The Path finder.
     */
    PathFinder pathFinder;
    /**
     * The Tile size.
     */
    int tileSize;

    /**
     * Instantiates a new Enemy.
     *
     * @param tileSize the tile size
     * @param board    the board
     */
    public Enemy(int tileSize, int[][] board) {
        this.width = tileSize;
        this.height = tileSize;
        this.tileSize = tileSize;
        this.board = transposeMatrix(board);
        setStartingValues(500, 200, 7, "UNKNOWN");
        getImage();
        pathFinder = new PathFinder(this.board);
    }

    /**
     * Adjust int [ ].
     *
     * @param x the x
     * @param y the y
     * @return the int [ ]
     */
    public int[] adjust(int x, int y) {
        for (int j = (int) Math.floor(x / (double) tileSize); j <= Math.ceil(x / (double) tileSize); j++) {
            for (int i = (int) Math.floor(y / (double) tileSize); i <= Math.ceil(y / (double) tileSize); i++) {
                if (i >= 0 && i < board.length && j >= 0 && j < board[0].length && canPassable(i, j))
                    return new int[]{j, i};
            }
        }
        return null;
    }


    /**
     * Update direction.
     */
    public void updateDirection(int mainCharacterXpos, int mainCharacterYpos) {
        if (xPos % tileSize != 0 && (direction.equals("LEFT") || direction.equals("RIGHT"))) return;
        if ((yPos % tileSize != 0) && (direction.equals("UP") || direction.equals("DOWN"))) return;
        List<int[]> path = pathFinder.shortestPath(adjust(xPos, yPos), adjust(mainCharacterXpos, mainCharacterYpos));
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
     * Update.
     */
    public void update(int mainCharacterXpos, int mainCharacterYpos) {
        updateDirection(mainCharacterXpos, mainCharacterYpos);
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
     * Draw.
     *
     * @param g the g
     */
    public void draw(Graphics2D g) {
        g.drawImage(snake, xPos, yPos, width, height, null);
    }

    /**
     * Gets image.
     */
    private void getImage() {
        try {
            snake = ImageIO.read(getClass().getResourceAsStream("/snake.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets starting values.
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
     * Can passable boolean.
     *
     * @param i the
     * @param j the j
     * @return the boolean
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

    public String getDirection() {
        return direction;
    }
}
