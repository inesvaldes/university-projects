package service;

import java.util.List;
import model.ThemePark;
import util.FileConstants;
import model.Accommodation;

public class ThemeParkService {

    public List<ThemePark> getAllParks(List<ThemePark> parks) {
        return parks;
    }

    public String getParkInfo(ThemePark p) { 
    	return "park.country" + p.getCountry() + "\n" +
    			"park.location" + p.getLocation() + "\n\n" + 
    			"park.description" + p.getDescription() +
    			"\n\n" + "park.adultprice" + p.getAdultPrice() + "€\n" + 
    			"park.childprice" + p.getChildPrice() + "€"; 
    	}

    public String getParkImagePath(ThemePark p) {
        return FileConstants.IMG_PATH + p.getParkCode() + FileConstants.IMG_EXTENSION;
    }

    public String getParkNameByCode(List<ThemePark> parks, String parkCode) {
        for (ThemePark p : parks) {
            if (p.getParkCode().equals(parkCode)) {
                return p.getName();
            }
        }
        return "Unknown park";
    }

    public List<ThemePark> getParksForSelectedAccommodation(List<ThemePark> parks, Accommodation selectedAccommodation) {
        if (selectedAccommodation == null) {
            return parks;
        }

        String parkCode = selectedAccommodation.getParkCode();

        return parks.stream()
                .filter(p -> p.getParkCode().equals(parkCode))
                .toList();
    }
}
