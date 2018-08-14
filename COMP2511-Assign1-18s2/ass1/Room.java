package ass1;

import java.time.LocalDate;
import java.util.ArrayList;

public class Room {

	private String roomNumber;
	private String hotelName;
	private Capacity capacity;
	private ArrayList<Order> orders;
	
	public Room(String roomNumber, String hotelName, Capacity capacity) {
		super();
		this.roomNumber = roomNumber;
		this.hotelName = hotelName;
		this.capacity = capacity;
		this.orders = new ArrayList<Order>();
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public Capacity getCapacity() {
		return capacity;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}
	
	public Order makeOrder(String name, LocalDate startDate, Capacity capacity, int length) {
		boolean available = true;
		for(Order order : orders) {
			if (startDate.isAfter(order.getEndDate()) || startDate.plusDays(length-1).isBefore(order.getStartDate())) {
				available = true;
			} else {
				available = false;
			}
		}
		if(available && this.capacity.equals(capacity)) {
			Order order = new Order(name, this.hotelName, this.roomNumber, startDate, length);
			addOrder(order);
			return order;
		}
		return null;
	}
	
	public void addOrder(Order order) {
		orders.add(order);
	}
	
	public Order releaseOrder(String name) {
		for(Order order: orders) {
			if(order.getName().equals(name)) {
				Order toRelease = order;
				orders.remove(toRelease);
				return toRelease;
			}
		}
		return null;
	}
	
	public String toString() {
		String str = roomNumber;
		for(Order order: orders)
			str += " " + order.toString();
		return str;
	}
	
}
