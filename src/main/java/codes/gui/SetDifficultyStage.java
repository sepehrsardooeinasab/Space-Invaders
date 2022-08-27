package codes.gui;

import codes.Constants;
import codes.logic.Difficulty;
import codes.logic.Game;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SetDifficultyStage {
    private final Pane root = new Pane();
    private Stage setDifficultyStage;
    private final Background background = new Background(new BackgroundImage(new Image(Constants.BACKGROUND_ADDRESS),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            new BackgroundSize(Constants.APP_WIDTH, Constants.APP_WIDTH, true, true, true, true)));
    private final Button easyButton = new Button("Easy");
    private final Button mediumButton = new Button("Medium");
    private final Button hardButton = new Button("Hard");
    private final Label gameName = new Label("Space Invaders");
    private final Label setDifficulty  = new Label("Choose difficulty");
    private Game game = new Game();

    public void scene(Stage stage, Game game) {
        setDifficultyStage = stage;
        this.game = game;
        gameName.setPrefSize(Constants.APP_WIDTH/2, 60);
        gameName.setFont(new Font("Arial", 40));
        gameName.setTextFill(Color.WHITE);
        gameName.relocate(Constants.APP_WIDTH/2 - Constants.APP_WIDTH/12 - 35, Constants.APP_HEIGHT/2 - Constants.APP_HEIGHT/4);
        setDifficulty.setPrefSize(Constants.APP_WIDTH/2, 60);
        setDifficulty.setFont(new Font("Arial", 15));
        setDifficulty.setTextFill(Color.WHITE);
        setDifficulty.relocate(Constants.APP_WIDTH/2 - Constants.APP_WIDTH/12 + 40, Constants.APP_HEIGHT/2 - Constants.APP_HEIGHT/10);
        HandlerSetDifficultyStage handlerSetDifficultyStage = new HandlerSetDifficultyStage();
        easyButton.setPrefSize(Constants.APP_WIDTH/4, 20);
        easyButton.relocate(Constants.APP_WIDTH/2 - Constants.APP_WIDTH/8, Constants.APP_HEIGHT/2);
        easyButton.setOnAction(handlerSetDifficultyStage);
        mediumButton.setPrefSize(Constants.APP_WIDTH/4, 20);
        mediumButton.relocate(Constants.APP_WIDTH/2 - Constants.APP_WIDTH/8, Constants.APP_HEIGHT/2 + 40);
        mediumButton.setOnAction(handlerSetDifficultyStage);
        hardButton.setPrefSize(Constants.APP_WIDTH/4, 20);
        hardButton.relocate(Constants.APP_WIDTH/2 - Constants.APP_WIDTH/8, Constants.APP_HEIGHT/2 + 80);
        hardButton.setOnAction(handlerSetDifficultyStage);
        root.setBackground(background);
        root.getChildren().addAll(easyButton, mediumButton, hardButton, gameName, setDifficulty);
        Scene scene = new Scene(root, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    private class HandlerSetDifficultyStage implements EventHandler {
        @Override
        public void handle(Event event) {
            if(event.getSource() == easyButton){
                game.setDifficulty(Difficulty.EASY);
                new LoadingStage().scene(setDifficultyStage, game);
            } else if (event.getSource() == mediumButton) {
                game.setDifficulty(Difficulty.MEDIUM);
                new LoadingStage().scene(setDifficultyStage, game);
            } else if (event.getSource() == hardButton) {
                game.setDifficulty(Difficulty.HARD);
                new LoadingStage().scene(setDifficultyStage, game);
            }
        }
    }
}
