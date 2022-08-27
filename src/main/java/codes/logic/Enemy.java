package codes.logic;

import codes.Constants;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

public abstract class Enemy extends SpaceShip{
    private Boolean directionRight = false;

    public Enemy(Image image, int x, int y) {
        super(image, x, y);
    }

    public Boolean getDirectionRight() {
        return directionRight;
    }

    public void setDirectionRight(Boolean directionRight) {
        this.directionRight = directionRight;
    }

    public void move(){
        checkChangeDirection();
        if(directionRight){
            moveRight();
        } else {
            moveLeft();
        }
    }

    public void checkChangeDirection(){
        if(directionRight && GridPane.getColumnIndex(this)==Constants.N-1){
            directionRight = !directionRight;
            moveDown();
        } else if (!directionRight && GridPane.getColumnIndex(this)==0) {
            directionRight = !directionRight;
            moveDown();
        }
    }
}
