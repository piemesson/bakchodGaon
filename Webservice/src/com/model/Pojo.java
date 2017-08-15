package com.model;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@Entity(name = "yoyoyoyo")
public class Pojo {

	//variable declaration
	private String username;
	
	
	@Id
	@Column
	private String email;
	private String password;
	private String gender;

	
	// setters and getters 
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	@Override
	public String toString() {
		return "Pojo [username=" + username + ", email=" + email
				+ ", password=" + password + ", gender=" + gender + "]";
	}
	

	
	
}
