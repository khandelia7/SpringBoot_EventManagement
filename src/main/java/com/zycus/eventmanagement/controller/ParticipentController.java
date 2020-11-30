package com.zycus.eventmanagement.controller;

import java.text.ParseException;
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
import com.zycus.eventmanagement.entities.Stats;
import com.zycus.eventmanagement.repository.EmployeeRepository;
import com.zycus.eventmanagement.repository.EventRepository;
import com.zycus.eventmanagement.repository.StatsRepository;

@RestController
@RequestMapping(path = "/dashboad2")
@Transactional
public class ParticipentController {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private StatsRepository statsRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EventManagementApplication EventManagementApplication;

	// post all api where employee can enroll for selected events
	@PostMapping("/events")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Event> postEvent(@Valid @RequestBody String[] authcust) throws ParseException {

		EventManagementApplication.valiadateEventDB();

		String username = authcust[0];
		String enroll = authcust[1];
		Long eventID = Long.parseLong(authcust[2]);
		Employee e = employeeRepository.findByUsername(username);

		List<Event> finalEventList = new ArrayList<Event>();
		// user-name present is DB
		if (e != null) {
			// getting all event from DB
			List<Event> eventList = eventRepository.findAll();

			// iterate each event
			for (Event event : eventList) {
				// only employee can see and not HR
				if (e.getRole().getRole().equalsIgnoreCase("employee")
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
									LocalDate eventDate = LocalDate.parse(event.getEventDate(), formatter);
									// now current date
									LocalDate currentdate = LocalDate.now();
									formatter.format(currentdate);
									// compare both date
									Period diff = Period.between(currentdate, eventDate);
									int diffinDate = diff.getDays();
									if (diffinDate > 1) {
										// level must be equal or high
										int count = e.getPost().getLevel();
										if (event.getPost().getLevel() <= count) {
											// participant have register that event already?
											List<Stats> statsListEmp = statsRepository.findAllByEmployee(e); // list in
																												// which
																												// employee
																												// register
											if (statsListEmp.isEmpty()) {
												// not register for single event
												// not same, add it
												System.out.println(
														"........This employee have not enroll for single events");
												int temp = event.getEventID().compareTo(eventID);
												if ((enroll.equalsIgnoreCase("true")) && temp == 0) {

													System.out.println(
															".........he/she want to enroll event for first time");
													event.setEntryCount(event.getEntryCount() + 1);

													// create new stats record
													Stats stats = new Stats();
													stats.setDate(event.getEventDate());
													stats.setEmployee(e);
													stats.setEvent(event);
													stats.setFlag(true);
													statsRepository.save(stats);

												}
												finalEventList.add(event);
											} else {
												System.out.println("This employee have enroll for events");
												List<Stats> statsListevent = statsRepository.findAllByEvent(event);
												// register for events but which one
												if (statsListevent.isEmpty()) {
													// not register for single event
													// not same, add it
													int temp = event.getEventID().compareTo(eventID);
													if (enroll.equalsIgnoreCase("true") && temp == 0) {
														// check date
														if (DateCheck(e.getUsername(), event.getEventDate())) {
															event.setEntryCount(event.getEntryCount() + 1);

															// create new stats record
															Stats stats = new Stats();
															stats.setDate(event.getEventDate());
															stats.setEmployee(e);
															stats.setEvent(event);
															stats.setFlag(true);
															statsRepository.save(stats);
														}
													}
													finalEventList.add(event);
												}
											}
										} else {
											System.out.println("level is low");
										}
									} else {
										System.out.println("date has gone to enroll");
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
			} // end of for loop
		} else {
			// username is not there in DB
			System.out.println("1. Username does not exist");
		}
		return finalEventList;
	}

	// show all event in which employee has enroll for event
	@GetMapping("/events/{username}")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Stats> postStats(@Valid @PathVariable String username) {

		Employee e = employeeRepository.findByUsername(username);
		if (e != null) {
			List<Stats> statsListEmp = statsRepository.findAllByEmployee(e);
			if (statsListEmp.isEmpty()) {
				System.out.println("Not enroll for single event");
				return null;
			} else {
				return statsListEmp;
			}
		} else {
			System.out.println("username not there in DB");
			return null;
		}
	}

	public Boolean DateCheck(String username, String Date) {

		Employee e = employeeRepository.findByUsername(username);
		if (e != null) {
			List<Stats> statsListEmp = statsRepository.findAllByEmployee(e);
			for (Stats stats : statsListEmp) {
				if (!Date.equals(stats.getDate())) {
					// allow
					System.out.println("Event is not enroll on this Day");
					return true;
				} else {
					// dont allow to register
					System.out.println("Event is Already enroll on this Day");
					return false;
				}
			}
		}
		return false;
	}

}
