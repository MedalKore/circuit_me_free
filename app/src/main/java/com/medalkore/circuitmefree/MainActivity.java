package com.medalkore.circuitmefree;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	protected CircuitData cd;
	public SQLiteDatabase db;
	public SimpleCursorAdapter cursorAdaptor;
	public Cursor cursor;
	protected Button createCircuitButton;
	protected ListView titles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		titles = (ListView) findViewById(R.id.list);
		createCircuitButton = (Button) findViewById(R.id.create_circuit);

		cd = new CircuitData(this);
		db = cd.getWritableDatabase();
		cursor = db.rawQuery("SELECT * FROM circuit_title", null);

		cursorAdaptor = new SimpleCursorAdapter(this, R.layout.title_list,
			cursor, new String[] {"title"}, new int[]{R.id.text1}, 0);

		titles.setAdapter(cursorAdaptor);
		titles.setOnItemClickListener(itemListener);
		createCircuitButton.setOnClickListener(buttonListener);

	}

	@Override
	protected void onPause(){
		super.onPause();
		cursor.close();
		db.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		cd = new CircuitData(this);
		db = cd.getWritableDatabase();
		cursor = db.rawQuery("SELECT * FROM circuit_title", null);
		cursorAdaptor = new SimpleCursorAdapter(this, R.layout.title_list,
			cursor, new String[] {"title"}, new int[]{R.id.text1}, 0);

		titles.setAdapter(cursorAdaptor);
		titles.setOnItemClickListener(itemListener);
	}

	AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> listview, View view, int position, long id){
      Intent intent = new Intent(MainActivity.this, RunCircuitActivity.class);
      intent.putExtra("title", id);
      startActivity(intent);
		}
	};

	View.OnClickListener buttonListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {

			Intent intent = new Intent(MainActivity.this, CreateCircuitActivity.class);
			startActivity(intent);
		}
	};

}
