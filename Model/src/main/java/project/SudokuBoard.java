package project;

import com.google.common.base.Objects;
import javafx.beans.property.IntegerProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.custexceptions.ForbiddenFieldValueException;


public class SudokuBoard implements PropertyChangeListener, Serializable, Cloneable {

    // Tablica board do wypełnienia
    private List<SudokuField> board = Arrays.asList(new SudokuField[81]);
    private static final int EMPTY = 0;
    private static final int SIZE = 0;
    boolean resultOfListener = false;

    private final SudokuSolver solver;




    //LOGGER
    public static final Logger logger1 = LoggerFactory.getLogger(SudokuBoard.class);
    static Locale locale = new Locale("en", "UK");
    static ResourceBundle exceptBundle = ResourceBundle.getBundle("Exception", locale);



    public SudokuBoard(SudokuSolver solver) {
        for (int row = 0; row < 81; row++) {
                board.set(row,new SudokuField(0));
        }
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                this.board.get(row * 9 + column).addPropertyChangeListener(this);
            }

        }
        this.solver = solver;
    }

    // Getter Prpp
    public IntegerProperty getProperty(int row, int column) { return board.get(row * 9 + column).getFieldProperty(); }


    // Getter
    public int get(int row, int column) {
        return board.get(row * 9 + column).getFieldValue();
    }


    // Setter
    public void set(int row, int column, int value) {
        board.get(row * 9 + column).setFieldValue(value);
    }


    private void fillSudoku() {
        Random randomFill = new Random();
        for (int digit = 1; digit <= 9; digit++) {
            int[] positionInBoard = {randomFill.nextInt(8), randomFill.nextInt(8)};
                board.get(positionInBoard[0]).setFieldValue(digit);

        }
    }

    // Wypełnianie 9x9 zerami
    private void fillWithZeroes() {
        for (SudokuField f: board) {
             f.setFieldValue(0);
        }
    }

    // Wyświetlanie w formacie sudoku
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" +-----+-----+-----+ \n"); //sufit
        for (int row = 0; row < 9; row++) {
            if (row % 3 == 0 && row != 0) { //
                stringBuilder.append(" +-----+-----+-----+ \n"); // piętra
            }
            for (int column = 0; column < 9; column++) {
                if (column % 3 == 0)  {
                    stringBuilder.append(" | "); // ścianki
                }
                if (column != 8) {
                    stringBuilder.append(board.get(row * 9 + column).getFieldValue());
                } else {
                    stringBuilder.append(board.get(row * 9 + column).getFieldValue() + " | ");
                }
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append(" +-----+-----+-----+ \n"); // podłoga
        return stringBuilder.toString();
    }

    // Void zgodny z UML zadania
    public void solveGame() {


        fillWithZeroes();
        fillSudoku();
        solver.solve(this);
        checkBoard();
        logger1.debug(exceptBundle.getString("created"));
    }

    private boolean checkBoard() {

        for (int i = 0; i < 9; i++) {
            if (!getColumn(i).verify() || !getRow(i).verify()) {
                return false;
            }

        }

        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                if (!getBox(i, j).verify()) {
                    return false;
                }
            }
        }

        return true;
    }

    public SudokuRow getRow(int y) {
        final List<SudokuField> field = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            field.set(i, board.get(y * 9 + i));
        }
        return new SudokuRow(field);
    }

    public SudokuColumn getColumn(int x) {
        final List<SudokuField> field = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            field.set(i, board.get(i * 9 + x));
        }
        return new SudokuColumn(field);
    }

    public SudokuBox getBox(int x, int y) {
        final List<SudokuField> field = Arrays.asList(new SudokuField[9]);
        int position = 0;
        x -= x % 3;
        y -= y % 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field.set(position++, board.get((x + i) * 9 + (y + j)));
            }
        }
        return new SudokuBox(field);
    }

    // Sekcja listenera
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (resultOfListener) {
            try {
                if (!checkBoard()) {
                    throw new ForbiddenFieldValueException(exceptBundle.getString("checkboard"));
                }
            } catch (ForbiddenFieldValueException e) {
                logger1.debug(exceptBundle.getString("fvalue"));
            }
        }
    }

    public void listenerFlagChange() {

        // resultOfListener = resultOfListener ^= true;

        resultOfListener = !resultOfListener;
    }

    // ZADANIE 6

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        SudokuBoard that = (SudokuBoard) object;
        return Objects.equal(board, that.board)
                && Objects.equal(solver, that.solver);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(board, solver);
    }

    @Override
    public SudokuBoard clone() throws CloneNotSupportedException {
        // deep clone
        SudokuBoard clonedBoard = (SudokuBoard)super.clone();
        // skopiowanie boarda wymaga skopiowanie wszystkich jego referencji
        // czyli sklonowanie też fieldsów
        ArrayList<SudokuField> clonedFields = new ArrayList<>();
        for (SudokuField sudokuField : board) {
            clonedFields.add((SudokuField) sudokuField.clone());
        }
        clonedBoard.board = clonedFields;
        return clonedBoard;
    }

    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        for (SudokuField field :
                board) {
            oos.writeInt(field.getFieldValue());
        }
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        for (int i = 0; i < 81; i++) {
            board.set(i, new SudokuField(ois.readInt()));
        }
    }
}
