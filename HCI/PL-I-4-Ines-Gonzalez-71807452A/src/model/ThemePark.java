package model;

public class ThemePark {

    private String parkCode;       
    private String name;          
    private String country;       
    private String location;       
    private String description;    
    private double adultPrice;     
    private double childPrice;     

    public ThemePark(String parkCode, String name, String country, String location,
                     String description, double adultPrice, double childPrice) {
        this.parkCode = parkCode;
        this.name = name;
        this.country = country;
        this.location = location;
        this.description = description;
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
    }

    public String getParkCode() { return parkCode; } 
    public String getName() { return name; } 
    public String getCountry() { return country; } 
    public String getLocation() { return location; } 
    public String getDescription() { return description; }
    public double getAdultPrice() { return adultPrice; } 
    public double getChildPrice() { return childPrice; }


    
    
	@Override
	public String toString() {
		return name;
	}

    

    
}

