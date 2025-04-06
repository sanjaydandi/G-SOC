package in.ascentrasolutions.krishi.Getters;

public class HalfPrice {

    private final String name, quantity, price, image, use_for, crop_id;

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getUse_for() {
        return use_for;
    }

    public String getCrop_id() {
        return crop_id;
    }

    public HalfPrice(String name, String quantity, String price, String image, String useFor, String crop_id) {
        this.crop_id = crop_id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        use_for = useFor;
    }



}
