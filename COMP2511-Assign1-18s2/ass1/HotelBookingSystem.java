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
	
	/*
	 * Initialise hotel booking system, construct list for hotel and booking names, create a default
	 * empty order for compare using
	 */
	public HotelBookingSystem() {
		this.hotelList = new ArrayList<Hotel>();
		this.bookNames = new ArrayList<String>();
		this.emptyOrder = new Order("empty", "-1", "-1", LocalDate.parse("1980-01-01"), 1);
	}
	/*
	 * Main function, keep a scanner until input file finish
	 * need a argument for input file name
	 * @param inputFile a input file for system
	 */
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
	
	/*
	 * get string from list by index and handle out of bounds
	 * @param arguments a list of arguments for functions
	 * @param index the index of specific argument
	 * @return the argument at specific index
	 */
	public String getStringByIndex(String[] arguments, int index) {
		try {
			String toReturn = arguments[index];
			return toReturn;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	/*
	 * distribute input to relavent functions
	 * @param input one line of input
	 */
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
	
	/*
	 * This function construct new room and new hotel(if not exist)
	 * @param hotelName the name of hotel (new or existing)
	 * @param roomNumber the roomnumber of new room
	 * @param capacity the capacity of new room
	 */
	public void addHotelRoom(String hotelName, String roomNumber, String capacity) {
		boolean hotelExist = false;
		boolean roomExist = false;
		Capacity capa = Capacity.values()[Integer.parseInt(capacity)-1];
		for(Hotel hotel: hotelList) {
			if(hotel.getName().equals(hotelName)) {
				hotelExist = true;
				roomExist = hotel.addRoom(roomNumber, capa);
				break;
			}
		}
		if(!hotelExist) {
			Hotel hotel = new Hotel(hotelName);
			roomExist = hotel.addRoom(roomNumber, capa);
			hotelList.add(hotel);
		}
		if(!roomExist) {
			System.out.println("Add Room rejected");
		}
	}
	
	/*
	 * This function will try book rooms for specific customer
	 * @param name customer name
	 * @param month booking start month
	 * @param day booking start date
	 * @param length how many nights for booking
	 * @param size1 room capacity 1
	 * @param num1 the number of rooms with capacity 1
	 * @param size2 room capacity 2, can be null
	 * @param num2 the number of rooms with capacity 2, can be null
	 * @param size3 room capacity 3, can be null
	 * @param num3 the number of rooms with capacity 3, can be null
	 * @return the string of room booking result
	 */
	public String bookRoom(String name, String month, String day, String length, String size1, String num1, String size2, String num2, String size3, String num3) throws ParseException {
		// if same name booking already exist, reject
		if(bookNames.contains(name)) {
			return "rejected";
		}
		//process with arugments to proper type
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
		//make an empty orderList and start to try booking
		ArrayList<Order> orderList = new ArrayList<Order>();
		//try through each hotel, order must be completed in one hotel or fully rejected
		for(Hotel hotel : hotelList) {
			boolean hotelFit = true;
			//try book room type 1
			for(int i = 0; i < bookNum1; i++) {
				Order newOrder = hotel.tryBooking(name, capa1, startDate, bookLength);
				if(newOrder == null) hotelFit = false; //null means order cannot be complete
				else if(!newOrder.equals(emptyOrder)) 
					orderList.add(newOrder);
			}
			//try book room type 2
			for(int i = 0; i < bookNum2; i++) {
				Order newOrder = hotel.tryBooking(name, capa2, startDate, bookLength);
				if(newOrder == null) hotelFit = false; 
				else if(!newOrder.equals(emptyOrder)) //emptyOrder means arguments contains null, use for handle null arguments for type 2 and 3
					orderList.add(newOrder);
			}
			//try book room type 3
			for(int i = 0; i < bookNum3; i++) {
				Order newOrder = hotel.tryBooking(name, capa3, startDate, bookLength);
				if(newOrder == null) hotelFit = false;
				else if(!newOrder.equals(emptyOrder)) 
						orderList.add(newOrder);
			}
			if(hotelFit == true) { //if the order can be completed in one hotel
				ArrayList<Order> returnOrderList = new ArrayList<Order>();
				for(Room room : hotel.getRooms())
					for(Order order: orderList)
						if(order.getRoomNumber().equals(room.getRoomNumber()))
							returnOrderList.add(order);
							//add order pending in orderList to returnOrderList in order of rooms
				String output = name + " " + hotel.getName();
				for(Order order : returnOrderList)
					output += " " + order.getRoomNumber();
				this.bookNames.add(name);
				//construct booking result string, add customer to booking list and return
				return output;
			}
			else { //order cannot be completed in this hotel
				hotelFit = true;
				for(Room room : hotel.getRooms()) { //cancel all previous orders in this hotel for the customer
					boolean release = true;
					while(release)
						release = (room.releaseOrder(name) != null);
				}
				orderList.clear();
			}
		}
		//finish loop and no return means order cannot be completed, reject
		return "rejected";
	}
	
	/*
	 * this function is used for change a customer's booking
	 * @param name customer name
	 * @param month booking start month
	 * @param day booking start date
	 * @param length how many nights for booking
	 * @param size1 room capacity 1
	 * @param num1 the number of rooms with capacity 1
	 * @param size2 room capacity 2, can be null
	 * @param num2 the number of rooms with capacity 2, can be null
	 * @param size3 room capacity 3, can be null
	 * @param num3 the number of rooms with capacity 3, can be null
	 * @return the string of room changing result
	 */
	public String changeBooking(String name, String month, String day, String length, String size1, String num1, String size2, String num2, String size3, String num3) throws ParseException {
		//if customer does not have any booking, reject
		if(!bookNames.contains(name)) {
			return "rejected";
		}
		//cancel all previous order of the customer and place it in a temporary storing list
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
		//try a booking of new requirement
		String result = this.bookRoom(name, month, day, length, size1, num1, size2, num2, size3, num3);
		if(result.equals("rejected")) { //if booking rejected, 
			for(Order order : originalOrders) { //put all previous orders back
				for(Hotel hotel: hotelList) {
					if(hotel.getName() == order.getHotelName()) {
						for(Room room : hotel.getRooms()) {
							if(room.getRoomNumber() == order.getRoomNumber())
								room.addOrder(order);
						}
					}
				}
			}
			bookNames.add(name);
			return result; //and return rejected
		} else { //else simply return new booking result
			return result;
		}
	}
	/*
	 * this function is used for cancel booking of a customer
	 * @param name customer name
	 * @return cancel result string
	 */
	public String cancelBooking(String name) {
		if(bookNames.contains(name)) { //if customer have previous booking
			for(Hotel hotel : hotelList) { //delete all his orders in all hotel and rooms
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
		} else //else reject as no booking
			return "rejected";
		
	}
	
	/*
	 * This function print all status of a hotel
	 * @param hotelName the name of hotel to be print
	 */
	public void printHotel(String hotelName) {
		for(Hotel hotel: hotelList)
			if(hotel.getName().equals(hotelName))
				hotel.print();
	}

}
