package org.GameObjects;

import org.Display.Screen;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class TileManagerTest {
    Screen screen = new Screen();

    @Test
    void testLoadBoardData() {
        TileManager tiles = new TileManager(screen);

        tiles.loadBoardData("/map_mock.txt");

        assertNotNull(tiles.getBoard());

        int[][] board = tiles.getBoard();

        // check that map loaded correctly
        assertEquals(0, board[0][0]);
        assertEquals(1, board[1][0]);
        assertEquals(2, board[0][1]);
        assertEquals(3, board[1][1]);
    }

    @Test
    void testGetTileImages() {
        TileManager tiles = new TileManager(screen);

        Tile[] tileTypes = tiles.getTileTypes();

        tiles.getTileImages();

        // assert we have all 4 tile images
        for(int i = 0; i < 4; i++) {
            assertNotNull(tileTypes[i].image);
        }
    }

    @Test
    void testGetBoard() {
        TileManager tiles = new TileManager(screen);
        int[][] board = tiles.getBoard();
        assertNotNull(board);
    }

    @Test
    void testGetTileTypes() {
        TileManager tiles = new TileManager(screen);
        Tile[] tileTypes = tiles.getTileTypes();
        assertNotNull(tileTypes);
    }
}