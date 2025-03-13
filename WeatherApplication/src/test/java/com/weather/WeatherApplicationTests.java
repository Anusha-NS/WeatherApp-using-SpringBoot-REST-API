package com.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.weather.entity.WeatherData;
import com.weather.repository.WeatherRepository;
import com.weather.service.WeatherService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class WeatherApplicationTests {

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherService weatherService;

    private WeatherData mockWeatherData;

    @BeforeEach
    void setUp() {
        mockWeatherData = new WeatherData();
        mockWeatherData.setPincode("411014");
        mockWeatherData.setLatitude(18.5204);
        mockWeatherData.setLongitude(73.8567);
        mockWeatherData.setWeatherDescription("Clear Sky");
        mockWeatherData.setTemperature(30.5);
        mockWeatherData.setDate(LocalDate.of(2020, 10, 15));
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testFetchWeatherFromCache() {
        when(weatherRepository.findByPincodeAndDate("411014", LocalDate.of(2020, 10, 15)))
                .thenReturn(Optional.of(mockWeatherData));

        WeatherData result = weatherService.getWeatherData("411014", LocalDate.of(2020, 10, 15));
        assertNotNull(result);
        assertEquals("Clear Sky", result.getWeatherDescription());
        verify(weatherRepository, times(1)).findByPincodeAndDate(anyString(), any());
        verifyNoMoreInteractions(weatherRepository);
    }

    @Test
    void testFetchWeatherFromAPIAndSave() {
        when(weatherRepository.findByPincodeAndDate(anyString(), any())).thenReturn(Optional.empty());
        when(weatherRepository.save(any(WeatherData.class))).thenReturn(mockWeatherData);

        WeatherData result = weatherService.getWeatherData("411014", LocalDate.of(2020, 10, 15));
        assertNotNull(result);
        assertEquals("Clear Sky", result.getWeatherDescription());
        verify(weatherRepository, times(1)).save(any(WeatherData.class));
    }

    @Test
    void testInvalidPincode() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            weatherService.getWeatherData("00000", LocalDate.of(2020, 10, 15)));
        assertEquals("Invalid pincode. Pincode must be a 6-digit number.", exception.getMessage());
    }
}