package com.Journal.journalDemo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

@Data
public class WeatherResponse {
    @Getter
    @Setter
    public Current current;

    @Data
    public class Current{
        @Getter
        @Setter
        public int temperature;
        //this below annotaion is for ensuring best practices(camelcase and snakecase)
        @JsonProperty("weather_descriptions")
        public ArrayList<String> weatherDescriptions;
        public int feelslike;
    }
}
