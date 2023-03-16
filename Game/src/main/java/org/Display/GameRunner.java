package org.Display;

import org.Logic.MenuLogic;

import javax.swing.*;

/**
 * Class that contains main() method for our game. Creates window, sets up all game objects, and commences
 * game loop.
 */
public class GameRunner {
    /**
     * Contains main game loop and general game display logic.
     */
    Screen screen;

    /**
     * Contains logic for starting, losing, and winning menus.
     */
    MenuLogic menuLogic;

    /**
     * Creates game window and runs our game.
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Starting The Legend of the Turtle: Turtle Run!");

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes the program when you click X
        window.setResizable(false); // do not allow resizing
        window.setTitle("The Legend of the Turtle: Turtle Run");

        Screen screen = new Screen();
        window.add(screen);

        window.pack(); // size window to fit size of screen

        window.setLocationRelativeTo(null); // window will be at centre of screen
        window.setVisible(true);

        screen.gameSetup(); // set up static game objects
        screen.startGameThread(); // commence GAME LOOP
    }
}
