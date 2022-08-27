package codes.logic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Barrier extends ImageView {
    private int health = 5;
    private Boolean isDead = false;

    public Barrier(int rowIndex, int columnIndex) {
        super(new Image("assets/obstacle.png"));
        GridPane.setRowIndex(this, rowIndex);
        GridPane.setColumnIndex(this, columnIndex);
        this.setFitWidth(30);
        this.setFitHeight(30);
    }

    public int getHealth() {
        return health;
    }

    public void reduceHealth(){
        health--;
    }

    public Boolean getDead() {
        return isDead;
    }

    public void setDead(Boolean dead) {
        isDead = dead;
    }
}
