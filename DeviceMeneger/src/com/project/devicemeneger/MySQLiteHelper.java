package com.project.devicemeneger;

import java.util.LinkedList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	
  //Database Version
  private static final int DATABASE_VERSION = 1;

  // Database Name
  private static final String DATABASE_NAME = "DEVICESBASE.db";
 
  public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }


//Devices table name
 


  @Override
  public void onCreate(SQLiteDatabase database) throws SQLException{
	  
	// SQL statement to create  table
			String DATABASE_CREATE = "CREATE TABLE devices ( " +
	                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
	                "name TEXT, "+
					"deviceID TEXT, "+
					"owner TEXT, "+
					"deviceIp TEXT )";
			
	// create  table
	database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// Drop older  table if existed
	  db.execSQL("DROP TABLE IF EXISTS ");
	// create fresh  table
    onCreate(db);
  }
  
  //Devices Table name
 public static final String TABLE_DEVICES= "Devices";
  
//Devices Table Columns names
  private static final String KEY_ID = "id";
  
  private static final String KEY_DEVICE_ID = "deviceID";
  private static final String KEY_NAME = "name";
  private static final String KEY_OWNER = "owner";
  private static final String KEY_IP = "deviceIp";
  
  
  private static final String[] COLUMNS = {KEY_ID,KEY_NAME,KEY_DEVICE_ID,KEY_OWNER,KEY_IP};
  
  
  
  public Device addDevice(Device device){
		Log.d("addDevice", device.toString());
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		 
		// 2. create ContentValues to add key "column"/value
      ContentValues values = new ContentValues();
      values.put(KEY_DEVICE_ID, device.getId());
      values.put(KEY_NAME, device.getName()); 
      values.put(KEY_OWNER, device.getOwner()); 
      values.put(KEY_IP, device.getIp());

      // 3. insert
      long insertId =  db.insert(TABLE_DEVICES, // table
      		null, //nullColumnHack
      		values); // key/value -> keys = column names/ values = column values
      Cursor cursor = db.query(MySQLiteHelper.TABLE_DEVICES, COLUMNS,
				MySQLiteHelper.KEY_ID + " = " + insertId, null, null, null,
				null);
      cursor.moveToFirst();
      Device  newInfo = cursorToInfo(cursor);
      System.out.println("db"+db.toString());
      System.out.println("values"+values);
      // 4. close
      db.close(); 
      return  newInfo;
	}
	
	private Device cursorToInfo(Cursor cursor) {
		Device info = new Device(0, null, 0, 0);
		info.setId(cursor.getInt(0));
		info.setName(cursor.getString(1));
		return info;
	}


	public Device getDevice(int id){

		// 1. get reference to readable DB
		SQLiteDatabase db = this.getReadableDatabase();
		 
		// 2. build query
      Cursor cursor = 
      		db.query(TABLE_DEVICES, // a. table
      		COLUMNS, // b. column names
      		" id = ?", // c. selections 
              new String[] { String.valueOf(id) }, // d. selections args
              null, // e. group by
              null, // f. having
              null, // g. order by
              null); // h. limit
      
      // 3. if we got results get the first one
      if (cursor != null)
          cursor.moveToFirst();

      // 4. build device object
      Device device = new Device();
  //    device.setId(Integer.parseInt(cursor.getString(0)));
      device.setId(cursor.getInt(1));
      device.setName(cursor.getString(2));
      device.setOwner(cursor.getInt(3));
      device.setIp(cursor.getInt(4));

		System.out.println("getDevice("+id+")" + device.toString());

      // 5. return Device
      return device;
	}
	
	// Get All device
  public List<Device> getAllDevices() {
      List<Device> devices = new LinkedList<Device>();
      

      // 1. build the query
      String query = "SELECT  * FROM " + TABLE_DEVICES;

  	  // 2. get reference to writable DB
      SQLiteDatabase db = this.getWritableDatabase();
      Cursor cursor = db.rawQuery(query, null);
      
      // 3. go over each row, build device and add it to list
      Device device = null;
      if (cursor.moveToFirst()) {
          do {
        	  device = new Device();
        	 // device.setId(Integer.parseInt(cursor.getString(0)));
        	  device.setId(cursor.getInt(1));
              device.setName(cursor.getString(2));
              device.setOwner(cursor.getInt(3));
              device.setIp(cursor.getInt(4));


              // Add device to devices
        	  devices.add(device);
          } while (cursor.moveToNext());
      }
      
		System.out.println("getAllDevices to string"+ devices.toString());
      // return devices
      return devices;
  }
	
	 // Updating single device
  public int updateDevice(Device device) {

  	// 1. get reference to writable DB
      SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
      ContentValues values = new ContentValues();
      values.put("title", device.getName()); // get title 
      values.put("author", device.getOwner()); // get author

      // 3. updating row
      int i = db.update(TABLE_DEVICES, //table
      		values, // column/value
      		KEY_ID+" = ?", // selections
              new String[] { String.valueOf(device.getId()) }); //selection args
      
      // 4. close
      db.close();
      
      return i;
      
  }

  // Deleting single Device
  public void deleteDevice(Device device) {

  	// 1. get reference to writable DB
      SQLiteDatabase db = this.getWritableDatabase();
      
      // 2. delete
      db.delete(TABLE_DEVICES,
      		KEY_ID+" = ?",
              new String[] { String.valueOf(device.getId()) });
      
      // 3. close
      db.close();
      

  }
  public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

} 
