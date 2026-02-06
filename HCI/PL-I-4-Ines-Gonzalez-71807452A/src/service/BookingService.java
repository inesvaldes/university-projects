package service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.DefaultListModel;

import model.AccommodationReservation;
import model.Booking;
import model.Customer;
import model.TicketReservation;

public class BookingService {

    public Booking createBooking(
            Customer customer,
            TicketReservation ticketReservation,
            AccommodationReservation accommodationReservation,
            double ticketPrice,
            double accommodationPrice,
            double discount,
            double totalPrice) {

        return new Booking(
                customer,
                ticketReservation,
                accommodationReservation,
                ticketPrice,
                accommodationPrice,
                discount,
                totalPrice
        );
    }

    public DefaultListModel<String> buildReservationSummary(
            TicketReservation ticketReservation,
            AccommodationReservation accommodationReservation,
            double ticketPrice,
            double accommodationPrice,
            double discount,
            double totalWithoutDiscount,
            double total) {

        DefaultListModel<String> model = new DefaultListModel<>();

        if (ticketReservation != null) {
            var tr = ticketReservation;

            String ticketLine =
                    "summary.tickets" +
                    " | " + "summary.park" + tr.getPark().getName() +
                    " | " + "summary.date" + tr.getDate() +
                    " | " + "summary.days" + tr.getDays() +
                    " | " + "summary.adults" + tr.getAdults() +
                    " | " + "summary.children" + tr.getChildren() +
                    " | " + "summary.price" + ticketPrice + " €";

            model.addElement(ticketLine);
        }

        if (accommodationReservation != null) {
            var ar = accommodationReservation;

            String accLine =
                    "summary.accommodation" + ar.getAccommodation().getName() +
                    " (" + ar.getAccommodation().getType() + ", " + ar.getAccommodation().getCategory() + ")" +
                    " | " + "summary.date" + ar.getDate() +
                    " | " + "summary.nights" + ar.getNights() +
                    " | " + "summary.people" + ar.getPeople() +
                    " | " + "summary.price" + accommodationPrice + " €";

            model.addElement(accLine);
        }

        if (discount > 0) {
            model.addElement("summary.totalWithoutDiscount" + totalWithoutDiscount + " €");
            model.addElement("summary.discount" + discount + " €");
        }

        model.addElement("summary.total" + total + " €");

        return model;
    }

    public String buildBookingSummaryText(Booking b, String parkName) {

        StringBuilder sb = new StringBuilder();
        String today = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        sb.append("booking.header").append("\n");
        sb.append("booking.separator").append("\n");

        sb.append("booking.confirmation").append(" – ")
                .append(today).append(" – ").append(parkName).append("\n");

        sb.append(b.getCustomer().getId()).append(" – ")
                .append(b.getCustomer().getName()).append(" ")
                .append(b.getCustomer().getSurname()).append("\n");

        sb.append("booking.separator").append("\n\n");

        sb.append("booking.details").append("\n");

        if (b.getParkReservation() != null) {
            var tr = b.getParkReservation();
            sb.append("booking.ticketsSection").append("\n");
            sb.append("booking.startDate")
                    .append(tr.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .append(" / ")
                    .append("booking.days").append(tr.getDays()).append("\n");

            sb.append("booking.adults").append(tr.getAdults())
                    .append(" / ")
                    .append("booking.children").append(tr.getChildren()).append("\n\n");
        }

        if (b.getAccommodationReservation() != null) {
            var ar = b.getAccommodationReservation();
            sb.append("booking.accommodationSection").append("\n");

            sb.append("booking.accommodation")
                    .append(ar.getAccommodation().getParkCode()).append(" / ")
                    .append(ar.getAccommodation().getType()).append(" / ")
                    .append(ar.getAccommodation().getCategory()).append(" / ")
                    .append(ar.getAccommodation().getName()).append("\n");

            sb.append("booking.startDate")
                    .append(ar.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .append(" / ")
                    .append("booking.nights").append(ar.getNights()).append("\n");

            sb.append("booking.people").append(ar.getPeople()).append("\n\n");
        }

        sb.append("booking.amount").append("\n");

        sb.append("booking.tickets").append(b.getTicketPrice()).append(" €\n");
        sb.append("booking.accommodation").append(b.getAccommodationPrice()).append(" €\n");

        if (b.getDiscount() > 0) {
            sb.append("booking.discount").append(b.getDiscount()).append(" €\n");
        }

        sb.append("booking.total").append(b.getTotalPrice()).append(" €\n");

        return sb.toString();
    }
}
