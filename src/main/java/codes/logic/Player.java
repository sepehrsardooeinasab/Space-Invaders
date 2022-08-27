package codes.logic;

import codes.Constants;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

public class Player extends SpaceShip {

    public Player() {
        super(new Image("assets/spaceship.png"), Constants.M-1, Constants.N-1);
        setHealth(3);
    }

    @Override
    public Bullet shoot() {
        return new PlayerBullet(GridPane.getColumnIndex(this), GridPane.getRowIndex(this), GridPane.getColumnIndex(this), 0);
    }
}