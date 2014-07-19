package com.project.devicemeneger;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_DATA = "Devicedata";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_DATA = "data";
 // public static final String COLUMN_IP = "ip";
  public static final String KEY_PLAYERS_NAME = "name";

  private static final String DATABASE_NAME = "DEVICE_DATA.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_DATA + "(" + COLUMN_ID
      + " integer primary key autoincrement, " + COLUMN_DATA  +  " text not null);";

  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) throws SQLException{
	  System.out.println("onCreate, DATABASE_CREATE=="+ DATABASE_CREATE);
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
    onCreate(db);
  }

} 


