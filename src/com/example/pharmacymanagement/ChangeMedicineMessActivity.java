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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ChangeMedicineMessActivity extends Activity implements OnClickListener{
	private EditText et_changeMedicineId, et_changeMedicineName;
	private EditText et_changeMedicineCompany, et_changeMedicineProductTime;
	private EditText et_changeMedicineEndTime, et_changeMedicinePrice, et_changeMedicineDescription;
	private RadioGroup rg_changetype;
	private Button bt_changeMedicineMess, bt_resetMedicineMess;
	private MyOpenHelper myOpenHelper;
	RadioButton rb_changeYes, rb_changeNo, rb_changeNot;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_medicine_mess);
		
		et_changeMedicineId = (EditText) findViewById(R.id.et_changeMedicineId);
		et_changeMedicineName = (EditText) findViewById(R.id.et_changeMedicineName);
		et_changeMedicineCompany = (EditText) findViewById(R.id.et_changeMedicineCompany);
		et_changeMedicineProductTime = (EditText) findViewById(R.id.et_changeMedicineProductTime);
		et_changeMedicineEndTime = (EditText) findViewById(R.id.et_changeMedicineEndTime);
		et_changeMedicinePrice = (EditText) findViewById(R.id.et_changeMedicinePrice);
		rg_changetype = (RadioGroup) findViewById(R.id.rg_changetype);
		
		rb_changeYes = (RadioButton) findViewById(R.id.rb_changeYes);
		rb_changeNo = (RadioButton) findViewById(R.id.rb_changeNo);
		rb_changeNot = (RadioButton) findViewById(R.id.rb_changeNot);
		
		et_changeMedicineDescription = (EditText) findViewById(R.id.et_changeMedicineDescription);
		
		bt_changeMedicineMess = (Button) findViewById(R.id.bt_changeMedicineMess);
		bt_resetMedicineMess = (Button) findViewById(R.id.bt_resetMedicineMess);
		
		myOpenHelper = new MyOpenHelper(getApplicationContext());
		//获得点击的账号
		Intent intent = getIntent();
		String medicineId = intent.getStringExtra("medicineId");
		//设置信息
		 setView(medicineId);
		 bt_changeMedicineMess.setOnClickListener(this);
		 bt_resetMedicineMess.setOnClickListener(this);
		
	}
	
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_changeMedicineMess:
			//获取输入的信息
			String id = et_changeMedicineId.getText().toString();
			String name = et_changeMedicineName.getText().toString().trim();
			String company = et_changeMedicineCompany.getText().toString().trim();
			String productTime = et_changeMedicineProductTime.getText().toString().trim();
			String endTime = et_changeMedicineEndTime.getText().toString().trim();
			String price = et_changeMedicinePrice.getText().toString().trim();
			String description = et_changeMedicineDescription.getText().toString().trim();
			
			RadioButton radioButton = (RadioButton) findViewById(rg_changetype.getCheckedRadioButtonId());
			String type = radioButton.getText().toString().trim();
			//修改医生的信息
			SQLiteDatabase db = myOpenHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("medicineName", name);
			values.put("productCompany", company);
			values.put("productTime", productTime);
			values.put("endTime", endTime);
			values.put("price", Double.valueOf(price));
			values.put("description", description);
			values.put("type", type);
			
			int update = db.update("medicines", values, "medicineId=?", new String[] {id});
			if(update==1) {
				Toast.makeText(getApplicationContext(), "更新成功", 1).show();
				finish();
			}else {
				Toast.makeText(getApplicationContext(), "更新失败", 1).show();
			}
			break;
		default:
			//清空数据
			et_changeMedicineName.setText("");
			et_changeMedicineCompany.setText("");
			et_changeMedicineProductTime.setText("");
			
			et_changeMedicineEndTime.setText("");
			et_changeMedicinePrice.setText(String.valueOf(""));
			et_changeMedicineDescription.setText("");
			break;
		}
	}
	
	
		public void setView(String medicineId){
			SQLiteDatabase db = myOpenHelper.getWritableDatabase();
			Cursor cursor = db.query("medicines", null, "medicineId=?", new String[] {medicineId}, null, null, null);
			if(cursor!=null&&cursor.getCount()>0) {
				while(cursor.moveToNext()) {
					
					String medicineName = cursor.getString(1);
					String productTime = cursor.getString(2);
					String endTime = cursor.getString(3);
					String type = cursor.getString(4);
					Double price =cursor.getDouble(5);
					String productCompany =cursor.getString(6);
					String description = cursor.getString(7);
					
					et_changeMedicineId.setText(medicineId);
					//编号设置不可修改
					et_changeMedicineId.setFocusable(false);
					
					et_changeMedicineName.setText(medicineName);
					et_changeMedicineCompany.setText(productCompany);
					et_changeMedicineProductTime.setText(productTime);
					
					et_changeMedicineEndTime.setText(endTime);
					et_changeMedicinePrice.setText(String.valueOf(price));
					et_changeMedicineDescription.setText(description);
					
					//设置选中的类型
					if (type=="处方") {
						rb_changeYes.setSelected(true);
					}else if(type=="非处方") {
						 rb_changeNo.setSelected(true);
					}else {
						rb_changeNot.setSelected(true);
					}
				}
			}
			
			db.close();
		}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_medicine_mess, menu);
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
