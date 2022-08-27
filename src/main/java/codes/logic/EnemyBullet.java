package codes.logic;

public class EnemyBullet extends Bullet {
    public EnemyBullet(int x0, int y0, int x1, int y1) {
        super(x0, y0, x1, y1);
    }

    @Override
    public String getType() {
        return "E";
    }
}
