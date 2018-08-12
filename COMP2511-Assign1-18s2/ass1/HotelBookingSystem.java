package ass1;

import java.util.ArrayList;

public class HotelBookingSystem {

	private ArrayList<Hotel> hotelList;
	
	public HotelBookingSystem() {
		this.hotelList = new ArrayList<Hotel>();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HotelBookingSystem HBSys = new HotelBookingSystem();
		HBSys.addHotelRoom("Test1", "101", "2");
		HBSys.addHotelRoom("Test1", "102", "1");
		HBSys.addHotelRoom("Test1", "103", "3");
		HBSys.addHotelRoom("Test2", "101", "1");
		HBSys.addHotelRoom("Test2", "201", "1");
		HBSys.addHotelRoom("Test2", "202", "2");
		HBSys.addHotelRoom("Test3", "101", "2");
		HBSys.addHotelRoom("Test3", "103", "3");
		HBSys.printHotel("Test1");
		HBSys.printHotel("Test2");
		HBSys.printHotel("Test3");
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
