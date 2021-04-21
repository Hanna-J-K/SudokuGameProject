package project;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuColumnTest {
    SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    SudokuBoard sudokuBoardTest = new SudokuBoard(new BacktrackingSudokuSolver());
    List<SudokuField> field = Arrays.asList(new SudokuField[81]);

    private static final Logger logger = LoggerFactory.getLogger(SudokuBoard.class);

    @Test
    public void checkVerifyTest() {

        for (int row = 0; row < 81; row++) {
            field.set(row,new SudokuField(0));
        }

        for (int i = 0; i < 9; i++) {
            field.get(i).setFieldValue(i + 1);
        }
        SudokuColumn column = new SudokuColumn(field);
        assertTrue(column.verify());
    }

    @Test
    public void hashCodeColumnTest () {
        SudokuField field1 = new SudokuField(9);
        SudokuField field2 = new SudokuField(16);

        SudokuColumn column1 = new SudokuColumn(Arrays.asList(field1));
        SudokuColumn column2 = new SudokuColumn(Arrays.asList(field1));
        SudokuColumn columnDifferent = new SudokuColumn(Arrays.asList(field2));

//        System.out.println(column1.hashCode());
        System.out.println(column2.hashCode());
        System.out.println(columnDifferent.hashCode());

        logger.debug("Kolumna1 hashcode: ", column1.hashCode());
        logger.debug("Kolumna2 hashcode: ", column2.hashCode());
        logger.debug("KolumnaDifferent hashcode: ", column1.hashCode());


        assertEquals(column1.hashCode(), column2.hashCode());
        assertNotEquals(column1.hashCode(), columnDifferent.hashCode());

    }
    @Test
    public void columnToStringTest () {
        SudokuField field4 = new SudokuField(9);

        SudokuColumn column = new SudokuColumn(Arrays.asList(field4));
        System.out.println(column.toString());
    }

    @Test
    public void columnEqualsTest () {
        SudokuField field1 = new SudokuField(7);
        SudokuField field2 = new SudokuField(8);

        SudokuColumn column1 = new SudokuColumn(Arrays.asList(field1));
        SudokuColumn column2 = new SudokuColumn(Arrays.asList(field1));
        SudokuColumn columnDifferent = new SudokuColumn(Arrays.asList(field2));
        SudokuColumn columnNull = null;

        assertTrue(column1.equals(column2));
        assertFalse(column1.equals(columnDifferent));
        assertFalse(column1.equals(columnNull));

        assertEquals(column1, column1);
        assertTrue(column1.equals(column1));

        assertFalse(column1.equals(11));

        assertNotEquals(column1, columnNull);
        assertEquals(column1, column2);
        assertEquals(column1.hashCode(), column2.hashCode());
        assertEquals(columnNull, null);

        assertNotEquals(column1, columnDifferent);
        assertNotEquals(column1.hashCode(), columnDifferent.hashCode());
    }

    @Test
    public void cloneColumnTest () throws CloneNotSupportedException {
        SudokuField fieldC = new SudokuField(16);
        SudokuColumn sudokuColumnC = new SudokuColumn(Arrays.asList(fieldC));
        assertEquals(sudokuColumnC, sudokuColumnC.clone());
    }

}
