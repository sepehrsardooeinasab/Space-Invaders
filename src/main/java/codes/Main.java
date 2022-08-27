package codes;

import codes.gui.FirstStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Space Invaders");
        stage.setResizable(false);
        new FirstStage().scene(stage);
    }
}