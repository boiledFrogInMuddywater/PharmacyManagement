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

public class BaseMessageActivity extends Activity implements OnClickListener {
	private EditText et_changeDoctorAccount, et_changeDoctorName;
	private EditText et_changeDoctorPhone, et_changeDoctorEmail;
	private Button bt_reset, bt_submit;
	private int doctorId;
	private MyOpenHelper myOpenHelper;
	private SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_message);
		// 获取登录的医生的id
		Intent intent = getIntent();
		doctorId = intent.getIntExtra("doctorId", 0);
		myOpenHelper = new MyOpenHelper(getApplicationContext());
		db = myOpenHelper.getWritableDatabase();
		et_changeDoctorAccount = (EditText) findViewById(R.id.et_changeDoctorAccount);
		et_changeDoctorName = (EditText) findViewById(R.id.et_changeDoctorName);
		et_changeDoctorPhone = (EditText) findViewById(R.id.et_changeDoctorPhone);
		et_changeDoctorEmail = (EditText) findViewById(R.id.et_changeDoctorEmail);
		bt_reset = (Button) findViewById(R.id.bt_reset);
		bt_submit = (Button) findViewById(R.id.bt_submit);

		bt_reset.setOnClickListener(this);
		bt_submit.setOnClickListener(this);
		System.out.println(doctorId);
		Cursor cursor = db.rawQuery("select account,name,phone,email from doctors where doctorId=?",
				new String[] { String.valueOf(doctorId) });
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				et_changeDoctorAccount.setText(cursor.getString(0));
				et_changeDoctorAccount.setFocusable(false);
				et_changeDoctorName.setText(cursor.getString(1));
				et_changeDoctorPhone.setText(cursor.getString(2));
				et_changeDoctorEmail.setText(cursor.getString(3));
			}
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_submit:
			// 提交信息
			String name = et_changeDoctorName.getText().toString().trim();
			String phone = et_changeDoctorPhone.getText().toString().trim();
			String email = et_changeDoctorEmail.getText().toString().trim();
			ContentValues values = new ContentValues();
			values.put("name", name);
			values.put("phone", phone);
			values.put("email", email);
			int line = db.update("doctors", values, "doctorId=?", new String[] { String.valueOf(doctorId) });
			if (line > 0) {
				Toast.makeText(getApplicationContext(), "更新成功", 0).show();
			} else {
				Toast.makeText(getApplicationContext(), "更新失败", 1).show();
			}
			break;

		default:
			// 重置信息
			et_changeDoctorName.setText("");
			et_changeDoctorPhone.setText("");
			et_changeDoctorEmail.setText("");
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.base_message, menu);
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
