package model;

import java.time.LocalDate;

public class AccommodationReservation {

    private Accommodation accommodation;
    private LocalDate date;
    private int nights;
    private int people;

    public AccommodationReservation(Accommodation accommodation, LocalDate date, int nights, int people) {
        this.accommodation = accommodation;
        this.date = date;
        this.nights = nights;
        this.people = people;
    }

    public Accommodation getAccommodation() { return accommodation; }
    public LocalDate getDate() { return date; }
    public int getNights() { return nights; }
    public int getPeople() { return people; }
    
    @Override
    public String toString() {
        return "** ACCOMMODATION **\n" +
               "Accommodation: " + accommodation.getParkCode() + " / " + accommodation.getType() + " / " + accommodation.getCategory() + " / " + accommodation.getName() + "\n" +
               "Start date: " + date + " / Number of nights: " + nights + "\n" +
               "N. People: " + people + "\n";
    }

}

