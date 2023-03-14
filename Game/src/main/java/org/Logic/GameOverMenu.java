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


public class GameOverMenu extends JFrame {
    private Screen screen;

    public GameOverMenu(Screen screen) {
        this.screen = screen;
    }

    public void displayGameOverMenu() {
        //creating a window for the game over menu
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(screen);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //setting the background image "back.jpg"
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image backgroundImage = ImageIO.read(getClass().getResource("/gameover2.jpg"));
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        backgroundPanel.setLayout(null);


        Font font = new Font("Arial", Font.BOLD, 16); //for the button text

        //creating restart game button
        JButton restartGameButton = new JButton("Restart Game");
        restartGameButton.setPreferredSize(new Dimension(140, 50));
        restartGameButton.setBounds(335, 220, 140, 50);
        restartGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                screen.startGameThread();
            }
        });

        //creating exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(140, 50));
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
        backgroundPanel.add(restartGameButton);
        backgroundPanel.add(exitButton);

        getContentPane().add(backgroundPanel);
        setVisible(true);
    }
}
