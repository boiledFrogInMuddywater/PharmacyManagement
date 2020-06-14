package com.example.pharmacymanagement;

import com.example.pharmacymanagement.doctor.DoctorIndexActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText et_uname;
	private EditText et_password;
	private Button bt_login;
	private MyOpenHelper myOpenHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		et_uname = (EditText) findViewById(R.id.et_uname);
		et_password = (EditText) findViewById(R.id.et_password);
		bt_login = (Button) findViewById(R.id.bt_login);
		// 打开或者创建数据库， 如果是第一次就是创建
		bt_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String account = et_uname.getText().toString().trim();
				String pwd = et_password.getText().toString().trim();
//				[2.1]判断name和password是否为空
				if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
					Toast.makeText(MainActivity.this, "用户名或密码不能为空", 1).show();
				} else {
					myOpenHelper = new MyOpenHelper(getApplicationContext());
					SQLiteDatabase db = myOpenHelper.getWritableDatabase();
					if (account.equals("root")) {
						// 如果是root用户登录就去admin表中查询
						Cursor cursor = db.query("admin", new String[] { "password" }, "username=?",
								new String[] { "root" }, null, null, null);
						if (cursor != null && cursor.getCount() > 0) {
							while (cursor.moveToNext()) {
								String password = cursor.getString(0);
								System.out.println(password);
								if (password.equals(pwd)) {
									// 跳转到首页
									Intent intent = new Intent(MainActivity.this, RootIndexActivity.class);
									startActivity(intent);
									Toast.makeText(MainActivity.this, "登录成功", 1).show();
								} else {
									Toast.makeText(MainActivity.this, "用户名或密码错误", 1).show();
								}
							}

						} else {
							Toast.makeText(MainActivity.this, "用户名或密码错误", 1).show();
						}

					} else {
						// 在医生表中查询DoctorIndexActivity
						Cursor cursor = db.query("doctors", new String[] { "password", "doctorId" }, "account=?",
								new String[] { account }, null, null, null);
						if (cursor != null && cursor.getCount() > 0) {
							while (cursor.moveToNext()) {
								String password = cursor.getString(0);
								int doctorId = cursor.getInt(1);
								if (password.equals(pwd)) {
									// 跳转到医生的首页，
									Intent intent = new Intent(MainActivity.this, DoctorIndexActivity.class);
									intent.putExtra("doctorId", doctorId);
									startActivity(intent);
									Toast.makeText(MainActivity.this, "登录成功", 1).show();
								} else {
									Toast.makeText(MainActivity.this, "用户名或密码错误", 1).show();
								}
							}
							

						}else {
							Toast.makeText(MainActivity.this, "用户名或密码错误", 1).show();
						}

					}

				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
