package ass1;

import java.time.LocalDate;

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
}
