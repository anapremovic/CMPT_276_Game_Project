package org.GameObjects;

import org.Display.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

/**
 * Contains all logic create a board of tiles and display them to the screen.
 */
public class TileManager {

    /**
     * Screen that the tiles will be displayed on.
     */
    private Screen screen;

    /**
     * Array where each element is a Tile, and each index represents the type of that tile.
     */
    private Tile[] tileTypes;

    /**
     * 2D array of ints, where each int represents the tile type corresponding to tileTypes[] for that position
     */
    private int board[][];

    /**
     * Initializes variables, loads tile sprites, and populates board.
     *
     * @param screen    screen that the tiles will be displayed on
     */
    public TileManager(Screen screen) {
        this.screen = screen;
        tileTypes = new Tile[4]; // we have 4 tile types in our game
        board = new int[screen.getNumColumns()][screen.getNumRows()];

        getTileImages();
        loadBoardData("/maps/map01.txt");
    }

    /**
     * Load board data into board[][] from a given text file.
     *
     * @param boardFilePath    file path corresponding to a txt file which encodes board information
     */
    public void loadBoardData(String boardFilePath) {
        try {
            InputStream is = getClass().getResourceAsStream(boardFilePath); // load map file
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // used for reading the file

            for(int row = 0; row < screen.getNumRows(); row++) {
                String curLine = br.readLine(); // the current line (row) of the map's txt file
                String curLineNumbers[] = curLine.split(" "); // convert the current line into an array

                for(int col = 0; col < screen.getNumColumns(); col++) {
                    int curTileNum = Integer.parseInt(curLineNumbers[col]); // current tile num at this row and col

                    board[col][row] = curTileNum; // load board data
                }
            }

            br.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * For each of our 4 tile types, load the correct image and set whether the tile is solid.
     */
    public void getTileImages() {

        try {
            // ground tile
            tileTypes[0] = new Tile();
            tileTypes[0].image = ImageIO.read(getClass().getResourceAsStream("/cave_background.png"));

            // outside barrier
            tileTypes[1] = new Tile();
            tileTypes[1].image = ImageIO.read(getClass().getResourceAsStream("/outside_barrier_tile.png"));
            tileTypes[1].collision = true;

            // inside barrier
            tileTypes[2] = new Tile();
            tileTypes[2].image = ImageIO.read(getClass().getResourceAsStream("/rock_tile.png"));
            tileTypes[2].collision = true;

            // ocean tile
            tileTypes[3] = new Tile();
            tileTypes[3].image = ImageIO.read(getClass().getResourceAsStream("/ocean_tile.png"));
            tileTypes[3].collision = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draw all the tiles in board[][] to the screen.
     *
     * @param g     the <code>Graphics</code> object to protect
     */
    public void draw(Graphics2D g) {
        for(int row = 0; row < screen.getNumRows(); row++) {
            for(int col = 0; col < screen.getNumColumns(); col++) {
                // current x and y position of this tile in pixels
                int curXPos = col * screen.getTileSize();
                int curYPos = row * screen.getTileSize();

                // current tile type number - corresponding to tileTypes
                int curTileNum = board[col][row];

                // draw corresponding tile onto screen
                g.drawImage(tileTypes[curTileNum].image, curXPos, curYPos,
                        screen.getTileSize(), screen.getTileSize(), null);
            }
        }
    }

    // GETTERS
    public int[][] getBoard() { return board; }
    public Tile[] getTileTypes() { return tileTypes; }

    /**
     * Load a new txt file encoding board information into board[][]. In other words, display a new map to the screen.
     * @param filePath
     */
    public void setMap(String filePath) {
        loadBoardData(filePath);
    }
}
