package org.Logic;

import java.io.PrintStream;
import java.util.*;

/**
 * The type Expansion list.
 */
class ExpansionList implements Comparable<ExpansionList> {
    private int x;
    private int y;
    private int expansionLevel;
    private int[] goal;
    private ExpansionList parent;
    private char directionEntered;

    /**
     * Instantiates a new Expansion list.
     *
     * @param x                the x
     * @param y                the y
     * @param expansionLevel   the expansion level
     * @param goal             the goal
     * @param parent           the parent
     * @param directionEntered the direction entered
     */
    public ExpansionList(int x, int y, int expansionLevel, int[] goal, ExpansionList parent, char directionEntered) {
        this.x = x;
        this.y = y;
        this.expansionLevel = expansionLevel;
        this.goal = goal;
        this.parent = parent;
        this.directionEntered = directionEntered;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
        return this.x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        return this.y;
    }

    /**
     * Gets expansion level.
     *
     * @return the expansion level
     */
    public int getExpansionLevel() {
        return expansionLevel;
    }

    /**
     * Heuristic for manhattan distance.
     *
     * @return the int
     */
    public int heuristic() {
        return Math.abs(goal[0] - this.x) + Math.abs(goal[1] - this.y);
    }

    @Override
    public int compareTo(ExpansionList e) {
        return this.heuristic() - e.heuristic();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ExpansionList)) return false;

        ExpansionList e = (ExpansionList) o;
        return this.getX() == e.getX() && this.getY() == e.getY();
    }

    @Override
    public int hashCode() {
        return (this.x * 257) * (this.y * 263);
    }

    /**
     * Whether this node has parent node.
     *
     * @return the boolean
     */
    public boolean hasParent() {
        return parent != null;
    }

    /**
     * Gets parent.
     *
     * @return the parent
     */
    public ExpansionList getParent() {
        return parent;
    }


    public String toString() {
        return "{(" + this.getY() + "," + this.getX() + "),expansion level:" + this.getExpansionLevel() + "}";
    }
}


/**
 * The type A star find path.
 */
public class AStarFindPath {

    private int[] start;
    private int[] goal;
    private int[][] maze;
    private ExpansionList solution;
    private static final int PATH = 0;

    /**
     * Gets solution.
     *
     * @return the solution
     */
    public ExpansionList getSolution() {
        return solution;
    }

    /**
     * Instantiates a new A star find path.
     *
     * @param start the start
     * @param goal  the goal
     * @param maze  the maze
     */
    public AStarFindPath(int[] start, int[] goal, int[][] maze) {
        this.start = start;
        this.goal = goal;
        this.maze = maze;
        this.solution = null;
    }


    /**
     * A star find path result.
     *
     * @return the path
     */
    public LinkedList<int[]> getPath() {
        LinkedList<int[]> path = new LinkedList<>();
        if (getSolution() == null) {

        } else {
            Stack<ExpansionList> stack = new Stack<ExpansionList>();
            for (ExpansionList current = getSolution(); current.hasParent(); current = current.getParent()) {
                stack.push(current);
            }
            while (!stack.empty()) {
                ExpansionList e = stack.pop();
                path.add(new int[]{e.getY(), e.getX()});
            }
        }
        return path;
    }


    /**
     * Main process of A* algorithm.
     */
    public void solve() {
        ExpansionList goalNode = new ExpansionList(goal[0], goal[1], 0, goal, null, ' ');
        ExpansionList current = new ExpansionList(start[0], start[1], 0, goal, null, ' ');
        PriorityQueue<ExpansionList> open = new PriorityQueue<ExpansionList>();
        HashSet<ExpansionList> explored = new HashSet<ExpansionList>();

        open.add(current);

        while (!open.isEmpty() && !current.equals(goalNode)) {
            //Up
            if (current.getY() > 0) {
                ExpansionList node = new ExpansionList(
                        current.getX(),
                        current.getY() - 1,
                        current.getExpansionLevel() + 1,
                        goal,
                        current,
                        '^'
                );

                if (maze[current.getY() - 1][current.getX()] == PATH && !explored.contains(node) && !open.contains(node)) {
                    open.add(node);
                }
            }
            //Down
            if (current.getY() < maze.length - 1) {
                ExpansionList node = new ExpansionList(
                        current.getX(),
                        current.getY() + 1,
                        current.getExpansionLevel() + 1,
                        goal,
                        current,
                        'v'
                );
                if (maze[current.getY() + 1][current.getX()] == PATH && !explored.contains(node) && !open.contains(node)) {
                    open.add(node);
                }
            }
            //Left
            if (current.getX() > 0) {
                ExpansionList node = new ExpansionList(
                        current.getX() - 1,
                        current.getY(),
                        current.getExpansionLevel() + 1,
                        goal,
                        current,
                        '<'
                );
                if (maze[current.getY()][current.getX() - 1] == PATH && !explored.contains(node) && !open.contains(node)) {
                    open.add(node);
                }
            }
            //Right
            if (current.getX() < maze[current.getY()].length - 1) {
                ExpansionList node = new ExpansionList(
                        current.getX() + 1,
                        current.getY(),
                        current.getExpansionLevel() + 1,
                        goal,
                        current,
                        '>'
                );

                if (maze[current.getY()][current.getX() + 1] == PATH && !explored.contains(node) && !open.contains(node)) {
                    open.add(node);
                }
            }
            explored.add(current);
            current = open.poll();
        }

        this.solution = current;
    }

}


