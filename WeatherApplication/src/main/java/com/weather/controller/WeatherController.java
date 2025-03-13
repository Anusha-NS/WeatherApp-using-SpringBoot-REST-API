package com.weather.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weather.entity.WeatherData;
import com.weather.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{pincode}/{date}")
    public WeatherData getWeather(@PathVariable String pincode, @PathVariable String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            return weatherService.getWeatherData(pincode, parsedDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use 'yyyy-MM-dd'.");
        }
    }
}
