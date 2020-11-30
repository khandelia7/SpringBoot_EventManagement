package com.zycus.eventmanagement.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zycus.eventmanagement.EventManagementApplication;
import com.zycus.eventmanagement.entities.Employee;
import com.zycus.eventmanagement.repository.EmployeeRepository;

@RestController
@Transactional
public class LoginController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private EventManagementApplication EventManagementApplication;

	// check login provide correct user-name and password
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("login")
	public Employee showLoginPage(@Valid @RequestBody String[] authcust, Employee employee) {
		
		EventManagementApplication.valiadateEventDB();
		
		String username = authcust[0];
		String password = authcust[1];
		Boolean b1 = employeeRepository.existsByUsernameAndPassword(username, password);
		Employee e = null;
		if (b1) {
			String role = employeeRepository.findRole(username);
			e = employeeRepository.findByUsername(username);
			System.out.println(role);
			return e;
		} else {
			System.out.println("invalid");
			return e;
		}
	}

	// get details of employee provide user-name
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("login/{username}")
	public Employee showEmployeePage(@Valid @PathVariable String username, Employee employee) {
		
		EventManagementApplication.valiadateEventDB();
		
		Employee e = employeeRepository.findByUsername(username);
		if(e!=null) {
			return e;
		} else {
			System.out.println("invalid");
			return e;
		}
	}

}
