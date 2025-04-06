package in.ascentrasolutions.krishi.Getters;

public class Soils {

    private final String soil_name, soil_color, soil_image, soil_id;

    public String getSoil_name() {
        return soil_name;
    }

    public String getSoil_color() {
        return soil_color;
    }

    public String getSoil_image() {
        return soil_image;
    }

    public String getSoil_id() {
        return soil_id;
    }

    public Soils(String soilName, String soilColor, String soilImage, String soil_id) {
        soil_name = soilName;
        soil_color = soilColor;
        soil_image = soilImage;
        this.soil_id = soil_id;
    }
}
