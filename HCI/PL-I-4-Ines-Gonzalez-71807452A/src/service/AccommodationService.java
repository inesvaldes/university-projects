package service;

import java.util.List;
import java.util.stream.Collectors;
import model.Accommodation;
import model.ThemePark;
import util.FileConstants;

public class AccommodationService {

    public List<Accommodation> filter(List<Accommodation> accommodations, String type, Integer category, ThemePark park) {
        return accommodations.stream()
            .filter(a -> type == null || a.getType().equals(type))
            .filter(a -> category == null || a.getCategory() == category)
            .filter(a -> park == null || a.getParkCode().equals(park.getParkCode()))
            .collect(Collectors.toList());
    }

    public String getAccommodationInfo(Accommodation a) { 
    	return "acc.name" + a.getName() + "\n" +
    			"acc.category" + a.getCategory() + "★\n" +
    			"acc.price" + a.getPrice() + "€\n" + 
    			"acc.park";
    }

    public String getAccommodationImagePath(Accommodation a) {
        return FileConstants.IMG_PATH + a.getAccommodationCode() + FileConstants.IMG_EXTENSION;
    }

    public boolean isValidAccommodationCapacity(Accommodation acc, int people, int rooms) {
        int maxAllowed = acc.getMaxOccupancy() * rooms;
        return people <= maxAllowed;
    }

    public List<Accommodation> getAccommodationsForSelectedPark(List<Accommodation> accommodations, ThemePark selectedPark) {
        if (selectedPark == null) {
            return accommodations;
        }
        return accommodations.stream()
                .filter(a -> a.getParkCode().equals(selectedPark.getParkCode()))
                .toList();
    }
}




