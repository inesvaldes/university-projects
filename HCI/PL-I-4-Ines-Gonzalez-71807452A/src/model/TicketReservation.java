package model;

import java.time.LocalDate;

public class TicketReservation {

    private ThemePark park;
    private LocalDate date;
    private int adults;
    private int children;
    private int days;

    public TicketReservation(ThemePark park, LocalDate date, int adults, int children, int days) {
        this.park = park;
        this.date = date;
        this.adults = adults;
        this.children = children;
        this.days = days;
    }

    public ThemePark getPark() { return park; }
    public LocalDate getDate() { return date; }
    public int getAdults() { return adults; }
    public int getChildren() { return children; }
    public int getDays() { return days; }
    
    
    @Override
    public String toString() {
        return "** TICKETS **\n" +
               "Start date: " + date + " / Number of days: " + days + "\n" +
               "N. adults: " + adults + " / N. children: " + children + "\n";
    }

}

