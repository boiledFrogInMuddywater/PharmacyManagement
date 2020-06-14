package com.example.pharmacymanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyOpenHelper extends SQLiteOpenHelper {
	/**
	 * 
	 * @param onClickListener ������
	 * name:���ݿ������
	 * Factory Ŀ�Ĵ���cursor����
	 * version ���ݿ�İ汾 ��1��ʼ
	 */
	public MyOpenHelper(Context onClickListener) {
		super(onClickListener, "pharmacymanagement.db", null, 1);
		
	}
	/**
	 * �����ݿ��һ�δ�����ʱ��͵���
	 * ��ô��������ر��ʺ�����ṹ�ĳ�ʼ�������������дSQL���
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		//ҩ���
		String medicineSql = "create table medicines(medicineId integer primary key autoincrement, "
				+ "medicineName varchar(32) not null, productTime varchar(32) not null, endTime varchar(32), type varchar(16),"
				+ "price double not null, productCompany varchar(32), description varchar(48),saleTotalNum integer,totalNum integer)";
		//����Ա��
		String adminSql = "create table admin(username varchar(16) not null, password varchar(16) not null)";
		//ҽ����
		String doctorSql = "create table doctors(doctorId integer primary key autoincrement, account varchar(8) not null, password varchar(16) not null,"
				+ "name varchar(16), gender varchar(8) not null, phone varchar(12), email varchar(32))";
		//���߱�
		String patientSql = "create table patients(patientId integer primary key autoincrement, name varchar(16) not null,"
				+ "gender varchar(8) not null, year integer, phone varchar(12))";
		//ҩ����
		String pharmacySql = "create table pharmacys(pharmacyId integer primary key autoincrement, doctorId integer, medicines varchar(48), "
				+ "patientId integer, diseaseDescription varchar(96),doctorJudge varchar(48), createTime varchar(32),"
				+ "constraint pharmacy_doctorId_fk foreign key(doctorId) references doctors(doctorId) ,"
				+ "constraint pharmacy_patientId_fk foreign key(patientId) references patients(patientId))";
		
		//��������
		String inventorySql = "create table inventorys(inventoryId integer primary key autoincrement, changeNum integer, changeType varchar(8), "
				+ "medicineId integer, changetime varchar(32),"
				+ "constraint inventory_medicineId_fk foreign key(medicineId) references medicines(medicineId) )";
		db.execSQL(medicineSql);
		db.execSQL(adminSql);
		db.execSQL(doctorSql);
		db.execSQL(patientSql);
		db.execSQL(pharmacySql);
		db.execSQL(inventorySql);
		
		//����Ա����Ĭ�ϵ��˺ź�����
		String setAdminSql = "insert into admin(username, password) values('root', '123456')";
		db.execSQL(setAdminSql);
		
	}
	
	/**
	 * ���ݿ�汾����ʱ���ã��ʺ�����Ľṹ
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists medicines");
		db.execSQL("drop table if exists admin");
		db.execSQL("drop table if exists doctors");
		
		db.execSQL("drop table if exists patients");
		db.execSQL("drop table if exists pharmacys");
		db.execSQL("drop table if exists inventorys");

		
	}
}