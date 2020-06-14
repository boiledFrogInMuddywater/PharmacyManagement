package com.example.pharmacymanagement;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeInventoryActivity extends Activity implements OnClickListener {

	private EditText et_inventoryMdcName, et_inventoryMdcNum;
	private Button bt_inventoryIn, bt_inventoryOut;
	private MyOpenHelper myOpenHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_inventory);

		et_inventoryMdcName = (EditText) findViewById(R.id.et_inventoryMdcName);
		et_inventoryMdcNum = (EditText) findViewById(R.id.et_inventoryMdcNum);
		bt_inventoryIn = (Button) findViewById(R.id.bt_inventoryIn);
		bt_inventoryOut = (Button) findViewById(R.id.bt_inventoryOut);

		myOpenHelper = new MyOpenHelper(getApplicationContext());

		bt_inventoryIn.setOnClickListener(this);
		bt_inventoryOut.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		String name = et_inventoryMdcName.getText().toString().trim();
		int num = Integer.valueOf(et_inventoryMdcNum.getText().toString().trim());
		// ��ʼ������
		int medicineId = 0;
		int totalNum = 0;
		// �ж�ҩƷ�Ƿ���ں�����
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		Cursor cursor = db.query("medicines", new String[] { "medicineId", "totalNum" }, "medicineName=?",
				new String[] { name }, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				medicineId = cursor.getInt(0);
				totalNum = cursor.getInt(1);
			}
		} else {
			Toast.makeText(getApplicationContext(), "��ҩƷ�����ڣ������Ҫ������ҩƷ��Ϣ���ٲ���", 1).show();
			return;
		}

		Date dNow = new Date();
		SimpleDateFormat fdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String changetime = fdf.format(dNow);

		switch (v.getId()) {
		case R.id.bt_inventoryIn:
			// �������ڿ��������Ӽ�¼���޸�ҩƷ�������
		
			
			ContentValues values = new ContentValues();
			values.put("changeNum", num);
			values.put("changeType", "���");
			values.put("medicineId", medicineId);
			values.put("changetime", changetime);
			// ����ֵ�����²����е�id
			long insert = db.insert("inventorys", null, values);
			//�޸�ҩƷ���е�������
			values = new ContentValues();
			values.put("totalNum", totalNum+num);
			db.update("medicines", values, "medicineId=?", new String[]{String.valueOf(medicineId)});
			Toast.makeText(getApplicationContext(), "���ɹ�", 1).show();
			break;

		default:
			// ��������ڿ��������Ӽ�¼���޸�ҩƷ�������
			//�ж�ҩƷ�����Ƿ����Ҫ�������
			if(num>totalNum) {
				Toast.makeText(getApplicationContext(), "�����������ܴ���ҩƷ��������ǰҩƷ������"+String.valueOf(totalNum), 1).show();
				return;
			}
			values = new ContentValues();
			values.put("changeNum", num);
			values.put("changeType", "����");
			values.put("medicineId", medicineId);
			values.put("changetime", changetime);
			// ����ֵ�����²����е�id
			insert = db.insert("inventorys", null, values);
			//�޸�ҩƷ���е�������
			values = new ContentValues();
			values.put("totalNum", totalNum-num);
			db.update("medicines", values, "medicineId=?", new String[]{String.valueOf(medicineId)});
			Toast.makeText(getApplicationContext(), "����ɹ�", 1).show();
			break;
		}
		db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_inventory, menu);
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
