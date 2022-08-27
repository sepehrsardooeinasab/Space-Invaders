package codes.logic;

import codes.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public abstract class SpaceShip extends ImageView{
    private int rowIndex = 0;
    private int columnIndex = 0;
    private int health = 1;
    private Boolean isDead = false;

    public SpaceShip(Image image, int x, int y) {
        super(image);
        this.rowIndex = x;
        this.columnIndex = y;
        setSize(40, 40);
        GridPane.setColumnIndex(this, columnIndex);
        GridPane.setRowIndex(this, rowIndex);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Boolean getDead() {
        return isDead;
    }

    public void setDead(Boolean dead) {
        isDead = dead;
    }

    public void moveRight(){
        if(columnIndex<Constants.N-1) {
            columnIndex++;
            GridPane.setColumnIndex(this, columnIndex);
        }
    }

    public void moveLeft(){
        if(columnIndex>0) {
            columnIndex--;
            GridPane.setColumnIndex(this, columnIndex);
        }
    }

    public void moveUp(){
        if(rowIndex>0) {
            rowIndex--;
            GridPane.setRowIndex(this, rowIndex);
        }
    }

    public void moveDown(){
        if(rowIndex<Constants.M-1) {
            rowIndex++;
            GridPane.setRowIndex(this, rowIndex);
        }
    }

    public void reduceHealth(){
        health--;
    }

    private void setSize(double weight, double height) {
        this.setFitWidth(weight);
        this.setFitHeight(height);
    }

    public abstract Bullet shoot();
}
