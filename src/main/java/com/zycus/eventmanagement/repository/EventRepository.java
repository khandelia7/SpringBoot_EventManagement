package com.zycus.eventmanagement.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zycus.eventmanagement.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

	public Event findByEventID(Long eventid);

	public List<Event> findAllByStatus(String status);

	public List<Event> findAllByEventDate(String eventDate, Pageable pageable);

	// filter by event date, user-name, draft
	@Query(value = "SELECT * FROM Event e "
			+ "WHERE e.event_Date =:eventDate AND e.status=:status AND e.made=:made", nativeQuery = true)
	public List<Event> ffilterDateUserStatus(String eventDate, String status, String made, Pageable pageable);

	// filter by department, user-name, draft
	@Query(value = "SELECT * FROM Event e LEFT JOIN Post p ON p.psotid = e.post_psotid "
			+ "WHERE e.status=:status AND p.department=:department AND e.made=:made", nativeQuery = true)
	public List<Event> ffilterDepartUserStatus(String department, String status, String made, Pageable pageable);

	// user-name, draft, no filter
	@Query(value = "SELECT * FROM EVENT e WHERE e.made=:made AND e.status=:status", nativeQuery = true)
	public List<Event> fNoFilterUserStatus(String status, String made, Pageable pageable);

	// filter by event date, compulsory publish
	@Query(value = "SELECT * FROM Event e "
			+ "WHERE e.event_Date =:eventDate AND e.status=:status ", nativeQuery = true)
	public List<Event> ffilterDateUserStatus(String eventDate, String status, Pageable pageable);

	// filter by department, compulsory publish
	@Query(value = "SELECT * FROM Event e LEFT JOIN Post p ON p.psotid = e.post_psotid "
			+ "WHERE e.status=:status AND p.department=:department ", nativeQuery = true)
	public List<Event> ffilterDepartUserStatus(String department, String status, Pageable pageable);

	// no filter, compulsory publish
	@Query(value = "SELECT * FROM EVENT e WHERE e.status=:status", nativeQuery = true)
	public List<Event> fNoFilterUserStatus(String status, Pageable pageable);
}
