package codes.gui;

import codes.Constants;
import codes.logic.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class LevelOne {
    private final Pane root = new Pane();
    private final Background background = new Background(new BackgroundImage(new Image(Constants.BACKGROUND_ADDRESS),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            new BackgroundSize(Constants.APP_WIDTH, Constants.APP_WIDTH, true, true, true, true)));
    private Stage levelStage = new Stage();
    private Game game;
    private final GridPane gameBoard = new GridPane();
    private final VBox gameDetail = new VBox();
    private final ImageView imageHeart1 = new ImageView("assets/heart.png");
    private final ImageView imageHeart2 = new ImageView("assets/heart.png");
    private final ImageView imageHeart3 = new ImageView("assets/heart.png");
    private final HBox heartHbox = new HBox();
    private final Text timerLabel = new Text("Time: 0");
    private int difficultyLevelHealth;
    private double difficultyShootPercentage;
    private final Text scoreLabel = new Text();
    private final ArrayList<ArrayList<Enemy>> enemies = new ArrayList<>();
    private final Player player = new Player();
    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private final ArrayList<Barrier> barriers = new ArrayList<>();
    private final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(250), e -> aLoop()));
    private final Timeline timelineUfo = new Timeline(new KeyFrame(Duration.millis(100), e -> ufo()));
    private final Timeline timer = new Timeline(new KeyFrame(Duration.millis(100), e -> setTime()));
    private double secondsSpent = 0;
    private UFO ufo;

    public void scene(Stage stage, Game game){
        this.game = game;
        levelStage = stage;
        gameBoard.relocate(0,0);
        gameBoard.setPrefSize(Constants.APP_WIDTH*4/5, Constants.APP_HEIGHT);
        for (int i = 0; i < Constants.N; i++) {
            ColumnConstraints column = new ColumnConstraints(Constants.APP_WIDTH*4/5/Constants.N);
            column.setHalignment(HPos.CENTER);
            gameBoard.getColumnConstraints().add(column);
        }
        for (int i = 0; i < Constants.M; i++) {
            RowConstraints row = new RowConstraints(Constants.APP_HEIGHT/Constants.M);
            row.setValignment(VPos.CENTER);
            gameBoard.getRowConstraints().add(row);
        }
        //gameBoard.setGridLinesVisible(true);
        imageHeart1.setFitWidth(30);
        imageHeart1.setFitHeight(30);
        imageHeart2.setFitWidth(30);
        imageHeart2.setFitHeight(30);
        imageHeart3.setFitWidth(30);
        imageHeart3.setFitHeight(30);
        heartHbox.getChildren().addAll(imageHeart1, imageHeart2, imageHeart3);
        heartHbox.setAlignment(Pos.CENTER);
        gameDetail.setPrefSize(Constants.APP_WIDTH/5, Constants.APP_HEIGHT);
        gameDetail.relocate(Constants.APP_WIDTH*4/5,0);
        gameDetail.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        gameDetail.getChildren().addAll(timerLabel, scoreLabel, heartHbox);
        gameDetail.setAlignment(Pos.CENTER);
        setDifficulty();
        initialEnemies();
        addEnemiesToGamePane();
        addPlayerToGamePane();
        initialBarrierAndAddToGamePane();
        initialUFOAndAddToGamePane();
        root.setBackground(background);
        root.getChildren().addAll(gameBoard, gameDetail);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        timelineUfo.setCycleCount(Animation.INDEFINITE);
        timelineUfo.play();
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
        Scene scene = new Scene(root, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        scene.setOnKeyPressed(new HandlerPlayer());
        stage.setScene(scene);
        stage.show();
    }

    private void setDifficulty() {
        switch (game.getDifficulty()){
            case EASY:
                difficultyLevelHealth = 1;
                difficultyShootPercentage = 0.95;
                break;
            case MEDIUM:
                difficultyLevelHealth = 2;
                difficultyShootPercentage = 0.9;
                break;
            case HARD:
                difficultyLevelHealth = 3;
                difficultyShootPercentage = 0.8;
                break;
        }
    }

    private void setTime() {
        secondsSpent+= 0.1;
    }

    private void addPlayerToGamePane() {
        game.setPlayer(player);
        gameBoard.add(player, Constants.N-1, Constants.M-1);
    }

    private void initialEnemies() {
        for (int i = 0; i < Constants.COLUMN_ENEMY; i++) {
            ArrayList<Enemy> enemiesInARow = new ArrayList<>();
            if (i%2 == 0) {
                for (int j = 0; j < Constants.ROW_ENEMY; j++) {
                    EnemyType1 enemyType1 = new EnemyType1(j+1, i+1);
                    enemyType1.setHealth(difficultyLevelHealth);
                    enemiesInARow.add(enemyType1);
                }
            }else{
                for (int j = 0; j < Constants.ROW_ENEMY; j++) {
                    EnemyType2 enemyType2 = new EnemyType2(j+1, i+1);
                    enemyType2.setHealth(difficultyLevelHealth);
                    enemiesInARow.add(enemyType2);
                }
            }
            enemies.add(enemiesInARow);
        }
    }

    private void addEnemiesToGamePane() {
        for (ArrayList<Enemy> enemy : enemies) {
            for (Enemy value : enemy) {
                gameBoard.add(value, GridPane.getRowIndex(value),
                        GridPane.getColumnIndex(value));
            }
        }
    }

    private void initialBarrierAndAddToGamePane() {
        for (int i = 0; i < 3; i++) {
            barriers.add(new Barrier(((int) (Math.random() * (Constants.M-2))), ((int) (Math.random() * (Constants.N-5)) + 3)));
        }
        for (Barrier barrier : barriers) {
            gameBoard.getChildren().add(barrier);
        }
    }

    private void initialUFOAndAddToGamePane() {
        ufo = new UFO();
        gameBoard.getChildren().add(ufo);
    }

    private void ufo() {
        ufo.move();
        for (Bullet bullet: bullets) {
            if(!bullet.getDead()){
                if(bullet.getType().equals("P") && GridPane.getRowIndex(bullet).equals(GridPane.getRowIndex(ufo)) &&
                        GridPane.getColumnIndex(bullet).equals(GridPane.getColumnIndex(ufo))) {
                    if (!ufo.getDead()) {
                        if (ufo.getHealth() == 1) {
                            game.setScore(game.getScore() + 100);
                        }
                        ufo.reduceHealth();
                        bullet.setDead(true);
                    }
                    if (ufo.getHealth() <= 0) {
                        gameBoard.getChildren().remove(ufo);
                        ufo.setDead(true);
                    }
                }
            }
        }
    }

    private void aLoop() {
        enemiesMoveAndShootAndIsDead();
        bulletsMoveAndIsDead();
        checkBulletAndSpaceShipAccident();
        updateGameDetail();
        checkWinOrLose();
    }

    private void checkWinOrLose() {
        boolean lose = false;
        boolean win = false;
        int counter = 0;
        for (ArrayList<Enemy> enemyInARow : enemies) {
            for (Enemy enemy: enemyInARow) {
                if(enemy.getDead()){
                    counter++;
                }
                if(GridPane.getRowIndex(enemy) == Constants.M-1){
                    lose = true;
                }
            }
        }
        if(player.getDead() || lose){
            lose = true;
        } else if (counter == Constants.ROW_ENEMY * Constants.COLUMN_ENEMY){
            win = true;
        }
        if(lose || win) {
            timer.stop();
            timeline.stop();
            if(game.getCurrentProfile().getHighestScore()<game.getScore()) {
                game.getCurrentProfile().setHighestScore(game.getScore());
            }
            game.getCurrentProfile().setNumberOfMatchPlayed(game.getCurrentProfile().getNumberOfMatchPlayed() + 1);
            game.getCurrentProfile().setTimeSpend(game.getCurrentProfile().getTimeSpend() + secondsSpent);
            try {
                FileOutputStream fileOut = new FileOutputStream("src/main/resources/profiles/Profile_" + (game.getCurrentProfile()).getName() + ".bin");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(game.getCurrentProfile());
                out.close();
                fileOut.close();
            } catch (IOException ignored) {
            }
            if(lose) {
                new LoseOrWin().scene(levelStage, "Lose");
            } else {
                new LoseOrWin().scene(levelStage, "Win");
            }
        }
    }

    private void updateGameDetail() {
        timerLabel.setText("Time: " + String.format("%.2f", secondsSpent) + " Sec");
        scoreLabel.setText("Score: " + game.getScore());
    }

    private void checkBulletAndSpaceShipAccident() {
        for (Bullet bullet: bullets) {
            if(!bullet.getDead()){
                if(bullet.getType().equals("E") && GridPane.getRowIndex(bullet).equals(GridPane.getRowIndex(player)) &&
                        GridPane.getColumnIndex(bullet).equals(GridPane.getColumnIndex(player))){
                    player.reduceHealth();
                    try {
                        heartHbox.getChildren().remove(0);
                    } catch (Exception ignore) {
                    }
                    bullet.setDead(true);
                    if(player.getHealth()<=0){
                        player.setDead(true);
                    }
                } else if(bullet.getType().equals("P")) {
                    for (ArrayList<Enemy> enemyInARow: enemies) {
                        for (Enemy enemy: enemyInARow) {
                            if(GridPane.getRowIndex(bullet).equals(GridPane.getRowIndex(enemy)) &&
                                    GridPane.getColumnIndex(bullet).equals(GridPane.getColumnIndex(enemy))){
                                if(!enemy.getDead()) {
                                    if(enemy.getHealth()==1) {
                                        game.setScore(game.getScore() + 100);
                                    }
                                    enemy.reduceHealth();
                                    bullet.setDead(true);
                                }
                                if(enemy.getHealth()<=0){
                                    gameBoard.getChildren().remove(enemy);
                                    enemy.setDead(true);
                                }
                            }
                        }
                    }
                    for (Barrier barrier: barriers) {
                        if(GridPane.getRowIndex(bullet).equals(GridPane.getRowIndex(barrier)) &&
                                GridPane.getColumnIndex(bullet).equals(GridPane.getColumnIndex(barrier))){
                            if(!barrier.getDead()) {
                                if(barrier.getHealth()==1) {
                                    game.setScore(game.getScore() + 10);
                                }
                                barrier.reduceHealth();
                                bullet.setDead(true);
                            }
                            if(barrier.getHealth()<=0){
                                gameBoard.getChildren().remove(barrier);
                                barrier.setDead(true);
                            }
                        }
                    }
                }
            }
        }
    }

    private void bulletsMoveAndIsDead() {
        for (Bullet bullet: bullets) {
            if (bullet.getDead()){
                bullet.setVisible(false);
                gameBoard.getChildren().remove(bullet);
                //bullets.remove(bullet);
            }
            if(!gameBoard.getChildren().contains(bullet)){
                gameBoard.getChildren().add(bullet);
            }
            bullet.move();
        }
    }

    private void enemiesMoveAndShootAndIsDead() {
        for (ArrayList<Enemy> enemiesInARow : enemies) {
            for (Enemy enemy: enemiesInARow) {
                if(Math.random()>=difficultyShootPercentage) {
                    if(enemy.shoot()!=null) {
                        bullets.add(enemy.shoot());
                    }
                }
                enemy.move();
            }
        }
    }

    private class HandlerPlayer implements EventHandler {
        @Override
        public void handle(Event event) {
            if (((KeyEvent) event).getCode().equals(KeyCode.RIGHT)) {
                player.moveRight();
            } else if (((KeyEvent) event).getCode().equals(KeyCode.LEFT)) {
                player.moveLeft();
            } else if (((KeyEvent) event).getCode().equals(KeyCode.SPACE)) {
                bullets.add(player.shoot());
            }
        }
    }
}
