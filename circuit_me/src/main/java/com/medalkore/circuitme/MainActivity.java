package com.medalkore.circuitme;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends Activity {

	public CircuitData cd;
	public SQLiteDatabase db;
	public SimpleCursorAdapter cursorAdaptor;
	public Cursor cursor;
	protected Button createCircuitButton;
	protected ListView titles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cd = new CircuitData(this);
		db = cd.getWritableDatabase();
		cursor = db.rawQuery("SELECT * FROM circuit_title", null);

		titles = (ListView) findViewById(R.id.list);
		createCircuitButton = (Button) findViewById(R.id.create_circuit);

		cursorAdaptor = new SimpleCursorAdapter(this, R.layout.title_list,
			cursor, new String[] {"title"}, new int[]{R.id.text1});

		titles.setAdapter(cursorAdaptor);

		createCircuitButton.setOnClickListener(buttonListener);


	}

	@Override
	protected void onPause(){
		super.onPause();
			cursor.close();
			db.close();
	}


	View.OnClickListener buttonListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(MainActivity.this, ShowCircuitActivity.class);
			startActivity(intent);
		}
	};


}
