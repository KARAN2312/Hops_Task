package com.hops.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
	
	@Transient
	public static final String SEQUENCE_NAME="User_sequence";
	
	@Id
	private int id;
	
	private String uName;
	private String image;
	private String code;
	private boolean active;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public User(int id, String uName, String image, String code, boolean active) {
		super();
		this.id = id;
		this.uName = uName;
		this.image = image;
		this.code = code;
		this.active = active;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", uName=" + uName + ", image=" + image + ", code=" + code + ", active=" + active
				+ "]";
	}
	
	
	
	

}
