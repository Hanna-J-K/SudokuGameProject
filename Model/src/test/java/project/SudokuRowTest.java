package project;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuRowTest {
    SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    SudokuBoard sudokuBoardTest = new SudokuBoard(new BacktrackingSudokuSolver());
    List<SudokuField> field = Arrays.asList(new SudokuField[81]);
    boolean testFlag = false;
    private static final Logger logger = LoggerFactory.getLogger(SudokuBoard.class);

    @Test
    public void checkVerifyTest() {

        for (int row = 0; row < 81; row++) {
            field.set(row,new SudokuField(0));
        }

        for (int i = 0; i < 9; i++) {
            field.get(i).setFieldValue(i + 1);
        }
        SudokuRow row = new SudokuRow(field);
        assertTrue(row.verify());
    }

    public void hashCodeRowTest () {
        SudokuField field1 = new SudokuField(5);
        SudokuField field2 = new SudokuField(11);

        SudokuRow row1 = new SudokuRow(Arrays.asList(field1));
        SudokuRow row2 = new SudokuRow(Arrays.asList(field1));
        SudokuRow rowDifferent = new SudokuRow(Arrays.asList(field2));

//        System.out.println(row1.hashCode());
//        System.out.println(row2.hashCode());
//        System.out.println(rowDifferent.hashCode());

        logger.debug(String.valueOf(row1.hashCode()));
        logger.debug(String.valueOf(row2.hashCode()));
        logger.debug(String.valueOf(rowDifferent.hashCode()));


        assertEquals(row1.hashCode(), row2.hashCode());
        assertNotEquals(row1.hashCode(), rowDifferent.hashCode());

    }

    @Test
    public void rowToStringTest () {
        SudokuField field4 = new SudokuField(9);

        SudokuRow row = new SudokuRow(Arrays.asList(field4));
//        System.out.println(row.toString());
        logger.debug(String.valueOf(row.hashCode()));
    }

    @Test
    public void rowEqualsTest () {
        SudokuField field1 = new SudokuField(7);
        SudokuField field2 = new SudokuField(8);

        SudokuRow row1 = new SudokuRow(Arrays.asList(field1));
        SudokuRow row2 = new SudokuRow(Arrays.asList(field1));
        SudokuRow rowDifferent = new SudokuRow(Arrays.asList(field2));
        SudokuRow rowNull = null;

        assertTrue(row1.equals(row2));
        assertFalse(row1.equals(rowDifferent));
        assertFalse(row1.equals(rowNull));

        assertTrue(row1.equals(row1));

        assertEquals(row1, row1);

        assertFalse(row1.equals(11));

        assertNotEquals(row1, rowNull);
        assertEquals(row1, row2);
        assertEquals(row1.hashCode(), row2.hashCode());
        assertEquals(rowNull, null);

        assertNotEquals(row1, rowDifferent);
        assertNotEquals(row1.hashCode(), rowDifferent.hashCode());
    }

    @Test
    public void cloneRowTest () throws CloneNotSupportedException {
        SudokuField fieldR = new SudokuField(16);
        SudokuRow sudokuRowR = new SudokuRow(Arrays.asList(fieldR));
        assertEquals(sudokuRowR, sudokuRowR.clone());
    }
}
