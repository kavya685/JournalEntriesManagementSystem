package com.Journal.journalDemo.service;

import com.Journal.journalDemo.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    @Autowired
    private RestTemplate restTemplate;

    // by using @Value, we make our key secure and not hard coded in our code,
    // we can change it in application.properties file without changing the code
    // make sure not to use static for the key variable, otherwise it will not work with @Value annotation
    // this annotation works with instance variable, so using static is not recommended here
    @Value("${weather.api.key}")
    public String key;

    public final String url = "https://api.weatherstack.com/current?access_key=what&query=New%20York";


    public WeatherResponse getWeather()
    {
        String API = url.replace("what",key);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(API, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }
}
