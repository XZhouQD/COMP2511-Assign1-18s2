package ass1;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class Order {

	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private int length;
	
	public Order(String name, LocalDate startDate, int length) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.endDate = this.startDate.plusDays(length-1);
		this.length = length;
	}
	
	public String getName() {
		return this.name;
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
}
