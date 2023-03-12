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
    private Tile[] tiles; // all the different types of tiles
    int board[][]; // 2D array of ints, where each int represents the tile type in the array tiles for that position

    public TileManager(Screen screen) {

        this.screen = screen;
        tiles = new Tile[4]; // we have 4 tile types in our game
        board = new int[screen.getNumColumns()][screen.getNumRows()];

        getTileImages();
        loadBoardData("/maps/map01.txt");
    }

    public void loadBoardData(String boardFilePath) {
        try {
            InputStream is = getClass().getResourceAsStream(boardFilePath); // load map file
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // used for reading the file

            for(int row = 0; row < screen.getNumRows(); row++) {
                String curLine = br.readLine(); // the current line (row) of the map's txt file
                String curLineNumbers[] = curLine.split(" "); // convert the current line into an array

                for(int col = 0; col < screen.getNumColumns(); col++) {
                    int curTileNum = Integer.parseInt(curLineNumbers[col]); // current tile num at this row and col

                    board[col][row] = curTileNum; // load into board 2D array
                }
            }

            br.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void getTileImages() {

        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/cave_background.png"));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/outside_barrier_tile.png"));

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/rock_tile.png"));

            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/lava.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {
        for(int row = 0; row < screen.getNumRows(); row++) {

            for(int col = 0; col < screen.getNumColumns(); col++) {
                int curXPos = col * screen.getTileSize();
                int curYPos = row * screen.getTileSize();

                int curTileNum = board[col][row];

                g.drawImage(tiles[curTileNum].image, curXPos, curYPos, screen.getTileSize(), screen.getTileSize(), null);
            }
        }
    }

}
