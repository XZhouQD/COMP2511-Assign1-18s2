package ass1;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class HotelBookingSystem {

	private ArrayList<Hotel> hotelList;
	private ArrayList<String> bookNames;
	private Order emptyOrder;
	
	public HotelBookingSystem() {
		this.hotelList = new ArrayList<Hotel>();
		this.bookNames = new ArrayList<String>();
		this.emptyOrder = new Order("empty", "-1", "-1", LocalDate.parse("1980-01-01"), 1);
	}
	
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		HotelBookingSystem HBSys = new HotelBookingSystem();
		Scanner sc = null;
		try {
			sc = new Scanner(new File(args[0]));
			while (sc.hasNextLine()) {
				HBSys.functionDistributor(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			if (sc != null)
				sc.close();
		}
	}
	
	public String getStringByIndex(String[] arguments, int index) {
		try {
			String toReturn = arguments[index];
			return toReturn;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public void functionDistributor (String input) throws ParseException {
		String[] arguments = input.split(" ");
		if(arguments[0].equals("Hotel")) 
			this.addHotelRoom(arguments[1], arguments[2], arguments[3]);
		if(arguments[0].equals("Cancel"))
			System.out.println("Cancel "+this.cancelBooking(arguments[1]));
		if(arguments[0].equals("Print"))
			this.printHotel(arguments[1]);
		if(arguments[0].equals("Booking"))
			System.out.println("Booking " + this.bookRoom(arguments[1], arguments[2], arguments[3], arguments[4], arguments[5], arguments[6], this.getStringByIndex(arguments, 7), this.getStringByIndex(arguments, 8), this.getStringByIndex(arguments, 9), this.getStringByIndex(arguments, 10)));
		if(arguments[0].equals("Change"))
			System.out.println("Change " + this.changeBooking(arguments[1], arguments[2], arguments[3], arguments[4], arguments[5], arguments[6], this.getStringByIndex(arguments, 7), this.getStringByIndex(arguments, 8), this.getStringByIndex(arguments, 9), this.getStringByIndex(arguments, 10)));
	}
	
	public boolean addHotelRoom(String hotelName, String roomNumber, String capacity) {
		boolean hotelExist = false;
		boolean RoomSuccess = false;
		Capacity capa = Capacity.values()[Integer.parseInt(capacity)-1];
		for(Hotel hotel: hotelList) {
			if(hotel.getName().equals(hotelName)) {
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
	
	public String bookRoom(String name, String month, String day, String length, String size1, String num1, String size2, String num2, String size3, String num3) throws ParseException {
		if(bookNames.contains(name)) {
			return "Rejected";
		}
		Date month2 = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month);
		Calendar cal = Calendar.getInstance();
		cal.setTime(month2);
		int monthInt = cal.get(Calendar.MONTH)+1;
		LocalDate startDate = LocalDate.of(2018, monthInt, Integer.parseInt(day));
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
				ArrayList<Order> returnOrderList = new ArrayList<Order>();
				for(Room room : hotel.getRooms())
					for(Order order: orderList)
						if(order.getRoomNumber().equals(room.getRoomNumber()))
							returnOrderList.add(order);
				String output = name + " " + hotel.getName();
				for(Order order : returnOrderList)
					output += " " + order.getRoomNumber();
				this.bookNames.add(name);
				return output;
			}
			else {
				hotelFit = true;
				for(Room room : hotel.getRooms()) {
					boolean release = true;
					while(release)
						release = (room.releaseOrder(name) != null);
				}
				orderList.clear();
			}
		}
		return "Rejected";
	}
	
	public String changeBooking(String name, String month, String day, String length, String size1, String num1, String size2, String num2, String size3, String num3) throws ParseException {
		if(!bookNames.contains(name)) {
			return "Rejected";
		}
		ArrayList<Order> originalOrders = new ArrayList<Order>();		
		for(Hotel hotel : hotelList) {
			for(Room room : hotel.getRooms()) {
				boolean release = true;
				while(release) {
					Order tempOrder = room.releaseOrder(name);
					if(tempOrder != null) 
						originalOrders.add(tempOrder);
					 else 
						release = false;
				}
			}
		}
		bookNames.remove(name);
		String result = this.bookRoom(name, month, day, length, size1, num1, size2, num2, size3, num3);
		if(result.equals("Rejected")) {
			for(Order order : originalOrders) {
				for(Hotel hotel: hotelList) {
					if(hotel.getName() == order.getHotelName()) {
						for(Room room : hotel.getRooms()) {
							if(room.getRoomNumber() == order.getRoomNumber())
								room.addOrder(order);
						}
					}
				}
			}
			return result;
		} else {
			return result;
		}
	}
	
	public String cancelBooking(String name) {
		if(bookNames.contains(name)) {
			for(Hotel hotel : hotelList) {
				for(Room room : hotel.getRooms()) {
					boolean release = true;
					while(release) {
						Order tempOrder = room.releaseOrder(name);
						if(tempOrder == null){
							release = false;
						}
					}
				}
			}
			return name;
		} else
			return "Rejected";
		
	}
	
	public void printHotel(String hotelName) {
		for(Hotel hotel: hotelList)
			if(hotel.getName().equals(hotelName))
				hotel.print();
	}

}
