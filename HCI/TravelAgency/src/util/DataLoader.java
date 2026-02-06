package util;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Accommodation;
import model.ThemePark;

public class DataLoader {
	
	
	public static List<ThemePark> loadThemeParks() throws IOException {
        List<ThemePark> parks = new ArrayList<>();

        BufferedReader br = new BufferedReader(
                new FileReader(FileConstants.PARKS_FILE));

        String line;
        while ((line = br.readLine()) != null) {
            String[] p = line.split("@");

            ThemePark park = new ThemePark(
                    p[0],                  // code
                    p[1],                  // name
                    p[2],                  // country
                    p[3],                  // location
                    p[4],                  // description
                    Double.parseDouble(p[5]), // adult price
                    Double.parseDouble(p[6])  // child price
            );

            parks.add(park);
        }

        br.close();
        return parks;
    }

    public static List<Accommodation> loadAccommodations() throws IOException {
        List<Accommodation> accs = new ArrayList<>();

        BufferedReader br = new BufferedReader(
                new FileReader(FileConstants.ACCOMMODATION_FILE));

        String line;
        while ((line = br.readLine()) != null) {
            String[] a = line.split("@");

            Accommodation acc = new Accommodation(
                    a[0],                    // code
                    a[1],                    // type
                    Integer.parseInt(a[2]),  // category
                    a[3],                    // name
                    a[4],                    // park code
                    Double.parseDouble(a[5]) // price
                    
            );

            accs.add(acc);
        }

        br.close();
        return accs;
    }
   

    
}
