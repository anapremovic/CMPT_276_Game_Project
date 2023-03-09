import java.awt.Canvas;
import javax.swing.JFrame;

public class Screen extends Canvas implements Runnable {
    int width;
    int height;
    JFrame frame;

    public Screen(String title, int width, int height) {
        frame = new JFrame(title);
        this.width = width;
        this.height = height;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(this.width, this.height);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.add(this);
    }

    public void MainMenu() {

    }

    public void WiningScreen() {

    }

    public void GameScreen() {

    }

    public void GameOverScreen() {

    }

    public void run() {

    }
}
