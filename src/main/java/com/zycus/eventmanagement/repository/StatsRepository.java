package com.zycus.eventmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zycus.eventmanagement.entities.Employee;
import com.zycus.eventmanagement.entities.Event;
import com.zycus.eventmanagement.entities.Stats;

public interface StatsRepository extends JpaRepository<Stats, Long> {

	List<Stats> findAllByEmployee(Employee employee);

	List<Stats> findAllByEvent(Event event);

	public Boolean existsByEventAndEmployee(Event event, Employee employee);

	public Boolean existsByEmployeeAndDate(Employee employee, String date);

//	// SELECT * FROM EventManagement.employee e 
//	LEFT JOIN (SELECT * FROM EventManagement.stats where date='27/11/2020') r
//	on e.employeeid = r.employee_employeeid 
//	where r.employee_employeeid IS null;

}
