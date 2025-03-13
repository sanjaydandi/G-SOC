package in.ascentrasolutions.krishi.Getters;

public class Yield {

    private final String name, quantity, price, image, use_for;

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

    public Yield(String name, String quantity, String price, String image, String useFor) {

        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        use_for = useFor;
    }

}
