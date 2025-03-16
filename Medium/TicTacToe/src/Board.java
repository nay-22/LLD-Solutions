package Medium.TicTacToe.src;

import Medium.TicTacToe.src.exceptions.InvalidBoardSetException;

public class Board {
    private char[][] board;
    private int filledCells;

    public Board() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public void set(int r, int c, Symbol symbol) throws InvalidBoardSetException {
        if (board[r][c] == ' ') {
            board[r][c] = symbol.name().charAt(0);
            filledCells++;
        } else {
            throw new InvalidBoardSetException("The specified rows have already been set.");
        }
    }

    public boolean isWinner(Symbol symbol) {
        return isAnyRowAndColValid(symbol) || isAnyDiagonalValid(symbol);
    }

    private boolean isAnyRowAndColValid(Symbol symbol) {
        for (int i = 0; i < 3; i++) {
            int rowCount = 0, colCount = 0;
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == symbol.name().charAt(0)) {
                    rowCount++;
                }
                if (board[j][i] == symbol.name().charAt(0)) {
                    colCount++;
                }
            }
            if (rowCount == 3 || colCount == 3) {
                return true;
            }
        }
        return false;
    }

    private boolean isAnyDiagonalValid(Symbol symbol) {
        boolean isPositiveDiagonalValid = true, isNegativeDiagonalValid = true;
        for (int i = 0; i < 3; i++) {
            if (board[i][i] != symbol.name().charAt(0)) {
                isPositiveDiagonalValid = false;
            }
            if (board[i][2 - i] != symbol.name().charAt(0)) {
                isNegativeDiagonalValid = false;
            }
        }

        return isPositiveDiagonalValid || isNegativeDiagonalValid;
    }

    public boolean isFull() {
        return filledCells == 9;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("-------------\n");

        for (int i = 0; i < 3; i++) {
            str.append("| ");
            for (int j = 0; j < 3; j++) {
                str.append(board[i][j]).append(" | ");
            }
            str.append("\n-------------\n");
        }
        return str.toString();
    }
}
