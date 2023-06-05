package com.hops.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hops.model.Users;



@Repository
public interface UsersRepository extends MongoRepository<Users, Integer>{

	Users findByEmailAndPassword(String email, String password);
	
	Users getByEmail(String email);

	
}
