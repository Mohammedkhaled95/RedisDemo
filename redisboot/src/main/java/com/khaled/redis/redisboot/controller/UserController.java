package com.khaled.redis.redisboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.khaled.redis.redisboot.model.User;
import com.khaled.redis.redisboot.repository.UserRepository;

@RestController
@RequestMapping("/")
public class UserController {

	
    private final String EMPLOYEE_CACHE = "USER";

    
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserRepository userRepository;

	/*@Autowired
	private RedisTemplate<String,Object> redisTemplate;
	
	private HashOperations<String, String, User> hashOperations;
    
    @PostConstruct
    private void intializeHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }*/

	@GetMapping
	public List<User> getAllUsers() {
		//hashOperations.entries(EMPLOYEE_CACHE);
		return userRepository.findAll();
	}

	/*
	 * @GetMapping(value = "/{userId}") public Optional<User> getUser(@PathVariable
	 * String userId) { return userRepository.findById(userId); }
	 */

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	@Cacheable(value = "userCache" ,key = "#userId")
	public Optional<User> getUser(@PathVariable String userId) {
		/*if(hashOperations.hasKey(EMPLOYEE_CACHE, userId)) {//in memory
			retrieve from memory
			return 		hashOperations.get(EMPLOYEE_CACHE, userId);

		}
	
	*/
		//else retrieve from repo
		//hashOperations.get(EMPLOYEE_CACHE, userId);
		return userRepository.findById(userId);
	}

/*
	 List<String> test2 = new ArrayList<>();

	@RequestMapping(value = "name/{name}", method = RequestMethod.GET)
	public String getUserByName(@PathVariable String name) {
		LOG.info("Getting user with name ::::", name);
	if (!redisTemplate.hasKey("test2")){
		test2.add(userRepository.findUserByName(name).getName());
		redisTemplate.opsForList().rightPushAll("test2", test2);
		return userRepository.findUserByName(name).getName().toString();

	}
		System.out.println(redisTemplate.opsForList().range("test2", 0, -1)); 


		return redisTemplate.opsForList().range("test2", 0, -1).toString();
	}
	*/
	

	@PostMapping()
	public User addNewUser(@RequestBody User user) {
       // hashOperations.put(EMPLOYEE_CACHE, user.getUserId(), user);
		return userRepository.save(user);
	}

	@PutMapping(value = "/{userId}")
	@CachePut(value = "userCache" , key = "#userId")
	public User updateUser(@PathVariable String userId,@RequestBody User user) {
		return userRepository.save(user);
	}

	@DeleteMapping(value = "/{userId}")
	@CacheEvict(value = "userCache" ,allEntries = true)
	public void deleteUser(@PathVariable String userId) {
		//hashOperations.delete(EMPLOYEE_CACHE, userId);
		userRepository.deleteById(userId);
		
	}
	
	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
	@Cacheable(value = "userCache" ,key = "#name")
	public User getUserbyname(@PathVariable String name) {
		LOG.info("Getting user with name 11111", name);

		User user =userRepository.findUserByName(name);	
		LOG.info("Getting user with name 2222", name);


		return user;
				
	}

}
