package com.csuf.bidbash.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csuf.bidbash.pojos.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

	@Query(value = "select count(*) from user_db where email = ?1 ", nativeQuery = true)
	public int checkIfEmailIdPresent(String emailId);

	@Query(value = "select user_id from user_db order by user_id desc limit 1", nativeQuery = true)
	public Integer findNextUserId();

	@Query(value = "select * from user_db  where email = ?1 and password = ?2", nativeQuery = true)
	public User findUserByEmaiAndPassword(String email, String password);

}
