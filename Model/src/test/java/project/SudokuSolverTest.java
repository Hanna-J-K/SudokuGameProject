package project;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuSolverTest {

    SudokuBoard boardSolver = new SudokuBoard(new BacktrackingSudokuSolver());
    private static final Logger logger = LoggerFactory.getLogger(SudokuBoard.class);

    @Test
    public void backtrackingTest() {
        
        boardSolver.solveGame();
        int fieldSum = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                fieldSum += boardSolver.get(i, j);
            }
        }


        logger.debug(String.valueOf(fieldSum));
//        System.out.println(fieldSum);
        assertEquals(fieldSum, 405);

    }

}
