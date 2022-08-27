package codes.logic;

import codes.Constants;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

public class UFO extends SpaceShip {
    private boolean directionRight = false;

    public UFO() {
        super(new Image("assets/ufo.png"), 1, 1);
        setHealth(5);
    }

    @Override
    public Bullet shoot() {
        return null;
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
        if(directionRight && GridPane.getColumnIndex(this)== Constants.N-1){
            directionRight = !directionRight;
        } else if (!directionRight && GridPane.getColumnIndex(this)==0) {
            directionRight = !directionRight;

        }
    }

}
