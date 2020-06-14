package com.example.pharmacymanagement;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBParserUtils {

	public static List<Doctor> getDoctor(SQLiteDatabase db) {
		// 查询数据库中医生的信息并返回
		List<Doctor> doctorlists = null;
		Cursor cursor = db.rawQuery("select * from doctors", null);
		if (cursor != null && cursor.getCount() > 0) {
			doctorlists = new ArrayList<Doctor>();
			while (cursor.moveToNext()) {
				Doctor doctor = new Doctor();
				int doctorId = cursor.getInt(0);
				String account = cursor.getString(1);
				String password = cursor.getString(2);
				String name = cursor.getString(3);
				String gender = cursor.getString(4);
				String phone = cursor.getString(5);
				String email = cursor.getString(6);
				doctor.setDoctorId(doctorId);
				doctor.setAccount(account);
				doctor.setPassword(password);
				doctor.setName(name);
				doctor.setGender(gender);
				doctor.setPhone(phone);
				doctor.setEmail(email);
				doctorlists.add(doctor);
			}
		}

		return doctorlists;

	}

	public static Boolean insertDoctorMess(SQLiteDatabase db, Doctor doctor) {
		// 将医生的信息插入数据库
		ContentValues values = new ContentValues();
		values.put("account", doctor.getAccount());
		values.put("password", doctor.getPassword());
		values.put("name", doctor.getName());
		values.put("gender", doctor.getGender());
		values.put("phone", doctor.getPhone());
		values.put("email", doctor.getEmail());
		// 返回值代表新插入行的id
		long insert = db.insert("doctors", null, values);
		if (insert > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 药物表
	String medicineSql = "create table medicines(medicineId integer primary key autoincrement, "
			+ "medicineName varchar(32) not null, productTime date not null, endTime date, type varchar(16),"
			+ "price double not null, productCompany varchar(32), description varchar(48),saleTotalNum integer )";

	public static List<Medicine> getMedicine(SQLiteDatabase db) {
		// 查询数据库中药物的信息并返回
		List<Medicine> medicinelists = null;
		Cursor cursor = db.rawQuery("select * from medicines", null);
		if (cursor != null && cursor.getCount() > 0) {
			medicinelists = new ArrayList<Medicine>();
			while (cursor.moveToNext()) {
				Medicine medicine = new Medicine();
				int medicineId = cursor.getInt(0);
				String medicineName = cursor.getString(1);
				String productTime = cursor.getString(2);
				String endTime = cursor.getString(3);
				String type = cursor.getString(4);
				Double price = cursor.getDouble(5);
				String productCompany = cursor.getString(6);
				String description = cursor.getString(7);
				int saleTotalNum = cursor.getInt(8);
				int totalNum = cursor.getInt(9);

				medicine.setMedicineId(medicineId);
				medicine.setMedicineName(medicineName);
				medicine.setProductTime(productTime);
				medicine.setEndTime(endTime);
				medicine.setType(type);
				medicine.setPrice(price);
				medicine.setProductCompany(productCompany);
				medicine.setDescription(description);
				medicine.setSaleTotalNum(saleTotalNum);
				medicine.setTotalNum(totalNum);
				medicinelists.add(medicine);
			}
		}

		return medicinelists;

	}

	public static Boolean insertMedicineMess(SQLiteDatabase db, Medicine medicine) {
		// 将医生的信息插入数据库
		ContentValues values = new ContentValues();
		values.put("medicineName", medicine.getMedicineName());
		values.put("productTime", medicine.getProductTime());
		values.put("endTime", medicine.getEndTime());
		values.put("type", medicine.getType());
		values.put("price", medicine.getPrice());
		values.put("productCompany", medicine.getProductCompany());
		values.put("description", medicine.getDescription());
		// 为添加的药物设置初始值
		values.put("saleTotalNum", 0);
		values.put("totalNum", 0);
		// 返回值代表新插入行的id
		long insert = db.insert("medicines", null, values);
		if (insert > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static ArrayList<ArrayList<String>> getInventoryInOutMess(SQLiteDatabase db) {
		String sql = "select medicineName, changeType, changeNum,productCompany, changetime from inventorys, medicines where inventorys.medicineId=medicines .medicineId";
		Cursor cursor = db.rawQuery(sql, null);
		
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();

		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				ArrayList<String> mess = new ArrayList<String>();
				mess.add( cursor.getString(0));
				mess.add(cursor.getString(1));
				mess.add(String.valueOf(cursor.getInt(2)));
				mess.add(cursor.getString(3));
				mess.add(cursor.getString(4));
				list.add(mess);
			}
		}
		return list;
	}
	public static ArrayList<ArrayList<String>> getPatientMess(SQLiteDatabase db) {
		String sql = "select name, phone, gender, year, doctorId, createTime,pharmacyId,diseaseDescription,doctorJudge,medicines, patients.patientId from pharmacys, patients where pharmacys.patientId=patients.patientId";
		Cursor cursor = db.rawQuery(sql, null);
		
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();

		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				ArrayList<String> mess = new ArrayList<String>();
				mess.add(cursor.getString(0));
				mess.add(cursor.getString(1));
				mess.add(cursor.getString(2));
				mess.add(String.valueOf(cursor.getString(3)));
				mess.add(String.valueOf(cursor.getString(4)));
				mess.add(cursor.getString(5));
				mess.add(String.valueOf(cursor.getString(6)));
				mess.add(cursor.getString(7));
				mess.add(cursor.getString(8));
				mess.add(cursor.getString(9));
				mess.add(String.valueOf(cursor.getString(10)));
				list.add(mess);
			}
		}
		return list;
	}
}
