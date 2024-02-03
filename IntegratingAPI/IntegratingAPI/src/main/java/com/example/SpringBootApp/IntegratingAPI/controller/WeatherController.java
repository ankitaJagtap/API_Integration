package com.example.SpringBootApp.IntegratingAPI.controller;


import com.example.SpringBootApp.IntegratingAPI.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("weather/")
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @GetMapping(value = "getForecastSummary/", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Object> getWeatherForecastSummaryByLocationName(@RequestParam("locationName") String locationName){
        return weatherService.getWeatherForecastSummaryByCityName(locationName);
    }

    @GetMapping(value = "getHourlyForecast/", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Object> getHourlyForecastByLocationName(@RequestParam("locationName") String locationName){
        return weatherService.getHourlyForecastByCityName(locationName);
    }

}
