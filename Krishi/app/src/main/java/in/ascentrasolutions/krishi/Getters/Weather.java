package in.ascentrasolutions.krishi.Getters;

import java.util.ArrayList;

public class Weather {

    private final String image, maxTemp_c, minTemp_c, avgTemp_c, maxWind_kph, itRain;
    private final ArrayList<WeatherHour> weatherHourList;



    public String getImage() {
        return image;
    }

    public String getItRain() {
        return itRain;
    }

    public String getMaxWind_kph() {
        return maxWind_kph;
    }

    public String getAvgTemp_c() {
        return avgTemp_c;
    }

    public String getMinTemp_c() {
        return minTemp_c;
    }

    public String getMaxTemp_c() {
        return maxTemp_c;
    }

    public ArrayList<WeatherHour> getWeatherHourList() {
        return weatherHourList;
    }

    public Weather(String image, String maxTempC, String minTempC, String avgTempC, String maxWindKph, String itRain, ArrayList<WeatherHour> weatherHourList) {
        this.image = image;
        maxTemp_c = maxTempC;
        minTemp_c = minTempC;
        avgTemp_c = avgTempC;
        maxWind_kph = maxWindKph;
        this.itRain = itRain;
        this.weatherHourList = weatherHourList;
    }

}
