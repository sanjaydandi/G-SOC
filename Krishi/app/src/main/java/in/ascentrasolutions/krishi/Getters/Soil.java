package in.ascentrasolutions.krishi.Getters;

public class Soil {

    private final String soil_name, soil_color;

    public String getSoil_name() {
        return soil_name;
    }

    public String getSoil_color() {
        return soil_color;
    }

    public Soil (String soil_color, String soil_name) {
        this.soil_color = soil_color;
        this.soil_name = soil_name;
    }
}
