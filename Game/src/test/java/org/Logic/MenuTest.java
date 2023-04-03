package org.Logic;

import org.junit.Test;

import javax.swing.JPanel;

import static org.junit.Assert.*;

public class MenuTest {

    @Test
    public void testDisplayMenu() {
        Menu menu = new Menu() {};
        String imagePath = "/path/to/background/image.png";
        menu.displayMenu(imagePath);

        JPanel backgroundPanel = menu.getBackgroundPanel();
        assertNotNull(backgroundPanel); // check if background panel exists
        assertEquals(800, menu.getWidth()); // check if the menu window width is correct
        assertEquals(600, menu.getHeight()); // check if the menu window height is correct
        assertFalse(menu.isResizable()); // check if the menu window is resizable
    }
}
