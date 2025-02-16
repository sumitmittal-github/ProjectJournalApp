package com.sumit.service;

import com.sumit.cache.MyCache;
import com.sumit.entity.Weather;
import com.sumit.utils.PlaceHolders;
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

    @Autowired
    private MyCache myCache;

    @Value("${weatherstack.api.token}")
    String weatherApiToken;

    public ResponseEntity<Weather> getCurrentWeather(String cityName){
        try{
            String weatherAPIUrl = myCache.cacheMap.get(PlaceHolders.CACHE_GET_WEATHER_API_KEY);
            String currentWeatherApi = String.format(weatherAPIUrl, weatherApiToken, cityName);
            log.info("API CALL : {}", currentWeatherApi);
            ResponseEntity<Weather> weather = restTemplate.exchange(currentWeatherApi, HttpMethod.GET, null, Weather.class);
            log.info(weather.toString());
            return weather;
        } catch (Exception e){
            log.error(STR."Exception in getCurrentWeather for city name = \{cityName}", e);
            throw new RuntimeException(STR."Exception in getCurrentWeather \{e.getMessage()}");
        }
    }

}