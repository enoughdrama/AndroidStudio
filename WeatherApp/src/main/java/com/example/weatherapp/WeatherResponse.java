package com.example.weatherapp;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("weather")
    private List<Weather> weather;

    @SerializedName("main")
    private Main main;

    @SerializedName("name")
    private String name;

    public List<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public String getName() {
        return name;
    }

    public static class Weather {
        @SerializedName("description")
        private String description;

        @SerializedName("icon")
        private String icon;

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }
    }

    public static class Main {
        @SerializedName("temp")
        private double temp;

        @SerializedName("humidity")
        private int humidity;

        @SerializedName("feels_like")
        private double feelsLike;

        public double getTemp() {
            return temp;
        }

        public int getHumidity() {
            return humidity;
        }

        public double getFeelsLike() {
            return feelsLike;
        }
    }
}