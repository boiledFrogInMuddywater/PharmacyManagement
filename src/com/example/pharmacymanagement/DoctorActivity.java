package com.example.pharmacymanagement;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DoctorActivity extends Activity{
	private ListView lv_doctor;
	private Button bt_addDoctor;
	private List<Doctor> doctorlists;
	private MyOpenHelper myOpenHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor);
		// 找到控件
		lv_doctor = (ListView) findViewById(R.id.lv_doctor);
		bt_addDoctor = (Button) findViewById(R.id.bt_addDoctor);
		initDoctorListView();
		
		//为listView设置长按事件
		lv_doctor.setOnItemLongClickListener(new OnItemLongClickListener() {
			 
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				//定义AlertDialog.Builder对象，当长按列表项的时候弹出确认删除对话框
				AlertDialog.Builder builder=new Builder(DoctorActivity.this);
				builder.setMessage("确定删除?");
				builder.setTitle("提示");
				//添加AlertDialog.Builder对象的setPositiveButton()方法
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Doctor doctor = doctorlists.remove(position);
						if(doctor!=null){
							//找到删除的医生信息
							int doctorId = doctor.getDoctorId();
							SQLiteDatabase db = myOpenHelper.getWritableDatabase();
							int delete = db.delete("doctors", "doctorId=?", new String[] {String.valueOf(doctorId)});
							if (delete>0) {
								Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
							}
							db.close();
							
						}else {
							Toast.makeText(getApplicationContext(), "删除失败", Toast.LENGTH_SHORT).show();
							System.out.println("failed");
						}
						MyAdapter myAdapter = new MyAdapter();
						myAdapter.notifyDataSetChanged();
						
					}
				});
				
				//添加AlertDialog.Builder对象的setNegativeButton()方法
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				
				builder.create().show();
				return true;
			}
		});

		
		//为listView设置点击事件
		lv_doctor.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				TextView tv_account = (TextView) view.findViewById(R.id.tv_account);
				String account = tv_account.getText().toString();
				//点击后进入医生信息修改界面
				Intent intent = new Intent(DoctorActivity.this, ChangeDoctorMessActivity.class);
				intent.putExtra("account", account);
				startActivity(intent);
			}
		});
		bt_addDoctor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到添加医生信息的界面
				Intent intent = new Intent(DoctorActivity.this, AddDoctorActivity.class);
				startActivity(intent);
			}
		});

	}
	


	private void initDoctorListView() {
		// TODO Auto-generated method stub

		// 在数据库中获取医生的信息
		myOpenHelper = new MyOpenHelper(getApplicationContext());
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		doctorlists = DBParserUtils.getDoctor(db);
		db.close();
		//如果有信息才显示
		if (doctorlists!=null) {
			lv_doctor.setAdapter(new MyAdapter());
		}
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			System.out.println("列表的size是");
			System.out.println(doctorlists.size());
			return doctorlists.size();
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
				view = inflater.inflate(R.layout.item, null);
			} else {
				view = convertView;
			}
			// 找到我們的控件， 显示集合里面的数据
			TextView tv_account = (TextView)view.findViewById(R.id.tv_account);
			TextView tv_name = (TextView)view.findViewById(R.id.tv_name);
			TextView tv_phone = (TextView)view.findViewById(R.id.tv_phone);
			TextView tv_email = (TextView)view.findViewById(R.id.tv_email);
			tv_account.setText(doctorlists.get(position).getAccount());
			tv_name.setText(doctorlists.get(position).getName());
			tv_phone.setText(doctorlists.get(position).getPhone());
			tv_email.setText(doctorlists.get(position).getEmail());

			return view;
		}

	}
}
