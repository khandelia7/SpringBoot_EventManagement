package com.zycus.eventmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zycus.eventmanagement.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	public boolean existsByDesignationAndDepartment(String designation, String department);

	@Query(value = "SELECT  p.psotID FROM Post p where p.designation = :designation and p.department = :department", nativeQuery = true)
	Long findID(String designation, String department);

	public Post findAllByPsotID(Long id);

	List<Post> findByDepartment(String department);

	@Query(value = "SELECT  p.level FROM Post p where p.designation = :designation and p.department = :department", nativeQuery = true)
	Integer findLevel(String designation, String department);
}
