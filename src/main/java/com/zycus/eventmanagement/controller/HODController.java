package com.zycus.eventmanagement.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zycus.eventmanagement.EventManagementApplication;
import com.zycus.eventmanagement.entities.Employee;
import com.zycus.eventmanagement.entities.Event;
import com.zycus.eventmanagement.entities.Post;
import com.zycus.eventmanagement.entities.Stats;
import com.zycus.eventmanagement.repository.EmployeeRepository;
import com.zycus.eventmanagement.repository.EventRepository;
import com.zycus.eventmanagement.repository.PostRepository;
import com.zycus.eventmanagement.repository.StatsRepository;

@RestController
@RequestMapping(path = "/dashboad3")
@Transactional
public class HODController {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private StatsRepository statsRepository;
	
	@Autowired
	private EventManagementApplication EventManagementApplication;

	// get events which hod has to registered this event
	@GetMapping("/stats/{username}")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Event> getHodEvent(@Valid @PathVariable String username) {
		
		EventManagementApplication.valiadateEventDB();
		
		Employee e = employeeRepository.findByUsername(username);
		List<Event> finalEventList = new ArrayList<Event>();
		// user-name present is DB
		if (e != null) {
			// check employee is head or not
			if (e.getPost().getLevel() == 2) {
				// getting all event from DB
				List<Event> eventList = eventRepository.findAll();
				// iterate each event
				for (Event event : eventList) {
					// only employee can see and not HR
					if (e.getRole().getRole().equalsIgnoreCase("hod")
							|| e.getRole().getRole().equalsIgnoreCase("admin")) {
						// department must be same
						if (e.getPost().getDepartment().equalsIgnoreCase(event.getPost().getDepartment())) {
							// status must be published
							if (event.getStatus().equalsIgnoreCase("publish")) {
								// action must be true, open, 1
								if (event.getAction()) {
									// control-panel must be less to entry-count
									if (event.getCountOfPanel() > event.getEntryCount()) {
										// check date string of event and current date
										DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
										// event date
										LocalDate escalationDate = LocalDate.parse(event.getEscalationTime(),
												formatter);
										// now current date
										LocalDate currentdate = LocalDate.now();
										formatter.format(currentdate);
										// compare both date
										Period diff = Period.between(currentdate, escalationDate);
										int diffinDate = diff.getDays();
										if (diffinDate == 0) {
											finalEventList.add(event);
										} else {
											System.out.println("time is there to enroll");
										}
									} else {
										System.out.println("already full");
									}
								} else {
									System.out.println("action is false that it is closed");
								}
							} else {
								System.out.println("draft event will not be shown");
							}
						} else {
							System.out.println("Department is different");
						}
					} else {
						System.out.println("HR username");
					}
				}
			} else {
				System.out.println("not head");
			}
		} else {
			// username is not there in DB
			System.out.println("1. Username does not exist");
		}
		return finalEventList;
	}

	// get employee for drop down 
	@PostMapping("/employee/{eventID}")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Employee> getEmployee(@Valid @RequestBody Post post1, @Valid @PathVariable Long eventID) {
		
		EventManagementApplication.valiadateEventDB();
		
		Long postID = postRepository.findID(post1.getDesignation(), post1.getDepartment());
		Event event = eventRepository.findByEventID(eventID);
		Post post = postRepository.findAllByPsotID(postID);
		List<Employee> finalEmployeeList = new ArrayList<Employee>();
		List<Employee> listEmployee = employeeRepository.findAll();
		for(Employee e: listEmployee) {
			Boolean presentFlag = checkEmployeeEnroll(e, event);
			if(!presentFlag) {
				// not enroll for that event
				if( (e.getPost().getDepartment()).equals(post.getDepartment()) ){
					if(post1.getLevel() > e.getPost().getLevel()) {
						finalEmployeeList.add(e);
					}
				}
			}else {
				// enroll for the event
				System.out.println("already enroll");
			}
			
		}
		return finalEmployeeList;
	}

	
	public Boolean checkEmployeeEnroll( Employee employee, Event event) {
		//System.out.println(statsRepository.existsByEventAndEmployee(event,employee));
		return statsRepository.existsByEventAndEmployee(event,employee);
	}
	
	// update HOD register / enrol employee 
	@PostMapping("/stats")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<String> updateStats(@Valid @RequestBody String[] authcust) {
		
		EventManagementApplication.valiadateEventDB();
		
		List<String> message = new ArrayList<String>();
		List<Long> employeeID = new ArrayList<Long>();
		Long eventID = Long.parseLong(authcust[0]);
		int count1 = Integer.parseInt(authcust[1]);
		int count = count1 + 2;
		for (int i=2; i < count; i++) {
			employeeID.add(Long.parseLong(authcust[i]));
		}
		
		Event event = eventRepository.findByEventID(eventID); // event object----------(1)
		// list of employee object
		List<Employee> employee1 = new ArrayList<Employee>();
		Employee employee = null;
		for (int i=0; i < employeeID.size(); i++) {
			employee1.add(employeeRepository.findByEmployeeID(employeeID.get(i)));
		}
		
		
		// check present in stats table or not
		for(int i=0; i<employee1.size(); i++) {	
			employee = employee1.get(i); // employee object----------(2)
			if(!statsRepository.existsByEventAndEmployee(event,employee)) {
				// not present add on stat table
				if(!statsRepository.existsByEmployeeAndDate(employee, event.getEventDate())) {
					// date check
					Stats stats = new Stats();
					stats.setEvent(event);
					stats.setEmployee(employee);
					stats.setFlag(true); // flag object----------(3)
					stats.setDate(event.getEventDate()); // date object----------(4)
					statsRepository.save(stats);
					// update event count also;
					event.setEntryCount(event.getEntryCount() + 1);
					String msg = employee.getUsername() + " has enroll Sucessfully.";
					message.add(msg);
				} else{
					System.out.println("this employee is register for that particular date");
					String msg = employee.getUsername() + " is already registered for this "+ event.getEventDate();
					message.add(msg);
				}
			} else {
				// present on stat table
				String msg = employee.getUsername() + " is already registered for this "+ event.getEvent_name();
				message.add(msg);
				System.out.println(" this employee present in database");
			}
		}
		return message;	
	}

}
