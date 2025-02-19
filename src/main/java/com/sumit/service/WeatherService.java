package com.sumit.service;

import com.sumit.cache.MyCache;
import com.sumit.entity.Weather;
import com.sumit.utils.Constants;
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

    @Autowired
    RedisService redisService;

    public Weather getCurrentWeather(String cityName){
        try{
            // try to get the weather data from redis cache and if found return the cached response
            Weather cachedWeather = redisService.getFromCache(cityName, Weather.class);
            if(cachedWeather != null)
                return cachedWeather;

            // if not found in redis cache, then get from weather API and also set in redis cache
            String weatherAPIUrl = myCache.cacheMap.get(PlaceHolders.CACHE_GET_WEATHER_API_KEY);
            String currentWeatherApi = String.format(weatherAPIUrl, weatherApiToken, cityName);
            log.info("API CALL : {}", currentWeatherApi);
            ResponseEntity<Weather> weather = restTemplate.exchange(currentWeatherApi, HttpMethod.GET, null, Weather.class);
            Weather weatherResponse = weather.getBody();
            log.info(weatherResponse);

            redisService.setInCache(cityName, weatherResponse, Constants.CACHE_TIME_TO_LIVE_MINUTES);
            return weatherResponse;
        } catch (Exception e){
            log.error(STR."Exception in getCurrentWeather for city name = \{cityName}", e);
            return null;
        }
    }

}