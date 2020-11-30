package com.zycus.eventmanagement.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity // java persistence for making table in DB
@Table(name = "Employee") // we can change the name of the table in DB
public class Employee {

	@Id // Important
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long employeeID; // Always keep a class and not primitive data-types

	@Column(name = "username", unique=true) // change the name of column if u need
	//@NotBlank(message = "username is mandatory")
	@Size(min=2, max=15)
	private String username;

	@Column(unique=true) 
	@NotBlank(message = "password is mandatory")
	@Size(min=2, max=15)
	private String password;

	@OneToOne
	private Role role;

	@OneToOne
	private Post post;

	public Long getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(Long employeeID) {
		this.employeeID = employeeID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeID + ", username=" + username + ", password=" + password + ", role="
				+ role + ", post=" + post + "]";
	}
}
