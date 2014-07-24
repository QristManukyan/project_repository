package com.project.devicemeneger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_DATA = "Devicedata";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_DATA = "data";
  
  
//  public static final String KEY_PLAYERS_ID = "_id";
//  public static final String KEY_PLAYERS_NAME = "name";
//  public static final String KEY_PLAYERS_SCORE = "score";
//  public static final String KEY_PLAYERS_ORDER = "turn";
//  
//  public static final String DATABASE_NAME = "scoreitdb";
//  public static final String TABLE_PLAYERS = "players";
//  public static final String TABLE_HISTORY = "history";
//
  private static final String DATABASE_NAME = "DEVICE_DATA.db";
  private static final int DATABASE_VERSION = 2;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_DATA + "(" + COLUMN_ID
      + " integer primary key autoincrement, " + COLUMN_DATA  +  " text not null);";

//  private static final String CREATE_TABLE_PLAYERS =
//	      "create table " + TABLE_PLAYERS + " (" + KEY_PLAYERS_ID + " integer primary key autoincrement, "
//	      + KEY_PLAYERS_NAME + " text not null, " + KEY_PLAYERS_SCORE + " long not null, "
//	      + KEY_PLAYERS_ORDER + " long not null);";
  
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
    db.execSQL("DROP TABLE IF EXISTS "+ TABLE_DATA);
    onCreate(db);
  }

} 
//
////
//class MySQLiteHelper extends SQLiteOpenHelper {
//
//    public MySQLiteHelper(Context context) {
//		super(context, "myDB", null, 1);
//		// TODO Auto-generated constructor stub
//	}
//
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//      System.out.println("--- onCreate database ---");
//      // создаем таблицу с полями
//      db.execSQL("create table mytable ("
//          + "id integer primary key autoincrement," 
//          + "name text,"
//          + "email text" + ");");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }
//  }
