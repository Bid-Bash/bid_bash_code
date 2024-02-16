package com.csuf.bidbash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csuf.bidbash.pojos.User;
import com.csuf.bidbash.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@GetMapping("/welcome")
	public String applicationStarted() {
		return "Application is started";
	}
	
	@PostMapping("/register")
	public User registerUser(@RequestBody User user) {
		return userService.registerNewUser(user);
	}
	
	@GetMapping("/{userId}")
	public User getUserById(@PathVariable("userId") int userId) {
		return userService.getUserById(userId);
	}
}
