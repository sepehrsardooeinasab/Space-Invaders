package codes.logic;

import codes.Constants;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

public abstract class Bullet extends Circle{
    private static final double RADIUS = 4;
    private Boolean isDead = false;
    private int columnIndex;
    private int rowIndex;
    private double x1;
    private double y1;

    public Bullet(int x0, int y0, int x1, int y1) {
        super();
        this.columnIndex = x0;
        this.rowIndex = y0;
        this.x1 = x1;
        this.y1 = y1;
        this.setRadius(RADIUS);
        GridPane.setColumnIndex(this, columnIndex);
        GridPane.setRowIndex(this, rowIndex);
    }

    public Boolean getDead() {
        return isDead;
    }

    public void setDead(Boolean dead) {
        isDead = dead;
    }

    public void move() {
        if(!isDead) {
            if (x1 - GridPane.getColumnIndex(this) > 0) {
                columnIndex++;
                GridPane.setColumnIndex(this, columnIndex);
            } else if (x1 - GridPane.getColumnIndex(this) < 0) {
                columnIndex--;
                GridPane.setColumnIndex(this, columnIndex);
            }
            if (y1 - GridPane.getRowIndex(this) > 0) {
                rowIndex++;
                GridPane.setRowIndex(this, rowIndex);
            } else if (y1 - GridPane.getRowIndex(this) < 0) {
                rowIndex--;
                GridPane.setRowIndex(this, rowIndex);
            }
        }
        if((y1 ==0 && this.rowIndex==0) || (y1 ==Constants.M-1 && this.rowIndex==Constants.M-1)){
            isDead = true;
        }
    }

    public abstract String getType();
}
