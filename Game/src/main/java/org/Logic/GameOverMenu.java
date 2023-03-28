/**
    This class represents the game over menu that is displayed when the player loses the game.
    It contains methods to display the game over menu and handle the user's interactions with the menu.
*/
package org.Logic;

import javax.swing.JButton;
import org.Display.Screen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Font;


public class GameOverMenu extends Menu {
    private Screen screen;

    /**
    * Creates a new instance of the GameOverMenu class with the specified screen.
    * 
    * @param screen The Screen object that the game is being displayed on.
    */

    public GameOverMenu(Screen screen) {
        this.screen = screen;
    }
    /**
    * Displays the game over menu on the screen.
    */
    public void displayGameOverMenu() {
        displayMenu("/gameover2.jpg");


        Font font = new Font("Arial", Font.BOLD, 16); //for the button text

        //creating restart game button
        JButton restartGameButton = new JButton("Restart Game");
        restartGameButton.setPreferredSize(new Dimension(140, 50));
        restartGameButton.setBounds(335, 220, 140, 50);
        restartGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //screen.endGameThread();
                screen.restartGame();
                dispose();
                //screen.startGameThread();
            }
        });

        //creating exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(30, 30));
        exitButton.setBounds(335, 300, 140, 50);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });

        //button text font
        restartGameButton.setFont(font);
        exitButton.setFont(font);

        //adding the components to the window
        getBackgroundPanel().add(restartGameButton);
        getBackgroundPanel().add(exitButton);

        //getContentPane().add(getBackgroundPanel());
        //setVisible(true);
    }
}
