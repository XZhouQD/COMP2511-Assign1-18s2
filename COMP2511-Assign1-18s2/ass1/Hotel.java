package ass1;

import java.time.LocalDate;
import java.util.ArrayList;

public class Hotel {

	private String name;
	private ArrayList<Room> rooms;
	
	/*
	 * initiate a new hotel
	 * @param name hotel name
	 */
	public Hotel(String name) {
		this.name = name;
		this.rooms = new ArrayList<Room>();
	}
	/*
	 * this function add a room to a hotel
	 * @param roomNumber the room number of new room
	 * @param capacity the capacity of room
	 * @return boolean if room already exist
	 */
	public boolean addRoom(String roomNumber, Capacity capacity) {
		boolean hasRoom = false;
		for(Room room : rooms)
			if(room.getRoomNumber().equals(roomNumber))
				hasRoom = true;
		if(hasRoom)
			return false;
		else {
			Room newRoom = new Room(roomNumber, this.name, capacity);
			rooms.add(newRoom);
			return true;
		}
	}

	/*
	 * get name of hotel
	 * @return name of hotel
	 */
	public String getName() {
		return name;
	}

	/*
	 * get a room list of hotel
	 * @return rooms of hotel
	 */
	public ArrayList<Room> getRooms() {
		return rooms;
	}
	
	/*
	 * try new booking in this hotel
	 * @param name customer name
	 * @param capacity capacity of room needed
	 * @param startDate order starting date
	 * @param length how many nights
	 * @return order that fit the requirement or null
	 */
	public Order tryBooking(String name, Capacity capacity, LocalDate startDate, int length) {
		if(capacity == null || startDate == null || length == 0) //if there is null arguments, return a default empty order
			return new Order("empty", "-1", "-1", LocalDate.parse("1980-01-01"), 1);
		for(Room room : rooms) {
			Order temp = room.makeOrder(name, startDate, capacity, length); //else try booking on rooms
			if(temp!=null) return temp;
		}
		//no room available return null
		return null;
	}
	
	/*
	 * add a order to room
	 * @param order the order that will be added
	 */
	public void addBooking(Order order) {
		for(Room room : rooms) {
			if(room.getRoomNumber() == order.getRoomNumber()) { //find proper room and add the order
				room.addOrder(order);
				return;
			}
		}
	}
	
	/*
	 * print out status of this hotel
	 */
	public void print() {
		for(Room room: rooms)
			System.out.println(this.name +" " + room.toString());
	}
}
