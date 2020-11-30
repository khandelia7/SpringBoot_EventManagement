package com.zycus.eventmanagement.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity // java persistence for making table in DB
@Table(name = "Role") // we can change the name of the table in DB
public class Role {

	@Id
	private Long roleID;

	@Column(unique=true) 
	@NotBlank(message = "Role is mandatory")
	private String Role;

	public Long getRoleID() {
		return roleID;
	}

	public void setRoleID(Long roleID) {
		this.roleID = roleID;
	}

	public String getRole() {
		return Role;
	}

	public void setRole(String role) {
		Role = role;
	}

	@Override
	public String toString() {
		return "Role [roleID=" + roleID + ", Role=" + Role + "]";
	}

}
