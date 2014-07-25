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
	
  private MySQLiteHelper dbHelper;
	
  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "DEVICESBASE.db";
 
  public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

  @Override
  public void onCreate(SQLiteDatabase database) throws SQLException{
	  
	// SQL statement to create  table
			String DATABASE_CREATE = "CREATE TABLE devices ( " +
	                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					"deviceID TEXT, "+
					"name TEXT, "+
					"owner TEXT, "+
					"deviceIp TEXT )";
			
	// create  table
	database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	db.execSQL("DROP TABLE IF EXISTS ");
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
		// get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		 
		// create ContentValues to add key "column"/value
      ContentValues values = new ContentValues();
      values.put(KEY_DEVICE_ID, device.getId());
      values.put(KEY_NAME, device.getName()); 
      values.put(KEY_OWNER, device.getOwner()); 
      values.put(KEY_IP, device.getIp());

      // insert
      long insertId =  db.insert(TABLE_DEVICES, null, values);
      Cursor cursor = db.query(MySQLiteHelper.TABLE_DEVICES, COLUMNS,
				MySQLiteHelper.KEY_ID + " = " + insertId, null, null, null,
				null);
      cursor.moveToFirst();
      Device  newInfo = cursorToInfo(cursor);
      //  close
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

		// get reference to readable DB
		SQLiteDatabase db = this.getReadableDatabase();
		 
		// build query
      Cursor cursor = 
      		db.query(TABLE_DEVICES, 
      		COLUMNS, 
      		" id = ?", 
              new String[] { String.valueOf(id) }, 
              null, 
              null, 
              null, 
              null);
      
      // if we got results get the first one
      if (cursor != null)
          cursor.moveToFirst();

      //  build device object
      Device device = new Device();
      // device.setId(Integer.parseInt(cursor.getString(0)));
      device.setId(cursor.getInt(1));
      device.setName(cursor.getString(2));
      device.setOwner(cursor.getInt(3));
      device.setIp(cursor.getInt(4));

		System.out.println("getDevice("+id+")" + device.toString());

      return device;
	}
	
	// Get All device
  public List<Device> getAllDevices() {
      List<Device> devices = new LinkedList<Device>();
      
      //  build the query
      String query = "SELECT  * FROM " + TABLE_DEVICES;

  	  //  get reference to writable DB
      SQLiteDatabase db = this.getWritableDatabase();
      Cursor cursor = db.rawQuery(query, null);
      
      //  go over each row, build device and add it to list
      Device device = null;
      if (cursor.moveToFirst()) {
          do {
        	  device = new Device();
        	 // device.setId(Integer.parseInt(cursor.getString(0)));
        	  device.setId(cursor.getInt(1));
              device.setName(cursor.getString(2));
              device.setOwner(cursor.getInt(3));
              device.setIp(cursor.getInt(4));
        	  devices.add(device);
          } while (cursor.moveToNext());
      }
      return devices;
  }
	
	 // Updating single device
  public int updateDevice(Device device) {

	  // get reference to writable DB
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();
      values.put("name", device.getName()); 
      values.put("owner", device.getOwner());
      values.put("deviceID", device.getId());
      values.put("deviceIp", device.getIp());
      
      //  updating row
      int i = db.update(TABLE_DEVICES, 
      		values, 
      		KEY_ID+" = ?",
            new String[] { String.valueOf(device.getId()) }); 
      db.close();
      
      return i;
      
  }

  // Deleting single Device
  public void deleteDevice(Device device) {

      SQLiteDatabase db = this.getWritableDatabase();
      db.delete(TABLE_DEVICES,
      		KEY_ID+" = ?",
              new String[] { String.valueOf(device.getId()) });
      db.close();
      

  }
  public void open() throws SQLException {
		dbHelper.getWritableDatabase();
	}

} 
