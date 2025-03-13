package com.weather.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pincode;
    private double latitude;
    private double longitude;
    private LocalDate date;
    private String weatherDescription;
    private double temperature;

    public WeatherData() {}

    public WeatherData(String pincode, double latitude, double longitude, LocalDate date, String weatherDescription, double temperature) {
        this.pincode = pincode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.weatherDescription = weatherDescription;
        this.temperature = temperature;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getWeatherDescription() { return weatherDescription; }
    public void setWeatherDescription(String weatherDescription) { this.weatherDescription = weatherDescription; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
}
