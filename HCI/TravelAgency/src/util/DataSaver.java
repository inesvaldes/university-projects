package util;


import java.io.FileWriter;
import java.io.IOException;


import model.Booking;

public class DataSaver {
		
	
	public static void saveCurrentBooking(Booking booking) {
	    if (booking == null) return;

	    String id = booking.getCustomer().getId(); 
	    String timestamp = java.time.LocalDateTime.now() .toString() .replace(":", "-"); 
	    String fileName = FileConstants.FILES_PATH + id + "_" + timestamp + FileConstants.FILES_EXTENSION;

	    try {
	        FileWriter fw = new FileWriter(fileName);
	        fw.write(booking.toString());
	        fw.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	




	



}
