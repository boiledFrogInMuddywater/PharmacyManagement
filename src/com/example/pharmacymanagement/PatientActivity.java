package com.example.pharmacymanagement;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class PatientActivity extends Activity {
	private ListView lv_patient;
	private MyOpenHelper myOpenHelper;
	ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient);
		
		lv_patient = (ListView) findViewById(R.id.lv_patient);
		//设置数据
		initDoctorListView();
		lv_patient.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//获取点击的item对应的数据
				
				Intent intent = new Intent(PatientActivity.this, PatientDetailActivity.class);
				intent.putExtra("data", list.get(position));
				startActivity(intent);
				
			}
		});
	}
	
	private void initDoctorListView() {
		// TODO Auto-generated method stub
		// 在数据库中获取医生的信息
		myOpenHelper = new MyOpenHelper(getApplicationContext());
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		list = DBParserUtils.getPatientMess(db);
		db.close();
		//如果有信息才显示
		if (list!=null) {
			lv_patient.setAdapter(new MyAdapter());
		}
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			if (convertView == null) {
				//第三种打气筒的方法
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.patient_item, null);
			} else {
				view = convertView;
			}
			// 找到我們的控件， 显示集合里面的数据
			TextView tv_patientName = (TextView)view.findViewById(R.id.tv_patientName);
			TextView tv_patientPhone = (TextView)view.findViewById(R.id.tv_patientPhone);
			TextView tv_patientGender = (TextView)view.findViewById(R.id.tv_patientGender);
			TextView tv_patientAge = (TextView)view.findViewById(R.id.tv_patientAge);
			TextView tv_patientDoctor = (TextView)view.findViewById(R.id.tv_patientDoctor);
			TextView tv_patientDiagnosisTime = (TextView)view.findViewById(R.id.tv_patientDiagnosisTime);
			
			tv_patientName.setText(list.get(position).get(0));
			tv_patientPhone.setText(list.get(position).get(1));
			tv_patientGender.setText(list.get(position).get(2));
			tv_patientAge.setText(list.get(position).get(3));
			tv_patientDoctor.setText(list.get(position).get(4));
			tv_patientDiagnosisTime.setText(list.get(position).get(5));
			return view;
		}

	}
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient, menu);
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
