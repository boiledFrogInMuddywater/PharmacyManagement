package com.example.pharmacymanagement;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
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
import android.widget.Toast;

public class PatientDetailActivity extends Activity {
	private EditText et_patientName, et_pharmacyId;
	private RadioGroup rg_patientSex;
	private RadioButton rb_patientFemale, rb_patientMan;
	private EditText et_patientPhone, et_patientAge;
	private EditText et_patientDoctorJudge, et_patientDescription, et_patientDoctor;
	private EditText et_patientSeeTime, et_patientMedicines;
	private Button bt_change;
	private ArrayList<String> mess;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_detail);

		Intent intent = getIntent();
		 mess = intent.getStringArrayListExtra("data");

		et_pharmacyId = (EditText) findViewById(R.id.et_pharmacyId);
		et_patientName = (EditText) findViewById(R.id.et_patientName);
		rg_patientSex = (RadioGroup) findViewById(R.id.rg_patientSex);
		rb_patientMan = (RadioButton) findViewById(R.id.rb_patientMan);
		rb_patientFemale = (RadioButton) findViewById(R.id.rb_patientFemale);
		et_patientAge = (EditText) findViewById(R.id.et_patientAge);
		et_patientPhone = (EditText) findViewById(R.id.et_patientPhone);
		et_patientDoctor = (EditText) findViewById(R.id.et_patientDoctor);
		et_patientDescription = (EditText) findViewById(R.id.et_patientDescription);
		et_patientDoctorJudge = (EditText) findViewById(R.id.et_patientDoctorJudge);
		et_patientMedicines = (EditText) findViewById(R.id.et_patientMedicines);
		et_patientSeeTime = (EditText) findViewById(R.id.et_patientSeeTime);
		bt_change = (Button) findViewById(R.id.bt_change);

		setDataToView(mess);

		bt_change.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = et_patientName.getText().toString().trim();
				String age = et_patientAge.getText().toString().trim();
				String phone = et_patientPhone.getText().toString().trim();
				String desc = et_patientDescription.getText().toString().trim();
				String judge = et_patientDoctorJudge.getText().toString().trim();
				String medicines = et_patientMedicines.getText().toString().trim();
				String sex = "";
				switch (rg_patientSex.getCheckedRadioButtonId()) {
				case R.id.rb_patientMan:
					sex = "男";
					break;

				default:
					sex = "女";
					break;
				}
				
			//修改数据库中的信息
				MyOpenHelper myOpenHelper = new MyOpenHelper(getApplicationContext());
				SQLiteDatabase db = myOpenHelper.getWritableDatabase();
				String patientsSql = "update patients set name=? ,year=? , phone=?, gender=? where patientId=?";
				String pharmacysSql = "update pharmacys set diseaseDescription=?,doctorJudge=?,medicines=? where pharmacyId=?";
				db.execSQL(patientsSql, new String[] {name, age, phone, sex,mess.get(10) });
				db.execSQL(pharmacysSql, new String[] {desc, judge, medicines, mess.get(6) });
				db.close();
				Toast.makeText(getApplicationContext(), "修改成功", 1).show();
			}
		});

	}
//	String sql = "select name, phone, gender, year, doctorId, createTime,pharmacyId,diseaseDescription,doctorJudge,medicines,patientId from pharmacys, patients where pharmacys.patientId=patients.patientId";

	public void setDataToView(ArrayList<String> mess) {
		// 病例编号设置不可修改
		et_pharmacyId.setText(mess.get(6));
		et_pharmacyId.setFocusable(false);

		et_patientName.setText(mess.get(0));

		if (mess.get(2).equals("男")) {
			rb_patientMan.setChecked(true);
		} else {
			rb_patientFemale.setChecked(true);
		}

		et_patientAge.setText(mess.get(3));
		et_patientPhone.setText(mess.get(1));
		// 医生编号设置不可修改
		et_patientDoctor.setText(mess.get(4));
		et_patientDoctor.setFocusable(false);

		et_patientDescription.setText(mess.get(7));
		et_patientDoctorJudge.setText(mess.get(8));
		et_patientMedicines.setText(mess.get(9));
		// 诊断日期设置不可修改
		et_patientSeeTime.setText(mess.get(5));
		et_patientSeeTime.setFocusable(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient_detail, menu);
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
