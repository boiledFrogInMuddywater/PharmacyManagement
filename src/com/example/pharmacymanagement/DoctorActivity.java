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
		// �ҵ��ؼ�
		lv_doctor = (ListView) findViewById(R.id.lv_doctor);
		bt_addDoctor = (Button) findViewById(R.id.bt_addDoctor);
		initDoctorListView();
		
		//ΪlistView���ó����¼�
		lv_doctor.setOnItemLongClickListener(new OnItemLongClickListener() {
			 
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				//����AlertDialog.Builder���󣬵������б����ʱ�򵯳�ȷ��ɾ���Ի���
				AlertDialog.Builder builder=new Builder(DoctorActivity.this);
				builder.setMessage("ȷ��ɾ��?");
				builder.setTitle("��ʾ");
				//���AlertDialog.Builder�����setPositiveButton()����
				builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Doctor doctor = doctorlists.remove(position);
						if(doctor!=null){
							//�ҵ�ɾ����ҽ����Ϣ
							int doctorId = doctor.getDoctorId();
							SQLiteDatabase db = myOpenHelper.getWritableDatabase();
							int delete = db.delete("doctors", "doctorId=?", new String[] {String.valueOf(doctorId)});
							if (delete>0) {
								Toast.makeText(getApplicationContext(), "ɾ���ɹ�", Toast.LENGTH_SHORT).show();
							}
							db.close();
							
						}else {
							Toast.makeText(getApplicationContext(), "ɾ��ʧ��", Toast.LENGTH_SHORT).show();
							System.out.println("failed");
						}
						MyAdapter myAdapter = new MyAdapter();
						myAdapter.notifyDataSetChanged();
						
					}
				});
				
				//���AlertDialog.Builder�����setNegativeButton()����
				builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener(){
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				
				builder.create().show();
				return true;
			}
		});

		
		//ΪlistView���õ���¼�
		lv_doctor.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				TextView tv_account = (TextView) view.findViewById(R.id.tv_account);
				String account = tv_account.getText().toString();
				//��������ҽ����Ϣ�޸Ľ���
				Intent intent = new Intent(DoctorActivity.this, ChangeDoctorMessActivity.class);
				intent.putExtra("account", account);
				startActivity(intent);
			}
		});
		bt_addDoctor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��ת�����ҽ����Ϣ�Ľ���
				Intent intent = new Intent(DoctorActivity.this, AddDoctorActivity.class);
				startActivity(intent);
			}
		});

	}
	


	private void initDoctorListView() {
		// TODO Auto-generated method stub

		// �����ݿ��л�ȡҽ������Ϣ
		myOpenHelper = new MyOpenHelper(getApplicationContext());
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		doctorlists = DBParserUtils.getDoctor(db);
		db.close();
		//�������Ϣ����ʾ
		if (doctorlists!=null) {
			lv_doctor.setAdapter(new MyAdapter());
		}
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			System.out.println("�б��size��");
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
				//�����ִ���Ͳ�ķ���
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.item, null);
			} else {
				view = convertView;
			}
			// �ҵ��҂��Ŀؼ��� ��ʾ�������������
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
