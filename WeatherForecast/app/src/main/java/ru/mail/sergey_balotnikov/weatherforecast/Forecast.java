package ru.mail.sergey_balotnikov.weatherforecast;

public class Forecast {
    private String description;
    private double temperature;
    private long time;
    private String iconId;

    public Forecast(String description, double temperature, long time, String iconId) {
        this.description = description;
        this.temperature = temperature;
        this.time = time;
        this.iconId = iconId;
    }

    public String getDescription() {
        return description;
    }

    public double getTemperature() {
        return temperature;
    }

    public long getTime() {
        return time;
    }

    public String getIconId() {
        return iconId;
    }
}