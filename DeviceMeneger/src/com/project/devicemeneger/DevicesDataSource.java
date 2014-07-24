package com.project.devicemeneger;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DevicesDataSource {

	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_DATA};

	public DevicesDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Device createInfo(String name) throws SQLException {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_DATA, name);
		


		long insertId = database.insert(MySQLiteHelper.TABLE_DATA, null, values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_DATA, allColumns,
				MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null,
				null);
		System.out.println("insertId"+insertId);
		cursor.moveToFirst();
		Device  newInfo = cursorToInfo(cursor);
		 System.out.println("newInfo"+newInfo);
		
		cursor.close();
		
		return  newInfo;
//		open();
//		 
//		// 2. create ContentValues to add key "column"/value
//        ContentValues values = new ContentValues();
//        values.put(MySQLiteHelper.COLUMN_DATA, name); // get title 
//       // values.put(KEY_AUTHOR, book.getAuthor()); // get author
// 
//        // 3. insert
//        database.insert(MySQLiteHelper.TABLE_DATA, // table
//        		null, //nullColumnHack
//        		values); // key/value -> keys = column names/ values = column values
//        
//        // 4. close
//        database.close();
		
	}

	// public void insertInfos(String info) {
	// System.out.println("insertInfos");
	// ContentValues values = new ContentValues();
	// values.put(MySQLiteHelper.COLUMN_DATA, info);
	// // values.put(MySQLiteHelper.COLUMN_IP, ip);
	// database.insert("MySQLiteHelper.TABLE_DATA", null, values);
	// database.close();
	//
	// }

	public void deleteInfo(Device info) {
		long id = info.getId();
		database.delete(MySQLiteHelper.TABLE_DATA, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Device> getAllInfos() throws SQLException {
		List<Device> infos = new ArrayList<Device>();
		System.out.println("this is  getAllInfos function");
		Cursor cursor = database.query(MySQLiteHelper.TABLE_DATA, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Device info = cursorToInfo(cursor);
			infos.add(info);
			cursor.moveToNext();
		}
		cursor.close();
		return infos;

	}
	
	public List<Device> getMoreInfos() throws SQLException {
		List<Device> infos = new ArrayList<Device>();
		System.out.println("this is  getMoreInfos function");
		Cursor cursor = database.query(MySQLiteHelper.TABLE_DATA, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Device info = cursorToInfo(cursor);
			infos.add(info);
			cursor.moveToNext();
		}
		cursor.close();
		return infos;
//		SQLiteDatabase db = DeviceManageActivity.db;
//		 Cursor c = db.query("mytable", null, null, null, null, null, null);
//
//	      if (c.moveToFirst()) {
//
//	        int idColIndex = c.getColumnIndex("id");
//	        int nameColIndex = c.getColumnIndex("name");
//	        int emailColIndex = c.getColumnIndex("email");
//
//	        do {
//	          System.out.println(
//	              "ID = " + c.getInt(idColIndex) + 
//	              ", name = " + c.getString(nameColIndex) + 
//	              ", email = " + c.getString(emailColIndex));
//	          Device info = cursorToInfo(c);
//	          infos.add(info);
//	          c.moveToNext();
//	          
//	        } while (c.moveToNext());
//	      } else
//	        System.out.println( "0 rows");
//	      c.close();
//	      
//	      return infos;

	}
	

	public List<Device> getResentInfos() throws SQLException {
		List<Device> infos = new ArrayList<Device>();
		System.out.println("this is getResentInfos function");
		Cursor cursor = database.query(MySQLiteHelper.TABLE_DATA, allColumns,
				null, null, null, null, null);
		cursor.moveToFirst();
			Device info = cursorToInfo(cursor);
			infos.add(info);
		cursor.close();
		return infos;

	}

	private Device cursorToInfo(Cursor cursor) {
		Device info = new Device(0, null, 0, 0);
		info.setId(cursor.getInt(0));
		info.setName(cursor.getString(1));
		return info;
	}
}
