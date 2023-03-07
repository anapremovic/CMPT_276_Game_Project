package org.Display;

public class GameRunner {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Screen screen = new Screen("CMPT 276 Game", 800, 600);
        screen.start();
    }
}
