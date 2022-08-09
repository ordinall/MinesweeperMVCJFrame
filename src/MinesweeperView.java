import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Canvas extends JPanel {

    Font font1 = new Font("SansSerif", Font.BOLD, 14);
    private int cellSize;

    private JTextField[][] board;

    Canvas(int x, int y) {
        initCanvas(x, y);
    }

    public void initBoard() {
        removeAll();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new JTextField("");
                board[i][j].addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        MouseListener[] ml = Canvas.this.getMouseListeners();
                        for (MouseListener l : ml) {
                            l.mousePressed(e);
                        }
                    }
                });
                board[i][j].setName(j + " " + i);
                board[i][j].setFont(font1);
                board[i][j].setBounds(j * cellSize, i * cellSize, cellSize, cellSize);
                board[i][j].setBackground(Color.GRAY);
                board[i][j].setHorizontalAlignment(JTextField.CENTER);
                board[i][j].setEditable(false);
                add(board[i][j]);
            }
        }
    }

    public void initCanvas(int x, int y) {
        this.cellSize = 25;
        this.board = new JTextField[y][x];
        setLayout(null);
        setSize(x * cellSize, y * cellSize);
        setPreferredSize(new Dimension(x * cellSize, y * cellSize));
        initBoard();
        setBounds(0, 0, x * cellSize, y * cellSize);
        setBackground(Color.WHITE);
        setVisible(true);
    }

    public void setCell(int x, int y, String text, Color color) {
        board[y][x].setText(text);
        board[y][x].setBackground(color);
        board[y][x].setFont(font1);
        board[y][x].setHorizontalAlignment(JTextField.CENTER);

    }

    public void setMouseListener(MouseListener ml) {
        addMouseListener(ml);
    }
}

public class MinesweeperView extends JFrame {

    private final Canvas canvas;

    private final JComboBox<String> difficulty;

    private final JLabel Bombsleft;
    String[] difficulties = { "Easy", "Medium", "Hard" };

    MinesweeperView(int x, int y) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas = new Canvas(x, y);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = createGbc(0, 0, 1, 1);
        add(new JLabel(""), gbc);

        difficulty = new JComboBox<>(difficulties);
        difficulty.setSelectedIndex(0);
        add(difficulty, createGbc(1, 0, 1, 1));

        gbc = createGbc(2, 0, 1, 1);
        add(new JLabel(" "), gbc);

        Bombsleft = new JLabel("Bomb Left: 0");
        gbc = createGbc(3, 0, 1, 1);
        gbc.ipady = 30;
        add(Bombsleft, gbc);

        gbc = createGbc(4, 0, 1, 1);
        add(new JLabel(""), gbc);

        gbc = createGbc(0, 1, 5, 1);
        add(canvas, gbc);

        setTitle("Minesweeper MVC");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }

    public void setMouseListener(MouseListener listener) {
        canvas.setMouseListener(listener);
    }

    private GridBagConstraints createGbc(int x, int y, int width, int height) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;

        gbc.anchor = (x == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
        // gbc.fill = (x == 0) ? GridBagConstraints.BOTH
        // : GridBagConstraints.HORIZONTAL;

        gbc.weightx = (x == 0) ? 0.1 : 1.0;
        gbc.weighty = 1.0;
        return gbc;
    }

    public void reInit(int x, int y) {
        canvas.initCanvas(x, y);
    }

    public void setBombsLeft(int bombs) {
        this.Bombsleft.setText("Bomb Left: " + bombs);
    }

    public void setCell(int x, int y, String text, Color color) {
        canvas.setCell(x, y, text, color);
    }

    public void setDifficultyActionListener(ActionListener listener) {
        difficulty.addActionListener(listener);
    }

}
