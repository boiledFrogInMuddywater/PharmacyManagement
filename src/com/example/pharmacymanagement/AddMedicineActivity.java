package com.example.pharmacymanagement;

import java.text.SimpleDateFormat;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddMedicineActivity extends Activity {
	private EditText et_addMedicineName, et_addMedicineCompany;
	private EditText et_addMedicineProductTime, et_addMedicineEndTime;
	private EditText et_addMedicinePrice, et_addMedicineDescription;
	private RadioGroup rgtype;
	private Button bt_addMedicineMess;
	private MyOpenHelper myOpenHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_medicine);

		et_addMedicineName = (EditText) findViewById(R.id.et_addMedicineName);
		et_addMedicineCompany = (EditText) findViewById(R.id.et_addMedicineCompany);
		et_addMedicineProductTime = (EditText) findViewById(R.id.et_addMedicineProductTime);
		et_addMedicineEndTime = (EditText) findViewById(R.id.et_addMedicineEndTime);
		et_addMedicinePrice = (EditText) findViewById(R.id.et_addMedicinePrice);
		et_addMedicineDescription = (EditText) findViewById(R.id.et_addMedicineDescription);
		rgtype = (RadioGroup) findViewById(R.id.rgtype);
		bt_addMedicineMess = (Button) findViewById(R.id.bt_addMedicineMess);
		myOpenHelper = new MyOpenHelper(getApplicationContext());

		bt_addMedicineMess.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 判断输入是否合法
				Medicine medicine = judgeInputMessage();

				SQLiteDatabase db = myOpenHelper.getWritableDatabase();
				if (medicine != null) {
					
					if (DBParserUtils.insertMedicineMess(db, medicine)) {
						Toast.makeText(getApplicationContext(), "添加成功", 1).show();
						finish();
					} else {
						Toast.makeText(getApplicationContext(), "添加失败", 1).show();
					}
					db.close();
				}
			}
		});
	}

	public Medicine judgeInputMessage() {
		String name = et_addMedicineName.getText().toString().trim();
		String comapny = et_addMedicineCompany.getText().toString().trim();
		String productTime = et_addMedicineProductTime.getText().toString().trim();
		String endTime = et_addMedicineEndTime.getText().toString().trim();
		String price = et_addMedicinePrice.getText().toString().trim();
		String descript = et_addMedicineDescription.getText().toString().trim();
		RadioButton radioButton = (RadioButton) findViewById(rgtype.getCheckedRadioButtonId());
		String type = radioButton.getText().toString().trim();
		Medicine medicine = new Medicine();

		// 判断名称、生产日期、价格是否为空
		if (name.equals("")) {
			Toast.makeText(getApplicationContext(), "药品名称不能为空，请输入药品名称", 1).show();
			return null;
		}
		if (productTime.equals("")) {
			Toast.makeText(getApplicationContext(), "生产日期不能为空， 请输入生产日期", 1).show();
			return null;
		}
		if (price.equals("")) {
			Toast.makeText(getApplicationContext(), "药品价格不能为空， 请输入价格", 1).show();
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 对输入的日期进行转换，判断日期是否符合规范
		try {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.parse(productTime);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "生产日期请输入正确的格式，如2020-03-06", 1).show();
			return null;
		}
		try {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.parse(endTime);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "有效期请输入正确的格式，如2020-03-06", 1).show();
			return null;
		}
		
		try {
			Double.valueOf(price);
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), "请输入正确的价格，价格只能是数字", 1).show();
			return null;
		}
		// 判断药物是否已经存在
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		Cursor cursor = db.query("medicines", new String[] { "medicineName" }, "medicineName=?", new String[] { name },
				null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			Toast.makeText(getApplicationContext(), name + ":该药物信息已存在，如要修改信息请到修改页面进行修改", 1).show();
			return null;
		}
		db.close();

		medicine.setMedicineName(name);
		medicine.setProductCompany(comapny);
		medicine.setProductTime(productTime);
		medicine.setEndTime(endTime);
		medicine.setPrice(Double.parseDouble(price));
		medicine.setDescription(descript);
		medicine.setType(type);

		return medicine;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_medicine, menu);
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
