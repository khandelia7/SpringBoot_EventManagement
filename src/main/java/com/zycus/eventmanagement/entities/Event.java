package com.zycus.eventmanagement.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity // java persistence for making table in DB
@Table(name = "Event") // we can change the name of the table in DB
public class Event {

	@Id // Important
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long eventID;

	@Column(unique = true)
	@NotBlank(message = "eventName is mandatory")
	@Size(min = 5, max = 30)
	private String eventName;

	private int countOfPanel;

	@NotBlank(message = "skillSet is mandatory")
	@Size(min = 3, max = 40)
	private String skillSet;

	@NotBlank(message = "eventDate is mandatory")
	private String eventDate;

	@NotBlank(message = "escalationTime is mandatory")
	private String escalationTime;

	@NotBlank(message = "status is mandatory")
	private String status;

	private int entryCount;

	@NotBlank(message = "madeBy is mandatory")
	private String made;

	@ManyToOne(cascade = { CascadeType.ALL })
	private Post post;

	// open or close
	private Boolean action;

	public Long getEventID() {
		return eventID;
	}

	public void setEventID(Long eventID) {
		this.eventID = eventID;
	}

	public String getEvent_name() {
		return eventName;
	}

	public void setEvent_name(String event_name) {
		this.eventName = event_name;
	}

	public int getCountOfPanel() {
		return countOfPanel;
	}

	public void setCountOfPanel(int countOfPanel) {
		this.countOfPanel = countOfPanel;
	}

	public String getSkillSet() {
		return skillSet;
	}

	public void setSkillSet(String skillSet) {
		this.skillSet = skillSet;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getEscalationTime() {
		return escalationTime;
	}

	public void setEscalationTime(String escalationTime) {
		this.escalationTime = escalationTime;
	}

	public int getEntryCount() {
		return entryCount;
	}

	public void setEntryCount(int entryCount) {
		this.entryCount = entryCount;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getAction() {
		return action;
	}

	public void setAction(Boolean action) {
		this.action = action;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getMadeEvent() {
		return made;
	}

	public void setMadeEvent(String madeEvent) {
		this.made = madeEvent;
	}

	@Override
	public String toString() {
		return "Event [eventID=" + eventID + ", eventName=" + eventName + ", countOfPanel=" + countOfPanel
				+ ", skillSet=" + skillSet + ", eventDate=" + eventDate + ", escalationTime=" + escalationTime
				+ ", status=" + status + ", entryCount=" + entryCount + ", made=" + made + ", post=" + post
				+ ", action=" + action + "]";
	}
}
