import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Board {
    JFrame frame = new JFrame();
    int[][] board = {{0, 1, 0, 0, 0, 1, 0}, {1, 0, 1, 0, 0, 0, 0}, {1, 0, 0, 2, 0, 1, 0}, {1, 0, 1, 1, 0, 0, 0}};

    public Board() {
        try {
            InputStream barrier = getClass().getResourceAsStream("/brick.png");
            InputStream backGround = getClass().getResourceAsStream("/background.png");
            InputStream turtle = getClass().getResourceAsStream("/turtle.png");
            ImageIcon barrierIcon = new ImageIcon(ImageIO.read(barrier));
            ImageIcon backGroundIcon = new ImageIcon(ImageIO.read(backGround));
            ImageIcon turtleIcon = new ImageIcon(ImageIO.read(turtle).getScaledInstance(64, 64, Image.SCALE_SMOOTH));
            int m = board.length;
            int n = board[0].length;
            for (int[] row : board) {
                for (int val : row) {
                    JLabel jLabel;
                    if (val == 0) jLabel = new JLabel(backGroundIcon);
                    else if (val == 2) jLabel = new JLabel(turtleIcon);
                    else jLabel = new JLabel(barrierIcon);
                    jLabel.setSize(Constants.ICON_WIDTH, Constants.ICON_HEIGHT);
                    jLabel.setLayout(null);
                    frame.add(jLabel);
                }
            }
            frame.setLayout(new GridLayout(m, n, 0, 0));
            frame.setSize(n * Constants.ICON_WIDTH, m * Constants.ICON_HEIGHT);
            frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JFrame getFrame() {
        return frame;
    }

    public static void main(String[] args) {
        new Board().getFrame();
    }
}
