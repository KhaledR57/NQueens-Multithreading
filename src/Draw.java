import javax.swing.*;
import java.awt.*;

public class Draw extends JFrame {
    private static final int N = 8;
    private static final int Square_Size = 80;

    public Draw() {
        super("line");
        setSize(Square_Size * N , Square_Size * N +  Square_Size);
        setTitle("Chess Board");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout());
        panel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new Board();
        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        panel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Draw();
    }

    public static class Board extends JPanel {

        public void paint(Graphics g) {
            int x, y;
            for (int row = 0; row < N; row++) {

                for (int col = 0; col < N; col++) {

                    // Set x coordinates of rectangle
                    x = row * Square_Size;

                    // Set y coordinates of rectangle
                    y = col * Square_Size;

                    // Check whether row and column
                    // are in even position
                    // If it is true set Black color
                    if ((row % 2 == 0) == (col % 2 == 0))
                        g.setColor(Color.BLACK);
                    else
                        g.setColor(Color.WHITE);

                    // Create a rectangle with
                    g.fillRect(x, y, Square_Size, Square_Size);
                }
            }
        }
    }

}