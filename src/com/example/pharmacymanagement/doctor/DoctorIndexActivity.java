package com.example.pharmacymanagement.doctor;

import com.example.pharmacymanagement.BaseMessageActivity;
import com.example.pharmacymanagement.ChangePwdActivity;
import com.example.pharmacymanagement.InventoryActivity;
import com.example.pharmacymanagement.MedicineActivity;
import com.example.pharmacymanagement.PatientActivity;
import com.example.pharmacymanagement.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class DoctorIndexActivity extends Activity implements OnClickListener {
	private ImageView iv_diagnostic, iv_medicine;
	private ImageView iv_patient, iv_inout;
	private ImageView iv_basemess, iv_changepwd;
	private int doctorId;
    private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_index);
		//获取登录的医生的id
		Intent intent = getIntent();
		doctorId = intent.getIntExtra("doctorId", 0);
		
		iv_diagnostic = (ImageView) findViewById(R.id.iv_diagnostic);
		iv_medicine = (ImageView) findViewById(R.id.iv_medicine);
		iv_patient = (ImageView) findViewById(R.id.iv_patient);
		iv_inout = (ImageView) findViewById(R.id.iv_inout);
		iv_basemess = (ImageView) findViewById(R.id.iv_basemess);
		iv_changepwd = (ImageView) findViewById(R.id.iv_changepwd);

		iv_diagnostic.setOnClickListener(this);
		iv_medicine.setOnClickListener(this);
		iv_patient.setOnClickListener(this);
		iv_inout.setOnClickListener(this);
		iv_basemess.setOnClickListener(this);
		iv_changepwd.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_diagnostic:
			//跳转到医生诊断界面
			intent = new Intent(DoctorIndexActivity.this, DiagnosticActivity.class);
			intent.putExtra("doctorId", doctorId);
			startActivity(intent);
			break;

		case R.id.iv_medicine:
			//跳转到药物信息界面，和root的一样
			intent = new Intent(DoctorIndexActivity.this, MedicineActivity.class);
			
			startActivity(intent);
			break;
		case R.id.iv_patient:
			//跳转到患者信息界面，和root的一样
			intent = new Intent(DoctorIndexActivity.this, PatientActivity.class);
			startActivity(intent);
			break;
		case R.id.iv_inout:
			//跳转到出入库信息界面，和root的一样
			intent = new Intent(DoctorIndexActivity.this, InventoryActivity.class);
			startActivity(intent);
			break;
		case R.id.iv_basemess:
			//跳转到基本信息界面
			intent = new Intent(DoctorIndexActivity.this, BaseMessageActivity.class);
			intent.putExtra("doctorId", doctorId);
			startActivity(intent);
			break;
		case R.id.iv_changepwd:
			intent = new Intent(DoctorIndexActivity.this, ChangePwdActivity.class);
			intent.putExtra("id", String.valueOf(doctorId));
			startActivity(intent);
			break;

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctor_index, menu);
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
