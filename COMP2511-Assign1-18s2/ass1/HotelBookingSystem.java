package ass1;

import java.util.ArrayList;

public class HotelBookingSystem {

	private ArrayList<Hotel> hotelList;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public boolean addHotelRoom(String hotelName, String roomNumber, String capacity) {
		boolean hotelExist = false;
		boolean RoomSuccess = false;
		Capacity capa = Capacity.values()[Integer.parseInt(capacity)-1];
		for(Hotel hotel: hotelList) {
			if(hotel.getName() == hotelName) {
				hotelExist = true;
				RoomSuccess = hotel.addRoom(roomNumber, capa);
				break;
			}
		}
		if(!hotelExist) {
			Hotel hotel = new Hotel(hotelName);
			RoomSuccess = hotel.addRoom(roomNumber, capa);
			hotelList.add(hotel);
			hotelExist = true;
		}
		return RoomSuccess;
	}
	
	public void printHotel(String hotelName) {
		for(Hotel hotel: hotelList)
			if(hotel.getName() == hotelName)
				hotel.print();
	}

}
