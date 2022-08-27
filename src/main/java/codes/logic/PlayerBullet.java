package codes.logic;

import javafx.scene.paint.Color;

public class PlayerBullet extends Bullet {
    public PlayerBullet(int x0, int y0, int x1, int y1) {
        super(x0, y0, x1, y1);
        this.setFill(Color.GREEN);
    }

    @Override
    public String getType() {
        return "P";
    }
}
