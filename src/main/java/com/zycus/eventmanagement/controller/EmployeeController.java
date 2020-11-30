package com.zycus.eventmanagement.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zycus.eventmanagement.entities.Employee;
import com.zycus.eventmanagement.repository.EmployeeRepository;

@RestController
@RequestMapping(path = "/employee")
@Transactional
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/test")
	public String testEmployeeRepository() {
		return "Employee Repository is working fine";
	}

	// post (add) all Post
	@PostMapping("/")
	public List<Employee> addAllEmployee(@RequestBody List<Employee> employee) {
		return employeeRepository.saveAll(employee);
	}

	@GetMapping("/")
	public List<Employee> showALLEmployee() {
		return employeeRepository.findAll();
	}

}
