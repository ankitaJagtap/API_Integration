package com.example.SpringBootApp.IntegratingAPI.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Slf4j
@Service
public class WeatherService {

    @Value("${url.summaryUrl}")
    private String summaryUrl;

    @Value("${url.hourlyUrl}")
    private String hourlyUrl;

    @Value("${key.xRapidAPIKey}")
    private String xRapidAPIKey;

    @Value("${host.xRapidAPIHost}")
    private String xRapidAPIHost;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.security.user.name}")
    private String client_id;

    @Value("${spring.security.user.password}")
    private String client_secret;

    public ResponseEntity<Object> getWeatherForecastSummaryByCityName(String location_name) {
        try{
            // Set headers value
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-RapidAPI-Key", xRapidAPIKey);
            headers.set("X-RapidAPI-Host", xRapidAPIHost);

            // create auth credentials
            String authStr = client_id + ":" + client_secret;
            String encodedAuthToken  = Base64.getEncoder().encodeToString(authStr.getBytes());

            // create headers
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("Authorization", "Basic " + encodedAuthToken);

            Map<String, String> params = new HashMap<String, String>();
            params.put("locationName", location_name);

            //Make call to the RapidAPI
            ResponseEntity<String> response = restTemplate.exchange(summaryUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class, params);

            log.info("Output of RapidAPI:{}", response.getBody());

            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);

        }catch (Exception e){
            log.error("Something went wrong while getting values from RapidAPI", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception while calling Rapid API for weather Forecast", e);
        }
    }

    public ResponseEntity<Object> getHourlyForecastByCityName(String location_name) {
        try{
            // Set headers value
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-RapidAPI-Key", xRapidAPIKey);
            headers.set("X-RapidAPI-Host", xRapidAPIHost);

            // create auth credentials
            String client_id = UUID.randomUUID().toString();
            String client_secret = UUID.randomUUID().toString();
            String authStr = client_id + ":" + client_secret;
            String encodedAuthToken  = Base64.getEncoder().encodeToString(authStr.getBytes());

            // create headers
            headers.add("Authorization", "Basic " + encodedAuthToken );

            Map<String, String> params = new HashMap<String, String>();
            params.put("locationName", location_name);

            // Make call to the RapidAPI
            ResponseEntity<String> response = restTemplate.exchange(hourlyUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class, params);

            log.info("Output og RapidAPI:{}", response.getBody());
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);

        }catch (Exception e){
            log.error("Something went wrong while getting values from RapidAPI", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception while calling Rapid API for weather Forecast", e);
        }
    }

}
