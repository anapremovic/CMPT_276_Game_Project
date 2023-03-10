package org.Display;

import org.GameObjects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Screen extends Canvas implements Runnable {
    JFrame frame;
    private Thread thread;
    private boolean running;
    int width;
    int height;

    // temporarily in Screen
    private MainCharacter player = new MainCharacter(100, 100, 64, 64);

    public Screen(String title, int w, int h) {
        running = false;
        frame = new JFrame(title);

        width = w;
        height = h;

        // screen properties
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes the program when you click X
        frame.setSize(width, height); // set size of the screen
        frame.setResizable(false); // do not allow resizing
        frame.setVisible(true); // set the screen to visible
        frame.add(this);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // start game logic
    public void start() {
        running = true;

        // create our new thread
        thread = new Thread(this);
        thread.start();
    }

    // stop game logic
    public void stop() {
        running = false;

        // stop our thread
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;

        while (running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1)
            {
                tick();
                updates++;
                delta--;
            }
            Render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }

        stop();
    }

    // update screen
    private void tick() {
        player.tick();
    }

    // paint onto window
    private void Render() {
        BufferStrategy bs = this.getBufferStrategy(); // makes transitions smoother

        // if haven't created BufferStrategy yet, create it
        if(bs == null) {
            this.createBufferStrategy(2);
            bs = this.getBufferStrategy();
        }

        Graphics g = bs.getDrawGraphics(); // create graphics

        // test if Render works
        g.setColor(Color.blue);
        g.fillRect(0, 0, width, height);

        player.Render(g);

        bs.show();
        g.dispose(); // clear the screen
    }

    public void MainMenu() {
        // implement me
    }

    public void WiningScreen() {
        // implement me
    }

    public void GameScreen() {
        // implement me
    }

    public void GameOverScreen() {
        // implement me
    }
}
