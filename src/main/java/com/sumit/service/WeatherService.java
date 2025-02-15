package com.sumit.service;

import com.sumit.entity.Weather;
import com.sumit.utils.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${weather.stack.api.token}")
    String weatherApiToken;

    public ResponseEntity<Weather> getCurrentWeather(String cityName){
        try{
            String currentWeatherApi = String.format(Constants.CURRENT_WEATHER_API, weatherApiToken, cityName);
            log.info("API CALL : {}", currentWeatherApi);
            ResponseEntity<Weather> weather = restTemplate.exchange(currentWeatherApi, HttpMethod.GET, null, Weather.class);
            log.info(weather.toString());
            return weather;
        } catch (Exception e){
            log.error("Exception in getCurrentWeather for city name = {}", cityName, e);
            throw new RuntimeException("Exception in getCurrentWeather " + e.getMessage());
        }
    }

}