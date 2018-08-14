package ass1;

import java.time.LocalDate;
import java.util.ArrayList;

public class Hotel {

	private String name;
	private ArrayList<Room> rooms;
	
	public Hotel(String name) {
		this.name = name;
		this.rooms = new ArrayList<Room>();
	}
	
	public boolean addRoom(String roomNumber, Capacity capacity) {
		boolean hasRoom = false;
		for(Room room : rooms)
			if(room.getRoomNumber() == roomNumber)
				hasRoom = true;
		if(hasRoom)
			return false;
		else {
			rooms.add(new Room(roomNumber, capacity));
			return true;
		}
	}

	public String getName() {
		return name;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}
	
	public Order tryBooking(String name, Capacity capacity, LocalDate startDate, int length) {
		if(capacity == null || startDate == null || length == 0)
			return new Order("empty", "-1", LocalDate.parse("1980-01-01"), 1);
		for(Room room : rooms) {
			Order temp = room.makeOrder(name, startDate, capacity, length);
			if(temp!=null) return temp;
		}
		return null;
	}
	
	public void addBooking(Order order) {
		for(Room room : rooms) {
			if(room.getRoomNumber() == order.getRoomNumber()) {
				room.addOrder(order);
				return;
			}
		}
	}
	
	public void print() {
		for(Room room: rooms)
			System.out.println(name + " " + room.toString());
	}
}
