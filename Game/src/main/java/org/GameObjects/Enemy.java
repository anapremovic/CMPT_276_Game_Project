package org.GameObjects;

//import org.javatuples.Pair;

import org.AStarFindPath;
import org.Display.Screen;
import org.Logic.CollisionDetector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class Enemy extends MovableObject {
    // ArrayList of pairs where each pair represents a tile on the 2D tilemap
    //ArrayList<Pair<Integer,Integer>> shortestPathToMainCharacter;
    Image snake;
    private CollisionDetector collisionDetector;
    int[][] board;
    String direction;

    public Enemy(Screen screen, CollisionDetector collisionDetector, TileManager gameTiles) {
        this.width = screen.getTileSize();
        this.height = screen.getTileSize();
        this.collisionDetector = collisionDetector;
        board = transposeMatrix(gameTiles.getBoard());
        // System.out.println(Arrays.deepToString(board));
        setStartingValues(500, 200, 1, "UNKNOWN");
        getImage();
    }

    public int adjust(int a, int b, int low) {
        int val = (int) Math.floor(a / (double) b);
        val = Math.max(low, val);
        return val;
    }


    public void updateDirection(int tileSize, MainCharacter mainCharacter) {
        AStarFindPath findPath = new AStarFindPath(new int[]{adjust(xPos, tileSize, 1), adjust(yPos, tileSize,1)}, new int[]{adjust(mainCharacter.xPos, tileSize,1), adjust(mainCharacter.yPos, tileSize,1)}, board);
        findPath.solve();
        //findPath.printSolutionPath(System.out);
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

    public void draw(Graphics2D g) {
        g.drawImage(snake, xPos, yPos, width, height, null);
    }

    public void getImage() {
        try {
            snake = ImageIO.read(getClass().getResourceAsStream("/snake.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStartingValues(int initialXPos, int initialYPos, int speed, String direction) {
        this.xPos = initialXPos;
        this.yPos = initialYPos;
        this.speed = speed;
        this.direction = direction;
    }

    public boolean canPassable(int i, int j) {
        return board[i][j] == 0 || board[i][j] == 3;
    }

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
