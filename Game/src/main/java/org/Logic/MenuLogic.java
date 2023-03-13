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

    public MenuLogic(Screen screen) {
        this.screen = screen;
    }

    public void startGame() {
        screen.startGameThread();
    }

    public void exitProgram() {
        System.exit(0);
    }

    public boolean isStartGameButtonPressed() {
        return isStartGameButtonPressed;
    }
    

    public boolean displayMenu() {
        //creating a window for the starting menu
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(screen);
        
        //setting the background image "start_game.jpg"
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

        //creating start game, start the game
        JButton startGameButton = new JButton("Start Game");
        startGameButton.setPreferredSize(new Dimension(50, 30));
        startGameButton.setBounds(330, 230, 170, 50);
        startGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isStartGameButtonPressed = true;
                dispose();
            }
        });

        //creating exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(30, 30));
        exitButton.setBounds(330, 290, 170, 50);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        //button text font
        Font font = new Font("Arial", Font.BOLD, 18);
        startGameButton.setFont(font);
        exitButton.setFont(font);

        //adding the buttons to the window
        backgroundPanel.add(startGameButton);
        backgroundPanel.add(exitButton);

        getContentPane().add(backgroundPanel);
        setVisible(true);

        while (!isStartGameButtonPressed) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //check if user wants to start or exit
        return isStartGameButtonPressed;

    }

}
