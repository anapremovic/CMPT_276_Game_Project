package org.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Takes user input in order to facilitate movement of the main character. Incorporates user input from
 * WASD keys as well as arrow keys.
 */
public class KeyHandler implements KeyListener {
    /**
     * Booleans that indicate whether a key to go up, down, left, or right has been pressed by the user.
     */
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Manually sets all key presses to false, which makes the main character stop moving.
     */
    public void nomovement(){
        upPressed = false;
        leftPressed = false;
        downPressed = false;
        rightPressed = false;
    }

    /**
     * Takes key press information from the user and updates corresponding boolean.
     *
     * @param e     the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // code of key pressed

        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if(code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if(code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if(code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }

    }

    /**
     * Takes key release information from user and updates the corresponding boolean.
     *
     * @param e     the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode(); // code of key released

        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }
}
