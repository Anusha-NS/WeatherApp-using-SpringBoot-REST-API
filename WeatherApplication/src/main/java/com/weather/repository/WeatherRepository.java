
package com.weather.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weather.entity.WeatherData;

public interface WeatherRepository extends JpaRepository<WeatherData, Long> {
    Optional<WeatherData> findByPincodeAndDate(String pincode, LocalDate date);
}
