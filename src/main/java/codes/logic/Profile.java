package codes.logic;

import java.io.Serializable;

public class Profile implements Serializable {
    private String name = "Default";
    private int highestScore = 0;
    private int numberOfMatchPlayed = 0;
    private double timeSpend = 0;

    public Profile(String name, int highestScore, int numberOfMatchPlayed, double timeSpend) {
        this.name = name;
        this.highestScore = highestScore;
        this.numberOfMatchPlayed = numberOfMatchPlayed;
        this.timeSpend = timeSpend;
    }

    public Profile(String name) {
        this.name = name;
    }

    public Profile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public int getNumberOfMatchPlayed() {
        return numberOfMatchPlayed;
    }

    public void setNumberOfMatchPlayed(int numberOfMatchPlayed) {
        this.numberOfMatchPlayed = numberOfMatchPlayed;
    }

    public double getTimeSpend() {
        return timeSpend;
    }

    public void setTimeSpend(double timeSpend) {
        this.timeSpend = timeSpend;
    }

    @Override
    public String toString() {
        return "Name = " + name +
                ", Highest Score = " + highestScore +
                ",\n Match Played = " + numberOfMatchPlayed +
                ", Time Spend = " + String.format("%.2f", timeSpend) + " Sec";
    }
}
