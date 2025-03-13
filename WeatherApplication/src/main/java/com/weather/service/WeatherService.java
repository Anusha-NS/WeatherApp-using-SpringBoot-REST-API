package com.weather.service;

import java.time.LocalDate;
import java.util.Optional;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.weather.entity.WeatherData;
import com.weather.repository.WeatherRepository;

@Service
public class WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;

    @Value("${openweather.api.key}")
    private String openWeatherApiKey;

    public WeatherService(WeatherRepository weatherRepository, RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
    }

    public WeatherData getWeatherData(String pincode, LocalDate date) {
        if (pincode == null || pincode.length() != 6 || !pincode.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid pincode. Pincode must be a 6-digit number.");
        }

        Optional<WeatherData> cachedData = weatherRepository.findByPincodeAndDate(pincode, date);
        if (cachedData.isPresent()) {
            logger.info("Returning cached weather data for pincode: {} and date: {}", pincode, date);
            return cachedData.get();
        }

        String url = String.format("http://api.openweathermap.org/data/2.5/weather?zip=%s,IN&appid=%s&units=metric", pincode, openWeatherApiKey);
        try {
            String response = restTemplate.getForObject(url, String.class);
            JSONObject json = new JSONObject(response);

            if (json.has("cod") && json.getInt("cod") != 200) {
                throw new RuntimeException("Failed to fetch weather data: " + json.toString());
            }

            double lat = json.getJSONObject("coord").optDouble("lat", 0.0);
            double lon = json.getJSONObject("coord").optDouble("lon", 0.0);
            String weatherDescription = json.getJSONArray("weather").getJSONObject(0).optString("description", "Unknown");
            double temperature = json.getJSONObject("main").optDouble("temp", 0.0);

            WeatherData weatherData = new WeatherData(pincode, lat, lon, date, weatherDescription, temperature);
            logger.info("Saving weather data for pincode: {} and date: {}", pincode, date);
            return weatherRepository.save(weatherData);
        } catch (Exception e) {
            logger.error("Failed to fetch weather data for pincode: {} and date: {}", pincode, date, e);
            throw new RuntimeException("Failed to fetch weather data: " + e.getMessage());
        }
    }
}