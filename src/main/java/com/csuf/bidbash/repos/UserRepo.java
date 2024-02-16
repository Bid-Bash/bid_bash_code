package com.csuf.bidbash.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csuf.bidbash.pojos.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

}
