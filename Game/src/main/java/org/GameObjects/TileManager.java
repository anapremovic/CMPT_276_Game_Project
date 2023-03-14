package org.GameObjects;

import org.Display.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;


public class TileManager {
    private Screen screen;
    private Tile[] tileTypes; // all the different types of tiles
    private int board[][]; // 2D array of ints, where each int represents the tile type corresponding to tiles[] for that position

    public TileManager(Screen screen) {
        this.screen = screen;
        tileTypes = new Tile[4]; // we have 4 tile types in our game
        board = new int[screen.getNumColumns()][screen.getNumRows()];

        getTileImages();
        loadBoardData("/maps/map01.txt");
    }

    // load board data from given txt file into board[][]
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

    // load all possible tile images into tileTypes[]
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

            tileTypes[3] = new Tile();
            tileTypes[3].image = ImageIO.read(getClass().getResourceAsStream("/ocean_tile.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // draw tiles onto screen
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

    public int[][] getBoard() { return board; }
    public Tile[] getTileTypes() { return tileTypes; }

    public void setMap(String filePath) {
        loadBoardData(filePath);
    }
}
