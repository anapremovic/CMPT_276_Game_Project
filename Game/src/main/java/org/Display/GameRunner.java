package org.Display;

import org.Logic.MenuLogic;

import javax.swing.*;

public class GameRunner {
    Screen screen;
    MenuLogic menuLogic;
    public static void main(String[] args) {
        System.out.println("Starting 2D Game!");

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes the program when you click X
        window.setResizable(false); // do not allow resizing
        window.setTitle("Group 4 Game");

        Screen screen = new Screen();
        window.add(screen);

        window.pack(); // size window to fit size of screen

        window.setLocationRelativeTo(null); // window will be at centre of screen
        window.setVisible(true);

        screen.startGameThread();
    }
}
