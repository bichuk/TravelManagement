package com.travel.usermicroservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "User")
public class User implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(max = 50)
	@Column(unique = true)
	private String userName;
	
	@NotNull
	@Size(max = 50)
	private String password;
	
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	
	private String address;
	@NotNull
	@Max(50)
	private Integer age;
	
	private String phone;
	
	@NotNull
	@Size(max = 50)
	@Column(unique = true)	
	private String email;
	
	@NotNull
	@Size(max = 10)
	private String type;
	
	public String getAddress() {
		return address;
	}
	public Integer getAge() {
		return age;
	}
	public String getEmail() {
		return email;
	}
	public String getFirstName() {
		return firstName;
	}
	public Long getId() {
		return id;
	}
	public String getLastName() {
		return lastName;
	}
	public String getPassword() {
		return password;
	}
	public String getPhone() {
		return phone;
	}

	public String getType() {
		return type;
	}
	public String getUserName() {
		return userName;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setType(String type) {
		this.type = type;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
