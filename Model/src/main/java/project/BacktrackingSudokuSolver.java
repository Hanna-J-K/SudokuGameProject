package project;

import java.io.Serializable;

public class BacktrackingSudokuSolver implements SudokuSolver, Serializable {


    private boolean solving(SudokuBoard board) {

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (board.get(row, column) == 0) {
                    for (int digit = 1; digit <= 9; digit++) {
                        board.set(row, column, digit);
                        if (board.getColumn(column).verify()
                                && board.getRow(row).verify()
                                && board.getBox(row, column).verify()) {
                            if (solving(board)) {
                                return true;
                            }
                        } else {
                            board.set(row, column, 0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public void solve(SudokuBoard board) {
        solving(board);
    }

}
