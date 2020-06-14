package com.example.pharmacymanagement;

import java.util.ArrayList;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class InventoryActivity extends Activity {
	private ArrayList<ArrayList<String>> list;
	private ListView lv_inventory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory);

		lv_inventory = (ListView) findViewById(R.id.lv_inventory);
		// ��ʾ����
		initInventoryListView();

	}

	private void initInventoryListView() {
		// �����ݿ��л�ȡҽ������Ϣ
		MyOpenHelper myOpenHelper = new MyOpenHelper(getApplicationContext());
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		list = DBParserUtils.getInventoryInOutMess(db);
		db.close();
		// �������Ϣ����ʾ
		if (list != null) {
			lv_inventory.setAdapter(new MyAdapter());
		}
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
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
				// �����ִ���Ͳ�ķ���
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.inventory_item, null);
			} else {
				view = convertView;
			}
			// �ҵ��҂��Ŀؼ��� ��ʾ�������������
			TextView tv_inventoryMdcName = (TextView) view.findViewById(R.id.tv_inventoryMdcName);
			TextView tv_inventoryMdcOption = (TextView) view.findViewById(R.id.tv_inventoryMdcOption);
			TextView tv_inventoryMdcNum = (TextView) view.findViewById(R.id.tv_inventoryMdcNum);
			TextView tv_inventoryMdcCompany = (TextView) view.findViewById(R.id.tv_inventoryMdcCompany);
			TextView tv_inventoryMdcOptionTime = (TextView) view.findViewById(R.id.tv_inventoryMdcOptionTime);

			tv_inventoryMdcName.setText(list.get(position).get(0));
			tv_inventoryMdcOption.setText(list.get(position).get(1));
			tv_inventoryMdcNum.setText(list.get(position).get(2));
			tv_inventoryMdcCompany.setText(list.get(position).get(3));
			tv_inventoryMdcOptionTime.setText(list.get(position).get(4));
			return view;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inventory, menu);
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
