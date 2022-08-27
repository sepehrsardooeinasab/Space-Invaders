package codes.gui;

import codes.Constants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoseOrWin {
    private final Pane root = new Pane();
    private final Background background = new Background(new BackgroundImage(new Image(Constants.BACKGROUND_ADDRESS),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            new BackgroundSize(Constants.APP_WIDTH, Constants.APP_WIDTH, true, true, true, true)));
    private final Label loseOrWIn = new Label();
    private final Timeline timer = new Timeline(new KeyFrame(Duration.millis(3000), e -> theEnd()));
    private Stage loseOrWinStage = new Stage();

    public void scene(Stage stage, String string) {
        loseOrWinStage = stage;
        if ("Lose".equals(string)) {
            loseOrWIn.setText("You Lose!");
        } else {
            loseOrWIn.setText("You Win! ");
        }
        loseOrWIn.setPrefSize(Constants.APP_WIDTH / 2, 60);
        loseOrWIn.setFont(new Font("Arial", 40));
        loseOrWIn.setTextFill(Color.WHITE);
        loseOrWIn.relocate(Constants.APP_WIDTH / 2 - Constants.APP_WIDTH / 12 + 20, Constants.APP_HEIGHT / 2 - 30);
        root.setBackground(background);
        root.getChildren().addAll(loseOrWIn);
        timer.play();
        Scene scene = new Scene(root, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    private void theEnd() {
        new FirstStage().scene(loseOrWinStage);
    }
}
