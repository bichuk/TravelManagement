package com.travel.usermicroservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.usermicroservice.model.User;



@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long>{

	User findByUserNameAndPasswordAndType(String userName, String password, String type);

	Iterable<User> findByType(String userType);

	Optional<User> findByUserName(String userName);

}
