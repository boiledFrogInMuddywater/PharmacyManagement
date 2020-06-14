package com.example.pharmacymanagement;

import android.R.integer;

public class Doctor {
	
	private int doctorId;
	private String account;
	private String password;
	private String name;
	private String gender;
	private String phone;
	private String email;
	public int getDoctorId() {
		return doctorId;
	}
	public String getAccount() {
		return account;
	}
	public String getPassword() {
		return password;
	}
	public String getName() {
		return name;
	}
	public String getGender() {
		return gender;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

}
