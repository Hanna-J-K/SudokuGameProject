import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.*;
import project.custexceptions.FilesException;
import project.custexceptions.ForbiddenFieldValueException;
import project.custexceptions.DaoException;



public class GridController extends Window implements Initializable {

    @FXML
    public GridPane sudokuGrid = new GridPane();

    @FXML
    public Button saveToFileButton = new Button();

    @FXML
    public Button openFromFileButton = new Button();

    Random random = new Random();
    private ArrayList<TextField> sudokuFields = new ArrayList<>();
    private static Difficulty difficulty;

    BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
    SudokuBoard sudokuBoard = new SudokuBoard(solver);
    SudokuBoard sudokuBoardCopy;
    FileChooser fileChooser = new FileChooser();
    public static final Logger logger2 = LoggerFactory.getLogger(SudokuBoard.class);
    static Locale locale = new Locale("en", "UK");
    static ResourceBundle exceptBundle = ResourceBundle.getBundle("Exception", locale);

    public SudokuBoard getSudokuBoard() {
        return sudokuBoard;
    }

    @FXML
    void saveGame() {
//        File savedFile = fileChooser.showSaveDialog(this);

        try (SudokuJDBC dao = new SudokuJDBC("sudoku_game")) {
            dao.write(sudokuBoard);
            logger2.debug(exceptBundle.getString("saved"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadGame() {
//        File loadedFile = fileChooser.showOpenDialog(this);
        try (SudokuJDBC dao = new SudokuJDBC("sudoku_game")){
            sudokuBoard = dao.read();
            logger2.debug(exceptBundle.getString("loaded"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringConverter<Number> converter = new NumberStringConverter();
        for (Node k: sudokuGrid.getChildren().subList(1, 82)) {
            Bindings.bindBidirectional(((TextField) k).textProperty(),
                    sudokuBoard.getProperty(GridPane.getRowIndex(k), GridPane.getColumnIndex(k)), converter);
        }
    }


    public static void setDifficulty(Difficulty difficulty) {
        GridController.difficulty = difficulty;
    }

    private void emptyTheFields(int empties) {
        int row;
        int column;
        for (int i = 0; i < empties; i++) {
            row = random.nextInt(8);
            column = random.nextInt(8);
            sudokuBoard.set(row, column, 0);
        }
    }

    public void setEmptyFields() {
        if (difficulty == Difficulty.EASY) {
            emptyTheFields(18);
        } else if (difficulty == Difficulty.MEDIUM) {
            emptyTheFields(36);
        } else if (difficulty == Difficulty.HARD) {
            emptyTheFields(48);
        }
    }

    private TextFormatter.Change filter(TextFormatter.Change change) {
        if (!change.getControlNewText().matches("[1-9]?")) {
            change.setText("");
        }
        return change;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StringConverter<Number> converter = new NumberStringConverter();
        sudokuBoard.solveGame();
        try {
            sudokuBoardCopy = sudokuBoard.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuFields.size() != 81) {
                    TextField textField = new TextField();
                    textField.setOnKeyPressed(e -> {
                        if (e.getText().matches("[1-9]")) {
                            textField.setText(e.getText());
                        }
                    });
                    textField.setTextFormatter(new TextFormatter<>(this::filter));
                    Bindings.bindBidirectional(textField.textProperty(), sudokuBoard.getProperty(i, j), converter);
                    sudokuFields.add(textField);
                    sudokuGrid.add(sudokuFields.get(i * 9 + j), j, i);
                }
            }
        }
        setEmptyFields();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!sudokuFields.get(i * 9 + j).getText().isEmpty()) {
                    sudokuFields.get(i * 9 + j).setDisable(true);
                }
            }
        }
    }

    public void newGame() {
        sudokuBoard.solveGame();
        setEmptyFields();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!sudokuFields.get(i * 9 + j).getText().isEmpty()) {
                    sudokuFields.get(i * 9 + j).setDisable(true);
                } else {
                    sudokuFields.get(i * 9 + j).setDisable(false);
                }
            }
        }
    }

}
