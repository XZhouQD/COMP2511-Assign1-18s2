package ass1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;

public class HotelBookingSystem {

	private ArrayList<Hotel> hotelList;
	private ArrayList<String> bookNames;
	private Order emptyOrder;
	
	public HotelBookingSystem() {
		this.hotelList = new ArrayList<Hotel>();
		this.bookNames = new ArrayList<String>();
		this.emptyOrder = new Order("empty", "-1", LocalDate.parse("1980-01-01"), 1);
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
	
	public boolean bookRoom(String name, String month, String day, String length, String size1, String num1, String size2, String num2, String size3, String num3) throws ParseException {
		for(String book:bookNames) {
			if(book==name) {
				System.out.println("Booking Rejected");
			}
		}
		DateFormat original = new SimpleDateFormat("yyyy MMM dd");
		DateFormat target = new SimpleDateFormat("yyyy-MM-dd");
		Date fullDate = original.parse("2018 "+month+" "+day);
		String formatDate = target.format(fullDate);
		LocalDate startDate = LocalDate.parse(formatDate);
		int bookLength = Integer.parseInt(length);
		Capacity capa1 = Capacity.valueOf(size1.toUpperCase());
		int bookNum1 = Integer.parseInt(num1);
		int bookNum2 = 0;
		int bookNum3 = 0;
		Capacity capa2 = Capacity.SINGLE;
		Capacity capa3 = Capacity.SINGLE;
		if(num2 != null && size2 != null) {
			capa2 = Capacity.valueOf(size2.toUpperCase());
			bookNum2 = Integer.parseInt(num2);
		}
		if(num3 != null && size3 != null) {
			bookNum3 = Integer.parseInt(num3);
			capa3 = Capacity.valueOf(size3.toUpperCase());
		}
		boolean available = false;
		ArrayList<Order> orderList = new ArrayList<Order>();
		for(Hotel hotel : hotelList) {
			boolean hotelFit = true;
			for(int i = 0; i < bookNum1; i++) {
				Order newOrder = hotel.tryBooking(name, capa1, startDate, bookLength);
				if(newOrder == null) hotelFit = false;
				else if(!newOrder.equals(emptyOrder))
					orderList.add(newOrder);
			}
			for(int i = 0; i < bookNum2; i++) {
				Order newOrder = hotel.tryBooking(name, capa2, startDate, bookLength);
				if(newOrder == null) hotelFit = false;
				else if(!newOrder.equals(emptyOrder))
					orderList.add(newOrder);
			}
			for(int i = 0; i < bookNum3; i++) {
				Order newOrder = hotel.tryBooking(name, capa3, startDate, bookLength);
				if(newOrder == null) hotelFit = false;
				else if(!newOrder.equals(emptyOrder))
					orderList.add(newOrder);
			}
			if(hotelFit == true) {
				String output = "Booking "+ name + " " + hotel.getName();
				for(Order order : orderList)
					output += " " + order.getRoomNumber();
				System.out.println(output);
				available = true;
				break;
			}
			else {
				hotelFit = true;
				for(Room room : hotel.getRooms()) {
					boolean release = true;
					while(release)
						release = room.releaseOrder(name) != null;
				}
				orderList.clear();
			}
		}
		
		if(available == false) System.out.println("Booking Rejected");
		
		return true;
	}
	
	public void printHotel(String hotelName) {
		for(Hotel hotel: hotelList)
			if(hotel.getName() == hotelName)
				hotel.print();
	}

}
