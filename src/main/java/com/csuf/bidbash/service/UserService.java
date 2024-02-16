package com.csuf.bidbash.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csuf.bidbash.pojos.User;
import com.csuf.bidbash.repos.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;
	
	
	public User registerNewUser(User user) {
		return userRepo.save(user);
	}
	
	public User getUserById(int userId) {
		return userRepo.findById(userId).get();
	}
}
