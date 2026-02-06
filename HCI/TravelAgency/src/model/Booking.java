package model;

import java.time.LocalDate;

public class Booking {

    private Customer customer;
    private TicketReservation parkReservation;
    private AccommodationReservation accommodationReservation;

    private double ticketPrice;
    private double accommodationPrice;
    private double discount;
    private double totalPrice;
    private boolean giftVoucher; 
    
    private LocalDate bookingDate;

    public Booking(Customer customer,
                   TicketReservation parkReservation,
                   AccommodationReservation accommodationReservation,
                   double ticketPrice,
                   double accommodationPrice,
                   double discount,
                   double totalPrice) {

        this.customer = customer;
        this.parkReservation = parkReservation;
        this.accommodationReservation = accommodationReservation;

        this.ticketPrice = ticketPrice;
        this.accommodationPrice = accommodationPrice;
        this.discount = discount;
        this.totalPrice = totalPrice;
        this.giftVoucher = false;
       
        this.bookingDate = LocalDate.now();

    }

    public Customer getCustomer() { return customer; }
    public TicketReservation getParkReservation() { return parkReservation; }
    public AccommodationReservation getAccommodationReservation() { return accommodationReservation; }

    public double getTicketPrice() { return ticketPrice; }
    public double getAccommodationPrice() { return accommodationPrice; }
    public double getDiscount() { return discount; }
    public double getTotalPrice() { return totalPrice; }
    
    public boolean hasGiftVoucher() { return giftVoucher; } 
    
    public void setGiftVoucher(boolean value) { this.giftVoucher = value; } 
   
    public void setTotalPrice(double value) { this.totalPrice = value; }
    
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        
        sb.append("OPENWORLD TRAVEL AGENCY\n");
        sb.append("--------------------------------------------------------------------------------------\n");
        String parkName="";
	    if (getParkReservation() != null && getParkReservation().getPark() != null) { 
	    	parkName = getParkReservation().getPark().getName(); 
	    } else if (getAccommodationReservation() != null && getAccommodationReservation().getAccommodation() != null) { 
	    		//parkName =getParkNameByCode(getAccommodationReservation().getAccommodation().getParkCode()) ; 
	    } else { 
	    		parkName = "N/A"; 
	    	}

        
        sb.append("BOOKING CONFIRMATION – ").append(bookingDate)
          .append(" – ").append(parkName).append("\n");
        sb.append("(").append(customer.getId()).append(" – ")
          .append(customer.getName()).append(")\n");
        sb.append("---------------------------------------------------------------------------------------\n");

        sb.append("**** BOOKING DETAILS ****\n");
        if (parkReservation != null) { 
        	sb.append(parkReservation.toString()).append("\n");
        }
        if (accommodationReservation != null) {
        	sb.append(accommodationReservation.toString()).append("\n");
        }
        sb.append("**** BOOKING AMOUNT ****\n");
        sb.append("Tickets: ").append(ticketPrice).append(" €\n");
        sb.append("Accommodation: ").append(accommodationPrice).append(" €\n");
        sb.append("Offer discount: ").append(discount).append(" €\n");
    
        if (giftVoucher) {
            sb.append("Obtained a 100€ gift voucher for your next booking\n");
        }
        
        sb.append("Total amount: ").append(totalPrice).append(" €\n\n");

        return sb.toString();
    }

   

}


