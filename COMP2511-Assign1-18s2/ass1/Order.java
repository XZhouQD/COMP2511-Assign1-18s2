package ass1;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class Order {

	private String name;
	private String hotelName;
	private String roomNumber;
	private LocalDate startDate;
	private LocalDate endDate;
	private int length;
	
	public Order(String name, String hotelName, String roomNumber, LocalDate startDate, int length) {
		super();
		this.name = name;
		this.hotelName = hotelName;
		this.roomNumber = roomNumber;
		this.startDate = startDate;
		this.endDate = this.startDate.plusDays(length-1);
		this.length = length;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getHotelName() {
		return this.hotelName;
	}
	
	public String getRoomNumber() {
		return this.roomNumber;
	}
	
	public LocalDate getStartDate() {
		return this.startDate;
	}
	
	public LocalDate getEndDate() {
		return this.endDate;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public String toString() {
		return startDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + startDate.getDayOfMonth() + " " + length;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (length != other.length)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (roomNumber == null) {
			if (other.roomNumber != null)
				return false;
		} else if (!roomNumber.equals(other.roomNumber))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}
	
}
