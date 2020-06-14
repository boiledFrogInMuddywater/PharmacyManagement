package com.example.pharmacymanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class RootIndexActivity extends Activity implements View.OnClickListener {

	private ImageView iv_man;
	private ImageView iv_medicine;
	private ImageView iv_cangku;
	private ImageView iv_inout;
	private ImageView iv_basemess;
	private ImageView iv_changepwd;
	private ImageView iv_patient;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_root_index);
		iv_man = (ImageView) findViewById(R.id.iv_man);
		iv_medicine = (ImageView) findViewById(R.id.iv_medicine);
		iv_cangku = (ImageView) findViewById(R.id.iv_cangku);
		iv_inout = (ImageView) findViewById(R.id.iv_inout);
		iv_basemess = (ImageView) findViewById(R.id.iv_basemess);
		iv_changepwd = (ImageView) findViewById(R.id.iv_changepwd);
		iv_patient = (ImageView) findViewById(R.id.iv_patient);

		iv_man.setOnClickListener(this);
		iv_medicine.setOnClickListener(this);
		iv_cangku.setOnClickListener(this);
		iv_inout.setOnClickListener(this);
		iv_basemess.setOnClickListener(this);
		iv_changepwd.setOnClickListener(this);
		iv_patient.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_man:
			// 跳转到医生信息管理界面
			intent = new Intent(RootIndexActivity.this, DoctorActivity.class);
			startActivity(intent);
			break;
		case R.id.iv_medicine:
			// 跳转到药物信息界面
			intent = new Intent(RootIndexActivity.this, MedicineActivity.class);
			startActivity(intent);
			break;
		case R.id.iv_cangku:
			// 跳转到出入库管理界面
			intent = new Intent(RootIndexActivity.this, ChangeInventoryActivity.class);
			startActivity(intent);
			break;
		case R.id.iv_inout:
			// 跳转到出入库信息界面
			intent = new Intent(RootIndexActivity.this, InventoryActivity.class);
			startActivity(intent);
			break;
		case R.id.iv_basemess:

			break;
		case R.id.iv_changepwd:
			//跳转到修改密码的页面
			intent = new Intent(RootIndexActivity.this, ChangePwdActivity.class);
			intent.putExtra("id", "root");
			startActivity(intent);
			break;
		case R.id.iv_patient:
			//显示患者信息
			intent = new Intent(RootIndexActivity.this, PatientActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.root_index, menu);
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
