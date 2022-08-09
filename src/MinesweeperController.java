import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class MinesweeperController {
    private MinesweeperModel model;
    private MinesweeperView view;

    public void run() {
        model = new MinesweeperModel(20, 20, 20);

        view = new MinesweeperView(20, 20);
        view.setMouseListener(new cellListener());
        view.setDifficultyActionListener(new difficultyListener());
        view.setBombsLeft(model.getNumMines());
    }

    public void updateBoard() {
        String[][] boardModel = model.getBoardModel();
        boolean[][] revealed = model.getRevealed();
        boolean[][] flagged = model.getFlagged();
        int numMines = model.getNumMines();
        int numFlagged = model.getNumFlagged();
        int gameOver = model.isGameOver();

        for (int i = 0; i < boardModel.length; i++) {
            for (int j = 0; j < boardModel[i].length; j++) {
                if (revealed[i][j]) {
                    if (Objects.equals(boardModel[i][j], "X")) {
                        view.setCell(i, j, "X", Color.RED);
                    } else {
                        view.setCell(i, j, boardModel[i][j], Color.WHITE);
                    }
                } else if (flagged[i][j]) {
                    view.setCell(i, j, "▶", Color.PINK);
                } else {
                    view.setCell(i, j, "", Color.GRAY);
                }

            }
        }
        view.setBombsLeft(numMines - numFlagged);
        if (gameOver == -1) {
            JOptionPane.showMessageDialog(view, "You Lose!");
            model.initModel(model.getX(), model.getY(), model.getNumMines());
            updateBoard();
        } else if (gameOver == 1) {
            JOptionPane.showMessageDialog(view, "You Win!");
            model.initModel(model.getX(), model.getY(), model.getNumMines());
            updateBoard();
        }

    }

    class cellListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            String[] coords = ((JTextField) e.getSource()).getName().split(" ");
            int cellx = Integer.parseInt(coords[0]);
            int celly = Integer.parseInt(coords[1]);
            if (e.getButton() == MouseEvent.BUTTON1) {
                model.reveal(cellx, celly);
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                model.flag(cellx, celly);
            }
            updateBoard();

        }

    }

    class difficultyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String difficulty = ((JComboBox<?>) e.getSource()).getSelectedItem().toString();
            switch (difficulty) {
                case "Easy":
                    model.initModel(20, 20, 20);
                    view.reInit(20, 20);
                    break;
                case "Medium":
                    model.initModel(30, 25, 60);
                    view.reInit(30, 25);
                    break;
                case "Hard":
                    model.initModel(40, 30, 200);
                    view.reInit(40, 30);
                    break;
            }
            updateBoard();
            view.pack();
        }
    }

}
