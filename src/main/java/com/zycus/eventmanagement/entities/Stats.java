package com.zycus.eventmanagement.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity // java persistence for making table in DB
@Table(name = "Stats") // we can change the name of the table in DB
public class Stats {

	@Id // Important
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long statsID; // Always keep a class and not primitive data-types

	@OneToOne
	private Employee employee;

	@OneToOne
	private Event event;

	@NotBlank(message = "date is mandatory")
	private String date;

	private Boolean flag;

	public Long getStatsID() {
		return statsID;
	}

	public void setStatsID(Long statsID) {
		this.statsID = statsID;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "Stats [statsID=" + statsID + ", employee=" + employee + ", event=" + event + ", Date=" + date
				+ ", flag=" + flag + "]";
	}

}
