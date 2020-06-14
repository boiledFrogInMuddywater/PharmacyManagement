package com.example.pharmacymanagement;

import android.app.Activity;
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

public class ChangePwdActivity extends Activity implements OnClickListener {
	private EditText et_changePwdAccount, et_changePwdNewPwdt, et_changePwdOldPwdt;
	private Button bt_changePwd, bt_changePwdReset;
	private MyOpenHelper myOpenHelper;
	private String id;
	private SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pwd);
		myOpenHelper = new MyOpenHelper(getApplicationContext());
		db = myOpenHelper.getWritableDatabase();
		
		id = getIntent().getStringExtra("id");
		et_changePwdAccount = (EditText) findViewById(R.id.et_changePwdAccount);
		et_changePwdNewPwdt = (EditText) findViewById(R.id.et_changePwdNewPwdt);
		et_changePwdOldPwdt = (EditText) findViewById(R.id.et_changePwdOldPwdt);
		System.out.println(id);
		setAccount();

		et_changePwdAccount.setFocusable(false);
		bt_changePwd = (Button) findViewById(R.id.bt_changePwd);
		bt_changePwdReset = (Button) findViewById(R.id.bt_changePwdReset);
		myOpenHelper = new MyOpenHelper(getApplicationContext());
		bt_changePwd.setOnClickListener(this);
		bt_changePwdReset.setOnClickListener(this);
	}

	public void setAccount() {
		if(id.equals("root")) {
			et_changePwdAccount.setText(id);
		}else {
			String sql = "select account from doctors where doctorId=?";
			Cursor cursor = db.rawQuery(sql, new String[] { String.valueOf(id) });
			if (cursor != null && cursor.getCount() > 0) {
				while(cursor.moveToNext()) {
					et_changePwdAccount.setText(cursor.getString(0));
				}
			}
		}
			
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_changePwd:
			// 修改密码
			String oldPwd = et_changePwdOldPwdt.getText().toString().trim();
			String newPwd = et_changePwdNewPwdt.getText().toString().trim();
			if(id.equals("root")) {
				// admin修改密码
				String sql = "select password from admin where username='root' and password=?";
				Cursor cursor = db.rawQuery(sql, new String[] { oldPwd });
				if (cursor != null && cursor.getCount() > 0) {
					db.execSQL("update admin set password=? where username='root'", new String[] { newPwd });
					Toast.makeText(getApplicationContext(), "修改密码成功", 1).show();
				} else {
					et_changePwdNewPwdt.setText("");
					et_changePwdOldPwdt.setText("");
					Toast.makeText(getApplicationContext(), "旧密码输入错误，请重新输入", 1).show();
				}
			} else {
				// 医生修改密码
				String sql = "select password from doctors where doctorId=? and password=?";
				Cursor cursor = db.rawQuery(sql, new String[] { id, oldPwd });
				if (cursor != null && cursor.getCount() > 0) {
					db.execSQL("update doctors set password=? where doctorId=?", new String[] { newPwd, id });
					Toast.makeText(getApplicationContext(), "修改密码成功", 1).show();
				} else {
					et_changePwdNewPwdt.setText("");
					et_changePwdOldPwdt.setText("");
					Toast.makeText(getApplicationContext(), "旧密码输入错误，请重新输入", 1).show();
				}
			}

			break;

		default:
			// 重置
			et_changePwdNewPwdt.setText("");
			et_changePwdOldPwdt.setText("");
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_pwd, menu);
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
