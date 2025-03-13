package in.ascentrasolutions.krishi.Getters;

public class WeatherHour {

    private final String time, temp_c, windKmp, humidity, itRain, cloud,  windChill_c, heat_c, dewPoint_c, icon;

    public String getTime() {
        return time;
    }

    public String getTemp_c() {
        return temp_c;
    }

    public String getWindKmp() {
        return windKmp;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getItRain() {
        return itRain;
    }

    public String getCloud() {
        return cloud;
    }

    public String getWindChill_c() {
        return windChill_c;
    }

    public String getHeat_c() {
        return heat_c;
    }

    public String getDewPoint_c() {
        return dewPoint_c;
    }

    public String getIcon() {
        return icon;
    }

    public WeatherHour(String time, String tempC, String windKmp, String humidity, String itRain, String cloud, String windChillC, String heatC, String dewPointC, String icon) {
        this.time = time;
        temp_c = tempC;
        this.windKmp = windKmp;
        this.humidity = humidity;
        this.itRain = itRain;
        this.cloud = cloud;
        windChill_c = windChillC;
        heat_c = heatC;
        dewPoint_c = dewPointC;
        this.icon = icon;
    }
}
