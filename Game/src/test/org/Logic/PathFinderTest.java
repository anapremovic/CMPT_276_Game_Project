package org.Logic;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

public class PathFinderTest {
    public static int[][] maze1 = {{0, 0, 1}, {1, 0, 0}, {0, 0, 2}};
    public static int[][] maze2 = {{0, 0, 1}, {1, 1, 1}, {0, 0, 2}};
    public static PathFinder pathFinder1 = new PathFinder(maze1);
    public static PathFinder pathFinder2 = new PathFinder(maze2);

    //test could find correct path
    @Test
    public void testShortestPath() {
        LinkedList<int[]> path = pathFinder1.shortestPath(new int[]{0, 0}, new int[]{2, 0});
        LinkedList<int[]> expectedPath = new LinkedList<>(Arrays.asList(new int[]{0, 1}, new int[]{1, 1}));
        Assert.assertEquals(path.size(), expectedPath.size());
        for (int i = 0; i < path.size(); i++) {
            Assert.assertArrayEquals(expectedPath.get(i), path.get(i));
        }
    }
    //test no path return empty result
    @Test
    public void testNoPath() {
        LinkedList<int[]> path = pathFinder2.shortestPath(new int[]{0, 0}, new int[]{2, 0});
        Assert.assertEquals(0, path.size());
    }
    // test for big maze run time within 100 millseconds
    @Test(timeout = 100)
    public void testBigMaze() {
        int[][] maze = new int[50][100];
        for (int i = 0; i < maze.length; i++) {
            Arrays.fill(maze[i], 1);
        }
        for (int i = 0; i < maze.length; i++) {
            maze[i][0] = 0;
        }
        for (int i = 0; i < maze[0].length; i++) {
            maze[maze.length - 1][i] = 0;
        }
        LinkedList<int[]> path = new PathFinder(maze).shortestPath(new int[]{0, 0}, new int[]{maze[0].length - 1, maze.length - 1});
        Assert.assertEquals(148, path.size());
    }
}