package com.csuf.bidbash.controller;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Object> registerUser(@RequestBody User user) {

		// check if userEmaiId
		boolean isEmailPresent = userService.checkIfEmailPresent(user.getEmail());
		if (isEmailPresent) {
			return new ResponseEntity<Object>("This email is already in use", HttpStatus.CONFLICT);
		}

		int userId = userService.nextUserId();
		user.setUserId(userId);

		User registeredUser = userService.registerNewUser(user);

		return new ResponseEntity<Object>(registeredUser, HttpStatus.CREATED);
	}

	@GetMapping("/{userId}")
	public User getUserById(@PathVariable("userId") int userId) {
		return userService.getUserById(userId);
	}

	@PostMapping("/login")
	public ResponseEntity<Object> loginUser(@RequestBody Map<String, String> requestBody) {
		String email = requestBody.get("email");
		String password = requestBody.get("password");
		User loggedInUser = userService.findUserByEmailAndPassword(email, password);
		if (Objects.isNull(loggedInUser)) {
			return new ResponseEntity<Object>("You have entered an invalid username or password", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Object>(loggedInUser, HttpStatus.OK);
	}
}
