package com.sumit.controller;

import com.sumit.entity.Weather;
import com.sumit.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public String getCurrentWeather(@RequestParam("city") String cityName){
        Weather response = weatherService.getCurrentWeather(cityName);
        if(response == null || response.getCurrent() == null)
            return "Please try again with some other City name !!";

        int feelsLike = response.getCurrent().getFeelslike();
        return STR."Today's weather is \{feelsLike}";
    }


}