import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class Screen extends Canvas implements Runnable {
    JFrame frame;
    private Thread thread;
    private boolean running;
    int width;
    int height;

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
        while(running) {
            tick();
            Render();
        }
    }

    // update screen
    private void tick() {

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

        bs.show();
        g.dispose(); // clear the screen
    }
}
