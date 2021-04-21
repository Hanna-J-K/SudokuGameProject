package project;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardTest {
    SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    SudokuBoard sudokuBoardTest = new SudokuBoard(new BacktrackingSudokuSolver());

    private static final Logger logger = LoggerFactory.getLogger(SudokuBoard.class);

    @Test
    public void pseudoTest() {
        SudokuBoard sudokuBoard0 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard0.solveGame();

//        System.out.println(sudokuBoard0.toString());

        logger.debug(sudokuBoard0.toString());

        sudokuBoard1.solveGame();

//        System.out.println(sudokuBoard1.toString());
        logger.debug(sudokuBoard1.toString());

    }


    @Test
    public void boardCheck() {
        sudokuBoard.solveGame();
        for (int i = 0; i < 9; i++) {
            assertTrue(sudokuBoard.getRow(i).verify());
            assertTrue(sudokuBoard.getColumn(i).verify());
        }
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                assertTrue(sudokuBoard.getBox(i, j).verify());
            }
        }
    }

    @Test
    public void differencesCheck() {

        List<Integer> board1 = Arrays.asList(new Integer[81]);
        List<Integer> board2 = Arrays.asList(new Integer[81]);
        SudokuBoard sudokuBoardTest = new SudokuBoard(new BacktrackingSudokuSolver());

        sudokuBoardTest.solveGame();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board1.set(i * 9 + j, sudokuBoardTest.get(i, j));
            }
        }

        sudokuBoardTest.solveGame();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board2.set(i * 9 + j, sudokuBoardTest.get(i, j));
            }
        }

        assertFalse(board1.equals(board2));
    }

    @Test
    public void checkFlagChangeTest() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

        sudokuBoard.listenerFlagChange();
        assertTrue(sudokuBoard.resultOfListener);
        sudokuBoard.listenerFlagChange();
        assertFalse(sudokuBoard.resultOfListener);
    }

    @Test
    public void listenerTest() {

        sudokuBoardTest.listenerFlagChange();

        sudokuBoardTest.set(3,3,1);
        sudokuBoardTest.set(3,4,1);

        sudokuBoardTest.set(4,3,1);

        sudokuBoardTest.set(3,3,0);
        sudokuBoardTest.set(3,4,0);

        sudokuBoardTest.set(2,2,3);
        sudokuBoardTest.set(1,1,3);


    }

    @Test
    public void boardHashCodeTest() {

        List<Integer> board1 = Arrays.asList(new Integer[81]);
        List<Integer> board2 = Arrays.asList(new Integer[81]);
        List<Integer> board3 = Arrays.asList(new Integer[81]);
        SudokuBoard sudokuBoardTest = new SudokuBoard(new BacktrackingSudokuSolver());

        sudokuBoardTest.solveGame();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board1.set(i * 9 + j, sudokuBoardTest.get(i, j));
                board3.set(i * 9 + j, sudokuBoardTest.get(i, j));
            }
        }

//        System.out.println(board1.hashCode());
//        System.out.println(board3.hashCode());
        logger.debug("Hashcode board1: ", board1.hashCode());
        logger.debug("Hashcode board1: ", board3.hashCode());

        assertEquals(board1.hashCode(), board3.hashCode());

        sudokuBoardTest.solveGame();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board2.set(i * 9 + j, sudokuBoardTest.get(i, j));
            }
        }

        //System.out.println(board2.hashCode());
        logger.debug("Hashcode board2: ", board2.hashCode());
        assertNotEquals(board1.hashCode(), board2.hashCode());

    }

    // wszystkie możliwe testy
    // za dużo

    @Test
    public void boardEqualsTest() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 = new SudokuBoard(solver);
        SudokuBoard sudokuBoard2 = new SudokuBoard(solver);
        SudokuBoard sudokuBoard3 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard sudokuNull = null;

        assertNotEquals(sudokuBoard1, sudokuNull);

        assertEquals(sudokuBoard1, sudokuBoard2);
        assertEquals(sudokuBoard1.hashCode(), sudokuBoard2.hashCode());

        assertTrue(sudokuBoard1.equals(sudokuBoard2));

        assertFalse(sudokuBoard1.equals(11));

        assertEquals(sudokuBoard1, sudokuBoard1);
        assertTrue(sudokuBoard1.equals(sudokuBoard1));

        assertNotEquals(sudokuBoard1, sudokuBoard3);

        sudokuBoard1.solveGame();
        sudokuBoard2.solveGame();

        assertNotEquals(sudokuBoard1, sudokuBoard2);
        assertNotEquals(sudokuBoard1.hashCode(), sudokuBoard2.hashCode());
        assertFalse(sudokuBoard1.equals(sudokuBoard2));
    }

    @Test
    public void extraSudokuFieldEqualsTest() {
        SudokuField field1 = new SudokuField(7);
        SudokuField field2 = null;

        assertNotEquals(field1, field2);
        assertEquals(field2, null);
        assertFalse(field1.equals(field2));
        assertTrue(field1.equals(field1));

        assertNotEquals(field1, 2);
    }

    @Test
    public void cloneBoardTest() throws CloneNotSupportedException {
        SudokuBoard sudokuBoardO = new SudokuBoard(new BacktrackingSudokuSolver());
        assertEquals(sudokuBoardO, sudokuBoardO.clone());
    }

    @Test
    public void enumTest() {
        Difficulty difficulty = Difficulty.EASY;
        assertEquals(difficulty, Difficulty.EASY);
    }

    @Test
    public void compareTest() {
        SudokuField fieldA = new SudokuField(1);
        SudokuField fieldB = new SudokuField(1);
        SudokuField fieldC = new SudokuField(7);
        assertEquals(fieldA.compareTo(fieldB), 0);
        assertEquals(fieldA.compareTo(fieldC), -6);
    }
}