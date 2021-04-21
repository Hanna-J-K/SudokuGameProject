package project;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoxTest {

    private static final Logger logger = LoggerFactory.getLogger(SudokuBoard.class);

    @Test
    public void checkVerifyTest() {
        List<SudokuField> field = Arrays.asList(new SudokuField[81]);

        for (int row = 0; row < 81; row++) {
            field.set(row,new SudokuField(0));
        }

        for (int i = 0; i < 9; i++) {
            field.get(i).setFieldValue(i + 1);
        }
        SudokuBox box = new SudokuBox(field);
        assertTrue(box.verify());
    }

    public void hashCodeBoxTest () {
        SudokuField field1 = new SudokuField(7);
        SudokuField field2 = new SudokuField(8);

        SudokuBox box1 = new SudokuBox(Arrays.asList(field1));
        SudokuBox box2 = new SudokuBox(Arrays.asList(field1));
        SudokuBox boxDifferent = new SudokuBox(Arrays.asList(field2));

//        System.out.println(box1.hashCode());
        logger.debug(String.valueOf(box1.hashCode()));

//        System.out.println(box2.hashCode());
        logger.debug(String.valueOf(box2.hashCode()));
//        System.out.println(boxDifferent.hashCode());
        logger.debug(String.valueOf(boxDifferent.hashCode()));

        assertEquals(box2.hashCode(), box2.hashCode());
        assertNotEquals(box2.hashCode(), boxDifferent.hashCode());

    }

    @Test
    public void boxToStringTest () {
        SudokuField field4 = new SudokuField(9);

        SudokuBox box = new SudokuBox(Arrays.asList(field4));

        logger.debug(box.toString());
//        System.out.println(box.toString());
    }

    @Test
    public void boxEqualsTest () {
        SudokuField field1 = new SudokuField(7);
        SudokuField field2 = new SudokuField(8);

        SudokuBox box1 = new SudokuBox(Arrays.asList(field1));
        SudokuBox box2 = new SudokuBox(Arrays.asList(field1));
        SudokuBox boxDifferent = new SudokuBox(Arrays.asList(field2));
        SudokuBox boxNull = null;

        assertTrue(box1.equals(box2));
        assertFalse(box1.equals(boxDifferent));
        assertFalse(box1.equals(boxNull));

        assertEquals(box1, box1);

        assertFalse(box1.equals(11));

        assertNotEquals(box1, boxNull);
        assertEquals(boxNull, null);
        assertEquals(box1, box2);
        assertEquals(box1.hashCode(), box2.hashCode());

        assertNotEquals(box1, boxDifferent);
        assertNotEquals(box1.hashCode(), boxDifferent.hashCode());
    }

    @Test
    public void cloneBoxTest () throws CloneNotSupportedException {
        SudokuField fieldB = new SudokuField(16);
        SudokuBox sudokuBoxB = new SudokuBox(Arrays.asList(fieldB));
        assertEquals(sudokuBoxB, sudokuBoxB.clone());
    }

}

