package com.medalkore.circuitmefree;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by alemmons on 4/29/2014.
 */
public class RunCircuitActivity extends Activity {
  protected CircuitData cd;
  protected SQLiteDatabase db;
  protected Cursor circuit_title;
  protected Cursor circuit_exercises;



	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_run_circuit);
		ImageButton start_button = (ImageButton) findViewById(R.id.start_button);
		TextView title = (TextView) findViewById(R.id.circuit_title);
		TextView exercise = (TextView) findViewById(R.id.exercise);

		Intent intent = getIntent();
		cd = new CircuitData(this);
		db = cd.getWritableDatabase();
		circuit_title = db.rawQuery("SELECT * FROM circuit_title WHERE _id=" + intent.getLongExtra("title", -1), null);
		int title_id = circuit_title.getColumnIndex("title");
		if (circuit_title != null && circuit_title.moveToFirst()) {
			title.setText(circuit_title.getString(title_id));
			circuit_exercises = db.rawQuery("SELECT * FROM circuit_interval WHERE _id="+title_id, null);
			if (circuit_exercises != null && circuit_exercises.moveToFirst()) {
				exercise.setText(circuit_exercises.getString(circuit_exercises.getColumnIndex("name")));
			}
		}


		start_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				TextView startPauseText = (TextView) findViewById(R.id.start_pause_text);
				if (startPauseText.getText() == "Start"){
					startPauseText.setText("Pause");
				}else{
					startPauseText.setText("Start");
				}
			}
		});



	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		circuit_title.close();
		circuit_exercises.close();
		db.close();
	}
}
