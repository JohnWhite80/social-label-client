package com.tute.userbean;

public class User {
	private int id;
	private String email;
	private String password;
	private String nikename;
	private String age;
	private String sex;
	private String city;
	public User(){
		
	}
	public User( String email, String password, String nikename,
			String age, String sex, String city) {
		this.email = email;
		this.password = password;
		this.nikename = nikename;
		this.age = age;
		this.sex = sex;
		this.city = city;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getNikename() {
		return nikename;
	}
	public void setNikename(String nikename) {
		this.nikename = nikename;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
