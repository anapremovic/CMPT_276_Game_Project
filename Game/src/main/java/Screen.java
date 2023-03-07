import java.awt.Canvas;
import javax.swing.JFrame;

public class Screen extends Canvas implements Runnable {
    JFrame frame;

    public Screen(String title, int width, int height) {
        frame = new JFrame(title);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.add(this);
    }

    public void run() {

    }
}
