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
		User userDb = userRepo.save(user);
		return userDb;
	}

	public boolean checkIfEmailPresent(String emailId) {
		int emailCount = userRepo.checkIfEmailIdPresent(emailId);
		if (emailCount == 1)
			return true;
		return false;
	}

	public User getUserById(int userId) {
		return userRepo.findById(userId).get();
	}

	public int nextUserId() {
		int user_id = userRepo.findNextUserId();
		return user_id + 1;
	}

	public User findUserByEmailAndPassword(String email, String Password) {
		return userRepo.findUserByEmaiAndPassword(email, Password);
	}
}
