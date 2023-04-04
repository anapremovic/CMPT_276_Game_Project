package org.Input;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class KeyHandlerTest {

    private KeyHandler keyHandler;

    @BeforeEach
    void setUp() { // Instantiates new KeyHandler object before each test is run to ensure independence
        keyHandler = new KeyHandler();
    }
//    @Test
//    void testKeyPressed() { // Verifies that correct boolean value based on key code passed in
//
//        KeyEvent keyEvent = new KeyEvent( null, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, KeyEvent.CHAR_UNDEFINED);
//        keyHandler.keyPressed(keyEvent);
//        assertTrue(keyHandler.upPressed);
//        assertTrue(keyHandler.downPressed);
//        assertTrue(keyHandler.leftPressed);
//        assertTrue(keyHandler.rightPressed);
//    }
//
//    @Test
//    void testKeyReleased() { // Verifies that KeyReleased sets correct boolean value based on key passed in
//        KeyEvent keyEvent = new KeyEvent(null, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_A, KeyEvent.CHAR_UNDEFINED);
//        keyHandler.keyReleased(keyEvent);
//        assertFalse(keyHandler.upPressed);
//        assertFalse(keyHandler.downPressed);
//        assertFalse(keyHandler.leftPressed);
//        assertFalse(keyHandler.rightPressed);
//    }

    @Test
    void testNomovement() { // Verifies that all boolean values are set to false
        keyHandler.stopMovement();
        assertFalse(keyHandler.upPressed);
        assertFalse(keyHandler.downPressed);
        assertFalse(keyHandler.leftPressed);
        assertFalse(keyHandler.rightPressed);
    }



}



