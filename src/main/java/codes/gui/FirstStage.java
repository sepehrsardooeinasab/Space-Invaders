package codes.gui;

import codes.Constants;
import codes.logic.Game;
import codes.logic.Profile;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;

public class FirstStage {
    private final Pane root = new Pane();
    private final Background background = new Background(new BackgroundImage(new Image(Constants.BACKGROUND_ADDRESS),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            new BackgroundSize(Constants.APP_WIDTH, Constants.APP_WIDTH, true, true, true, true)));
    private final ComboBox playerProfilesComboBox = new ComboBox();
    private ArrayList<Object> profiles = new ArrayList<>();
    private final Button exitButton = new Button("Exit");
    private final Button deleteProfileButton = new Button("Delete Profile");
    private final Button newGameButton = new Button("New game");
    private final Label gameName = new Label("Space Invaders");
    private final TextField textField = new TextField("Enter your name");
    private Stage firstStage = new Stage();

    public void scene(Stage stage){
        loadProfiles();
        firstStage = stage;
        gameName.setPrefSize(Constants.APP_WIDTH/2, 60);
        gameName.setFont(new Font("Arial", 40));
        gameName.setTextFill(Color.WHITE);
        gameName.relocate(Constants.APP_WIDTH/2 - Constants.APP_WIDTH/12 - 35, Constants.APP_HEIGHT/2 - Constants.APP_HEIGHT/4);
        playerProfilesComboBox.setPrefSize(Constants.APP_WIDTH/4, 20);
        playerProfilesComboBox.relocate(Constants.APP_WIDTH/2 - Constants.APP_WIDTH/8, Constants.APP_HEIGHT/2);
        playerProfilesComboBox.setOnAction(new HandlerPlayerComboBox());
        playerProfilesComboBox.setVisibleRowCount(5);
        textField.setDisable(true);
        textField.setVisible(false);
        textField.relocate(Constants.APP_WIDTH/2 + Constants.APP_WIDTH/8 + 20, Constants.APP_HEIGHT/2);
        textField.setOnKeyPressed(new HandlerTextFiled());
        newGameButton.setPrefSize(Constants.APP_WIDTH/4, 20);
        newGameButton.relocate(Constants.APP_WIDTH/2 - Constants.APP_WIDTH/8, Constants.APP_HEIGHT/2 + 40);
        newGameButton.setOnAction(new HandlerNewGameButton());
        newGameButton.setDisable(true);
        deleteProfileButton.setPrefSize(Constants.APP_WIDTH/4, 20);
        deleteProfileButton.relocate(Constants.APP_WIDTH/2 - Constants.APP_WIDTH/8, Constants.APP_HEIGHT/2 + 80);
        deleteProfileButton.setOnAction(new HandlerDeleteProfileButton());
        deleteProfileButton.setDisable(true);
        exitButton.setPrefSize(Constants.APP_WIDTH/4, 20);
        exitButton.relocate(Constants.APP_WIDTH/2 - Constants.APP_WIDTH/8, Constants.APP_HEIGHT/2 + 120);
        exitButton.setOnAction(new HandlerExitButton());
        root.setBackground(background);
        root.getChildren().addAll(gameName, playerProfilesComboBox, newGameButton, deleteProfileButton, exitButton, textField);
        Scene scene = new Scene(root, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    private void loadProfiles() {
        profiles = readAllProfiles();
        if(profiles.size() == 0){
            profiles.add(new Profile("Default"));
        }
        playerProfilesComboBox.setItems(FXCollections.observableList(profiles));
        profiles.add(0,"Add new profile");
    }

    private ArrayList<Object> readAllProfiles() {
        File file = new File("src/main/resources/profiles");
        String[] filesNames = file.list();
        ArrayList<Object> profiles = new ArrayList<>();
        if (!(filesNames == null)) {
            for (String string : filesNames) {
                try {
                    FileInputStream fileIn = new FileInputStream("src/main/resources/profiles/" + string);
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    Profile profile = (Profile) in.readObject();
                    profiles.add(profile);
                    in.close();
                    fileIn.close();
                } catch (IOException | ClassNotFoundException ignored) {
                }
            }
        }
        return profiles;
    }

    private void writeAllProfiles(ArrayList<Object> profiles) {
        for (Object profile : profiles) {
            try {
                FileOutputStream fileOut = new FileOutputStream("src/main/resources/profiles/Profile_" + ((Profile) profile).getName() + ".bin");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(profile);
                out.close();
                fileOut.close();
            } catch (IOException ignored) {
            }
        }
    }

    private class HandlerExitButton implements EventHandler {
        @Override
        public void handle(Event event) {
            if(event.getSource() == exitButton) {
                firstStage.close();
            }
        }
    }

    private class HandlerNewGameButton implements EventHandler {
        @Override
        public void handle(Event event) {
            if (playerProfilesComboBox.getValue() != null) {
                if (!playerProfilesComboBox.getValue().getClass().getSimpleName().equals("String")) {
                    Game game = new Game();
                    game.setCurrentProfile((Profile) playerProfilesComboBox.getValue());
                    new SetDifficultyStage().scene(firstStage, game);
                }
            }
        }
    }

    private class HandlerDeleteProfileButton implements EventHandler{
        @Override
        public void handle(Event event) {
            if (playerProfilesComboBox.getValue()!=null && !playerProfilesComboBox.getValue().getClass().getSimpleName().equals("String")) {
                String profileName = ((Profile) playerProfilesComboBox.getValue()).getName();
                String profileAddress = String.format("src/main/resources/profiles/Profile_%s.bin", profileName);
                try {
                    File profileFile = new File(profileAddress);
                    profileFile.delete();
                } catch (Exception ignored) {
                }
                profiles = new ArrayList<>();
                loadProfiles();
                playerProfilesComboBox.setItems(FXCollections.observableList(profiles));
                playerProfilesComboBox.setValue(playerProfilesComboBox.getItems().get(profiles.size() - 1));
            }
        }
    }

    private class HandlerTextFiled implements EventHandler{
        @Override
        public void handle(Event event) {
            if (((KeyEvent) event).getCode().equals(KeyCode.ENTER)) {
                textField.setDisable(true);
                textField.setVisible(false);
                exitButton.setDisable(false);
                deleteProfileButton.setDisable(false);
                newGameButton.setDisable(false);
                playerProfilesComboBox.setDisable(false);
                profiles.remove(0);
                profiles.add(new Profile(textField.getText().strip()));
                writeAllProfiles(profiles);
                playerProfilesComboBox.setItems(FXCollections.observableList(profiles));
                playerProfilesComboBox.setValue(playerProfilesComboBox.getItems().get(profiles.size() - 1));
                profiles.add(0,"Add new profile");
            }
        }
    }

    private class HandlerPlayerComboBox implements EventHandler{
        @Override
        public void handle(Event event) {
            if (playerProfilesComboBox.getValue()!=null && playerProfilesComboBox.getValue().getClass().getSimpleName().equals("String") &&
                    playerProfilesComboBox.getValue().equals("Add new profile")) {
                textField.setText("");
                textField.setDisable(false);
                textField.setVisible(true);
                exitButton.setDisable(true);
                deleteProfileButton.setDisable(true);
                newGameButton.setDisable(true);
                playerProfilesComboBox.setDisable(true);
            }
            if (!(playerProfilesComboBox.getValue()!=null && playerProfilesComboBox.getValue().getClass().getSimpleName().equals("String") &&
                    playerProfilesComboBox.getValue().equals("Add new profile"))) {
                deleteProfileButton.setDisable(false);
                newGameButton.setDisable(false);
            }
        }
    }
}
