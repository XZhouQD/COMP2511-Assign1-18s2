package ass1;

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
	
	public void printHotel() {
		for(Room room: rooms)
			System.out.println(name + " " + room.toString());
	}
}
