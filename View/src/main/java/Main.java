import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.custexceptions.ForbiddenFieldValueException;
import project.custexceptions.DaoException;
import project.custexceptions.FilesException;



public class Main extends Application {

    private static Scene scene;
    static Locale locale = new Locale("en", "UK");
    static ResourceBundle languageBundle = ResourceBundle.getBundle("Language", locale);
    static ResourceBundle listResource = ResourceBundle.getBundle("Authors",locale);

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sudoku.fxml"), languageBundle);
        scene = new Scene(root, 650, 600);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    static void setRoot(String fxml) throws IOException {
        languageBundle = ResourceBundle.getBundle("Language", Locale.getDefault());
        scene.setRoot(FXMLLoader.load(Main.class.getResource(fxml), languageBundle));
    }

    public static void main(String[] args) {
        launch(args);
    }

}
