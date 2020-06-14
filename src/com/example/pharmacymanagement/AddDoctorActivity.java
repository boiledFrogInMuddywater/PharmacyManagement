package com.example.pharmacymanagement;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class AddDoctorActivity extends Activity {
	private EditText et_account, et_password,et_name,et_phone,et_email;
	private RadioGroup rgSex;
	private Button bt_addDoctorMess;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_doctor);
		
		et_account = (EditText) findViewById(R.id.et_account);
		et_password = (EditText) findViewById(R.id.et_password);
		et_name = (EditText) findViewById(R.id.et_name);
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_email = (EditText) findViewById(R.id.et_email);
		bt_addDoctorMess = (Button) findViewById(R.id.bt_addDoctorMess);
		rgSex=(RadioGroup) findViewById(R.id.rgSex);
		
		
		
		bt_addDoctorMess.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//调用一个方法判断获取的数据是否符合规范，是否没填完整
				Doctor doctor = judgeInput();
				MyOpenHelper myOpenHelper = new MyOpenHelper(getApplicationContext());
				SQLiteDatabase db = myOpenHelper.getWritableDatabase();
				if(doctor != null) {
					if(DBParserUtils.insertDoctorMess(db, doctor)) {
						Toast.makeText(getApplicationContext(), "添加成功", 1).show();
						finish();
					}else {
						Toast.makeText(getApplicationContext(), "添加失败", 1).show();
					}
					db.close();
				}
			}
		});
	}
	
	public Doctor judgeInput() {
		//判断输入并返回一个doctor对象
		 String account = et_account.getText().toString().trim();
		 String password = et_password.getText().toString().trim();
		 String name = et_name.getText().toString().trim();
		 String phone = et_phone.getText().toString().trim();
		 String email = et_email.getText().toString().trim();
		 RadioButton radioButton = (RadioButton) findViewById(rgSex.getCheckedRadioButtonId());
		 String gender = radioButton.getText().toString().trim();
		 System.out.println(gender);
		 Doctor doctor = new Doctor();
		 if (account.equals("")){
			 Toast.makeText(getApplicationContext(), "医生账号不能为空，请输入账号", 1).show();
			 return null;
		 }
		 if (password.equals("")){
			 Toast.makeText(getApplicationContext(), "账号密码不能为空， 请输入密码", 1).show();
			 return null;
		 }
		 if (name.equals("")){
			 Toast.makeText(getApplicationContext(), "医生姓名不能为空， 请输入姓名", 1).show();
			 return null;
		 }
//		 if(phone.length()>0 && phone.length()<11) {
//			 Toast.makeText(getApplicationContext(), "请输入十一位的电话号码", 1).show();
//			 return null;
//		 }
		 doctor.setAccount(account);
		 doctor.setPassword(password);
		 doctor.setName(name);
		 doctor.setGender(gender);
		 doctor.setPhone(phone);
		 
		 doctor.setEmail(email);
		 return doctor;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_doctor, menu);
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
