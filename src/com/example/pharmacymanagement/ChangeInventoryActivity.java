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
		// 初始化数据
		int medicineId = 0;
		int totalNum = 0;
		// 判断药品是否存在和数量
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		Cursor cursor = db.query("medicines", new String[] { "medicineId", "totalNum" }, "medicineName=?",
				new String[] { name }, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				medicineId = cursor.getInt(0);
				totalNum = cursor.getInt(1);
			}
		} else {
			Toast.makeText(getApplicationContext(), "该药品不存在，请添加要操作的药品信息后再操作", 1).show();
			return;
		}

		Date dNow = new Date();
		SimpleDateFormat fdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String changetime = fdf.format(dNow);

		switch (v.getId()) {
		case R.id.bt_inventoryIn:
			// 入库管理，在库存表中增加记录，修改药品表的数量
		
			
			ContentValues values = new ContentValues();
			values.put("changeNum", num);
			values.put("changeType", "入库");
			values.put("medicineId", medicineId);
			values.put("changetime", changetime);
			// 返回值代表新插入行的id
			long insert = db.insert("inventorys", null, values);
			//修改药品表中的总数量
			values = new ContentValues();
			values.put("totalNum", totalNum+num);
			db.update("medicines", values, "medicineId=?", new String[]{String.valueOf(medicineId)});
			Toast.makeText(getApplicationContext(), "入库成功", 1).show();
			break;

		default:
			// 出库管理，在库存表中增加记录，修改药品表的数量
			//判断药品数量是否大于要出库的量
			if(num>totalNum) {
				Toast.makeText(getApplicationContext(), "出库数量不能大于药品总数，当前药品总数："+String.valueOf(totalNum), 1).show();
				return;
			}
			values = new ContentValues();
			values.put("changeNum", num);
			values.put("changeType", "出库");
			values.put("medicineId", medicineId);
			values.put("changetime", changetime);
			// 返回值代表新插入行的id
			insert = db.insert("inventorys", null, values);
			//修改药品表中的总数量
			values = new ContentValues();
			values.put("totalNum", totalNum-num);
			db.update("medicines", values, "medicineId=?", new String[]{String.valueOf(medicineId)});
			Toast.makeText(getApplicationContext(), "出库成功", 1).show();
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
