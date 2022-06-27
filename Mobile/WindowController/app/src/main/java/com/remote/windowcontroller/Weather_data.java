package com.remote.windowcontroller;

public class Weather_data {
    private String cloud;
    private String feels_like;
    private String humidity;
    private String temp;
    private String temp_max;
    private String temp_min;
    private String weather;
    private String wind;
    private String fine_dust;

    public Weather_data(){}


    public String getFine_dust() { return fine_dust; }

    public void setFine_dust(String fine_dust) { this.fine_dust = fine_dust; }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(String feels_like) {
        this.feels_like = feels_like;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public Weather_data(String cloud, String feels_like, String humidity, String temp, String temp_max, String temp_min, String weather, String wind, String fine_dust)
    {
        this.cloud = cloud;
        this.feels_like = feels_like;
        this.humidity = humidity;
        this.temp = temp;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
        this.weather = weather;
        this.wind = wind;
        this.fine_dust = fine_dust;
    }
}