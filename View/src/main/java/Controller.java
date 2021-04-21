import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import project.BacktrackingSudokuSolver;
import project.Difficulty;
import project.SudokuBoard;
import project.custexceptions.ForbiddenFieldValueException;
import project.custexceptions.DaoException;
import project.custexceptions.FilesException;



public class Controller implements Initializable {

    private Difficulty difficulty;

    public Difficulty getDifficulty() {
        return difficulty;
    }

    @FXML
    ToggleGroup group = new ToggleGroup();
    @FXML
    ToggleGroup group1 = new ToggleGroup();

    public RadioButton easyButton = new RadioButton();
    public RadioButton mediumButton = new RadioButton();
    public RadioButton hardButton = new RadioButton();

    public Button startButton = new Button();

    public Label authorsLabel = new Label();
    public Label selectLevel = new Label();
    public MenuButton selectLanguage = new MenuButton();

    public MenuItem englishButton = new MenuItem();
    public MenuItem frenchButton = new MenuItem();
    public MenuItem polishButton = new MenuItem();

    public void chooseEasy() {
        easyButton.setSelected(true);
        easyButton.requestFocus();
        easyButton.setToggleGroup(group);
    }

    public void chooseMedium() {
        mediumButton.setSelected(true);
        mediumButton.requestFocus();
        mediumButton.setToggleGroup(group);
        group.getSelectedToggle();
    }

    public void chooseHard() {
        hardButton.setSelected(true);
        hardButton.requestFocus();
        hardButton.setToggleGroup(group);
    }

    public void chooseEnglish() throws IOException {
        Locale.setDefault(new Locale("en", "UK"));
        Main.listResource = ResourceBundle.getBundle("Authors", new Locale("en", "UK"));
        authorsLabel.setText(Main.listResource.getObject("name").toString() + " "
                + Main.listResource.getObject("surname").toString()
                + "\n" + Main.listResource.getObject("name2").toString() + " "
                + Main.listResource.getObject("surname2").toString());
        Main.setRoot("sudoku.fxml");
    }

    public void chooseFrench() throws IOException {
        Locale.setDefault(new Locale("fr", "FR"));
        Main.listResource = ResourceBundle.getBundle("Authors", new Locale("en", "UK"));
        authorsLabel.setText(Main.listResource.getObject("name").toString() + " "
                + Main.listResource.getObject("surname").toString()
                + "\n" + Main.listResource.getObject("name2").toString()
                + Main.listResource.getObject("surname2").toString());
        Main.setRoot("sudoku.fxml");
    }

    public void choosePolish() throws IOException {
        Locale.setDefault(new Locale("pl", "PL"));
        Main.listResource = ResourceBundle.getBundle("Authors", new Locale("pl", "PL"));
        authorsLabel.setText(Main.listResource.getObject("name").toString() + " "
                + Main.listResource.getObject("surname").toString()
                + "\n" + Main.listResource.getObject("name2").toString() + " "
                + Main.listResource.getObject("surname2").toString());
        Main.setRoot("sudoku.fxml");
    }

    public void startGame() throws CloneNotSupportedException, IOException {
        if (group.getSelectedToggle() != null) {
            if (easyButton.isSelected()) {
                difficulty = Difficulty.EASY;
            } else if (mediumButton.isSelected()) {
                difficulty = Difficulty.MEDIUM;
            } else {
                difficulty = Difficulty.HARD;
            }
            GridController.setDifficulty(difficulty);
            Main.setRoot("display.fxml"); // pom dependency
        }
    }

    public static void main(String[] args) {
        Main.main(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //authorsLabel.textProperty().bind(I18N.createStringBinding("authorsLabel"));
        authorsLabel.setText(Main.listResource.getObject("name").toString() + " "
                + Main.listResource.getObject("surname").toString()
                + "\n" + Main.listResource.getObject("name2").toString() + " "
                + Main.listResource.getObject("surname2").toString());
    }

    }


