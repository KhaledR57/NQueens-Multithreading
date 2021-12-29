import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Gui8Queens {
    JFrame f;
    JMenuBar menuBar = new JMenuBar();
    JMenuItem solve8QueenMenuItem = new JMenuItem("Solve 8 queen");
    JMenuItem validateMenuItem = new JMenuItem("Validate");
    CardLayout crd = new CardLayout();
    JPanel allDefaultPanel = new JPanel(new BorderLayout());
    JPanel defaultPanel = new JPanel(new GridLayout(8, 8));
    JPanel validatePanel = new JPanel(new GridLayout(9, 8));
    JButton[][] buttons = new JButton[8][8];
    JButton[][] validateButtons = new JButton[8][8];
    JButton next;
    JButton prev;
    JButton validate;
    HashSet<Object> postList = new HashSet<>();

    Gui8Queens() {
        f = new JFrame();
        next = new JButton("NEXT");
        prev = new JButton("PREV");
        validate = new JButton("Validate");
        List<List<String>> sols = new Solve().solveNQueens(8);
//        for ( List<String> list : sols) {
//            for ( String sol : list )
//                System.out.println(sol);
//            System.out.println("\n");
//        }

        AtomicInteger solutions = new AtomicInteger();
        System.out.println(sols.size());
        for (int i = 0; i < 8; i++) {
            List<String> sol = sols.get(solutions.get());
            for (int j = 0; j < 8; j++) {
                String ans = sol.get(i);
                //System.out.println(ans);
                buttons[i][j] = new JButton();
                validateButtons[i][j] = new JButton();
                if ((i + j) % 2 == 0) {
                    validateButtons[i][j].setBackground(Color.white);
                    buttons[i][j].setBackground(Color.white);
                } else {
                    buttons[i][j].setBackground(Color.black);
                    validateButtons[i][j].setBackground(Color.black);
                }
                if (ans.charAt(j) == 'Q')
                    buttons[i][j].setIcon(new ImageIcon("resources/queen1.png"));
                validateButtons[i][j].setActionCommand(i + " " + j);
                int finalI = i;
                int finalJ = j;
                validateButtons[i][j].addActionListener(e -> {
                    System.out.println(e.getActionCommand());
                    if (validateButtons[finalI][finalJ].getIcon() == null) {
                        validateButtons[finalI][finalJ].setIcon(new ImageIcon("resources/queen1.png"));
                        postList.add(e.getActionCommand());
                    } else {
                        validateButtons[finalI][finalJ].setIcon(null);
                        postList.remove(e.getActionCommand());
                    }

                });
                defaultPanel.add(buttons[i][j]);
                validatePanel.add(validateButtons[i][j]);
            }
        }
        validatePanel.add(validate);
        menuBar.add(solve8QueenMenuItem);
        menuBar.add(validateMenuItem);
        f.setJMenuBar(menuBar);
        allDefaultPanel.add(defaultPanel, BorderLayout.CENTER);
        allDefaultPanel.add(prev, BorderLayout.WEST);
        allDefaultPanel.add(next, BorderLayout.EAST);
        f.setLayout(crd);
        f.add(allDefaultPanel);
        f.add(validatePanel);
        f.setSize(1000, 1000);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        solve8QueenMenuItem.addActionListener(e -> {
            crd.first(f.getContentPane());
        });
        validateMenuItem.addActionListener(e -> {
            crd.last(f.getContentPane());
        });
        validate.addActionListener(e -> {
            if (postList.size() != 8) {
                JOptionPane.showMessageDialog(f, "Error Enter Valid Chess Board", "Error !!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ArrayList<StringBuilder> list = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                StringBuilder st = new StringBuilder();
                for (int j = 0; j < 8; j++) {
                    if (postList.contains(i + " " + j)) {
                        st.append('Q');
                    } else {
                        st.append(".");
                    }
                }
                list.add(st);
            }
            Validate validator = new Validate();
            validator.matrix = list;
            validator.validateNQueens(8);
            if (validator.res) {
                JOptionPane.showMessageDialog(f, "This Board Configuration is Valid ", "Success", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(f, "Error This Board Configuration is INValid !!", "Error !!", JOptionPane.ERROR_MESSAGE);
            }
            System.out.println(list);
        });
        final int solution = solutions.get();
        next.addActionListener(e -> {
            if (solution < sols.size() - 1)
                solutions.getAndIncrement();
            System.out.println(solutions.get());
            for (int i = 0; i < 8; i++) {
                try {
                    List<String> sol = sols.get(solutions.get());
                    for (int j = 0; j < 8; j++) {
                        String ans = sol.get(i);
                        if (ans.charAt(j) == 'Q')
                            buttons[i][j].setIcon(new ImageIcon("resources/queen1.png"));
                        else
                            buttons[i][j].setIcon(null);
                    }
                } catch (Exception ex) {
                    System.out.println("Array out");
                }
            }
        });
        prev.addActionListener(e -> {
            if (solutions.get() > 0)
                solutions.getAndDecrement();
            System.out.println(solutions.get());
            for (int i = 0; i < 8; i++) {
                try {
                    List<String> sol = sols.get(solutions.get());
                    for (int j = 0; j < 8; j++) {
                        String ans = sol.get(i);
                        if (ans.charAt(j) == 'Q')
                            buttons[i][j].setIcon(new ImageIcon("resources/queen1.png"));
                        else
                            buttons[i][j].setIcon(null);
                    }
                } catch (Exception ex) {
                    System.out.println("Array out");
                }
            }
        });
    }

    public static void main(String[] args) {
        new Gui8Queens();
    }
}