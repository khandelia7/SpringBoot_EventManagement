package com.zycus.eventmanagement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

import com.zycus.eventmanagement.entities.Event;
import com.zycus.eventmanagement.repository.EventRepository;

/* The @EnableJdbcHttpSession annoÏtation creates a Spring Bean with the name of springSessionRepositoryFilter  
 * that implements the filter. The filter is what is in charge of replacing the HttpSession implementation 
 * to be backed by Spring Session. In this instance, Spring Session is backed by a relational database. 
 * By default, the session timeout is 1800 seconds (30 minutes).
 */
//@EnableJdbcHttpSession
@SpringBootApplication
public class EventManagementApplication {

	@Autowired
	private EventRepository eventRepository;

	public static void main(String[] args) {
		SpringApplication.run(EventManagementApplication.class, args);
	}

	public void valiadateEventDB() {
		List<Event> eventList = eventRepository.findAll();
		for (Event event : eventList) {
			// check escalationTime and set to (EventDate - 1 day)
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			// event date
			LocalDate eventDate = LocalDate.parse(event.getEventDate(), formatter);
			eventDate = eventDate.minusDays(1);
			String escalationTime = eventDate.format(formatter);
			event.setEscalationTime(escalationTime);

//			// if event date gone then make it draft
//			LocalDate currentdate = LocalDate.now();
//			formatter.format(currentdate);
//			LocalDate eventDate1 = LocalDate.parse(event.getEventDate(), formatter);
//			Period diff = Period.between(currentdate, eventDate1);
//			int diffinDate = diff.getDays();
//			if(diffinDate >= 1) {
//			}
//			else {
//				// negative
//				event.setStatus("draft");
//			}

//			// check it is status is draft or not
//			if (event.getStatus().equalsIgnoreCase("draft")) {
//				event.setEntryCount(0);
//				event.setAction(false);
//			} else {
////				System.out.println();
////				System.out.println("Event is publish");
//			}

//			// check seat available
//			if (event.getCountOfPanel() <= event.getEntryCount()) {
//				event.setAction(true);
//			} else {
////				System.out.println();
////				System.out.println("Seat are available");
//			}

		}
	}
}
