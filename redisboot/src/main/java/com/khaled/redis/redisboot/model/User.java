package com.khaled.redis.redisboot.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
public class User implements Serializable{

    private static final long serialVersionUID = 7156526077883281623L;

	@Id
	private String userId;
	private String name;
	private Date creationDate = new Date();
	
	
	 public User() {
	    }
	 
	 
	 
	public User(String name, Date creationDate) {
		this.name = name;
		this.creationDate = creationDate;
	}



	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", creationDate=" + creationDate + ", userSettings="
				 + "]";
	}

	
	
	
	


}
