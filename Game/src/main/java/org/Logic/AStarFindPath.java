package org.Logic;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

class ExpansionList implements Comparable<ExpansionList> {
    private int x;
    private int y;
    private int expansionLevel;
    private int[] goal;
    private ExpansionList parent;
    private char directionEntered;

    public ExpansionList(int x, int y, int expansionLevel, int[] goal, ExpansionList parent, char directionEntered) {
        this.x = x;
        this.y = y;
        this.expansionLevel = expansionLevel;
        this.goal = goal;
        this.parent = parent;
        this.directionEntered = directionEntered;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getExpansionLevel() {
        return expansionLevel;
    }

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

    public boolean hasParent() {
        return parent != null;
    }

    public ExpansionList getParent() {
        return parent;
    }

    public char getDirectionEntered() {
        return directionEntered;
    }

    public String toString() {
        return "{(" + this.getY() + "," + this.getX() + "),expansion level:" + this.getExpansionLevel() + "}";
    }
}


public class AStarFindPath {

    private int[] start;
    private int[] goal;
    private int[][] maze;
    private ExpansionList solution;
    private static final int PATH = 0;

    public void setStart(int[] start) {
        this.start = start;
    }

    public void setGoal(int[] goal) {
        this.goal = goal;
    }

    public void setMaze(int[][] maze) {
        this.maze = maze;
    }

    public int[][] getMaze() {
        return maze;
    }

    public int[] getStart() {
        return start;
    }

    public int[] getGoal() {
        return goal;
    }

    public ExpansionList getSolution() {
        return solution;
    }

    public AStarFindPath(int[] start, int[] goal, int[][] maze) {
        this.start = start;
        this.goal = goal;
        this.maze = maze;
        this.solution = null;
    }

    public boolean hasAccurateSolution() {
        if (solution == null) return false;
        return solution.getX() == goal[0] && solution.getY() == goal[1];
    }

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

    public void printSolutionPath(PrintStream output) {
        if (getSolution() == null) {
            output.println("No solution found. Call 'solve' to generate a solution path");
        } else {
            Stack<ExpansionList> stack = new Stack<ExpansionList>();
            output.println("Solution=" + getSolution().toString());
            output.println("getSolution().hasParent() =" + getSolution().hasParent());

            for (ExpansionList current = getSolution(); current.hasParent(); current = current.getParent()) {
                stack.push(current);
            }
            while (!stack.empty()) {
                ExpansionList e = stack.pop();
                output.println(e);
            }
        }
    }


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


class AStar {

    public static void main(String[] args) {
        System.out.println("Start");

        int[] start = {0, 0};
        int[] goal = {2, 6};

        int[][] maze =
                {
                        {0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 1, 0, 1, 0, 0, 1, 0},
                        {1, 0, 0, 0, 0, 0, 1, 0, 1, 0},
                        {0, 1, 0, 1, 0, 1, 0, 0, 1, 0},
                        {1, 0, 0, 0, 0, 1, 0, 0, 1, 0},
                        {1, 0, 0, 1, 0, 1, 0, 0, 1, 0},
                        {0, 0, 0, 1, 1, 0, 1, 0, 1, 0},
                        {0, 1, 0, 0, 0, 0, 1, 0, 0, 0}
                };
        AStarFindPath solver = new AStarFindPath(start, goal, maze);
        long startT = System.currentTimeMillis();
        solver.solve();
        System.out.println(System.currentTimeMillis() - startT);
        solver.printSolutionPath(System.out);
        if (!solver.hasAccurateSolution()) System.out.println("No Solution");
    }
}


