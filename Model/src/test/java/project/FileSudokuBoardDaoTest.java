package project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.custexceptions.FilesException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class FileSudokuBoardDaoTest {

    /*@BeforeEach
    public static void factoryTest() {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 = new SudokuBoard(solver);
        SudokuBoard sudokuBoard2 = new SudokuBoard(solver);
        Dao<SudokuBoard> file1;

    }*/
    private static final Logger logger = LoggerFactory.getLogger(SudokuBoard.class);
    static Locale locale = new Locale("en", "UK");
    static ResourceBundle exceptBundle = ResourceBundle.getBundle("Exception", locale);

    @Test
    public void readAndWriteTest() {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoardToWrite = new SudokuBoard(solver);
        SudokuBoard sudokuBoardToRead = new SudokuBoard (solver);
        Dao<SudokuBoard> file1;

        sudokuBoardToWrite.solveGame();

        file1 = factory.getFileDao("Game 1");
        try {
            file1.write(sudokuBoardToWrite);
        } catch (FilesException e) {
            e.printStackTrace();
            logger.debug(exceptBundle.getString("exgeneral"));

        }
        try {
            sudokuBoardToRead = file1.read();
        } catch (FilesException e) {
            e.printStackTrace();
            logger.debug(exceptBundle.getString("exgeneral"));
        }

        List<Integer> board1 = Arrays.asList(new Integer[81]);
        List<Integer> board2 = Arrays.asList(new Integer[81]);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board1.set(i * 9 + j, sudokuBoardToRead.get(i, j));
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board2.set(i * 9 + j, sudokuBoardToWrite.get(i, j));
            }
        }

        assertNotEquals(sudokuBoardToRead.hashCode(), sudokuBoardToWrite.hashCode());
        assertTrue(board1.equals(board2));
    }

    @Test
    public void exceptionsTest() throws Exception {
        SudokuBoardDaoFactory factory2 = new SudokuBoardDaoFactory();
        BacktrackingSudokuSolver solver2 = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard2 = new SudokuBoard(solver2);
        SudokuBoard sudokuBoard3 = null;
        Dao<SudokuBoard> file2;
        Dao<SudokuBoard> file3;
        Dao<SudokuBoard> file4;

        file2 = factory2.getFileDao("game2");

        file3 = factory2.getFileDao("?");
        //garbage collector
        file2.close();
        System.gc();
    }
}