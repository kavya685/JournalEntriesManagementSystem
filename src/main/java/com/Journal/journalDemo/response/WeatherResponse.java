package com.Journal.journalDemo.response;

import java.util.ArrayList;

public class WeatherResponse {
    public Current current;

    public class Current{
        public int temperature;
        public ArrayList<String> weather_descriptions;
        public int feelslike;
    }
}
