package controller;

import java.io.IOException;
import java.util.List;
import java.util.Random;


import javax.swing.DefaultListModel;

import model.Accommodation;
import model.AccommodationReservation;
import model.Booking;
import model.Customer;
import model.ThemePark;
import model.TicketReservation;
import service.AccommodationService;
import service.MiniGameService;
import service.PriceService;
import service.ThemeParkService;
import service.BookingService;
import util.DataLoader;
import util.DataSaver;

public class AppController {

    private List<ThemePark> parks;
    private List<Accommodation> accommodations;

    private ThemePark offerPark;
    private ThemePark selectedPark;
    private Accommodation selectedAccommodation;

    private final ThemeParkService themeParkService = new ThemeParkService();
    private final AccommodationService accommodationService = new AccommodationService();
    private final PriceService priceService = new PriceService();
    private final MiniGameService miniGameService = new MiniGameService();
    private final BookingService bookingService = new BookingService();

    private TicketReservation ticketReservation;
    private AccommodationReservation accommodationReservation;

    private Customer customer;
    private Booking booking;

    public AppController() {
        try {
            parks = DataLoader.loadThemeParks();
            accommodations = DataLoader.loadAccommodations();
        } catch (IOException e) {
            e.printStackTrace();
        }

        parks = parks.stream()
                .sorted((a, b) -> a.getName().compareToIgnoreCase(b.getName()))
                .toList();

        accommodations = accommodations.stream()
                .sorted((a, b) -> a.getName().compareToIgnoreCase(b.getName()))
                .toList();

        chooseOfferPark();
        this.customer = null;
        this.booking = null;
    }

    public void chooseOfferPark() {
        Random r = new Random();
        offerPark = parks.get(r.nextInt(parks.size()));
    }

    public List<ThemePark> getParks() {
        return parks;
    }

    public List<Accommodation> getAccommodations() {
        return accommodations;
    }

    public ThemePark getOfferPark() {
        return offerPark;
    }

    public ThemePark getSelectedPark() {
        return selectedPark;
    }

    public void setSelectedPark(ThemePark p) {
        selectedPark = p;
    }

    public Accommodation getSelectedAccommodation() {
        return selectedAccommodation;
    }

    public void setSelectedAccommodation(Accommodation a) {
        selectedAccommodation = a;
    }

    public String getParkNameByCode(String parkCode) {
        return themeParkService.getParkNameByCode(parks, parkCode);
    }

    public List<Accommodation> getAccommodationsForSelectedPark() {
        return accommodationService.getAccommodationsForSelectedPark(accommodations, selectedPark);
    }

    public List<ThemePark> getParksForSelectedAccommodation() {
        return themeParkService.getParksForSelectedAccommodation(parks, selectedAccommodation);
    }

    public void resetSelections() {
        selectedPark = null;
        selectedAccommodation = null;
    }

    public boolean hasSelectedPark() {
        return selectedPark != null;
    }

    public boolean hasSelectedAccommodation() {
        return selectedAccommodation != null;
    }

    public boolean isParkOnOffer(ThemePark p) {
        return p.equals(getOfferPark());
    }

    public TicketReservation getTicketReservation() {
        return ticketReservation;
    }

    public AccommodationReservation getAccommodationReservation() {
        return accommodationReservation;
    }

    public void setTicketReservation(TicketReservation r) {
        ticketReservation = r;
    }

    public void setAccommodationReservation(AccommodationReservation r) {
        accommodationReservation = r;
    }

    public void removeTicketReservation() {
        ticketReservation = null;
    }

    public void removeAccommodationReservation() {
        accommodationReservation = null;
    }

    public boolean isValidAccommodationCapacity(Accommodation acc, int people, int rooms) {
        return accommodationService.isValidAccommodationCapacity(acc, people, rooms);
    }

    public double getTicketPrice() {
        return priceService.calculateParkPrice(ticketReservation);
    }

    public double getAccommodationPrice() {
        return priceService.calculateAccommodationPrice(accommodationReservation);
    }

    public double getDiscount() {
        return priceService.calculateDiscount(ticketReservation, accommodationReservation, offerPark);
    }

    public double getTotalPrice() {
        return priceService.calculateTotalAfterDiscount(ticketReservation, accommodationReservation, offerPark);
    }

    public double getTotalWithoutDiscount() {
        return getTicketPrice() + getAccommodationPrice();
    }

   

    public DefaultListModel<String> getReservationSummary() {
        return bookingService.buildReservationSummary(
                ticketReservation,
                accommodationReservation,
                getTicketPrice(),
                getAccommodationPrice(),
                getDiscount(),
                getTotalWithoutDiscount(),
                getTotalPrice()
        );
    }

    public void setCustomer(Customer c) {
        this.customer = c;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void createBooking() {
        booking = bookingService.createBooking(
                customer,
                ticketReservation,
                accommodationReservation,
                getTicketPrice(),
                getAccommodationPrice(),
                getDiscount(),
                getTotalPrice()
        );
    }

    public Booking getBooking() {
        return booking;
    }

    public void resetAllReservations() {
        ticketReservation = null;
        accommodationReservation = null;
        customer = null;
        booking = null;
    }

    public String getBookingSummaryText() {
        Booking b = getBooking();
        String parkName;

        if (b.getParkReservation() != null && b.getParkReservation().getPark() != null) {
            parkName = b.getParkReservation().getPark().getName();
        } else if (b.getAccommodationReservation() != null &&
                   b.getAccommodationReservation().getAccommodation() != null) {
            parkName = getParkNameByCode(b.getAccommodationReservation().getAccommodation().getParkCode());
        } else {
            parkName = "N/A";
        }

        return bookingService.buildBookingSummaryText(b, parkName);
    }

    public void startMiniGame() {
        miniGameService.resetGame();
    }

    public boolean revealCell(int index) {
        return miniGameService.selectCell(index);
    }

    public String getMiniGameImagePath(int index) {
        return miniGameService.getCell(index).getImagePath();
    }

    public int getMiniGameCellCount() {
        return miniGameService.getCellCount();
    }

    public boolean hasWonMiniGame() {
        return miniGameService.hasPlayed() && miniGameService.hasWon();
    }
    public boolean hasPlayedMiniGame() {
        return miniGameService.hasPlayed() ;
    }

    public void resetMiniGame() {
        miniGameService.resetGame();
    }

   

    public void saveCurrentBooking() {
        DataSaver.saveCurrentBooking(booking);
    }
    public String getParkInfo(ThemePark p) {
        return themeParkService.getParkInfo(p);
    }

    public String getParkImagePath(ThemePark p) {
        return themeParkService.getParkImagePath(p);
    }
    public String getAccommodationInfo(Accommodation a) {
        return accommodationService.getAccommodationInfo(a);
    }

    public String getAccommodationImagePath(Accommodation a) {
        return accommodationService.getAccommodationImagePath(a);
    }


}
