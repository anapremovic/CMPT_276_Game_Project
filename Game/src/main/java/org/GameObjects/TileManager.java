package org.GameObjects;

import org.Display.*;
import java.io.IOException;
import javax.imageio.ImageIO;


public class TileManager {
    
    Screen screen;
    Tile[] tile;

    public TileManager(Screen screen) {

        this.screen = screen;
        tile = new Tile[10];

        getTileImage();
    
    }

    public void getTileImage() {

        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("background.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("background.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("background.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
