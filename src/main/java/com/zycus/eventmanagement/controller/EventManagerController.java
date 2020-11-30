package com.zycus.eventmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zycus.eventmanagement.EventManagementApplication;
import com.zycus.eventmanagement.entities.Employee;
import com.zycus.eventmanagement.entities.Event;
import com.zycus.eventmanagement.repository.EmployeeRepository;
import com.zycus.eventmanagement.repository.EventRepository;

@RestController
@RequestMapping(path = "/dashboad1")
@Transactional
public class EventManagerController {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EventManagementApplication EventManagementApplication;

	@GetMapping("/test")
	@CrossOrigin(origins = "http://localhost:4200")
	public String testEventManagerContoller() {
		return "EventManager dashboard Controller is working fine";
	}

	// create EVENT if EventID is not there
	// and update if EventID is there and it is draft status
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/event/{username}")
	public Event postEvent(@Valid @RequestBody Event event, @PathVariable String username) {
		// eventID, event_name,countOfPanel, skillSet, eventDate, escalationTime,
		// status, entryCount, POST
		// check username ROLE
		EventManagementApplication.valiadateEventDB();

		Employee e = employeeRepository.findByUsername(username);
		if (e == null) {
			// employee not present in DB
		} else {
			// employee present in DB
			// check employee role is HR
			String roletemp = employeeRepository.findRole(username);
			if (roletemp.equalsIgnoreCase("hr")) {
				// Role is HR allow them
				if (event.getEventID() == null) {
					// insert
					event.setEntryCount(0);
					// false close & true is open
					event.setAction(true);
					return eventRepository.save(event);
				} else {
					Event eventTemp = eventRepository.getOne(event.getEventID());
					if (eventTemp != null) {
						// update
						String status = eventTemp.getStatus();
						status = status.toLowerCase();
						if (status.equals("draft")) {
							eventTemp.setEventID(event.getEventID());
							eventTemp.setEvent_name(event.getEvent_name());
							eventTemp.setCountOfPanel(event.getCountOfPanel());
							eventTemp.setSkillSet(event.getSkillSet());
							eventTemp.setEventDate(event.getEventDate());
							eventTemp.setEscalationTime(event.getEscalationTime());
							eventTemp.setStatus(event.getStatus());
							if (event.getCountOfPanel() >= event.getEntryCount()) {
								eventTemp.setEntryCount(event.getEntryCount());
							} else {
								eventTemp.setEntryCount(event.getCountOfPanel());
							}
							eventTemp.setPost(event.getPost());
							if (event.getCountOfPanel() <= event.getEntryCount()) {
								// action is close: false: 0
								eventTemp.setAction(false);
							}
							return eventRepository.save(eventTemp);
						}
					}
					return null;
				}
			} else {
				// not HR
				System.out.println("Role is not HR");
				return null;
			}
		}
		return null;

	}

	// get all event by user-name authenticate, status = draft and filter
	@GetMapping("/event/{status}/{username}/{filter}")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Event> getFilterEvent(
			@Valid @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "1000") Integer size,
			@RequestParam(value = "filterOn", required = false) String filterOn, @PathVariable("status") String status,
			@PathVariable("username") String username, @PathVariable("filter") String filter) {

		EventManagementApplication.valiadateEventDB();
		List<Event> listfilter = new ArrayList<Event>();
		Employee e = employeeRepository.findByUsername(username); // check username ROLE
		if (e == null) { // employee not present in DB
			System.out.println("username is invalid");
		} else { // employee present in DB
			// check employee role is HR
			String roletemp = employeeRepository.findRole(username);
			if (roletemp.equalsIgnoreCase("hr")) {
				// Role is HR allow them

				// perform filter
				Pageable pageable = PageRequest.of(page, size);
				if (status.equalsIgnoreCase("publish")) {
					// publish
					if (filter.equals("eventDate")) {
						// filter by event date, compulsory publish
						System.out.println("filter by event date, compulsory publish");
						listfilter = eventRepository.ffilterDateUserStatus(filterOn, status, pageable);
					} else if (filter.equals("department")) {
						// filter by department, compulsory publish
						System.out.println("filter by department, compulsory publish");
						listfilter = eventRepository.ffilterDepartUserStatus(filterOn, status, pageable);
					} else {
						// no filter, compulsory publish
						System.out.println("no filter, compulsory publish");
						listfilter = eventRepository.fNoFilterUserStatus(status, pageable);
					}
				} else {
					// draft
					if (filter.equals("eventDate")) {
						// filter by event date, user-name, status
						System.out.println("filter by event date, user-name, status");
						listfilter = eventRepository.ffilterDateUserStatus(filterOn, status, username, pageable);
					} else if (filter.equals("department")) {
						// filter by department, user-name, status
						System.out.println("filter by department, user-name, status");
						listfilter = eventRepository.ffilterDepartUserStatus(filterOn, status, username, pageable);
					} else {
						// user-name, status, no filter
						System.out.println("user-name, status, no filter");
						listfilter = eventRepository.fNoFilterUserStatus(status, username, pageable);
					}
				}
			} else {
				System.out.println("role is not hr");
			}
		}
		return listfilter;
	}

	// delete event provided eventID
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/event/delete/{eventID}") // passing arguments
	public void deleteOneEvent(@PathVariable("eventID") long eventID) {
		eventRepository.deleteById(eventID);
	}

}
