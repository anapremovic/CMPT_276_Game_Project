package org.GameObjects;

import org.Display.Screen;
import org.GameObjects.Enemy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class EnemyTest {
    int tileSize = 48;
    int board[][] = new int[][]{
            {1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1},
            {1, 0, 1, 0, 1},
            {1, 0, 0, 0, 1},
            {1, 1, 1, 1, 1},
    };
    Enemy enemy = new Enemy(tileSize, board);

    //test passable
    @Test
    public void testPassable() {
        Assert.assertTrue(enemy.canPassable(1, 1));
        Assert.assertFalse(enemy.canPassable(0, 0));
    }

    // test adjust enemy to empty board
    @Test
    public void testAdjust() {
        int[] boardPos = enemy.adjust(55, 55);
        Assert.assertTrue(enemy.canPassable(boardPos[0], boardPos[1]));
    }

    @Test
    public void testRight() {
        enemy.setStartingValues(96, 48, 2, "UNKNOWN");
        enemy.update(150, 48);
        Assert.assertEquals("RIGHT", enemy.direction);
        Assert.assertEquals(98, enemy.xPos);
        Assert.assertEquals(48, enemy.yPos);
    }
    @Test
    public void testLeft() {
        enemy.setStartingValues(125, 48, 2, "UNKNOWN");
        enemy.update(55, 48);
        Assert.assertEquals("LEFT", enemy.direction);
        Assert.assertEquals(123, enemy.xPos);
        Assert.assertEquals(48, enemy.yPos);
    }
    @Test
    public void testUp() {
        enemy.setStartingValues(48, 100, 2, "UNKNOWN");
        enemy.update(48, 42);
        Assert.assertEquals("UP", enemy.direction);
        Assert.assertEquals(48, enemy.xPos);
        Assert.assertEquals(98, enemy.yPos);
    }
    @Test
    public void testDown() {
        enemy.setStartingValues(48, 50, 2, "UNKNOWN");
        enemy.update(48, 100);
        Assert.assertEquals("DOWN", enemy.direction);
        Assert.assertEquals(48, enemy.xPos);
        Assert.assertEquals(52, enemy.yPos);
    }
    @Test
    public void testTransposeMatrix() {
        int[][] matrix = new int[][]{{0, 1}, {2, 3}};
        int[][] transposedMatrix = new int[][]{{0, 2}, {1, 3}};
        Assert.assertArrayEquals(enemy.transposeMatrix(matrix),transposedMatrix);
    }
}