package project;


import org.junit.jupiter.api.Test;
import project.custexceptions.DBException;
import project.custexceptions.GeneralException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SudokuJDBCTest {

    SudokuBoard testBoard1 = new SudokuBoard(new BacktrackingSudokuSolver());
    SudokuBoard testBoard2;


    @Test
    void loadedEqualsTest() {
        try (Dao<SudokuBoard> databaseSudoku = SudokuBoardDaoFactory.getSudokuJDBC("test2")) {
            testBoard1.solveGame();
            databaseSudoku.write(testBoard1);

            testBoard2 = databaseSudoku.read();

            //assertThrows(); !!!
            assertEquals(testBoard1.toString(), testBoard2.toString());
        } catch (GeneralException | DBException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
