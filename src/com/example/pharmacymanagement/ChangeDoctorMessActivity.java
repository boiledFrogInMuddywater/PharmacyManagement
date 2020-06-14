package com.example.pharmacymanagement;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeDoctorMessActivity extends Activity implements OnClickListener{
	private EditText et_resetDoctorId, et_resetDoctorName;
	private EditText et_resetDoctorPhone, et_resetDoctorEmail;
	private Button bt_submitDoctorResetMess, bt_clearDoctorMess;
	private MyOpenHelper myOpenHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_doctor_mess);
		

		et_resetDoctorId = (EditText) findViewById(R.id.et_resetDoctorId);
		et_resetDoctorName = (EditText) findViewById(R.id.et_resetDoctorName);
		et_resetDoctorPhone =(EditText) findViewById(R.id.et_resetDoctorPhone);
		et_resetDoctorEmail =(EditText) findViewById(R.id.et_resetDoctorEmail);
		bt_submitDoctorResetMess = (Button) findViewById(R.id.bt_submitDoctorResetMess);
		bt_clearDoctorMess = (Button) findViewById(R.id.bt_clearDoctorMess);
		myOpenHelper = new MyOpenHelper(getApplicationContext());
		//获得点击的账号
		Intent intent = getIntent();
		String account = intent.getStringExtra("account");
		//设置信息
		 setView(account);
		 bt_submitDoctorResetMess.setOnClickListener(this);
		 bt_clearDoctorMess.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_submitDoctorResetMess:
			//获取输入的信息
			String id = et_resetDoctorId.getText().toString();
			String name = et_resetDoctorName.getText().toString().trim();
			String phone = et_resetDoctorPhone.getText().toString().trim();
			String email = et_resetDoctorEmail.getText().toString().trim();
			//修改医生的信息
			SQLiteDatabase db = myOpenHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("name", name);
			values.put("phone", phone);
			values.put("email", email);
			int update = db.update("doctors", values, "doctorId=?", new String[] {id});
			if(update==1) {
				Toast.makeText(getApplicationContext(), "更新成功", 1).show();

			   finish();
			}else {
				Toast.makeText(getApplicationContext(), "更新失败", 1).show();
			}
			break;
		default:
			//清空数据
			et_resetDoctorName.setText("");
			et_resetDoctorPhone.setText("");
			et_resetDoctorEmail.setText("");
			break;
		}
	}
	public void setView(String account){
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		Cursor cursor = db.query("doctors", null, "account=?", new String[] {account}, null, null, null);
		if(cursor!=null&&cursor.getCount()>0) {
			while(cursor.moveToNext()) {
				int doctorId = cursor.getInt(0);
				String name = cursor.getString(3);
				String phone =cursor.getString(5);
				String email = cursor.getString(6);
				
				et_resetDoctorId.setText(String.valueOf(doctorId));
				//编号设置不可修改
				et_resetDoctorId.setFocusable(false);
				et_resetDoctorName.setText(name);
				et_resetDoctorPhone.setText(phone);
				et_resetDoctorEmail.setText(email);
			}
		}
		
		db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_doctor_mess, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
}
