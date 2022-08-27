package codes.logic;

import codes.Constants;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class EnemyType2 extends Enemy {

    public EnemyType2(int x, int y) {
        super(new Image("assets/alien_two_white.png"), x, y);
    }

    @Override
    public Bullet shoot() {
        if(!getDead()) {
            EnemyBullet enemyBullet = new EnemyBullet(GridPane.getColumnIndex(this), GridPane.getRowIndex(this), GridPane.getColumnIndex(this), Constants.N - 1);
            enemyBullet.setFill(Color.BLUE);
            return enemyBullet;
        } else {
            return null;
        }
    }
}