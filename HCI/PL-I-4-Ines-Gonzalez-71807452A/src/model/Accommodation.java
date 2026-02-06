package model;

public class Accommodation {

    private String accommodationCode; 
    private String type;              // AP, HO, AH
    private int category;             // Star rating
    private String name;              
    private String parkCode;         
    private double price;             //For hotels, the Price is per person per night
    private int maxOccupancy;			//For aparthotels or apartments, the price is per night, regardless of the number of people						
    
    
    public Accommodation(String accommodationCode, String type, int category,
                         String name, String parkCode, double price) {
        this.accommodationCode = accommodationCode;
        this.type = type;
        this.category = category;
        this.name = name;
        this.parkCode = parkCode;
        this.price = price;
        this.maxOccupancy = type.equals("HO") ? 2 : 4;
    }

    
    public String getAccommodationCode() { return accommodationCode; }
    public String getType() { return type; }
    public int getCategory() { return category; }
    public String getName() { return name; }
    public String getParkCode() { return parkCode; }
    public double getPrice() { return price; }
    public int getMaxOccupancy() { return maxOccupancy; }
    


	@Override
	public String toString() {
		return name;
	}

    
}

