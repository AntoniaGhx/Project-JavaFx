package org.example.domain;

public class Location extends Entity {
    private String startTime;
    private String stopTime;
    private float price;

    public Location(int idEntity, String startTime, String stopTime, float price) {
        super(idEntity);
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.price = price;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Location{" +
                "startTime='" + startTime + '\'' +
                ", stopTime='" + stopTime + '\'' +
                ", price=" + price +
                '}';
    }
}
