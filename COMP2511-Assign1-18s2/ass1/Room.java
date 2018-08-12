package ass1;

import java.time.LocalDate;
import java.util.ArrayList;

public class Room {

	private String roomNumber;
	private Capacity capacity;
	private ArrayList<Order> orders;
	
	public Room(String roomNumber, Capacity capacity) {
		super();
		this.roomNumber = roomNumber;
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
	
	public Order makeOrder(String name, LocalDate startDate, int length) {
		boolean available = true;
		for(Order order : orders) {
			if (startDate.isAfter(order.getEndDate()) || startDate.plusDays(length-1).isBefore(order.getStartDate())) {
			} else {
				available = false;
			}
		}
		if(available) {
			return new Order(name, startDate, length);
		}
		return null;
	}
	
	public void addOrder(Order order) {
		orders.add(order);
	}
	
	public Order releaseOrder(String name) {
		for(Order order: orders) {
			if(order.getName() == name) {
				Order toRelease = order;
				orders.remove(order);
				return toRelease;
			}
		}
		return null;
	}
	
}
