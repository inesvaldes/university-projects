package service;

import model.TicketReservation;
import model.AccommodationReservation;
import model.ThemePark;

public class PriceService {

    
    private static final double DISCOUNT_RATE = 0.15;
   

    public double calculateParkPrice(TicketReservation r) {
        if (r == null) return 0;
        double base = (r.getAdults() * r.getPark().getAdultPrice() +
                       r.getChildren() * r.getPark().getChildPrice()) * r.getDays();
        return round(base);
    }

    public double calculateAccommodationPrice(AccommodationReservation r) {
        if (r == null) return 0;
        double pricePerNight = r.getAccommodation().getPrice();
        int nights = r.getNights();
        int people = r.getPeople();
        String type = r.getAccommodation().getType();
        double base;
        if (type.equals("HO")) {
            base = pricePerNight * people * nights;
        } else {
            base = pricePerNight * nights;
        }
        return round(base);
    }


    public double calculateDiscount(TicketReservation tr, AccommodationReservation ar, ThemePark parkOnOffer) {
        double discount = 0;

        if (tr != null && tr.getPark().equals(parkOnOffer)) {
            discount += calculateParkPrice(tr) * DISCOUNT_RATE;
        }
        if (ar != null && ar.getAccommodation().getParkCode().equals(parkOnOffer.getParkCode())) {
            discount += calculateAccommodationPrice(ar) * DISCOUNT_RATE;
        }
        return round(discount);
    }

    public double calculateTotalAfterDiscount(TicketReservation tr, AccommodationReservation ar, ThemePark parkOnOffer) {
        double t = calculateParkPrice(tr);
        double a = calculateAccommodationPrice(ar);
        double d = calculateDiscount(tr, ar, parkOnOffer);
        return round(t + a - d);
    }

    


    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
