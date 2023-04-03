package org.Logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

import org.Display.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("GameOverMenu Test")
public class GameOverMenuTest {

    private Screen screen;
    private GameOverMenu gameOverMenu;
    
    @BeforeEach
    void setUp() {
        screen = new Screen();
        gameOverMenu = new GameOverMenu(screen);
    }
    
    @Test
    @DisplayName("Test displayGameOverMenu")
    void testDisplayGameOverMenu() {
        gameOverMenu.displayGameOverMenu();
        assertNotNull(gameOverMenu.getBackgroundPanel());
        assertEquals(2, gameOverMenu.getBackgroundPanel().getComponentCount());
        
        JButton restartGameButton = (JButton) gameOverMenu.getBackgroundPanel().getComponent(0);
        assertEquals("Restart Game", restartGameButton.getText());
        assertEquals(new Dimension(140, 50), restartGameButton.getPreferredSize());
        assertEquals(335, restartGameButton.getX());
        assertEquals(220, restartGameButton.getY());
        Font font = new Font("Arial", Font.BOLD, 16);
        assertEquals(font, restartGameButton.getFont());
        
        JButton exitButton = (JButton) gameOverMenu.getBackgroundPanel().getComponent(1);
        assertEquals("Exit", exitButton.getText());
        assertEquals(new Dimension(30, 30), exitButton.getPreferredSize());
        assertEquals(335, exitButton.getX());
        assertEquals(300, exitButton.getY());
        assertEquals(font, exitButton.getFont());
    }
}
