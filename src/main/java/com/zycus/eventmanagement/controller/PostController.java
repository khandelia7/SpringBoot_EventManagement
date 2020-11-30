package com.zycus.eventmanagement.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zycus.eventmanagement.entities.Post;
import com.zycus.eventmanagement.repository.PostRepository;

@RestController
@RequestMapping(path = "/post")
@Transactional
public class PostController {

	@Autowired
	private PostRepository postRepository;

	@GetMapping("/test")
	public String testPostContoller() {
		return "Post Controller is working fine";
	}

	// post (add) all Post
	@PostMapping("/")
	public List<Post> addAllPost(@RequestBody List<Post> post) {
		return postRepository.saveAll(post);
	}

	@GetMapping("/")
	public List<Post> showAllPost() {
		return postRepository.findAll();
	}

	@GetMapping("/department")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Post> showAllDepartment() {
		return postRepository.findAll();
	}
}
