package org.Logic;

import static org.junit.jupiter.api.Assertions.*;

import org.Display.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MenuLogic Test")
public class MenuLogicTest {

    private MenuLogic menuLogic;
    
    @BeforeEach
    void setUp() {
        Screen screen = new Screen();
        menuLogic = new MenuLogic(screen);
    }

    @Test
    @DisplayName("Start game button pressed")
    void testStartGameButtonPressed() {
        assertFalse(menuLogic.isStartGameButtonPressed());
        menuLogic.displayMenu();
        assertTrue(menuLogic.isStartGameButtonPressed());
    }
    
    @Test
    @DisplayName("Exit program")
    void testExitProgram() {
        assertDoesNotThrow(() -> menuLogic.exitProgram());
    }
    
    @Test
    @DisplayName("Start game thread")
    void testStartGame() {
        assertDoesNotThrow(() -> menuLogic.startGame());
    }
}
