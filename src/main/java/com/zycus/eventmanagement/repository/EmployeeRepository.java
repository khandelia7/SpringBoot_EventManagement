package com.zycus.eventmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zycus.eventmanagement.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Employee findByUsername(String username);

	public boolean existsByUsernameAndPassword(String username, String password);

	@Query(value = "SELECT B.Role FROM Employee A inner join Role B ON A.role_roleid = B.roleID where A.username = :username", nativeQuery = true)
	String findRole(String username);

	public Employee findByEmployeeID(Long employeeID);

}
