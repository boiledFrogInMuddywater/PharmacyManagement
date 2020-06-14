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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class MedicineActivity extends Activity {
	private Button bt_addMedicine;
	private ListView lv_medicine;
	private List<Medicine> medicinelists;
	private MyOpenHelper myOpenHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medicine);
		
		bt_addMedicine = (Button) findViewById(R.id.bt_addMedicine);
		lv_medicine = (ListView) findViewById(R.id.lv_medicine);
		//��ʾҩ������
		initMedicineListView();
		bt_addMedicine.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ��ת�����ҩ����Ϣ�Ľ���
				Intent intent = new Intent(MedicineActivity.this, AddMedicineActivity.class);
				startActivity(intent);
			}
		});
		
		
		
		
		//ΪlistView���ó����¼�
		lv_medicine.setOnItemLongClickListener(new OnItemLongClickListener() {
					 
					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View view,
							final int position, long id) {
						//����AlertDialog.Builder���󣬵������б����ʱ�򵯳�ȷ��ɾ���Ի���
						AlertDialog.Builder builder=new Builder(MedicineActivity.this);
						builder.setMessage("ȷ��ɾ��?");
						builder.setTitle("��ʾ");
						//���AlertDialog.Builder�����setPositiveButton()����
						builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Medicine medicine = medicinelists.remove(position);
								if(medicine!=null){
									//�ҵ�ɾ����ҩ����Ϣ��Ϣ
									int medicineId = medicine.getMedicineId();
									SQLiteDatabase db = myOpenHelper.getWritableDatabase();
									int delete = db.delete("medicines", "medicineId=?", new String[] {String.valueOf(medicineId)});
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
		lv_medicine.setOnItemClickListener(new OnItemClickListener() {
					
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub
						TextView tv_medicineId = (TextView) view.findViewById(R.id.tv_medicineId);
						String medicineId = tv_medicineId.getText().toString();
						//��������ҽ����Ϣ�޸Ľ���
						Intent intent = new Intent(MedicineActivity.this, ChangeMedicineMessActivity.class);
						intent.putExtra("medicineId", medicineId);
						startActivity(intent);
					}
				});
	}
	
	
	
	
	
	
	private void initMedicineListView() {
		// �����ݿ��л�ȡҽ������Ϣ
		myOpenHelper = new MyOpenHelper(getApplicationContext());
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		medicinelists = DBParserUtils.getMedicine(db);
		db.close();
		//�������Ϣ����ʾ
		if (medicinelists!=null) {
			lv_medicine.setAdapter(new MyAdapter());
		}
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			System.out.println("�б��size��");
			System.out.println(medicinelists.size());
			return medicinelists.size();
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
				view = inflater.inflate(R.layout.medicine_item, null);
			} else {
				view = convertView;
			}
			// �ҵ��҂��Ŀؼ��� ��ʾ�������������
			TextView tv_medicineId = (TextView)view.findViewById(R.id.tv_medicineId);
			TextView tv_medicineName = (TextView)view.findViewById(R.id.tv_medicineName);
			TextView tv_medicineCompany = (TextView)view.findViewById(R.id.tv_medicineCompany);
			TextView tv_medicieType = (TextView)view.findViewById(R.id.tv_medicieType);
			TextView tv_medicineProductTime = (TextView)view.findViewById(R.id.tv_medicineProductTime);
			TextView tv_medicineEndTime = (TextView)view.findViewById(R.id.tv_medicineEndTime);
			TextView tv_medicineDescription = (TextView)view.findViewById(R.id.tv_medicineDescription);
			TextView tv_medicinePrice = (TextView)view.findViewById(R.id.tv_medicinePrice);
			TextView tv_medicineTotalCount = (TextView)view.findViewById(R.id.tv_medicineTotalCount);
			TextView tv_medicineSaleNum = (TextView)view.findViewById(R.id.tv_medicineSaleNum);

			tv_medicineId.setText(String.valueOf(medicinelists.get(position).getMedicineId()));
			tv_medicineName.setText(medicinelists.get(position).getMedicineName()  );
			tv_medicineCompany.setText(medicinelists.get(position).getProductCompany() );
			tv_medicieType.setText(medicinelists.get(position).getType()  );
			tv_medicineProductTime.setText(medicinelists.get(position).getProductTime()  );
			tv_medicineEndTime.setText(medicinelists.get(position).getEndTime()  );
			tv_medicineDescription.setText(medicinelists.get(position).getDescription()  );
			tv_medicinePrice.setText(String.valueOf(medicinelists.get(position).getPrice()));
			tv_medicineTotalCount.setText(String.valueOf(medicinelists.get(position).getTotalNum()));
			tv_medicineSaleNum.setText(String.valueOf(medicinelists.get(position).getSaleTotalNum()));

			return view;
		}

	}
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.medicine, menu);
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
