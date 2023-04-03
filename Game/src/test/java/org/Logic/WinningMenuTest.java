package org.Logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javax.swing.JLabel;

import org.Display.Screen;
import org.junit.Before;
import org.junit.Test;

public class WinningMenuTest {

    private WinningMenu winningMenu;
    private Screen screen;

    @Before
    public void setUp() {
        screen = new Screen();
        winningMenu = new WinningMenu(60, screen, 20);
    }

    @Test
    public void testDisplayWinningMenu() {
        winningMenu.displayWinningMenu();
        assertNotNull(winningMenu.getBackgroundPanel());
        assertNotNull(winningMenu.getContentPane());

        // Test if the score label is created and displayed correctly
        JLabel scoreLabel = (JLabel) winningMenu.getBackgroundPanel().getComponent(0);
        assertNotNull(scoreLabel);
        assertEquals("380", scoreLabel.getText());

        // Test if the time taken label is created and displayed correctly
        JLabel timeTakenLabel = (JLabel) winningMenu.getBackgroundPanel().getComponent(1);
        assertNotNull(timeTakenLabel);
        assertEquals("01:00", timeTakenLabel.getText());
    }

}
