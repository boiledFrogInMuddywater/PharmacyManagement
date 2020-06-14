package com.example.pharmacymanagement.doctor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.pharmacymanagement.DBParserUtils;
import com.example.pharmacymanagement.Medicine;
import com.example.pharmacymanagement.MyOpenHelper;
import com.example.pharmacymanagement.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DiagnosticActivity extends Activity implements OnClickListener {
	private EditText et_patientName, et_patientAge;
	private EditText et_patientPhone, et_patientDesc;
	private EditText et_patientJudge;
	private RadioGroup rg_patientSex;
	private RadioButton rb_manPatient, rb_femalePatient;
	private Button bt_prescribe, bt_submit;
	private ListView lv_selectMedicine;

	private TextView tv_selectedCompany, tv_selectedPrice;
	private EditText et_selectedCount;

	private View myviewondialog;

	private MyOpenHelper myOpenHelper;
	private List<Medicine> medicineList;
	private ArrayList<ArrayList<String>> listviewList;
	private int selectedId;
	private int doctorId;
	private AlertDialog mydialoginstance;
	private SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diagnostic);

		// ��ȡ��¼��ҽ����account
		Intent intent = getIntent();
		doctorId = intent.getIntExtra("doctorId", 0);

		LayoutInflater dialogXml = LayoutInflater.from(getApplicationContext());
		myviewondialog = dialogXml.inflate(R.layout.medicine_dialog, null);

		// �ҵ����пؼ�
		et_patientName = (EditText) findViewById(R.id.et_patientName);
		rg_patientSex = (RadioGroup) findViewById(R.id.rg_patientSex);

		rb_manPatient = (RadioButton) findViewById(R.id.rb_manPatient);
		rb_femalePatient = (RadioButton) findViewById(R.id.rb_femalePatient);

		et_patientAge = (EditText) findViewById(R.id.et_patientAge);
		et_patientPhone = (EditText) findViewById(R.id.et_patientPhone);

		et_patientDesc = (EditText) findViewById(R.id.et_patientDesc);
		et_patientJudge = (EditText) findViewById(R.id.et_patientJudge);

		bt_prescribe = (Button) findViewById(R.id.bt_prescribe);
		bt_submit = (Button) findViewById(R.id.bt_submit);
		lv_selectMedicine = (ListView) findViewById(R.id.lv_selectMedicine);

		// �ҵ�dialog�е�������Ҫ�Ŀؼ�
		tv_selectedCompany = (TextView) myviewondialog.findViewById(R.id.tv_selectedCompany);
		tv_selectedPrice = (TextView) myviewondialog.findViewById(R.id.tv_selectedPrice);
		et_selectedCount = (EditText) myviewondialog.findViewById(R.id.et_selectedCount);
		Spinner spinner = (Spinner) myviewondialog.findViewById(R.id.spinner_selected);

		// ���ÿ���ҩ�ﰴť�ļ������ύ��ť�ļ���
		bt_prescribe.setOnClickListener(this);
		bt_submit.setOnClickListener(this);
		myOpenHelper = new MyOpenHelper(getApplicationContext());
		db = myOpenHelper.getWritableDatabase();

		// ��ȡҩ��������е�ҩ����Ϣ
		medicineList = DBParserUtils.getMedicine(db);
		// ����spinner�е�ҩ����Ϣ
		ArrayList<String> spinnerList = new ArrayList<String>();
		for (Medicine medicine : medicineList) {
			spinnerList.add(medicine.getMedicineName());
		}
		// �洢listView����ʾ������
		listviewList = new ArrayList<ArrayList<String>>();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_dropdown_item,
				R.id.tv_spinner_dropdown, spinnerList);
		adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				// ����ѡ���ҩ�ｫ������dialog�����TextView���óɸ�ҩ���Ӧ����Ϣ
				tv_selectedCompany.setText(medicineList.get((int) id).getProductCompany());
				tv_selectedPrice.setText(String.valueOf(medicineList.get((int) id).getPrice()));
				// ��¼ѡ���id
				selectedId = (int) id;

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_prescribe:
			// ����һ��dialogѡ��ҩ��
			if (mydialoginstance == null) {
				mydialoginstance = new AlertDialog.Builder(DiagnosticActivity.this).setTitle("����ҩ��")
						.setView(myviewondialog).setPositiveButton("�ύ", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// ����Ի����е��ύ��ť��,��Dialog�е��������õ�listview��
								ArrayList<String> data = new ArrayList<String>();
								data.add(medicineList.get(selectedId).getMedicineName());
								data.add(medicineList.get(selectedId).getProductCompany());
								data.add(medicineList.get(selectedId).getPrice().toString());
								data.add(et_selectedCount.getText().toString());
								// ��һid�������Ĳ���
								data.add(String.valueOf(medicineList.get(selectedId).getMedicineId()));
								listviewList.add(data);
								lv_selectMedicine.setAdapter(new MyAdapter());
							}
						}).create();
				mydialoginstance.show();
			} else {
				mydialoginstance.show();// ��ʾ�Ի���
			}

			break;

		case R.id.bt_submit:
			// ����ύ��Ͱ��������ݸ��µ����ݿ�
			submitAllMessage();
			break;
		}

	}

	public void submitAllMessage() {
		try {
			// ����ҩƷ������
						updateMedicineCount();
			// �Ȳ���һ�����ߵ���Ϣ����
			int patientId = insertPatient();
			
			// ����һ��ҩ������
			insertPharmacyMess(patientId);
			Toast.makeText(getApplicationContext(), "�ύ�ɹ�", 0).show();
		}catch (Exception e) {
			//������е���ѡҩ��
			listviewList.clear();
			Toast.makeText(getApplicationContext(), "�ύʧ��", 0).show();
//			e.printStackTrace();
		}
	}
			
	public void insertPharmacyMess(int patientId) {
		//����������Ϣmedicines������ҩ�����ֵ�String
		String medicines="";
		for(ArrayList<String> data:listviewList) {
			medicines += data.get(0)+"��";
		}
		medicines = medicines.substring(0, medicines.length()-1);
		
		String diseaseDescription = et_patientDesc.getText().toString().trim();
		String doctorJudge = et_patientJudge.getText().toString().trim();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=sdf.format(date.getTime());
		//doctorId��patientId ����id��ҽ��id
		ContentValues values = new ContentValues();
		values.put("doctorId", doctorId);
		values.put("medicines", medicines);
		values.put("patientId", patientId);
		values.put("diseaseDescription", diseaseDescription);
		values.put("doctorJudge", doctorJudge);
		values.put("createTime", createTime);
		db.insert("pharmacys", null, values);
	}
	public void updateMedicineCount() {
		// ����listviewList��ȡ����ҩƷ��id
		for (ArrayList<String> mess : listviewList) {
			// �õ�ҩƷid��ҽ����������
			int medicinesNum = Integer.valueOf(mess.get(3));
			String medicineId = mess.get(4);
			// ����ҩƷ����
			Cursor cursor = db.rawQuery("select saleTotalNum, totalNum from medicines where medicineId=?",
					new String[] { medicineId });
			int saleTotalNum = 0;
			int totalNum = 0;
			if (cursor != null && cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					saleTotalNum = cursor.getInt(0);
					totalNum = cursor.getInt(1);
				}
			}
			if (totalNum < medicinesNum) {
				Toast.makeText(getApplicationContext(), "ҩƷ��" + medicineId + "��ǰҩ����û����ô�����������ѡ������ҩ��", 1).show();
				//
				throw new RuntimeException();
			}
			String sale = String.valueOf(saleTotalNum + medicinesNum);
			String total = String.valueOf(totalNum - medicinesNum);
			db.execSQL("update medicines set saleTotalNum=?, totalNum=? where medicineId=?",
					new String[] { sale, total, medicineId });
		}
	}

	public int insertPatient() {
		// �Ȼ�ȡ�����ϵ�����
		String name = et_patientName.getText().toString().trim();
		String age = et_patientAge.getText().toString().trim();
		String phone = et_patientPhone.getText().toString().trim();
		String sex = "";
		switch (rg_patientSex.getCheckedRadioButtonId()) {
		case R.id.rb_manPatient:
			sex = "��";
			break;
		default:
			sex = "Ů";
			break;
		}
		// ����һ���µĻ�������
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("gender", sex);
		values.put("year", Integer.valueOf(age));
		values.put("phone", phone);
		db.insert("patients", null, values);
		// ��ȡ�²���Ļ��ߵ�id
		int patientId = 0;
		Cursor cursor = db.rawQuery("select max(patientId) from patients", null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				patientId = cursor.getInt(0);
			}
		}
		return patientId;
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return listviewList.size();
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
				// �����ִ���Ͳ�ķ���
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.dialog_listview_item, null);
			} else {
				view = convertView;
			}
			// �ҵ��҂��Ŀؼ��� ��ʾ�������������
			TextView tv_dl_lv_name = (TextView) view.findViewById(R.id.tv_dl_lv_name);
			TextView tv_dl_lv_company = (TextView) view.findViewById(R.id.tv_dl_lv_company);
			TextView tv_dl_lv_price = (TextView) view.findViewById(R.id.tv_dl_lv_price);
			TextView tv_dl_lv_count = (TextView) view.findViewById(R.id.tv_dl_lv_count);

			tv_dl_lv_name.setText(listviewList.get(position).get(0));
			tv_dl_lv_company.setText(listviewList.get(position).get(1));
			tv_dl_lv_price.setText(listviewList.get(position).get(2));
			tv_dl_lv_count.setText(listviewList.get(position).get(3));

			return view;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.diagnostic, menu);
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
