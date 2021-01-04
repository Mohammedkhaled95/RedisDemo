package com.khaled.redis.redisboot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.khaled.redis.redisboot.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	User findUserByName(String name);

}
