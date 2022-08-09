

public class MinesweeperModel {

    private int x;
    private int y;
    private int numMines;

    private int numFlagged;

    private int numRevealed;
    private boolean[][] flagged;
    private boolean[][] revealed;
    private String[][] boardModel;

    public MinesweeperModel(int x, int y, int numMines) {
        this.initModel(x, y, numMines);

    }

    public void initModel(int x, int y, int numMines) {

        this.x = x;
        this.y = y;
        this.numMines = numMines;
        this.numFlagged = 0;
        this.numRevealed = 0;
        revealed = new boolean[x][y];
        boardModel = new String[x][y];
        flagged = new boolean[x][y];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                revealed[i][j] = false;
                boardModel[i][j] = "";
            }
        }

        placeMines();
        placeNumbers();
    }

    public void placeMines() {
        int minesPlaced = 0;
        while (minesPlaced < numMines) {
            int x = (int) (Math.random() * this.x);
            int y = (int) (Math.random() * this.y);
            if (boardModel[x][y].equals("")) {
                boardModel[x][y] = "M";
                minesPlaced++;
            }
        }
    }

    public void placeNumbers() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (boardModel[i][j].equals("")) {
                    int numMines = 0;
                    for (int k = -1; k <= 1; k++) {
                        for (int l = -1; l <= 1; l++) {
                            if (i + k >= 0 && i + k < x && j + l >= 0 && j + l < y) {
                                if (boardModel[i + k][j + l].equals("M")) {
                                    numMines++;
                                }
                            }
                        }
                    }
                    if (numMines > 0) {
                        boardModel[i][j] = "" + numMines;
                    }
                }
            }
        }
    }

    public void reveal(int x, int y) {

        if(x < 0 || x >= this.x || y < 0 || y >= this.y) {
            return;
        }
        if(flagged[x][y]) {
            return;
        }
        if (revealed[x][y]) {
            return;
        }
        if (boardModel[x][y].equals("M")) {
            boardModel[x][y] = "X";
            revealed[x][y] = true;
            return;
        }
        if (boardModel[x][y].equals("")) {
            revealed[x][y] = true;
            numRevealed++;
            reveal(x - 1, y);
            reveal(x + 1, y);
            reveal(x, y - 1);
            reveal(x, y + 1);
            reveal(x - 1, y - 1);
            reveal(x + 1, y + 1);
            reveal(x - 1, y + 1);
            reveal(x + 1, y - 1);
        } else {
            revealed[x][y] = true;
            numRevealed++;
        }
    }

    public void flag(int x, int y) {
        if(!revealed[x][y]) {
            if (flagged[x][y]) {
                flagged[x][y] = false;
                numFlagged--;
            } else {
                flagged[x][y] = true;
                numFlagged++;
            }
        }
    }

    public String[][] getBoardModel() {
        return boardModel;
    }

    public int getNumMines() {
        return numMines;
    }

    public int getNumFlagged() {
        return numFlagged;
    }

    public boolean[][] getFlagged() {
        return flagged;
    }

    public boolean[][] getRevealed() {
        return revealed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int isGameOver() {
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                if(boardModel[i][j].equals("X")) {
                    return -1;
                }
            }
        }
        return numRevealed == x * y - numMines ? 1 : 0;
    }

}
