package org.Display;

import org.Logic.MenuLogic;

import javax.swing.*;

public class GameRunner {
    Screen screen;
    MenuLogic menuLogic;
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
