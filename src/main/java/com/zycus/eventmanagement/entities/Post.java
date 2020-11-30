package com.zycus.eventmanagement.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity // java persistence for making table in DB
@Table(name = "Post") // we can change the name of the table in DB
public class Post {
	
	@Id //Important
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long psotID; // Always keep a class and not primitive data-types

	@NotBlank(message = "designation is mandatory")
	private String designation;

	@NotBlank(message = "department is mandatory")
	private String department;
	
	private int level;
	
	public Long getPsotID() {
		return psotID;
	}

	public void setPsotID(Long psotID) {
		this.psotID = psotID;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Post [designation=" + designation + ", department=" + department + ", level=" + level + "]";
	}

}
