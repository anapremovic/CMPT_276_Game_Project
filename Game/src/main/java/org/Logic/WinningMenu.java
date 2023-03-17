/**

The WinningMenu class creates a window to display the winning menu when the game is won.
It displays the score, time taken, restart game button, and exit button.
*/

package org.Logic;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.Display.Screen;
import org.GameObjects.Reward;

import java.awt.Image;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;


public class WinningMenu extends JFrame {
    /**
    * Time taken to win the game.
    */
    private long timeTaken;

    /**
    * The screen on which the game is played.
    */
    private Screen screen;
    
    /**
    * Points earned by the player.
    */
    private int points;


    /**
    * Constructs a WinningMenu object.
    * @param timeTaken The time taken to win the game.
    * @param screen The screen on which the game is played.
    * @param points Points earned by the player.
    */
    public WinningMenu(long timeTaken, Screen screen, int points) {
        this.timeTaken = timeTaken;
        this.screen = screen;
        this.points = points;
    }

    /**
    * Displays the winning menu.
    */
    public void displayWinningMenu() {
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
                    Image backgroundImage = ImageIO.read(getClass().getResource("/congrats.jpg"));
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        backgroundPanel.setLayout(null);

        //calculate score based on time taken and bonus reward
        int score = (int) (1000 - timeTaken * 12);
        if (score < 0){
            score = 0;
        }
        
        if (points > 10){
            score = score + 100;
        }

        //creating score label
        JLabel scoreLabel = new JLabel("" + score);
        scoreLabel.setBounds(450, 210, 170, 50);
        scoreLabel.setForeground(Color.black);
        Font font2 = new Font("Arial", Font.BOLD, 40); // for the score and time text
        scoreLabel.setFont(font2);


        //creating time taken label
        JLabel timeTakenLabel = new JLabel("" + formatTime((int)timeTaken));
        timeTakenLabel.setBounds(450, 270, 170, 50);
        Font font = new Font("Arial", Font.BOLD, 16); //for the button text
        timeTakenLabel.setForeground(Color.black);
        timeTakenLabel.setFont(font2);

        //creating restart game button
        JButton restartGameButton = new JButton("Restart Game");
        restartGameButton.setPreferredSize(new Dimension(30, 30));
        restartGameButton.setBounds(330, 420, 140, 50);
        restartGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                screen.restartGame();
                dispose();
                //screen.startGameThread();
            }
        });

        //creating exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(30, 30));
        exitButton.setBounds(330, 480, 140, 50);
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
        backgroundPanel.add(scoreLabel);
        backgroundPanel.add(timeTakenLabel);
        backgroundPanel.add(restartGameButton);
        backgroundPanel.add(exitButton);

        getContentPane().add(backgroundPanel);
        setVisible(true);
    }

    //helper method to format time in mm:ss format
    private String formatTime(int timeInSeconds) {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
