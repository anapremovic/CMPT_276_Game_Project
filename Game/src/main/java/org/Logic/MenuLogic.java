/**
    The MenuLogic class creates a starting menu window with two buttons: Start Game and Exit.
    The Start Game button starts the game thread, while the Exit button closes the window and
    exits the program.
    The class also provides a method to check if the Start Game button has been pressed, and
    a displayMenu method to display the starting menu and wait for the user to make a choice.
    */

package org.Logic;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.Display.Screen;
import java.awt.Image;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Font;


public class MenuLogic extends JFrame {
    private Screen screen;
    public boolean isStartGameButtonPressed;
    private JButton startGameButton;
    private JButton exitButton;

    /**
    * Constructs a new MenuLogic object with the specified Screen object.
    * 
    * @param screen the Screen object used to display the starting menu
    */
    public MenuLogic(Screen screen) {
        this.screen = screen;
    }

    /**
    * Starts the game thread by calling the startGameThread method of the Screen object.
    */
    public void startGame() {
        screen.startGameThread();
    }

    /**
    * Exits the program by calling the System.exit method.
    */
    public void exitProgram() {
        System.exit(0);
    }

    /**
    * Returns whether the Start Game button has been pressed.
    * 
    * @return true if the Start Game button has been pressed, false otherwise
    */
    public boolean isStartGameButtonPressed() {
        return isStartGameButtonPressed;
    }
    
    /**
    * Displays the starting menu window and waits for the user to make a choice. 
    * 
    * @return true if the user pressed the Start Game button, false if the user pressed the Exit button
    */
    public boolean displayMenu() {
        createWindow();
        setBackground();
        createButtons();
        addButtonsToWindow();
    
        while (!isStartGameButtonPressed) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    
        return isStartGameButtonPressed;
    }
    
    private void createWindow() {
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(screen);
    }
    
    private void setBackground() {
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image backgroundImage = ImageIO.read(getClass().getResource("/start_game.jpg"));
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        backgroundPanel.setLayout(null);
        getContentPane().add(backgroundPanel);
        setVisible(true);
    }
    
    private void createButtons() {
        Font font = new Font("Arial", Font.BOLD, 18);

        startGameButton = new JButton("Start Game");
        startGameButton.setPreferredSize(new Dimension(50, 30));
        startGameButton.setBounds(330, 230, 170, 50);
        startGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isStartGameButtonPressed = true;
                dispose();
            }
        });
        startGameButton.setFont(font);

        exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(30, 30));
        exitButton.setBounds(330, 290, 170, 50);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        exitButton.setFont(font);
    }
    
    private void addButtonsToWindow() {
        JPanel backgroundPanel = (JPanel) getContentPane().getComponent(0);
        backgroundPanel.add(startGameButton);
        backgroundPanel.add(exitButton);
    }
    

}
