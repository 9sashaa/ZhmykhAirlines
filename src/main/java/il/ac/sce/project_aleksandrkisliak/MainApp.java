package il.ac.sce.project_aleksandrkisliak;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private static final MainState state = new MainState();


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/il/ac/sce/project_aleksandrkisliak/mainPage.fxml"));
        double width = 1200;
        double height = 700;

        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle("Zhmykh airlines!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static MainState getAppState() {
        return state;
    }
}