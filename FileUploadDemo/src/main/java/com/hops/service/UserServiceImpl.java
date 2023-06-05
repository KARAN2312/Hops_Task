package com.hops.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hops.model.User;
import com.hops.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	

	    @Autowired
	    UserRepository userRepository;

	    
		public void addUser(User user) {
			 userRepository.save(user);
			
		}
		public List<User> getTheListUser() {
			
			 return userRepository.findAll();
		}
	}


