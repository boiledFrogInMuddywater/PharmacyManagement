package com.example.pharmacymanagement;

import java.util.Date;

public class Medicine {

	private int medicineId;
	private String medicineName;
	private String productTime;
	private String endTime;
	private String type;
	private Double price;
	private String productCompany;
	private String description;
	private int saleTotalNum;
	private int totalNum;

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getMedicineId() {
		return medicineId;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public String getProductTime() {
		return productTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getType() {
		return type;
	}

	public Double getPrice() {
		return price;
	}

	public String getProductCompany() {
		return productCompany;
	}

	public String getDescription() {
		return description;
	}

	public int getSaleTotalNum() {
		return saleTotalNum;
	}

	public void setMedicineId(int medicineId) {
		this.medicineId = medicineId;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public void setProductTime(String productTime) {
		this.productTime = productTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setProductCompany(String productCompany) {
		this.productCompany = productCompany;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSaleTotalNum(int saleTotalNum) {
		this.saleTotalNum = saleTotalNum;
	}

}
