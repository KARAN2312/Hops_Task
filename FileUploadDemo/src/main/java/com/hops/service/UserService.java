package com.hops.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hops.model.User;

@Service
public interface UserService  {
	
		

		List<User> getTheListUser();

		void addUser(User user);

		

}
