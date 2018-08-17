package ass1;

import java.time.LocalDate;
import java.util.ArrayList;

public class Room {

	private String roomNumber;
	private String hotelName;
	private Capacity capacity;
	private ArrayList<Order> orders;
	
	/*
	 * initiate a new room
	 * @param roomNumber the roomNumber of room
	 * @param hotelName the name of hotel of this room
	 * @param capacity the capacity of this room
	 */
	public Room(String roomNumber, String hotelName, Capacity capacity) {
		super();
		this.roomNumber = roomNumber;
		this.hotelName = hotelName;
		this.capacity = capacity;
		this.orders = new ArrayList<Order>();
	}

	/*
	 * get room number of the room
	 * @return roomNumber of room
	 */
	public String getRoomNumber() {
		return roomNumber;
	}

	/*
	 * get capacity of room
	 * @return capacity of room
	 */
	public Capacity getCapacity() {
		return capacity;
	}

	/*
	 * get order list of the room
	 * @return orders of room
	 */
	public ArrayList<Order> getOrders() {
		return orders;
	}
	
	/*
	 * try make a order on this room
	 * @param name customer name
	 * @param startDate order starting date
	 * @param capacity capacity of room needed
	 * @param length how many nights
	 * @return order that fit the requirement or null
	 */
	public Order makeOrder(String name, LocalDate startDate, Capacity capacity, int length) {
		boolean available = true;
		for(Order order : orders) { //check if the required dates is available
			if (startDate.isAfter(order.getEndDate()) || startDate.plusDays(length-1).isBefore(order.getStartDate())) {
				available = true;
			} else {
				available = false;
				break;
			}
		}
		if(available && this.capacity.equals(capacity)) { //if date available and capacity match
			Order order = new Order(name, this.hotelName, this.roomNumber, startDate, length); //make new order
			addOrder(order); //add to order list of room
			return order; //return it
		}
		return null; //cannot make order, return null
	}
	
	/*
	 * this fucntion manually add an order in order of date to order list
	 * @param order the order gonna be added
	 */
	public void addOrder(Order order) {
		for (int i = 0; i < orders.size(); i++) { //find if order add at the middle of list
			if (orders.get(i).getStartDate().isBefore(order.getStartDate()))
				continue;
			else {
				orders.add(i, order);
				return;
			}
		}
		orders.add(order); //not at the middle, append at the end
	}
	
	/*
	 * this function release a order of specific customer
	 * @param name customer name
	 * @return order an order belongs to the specific customer
	 */
	public Order releaseOrder(String name) {
		for(Order order: orders) {
			if(order.getName().equals(name)) { //if customer match
				Order toRelease = order;
				orders.remove(toRelease); //remove from order list
				return toRelease; //return it
			}
		}
		return null; //no matches, return null
	}
	
	public String toString() {
		String str = roomNumber;
		for(Order order: orders)
			str += " " + order.toString(); //attach order status to roomNumber
		return str;
	}
	
}
