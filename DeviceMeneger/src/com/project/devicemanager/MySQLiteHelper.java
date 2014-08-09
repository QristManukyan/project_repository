package com.project.devicemanager;

import java.util.LinkedList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.database.Cursor;
import android.database.SQLException;

public class MySQLiteHelper extends SQLiteOpenHelper {

	private MySQLiteHelper dbHelper;

	private static final int DATABASE_VERSION = 1;

	public MySQLiteHelper(Context context, String DATABASE_NAME) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) throws SQLException {

		// SQL statement to create table
		String DATABASE_CREATE = "CREATE TABLE devices ( "
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "name TEXT, " 
				+ "owner TEXT, " 
				+ "deviceIp TEXT )";

		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS ");
		onCreate(db);
	}

	// Devices Table name
	public static final String TABLE_DEVICES = "Devices";

	// Devices Table Columns names
	private static final String KEY_ID = "id";

	//	private static final String KEY_DEVICE_ID = "deviceID";
	private static final String KEY_NAME = "name";
	private static final String KEY_OWNER = "owner";
	private static final String KEY_IP = "deviceIp";

	private static final String[] COLUMNS = { KEY_ID, KEY_NAME,  KEY_OWNER,
			KEY_IP };

	//Add device in database
	public Device addDevice(Device device) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, device.getName());
		values.put(KEY_OWNER, device.getOwner());
		values.put(KEY_IP, device.getIp());

		// insert
		long insertId = db.insert(TABLE_DEVICES, null, values);
		Cursor cursor = db.query(MySQLiteHelper.TABLE_DEVICES, COLUMNS,
				MySQLiteHelper.KEY_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Device newInfo = cursorToInfo(cursor);
		db.close();
		return newInfo;
	}

	private Device cursorToInfo(Cursor cursor) {
		Device info = new Device( null, 0, 0);
		info.setId(cursor.getInt(0));
		info.setName(cursor.getString(1));
		return info;
	}

	//Get Deivce from database
	public Device getDevice(int id) {

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_DEVICES, COLUMNS, " id = ?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();
		Device device = new Device();
		device.setId(Integer.parseInt(cursor.getString(0)));
		device.setName(cursor.getString(1));
		device.setOwner(cursor.getInt(2));
		device.setIp(cursor.getInt(3));
		//System.out.println("getDevice(" + id + ")" + device.toString());
		return device;
	}
	
	// Get next device id
	public int getNextId(){
		
		int id = 0;
		String query = "SELECT  * FROM " + TABLE_DEVICES;
		SQLiteDatabase database = this.getReadableDatabase();
		Cursor cursor = database.rawQuery(query, null);
		if(cursor!=null)
			cursor.moveToLast();
		id = Integer.parseInt(cursor.getString(0));
		int nextId = id + 1;
		return nextId;
	}
	
	
	// Getting devices Count
	    public int getDevicesCount() {
	        String countQuery = "SELECT  * FROM " + TABLE_DEVICES;
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(countQuery, null);
	        return cursor.getCount();
	    }
	
	// Get All devices
	public List<Device> getAllDevices() {
		List<Device> devices = new LinkedList<Device>();

		String query = "SELECT  * FROM " + TABLE_DEVICES;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		Device device = null;
		if (cursor.moveToFirst()) {
			do {
				device = new Device();
				device.setId(Integer.parseInt(cursor.getString(0)));
				device.setName(cursor.getString(1));
				device.setOwner(cursor.getInt(2));
				device.setIp(cursor.getInt(3));
				devices.add(device);
			} while (cursor.moveToNext());
		}
		return devices;
	}

	// Updating single device
	public int updateDevice(Device device) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", device.getName());
		values.put("owner", device.getOwner());
		values.put("deviceIp", device.getIp());

		// updating row
		int i = db.update(TABLE_DEVICES, values, KEY_ID + " = ?",
				new String[] { String.valueOf(device.getId()) });
		db.close();

		return i;

	}

	// Deleting single Device
	public void deleteDevice(Device device) {

		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_DEVICES, KEY_ID + " = ?",
				new String[] { String.valueOf(device.getId()) });
		db.close();

	}

	public void open() throws SQLException {
		dbHelper.getWritableDatabase();
	}

}
