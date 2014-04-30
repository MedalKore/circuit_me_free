package com.medalkore.circuitme;

/**
 * Created by alemmons on 4/24/2014.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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
	  ContentValues cv = new ContentValues(4);
	  cv.put(TITLE, "Hockey Workout");
	  cv.put(TITLE, "Another Workout");
	  cv.put(TITLE, "Nother One");
	  cv.put(TITLE, "One More");
	  int i = 0;
	  while (i < 2){
		  db.insert(DB_TABLE1, null, cv);
		  i++;
	  }
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

  }


	protected long newCircuitTitle(SQLiteDatabase database, String title) {
		ContentValues values = new ContentValues();
		values.put(TITLE, title);

		return database.insert(DB_TABLE1, null, values);
	}





}
