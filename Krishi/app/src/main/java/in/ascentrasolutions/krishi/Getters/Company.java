package in.ascentrasolutions.krishi.Getters;

public class Company {

    private final String company_name, company_location, company_number, company_image;


    public String getCompany_name() {
        return company_name;
    }

    public String getCompany_location() {
        return company_location;
    }

    public String getCompany_number() {
        return company_number;
    }

    public String getCompany_image() {
        return company_image;
    }

    public Company(String company_image, String company_location, String company_name, String company_number) {
        this.company_image = company_image;
        this.company_location = company_location;
        this.company_name = company_name;
        this.company_number = company_number;
    }



}
