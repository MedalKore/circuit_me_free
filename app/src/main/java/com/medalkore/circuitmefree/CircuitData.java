package com.medalkore.circuitmefree;

/**
 * Created by alemmons on 4/24/2014.
 */


import android.database.Cursor;
import android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;


public class CircuitData extends SQLiteOpenHelper {

  public static final String DB_NAME = "circuit.db";
  public static final String DB_TABLE1 = "circuit_title";
  public static final String DB_TABLE2 = "circuit_interval";
	public static final String TITLE = "title";
  public static final int VERSION = 1;
	public static final String CREATE_CIRCUIT_TITLE_TABLE = "CREATE TABLE " + DB_TABLE1 + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT);";
	public static final String CREATE_CIRCUIT_INTERVAL_TABLE = "CREATE TABLE " + DB_TABLE2 + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, title_id INTEGER, name TEXT, time INTEGER);";

  public CircuitData(Context context) {
    super(context, DB_NAME, null, VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
	  db.execSQL(CREATE_CIRCUIT_TITLE_TABLE);
	  db.execSQL(CREATE_CIRCUIT_INTERVAL_TABLE);
    newCircuitInterval(db, newCircuitTitle(db, "Hockey Workout"));
	}

  @Override
  public void onUpgrade(SQLiteDatabase db, int i, int i2) {
	  Log.w(CircuitData.class.getName(),
		  "Upgrading database from version " + i + " to "
			  + i2 + ", which will destroy all old data");
	  db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE1);
	  db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE2);
	  onCreate(db);
  }

	protected long newCircuitTitle(SQLiteDatabase database, String title) {
		ContentValues values = new ContentValues();
		values.put(TITLE, title);
		return database.insert(DB_TABLE1, null, values);
	}

  protected void newCircuitInterval(SQLiteDatabase db , long rowId){
    ContentValues values = new ContentValues();
    values.put("title_id", rowId);
    values.put("name", "Push Ups");
    values.put("time", 30);
    db.insert(DB_TABLE2, null, values);
    values.put("name", "Break");
    values.put("time", 10);
    db.insert(DB_TABLE2, null, values);
    values.put("name", "Situps");
    values.put("time", 20);
    db.insert(DB_TABLE2, null, values);

  }



}
