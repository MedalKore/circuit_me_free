package com.medalkore.circuitmefree;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
	long timeInMilliseconds;
	ProgressBar timer;
	TextView exercise;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_run_circuit);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		final ImageButton start_pause_button = (ImageButton) findViewById(R.id.start_pause_button);
		TextView title = (TextView) findViewById(R.id.circuit_title);
		exercise = (TextView) findViewById(R.id.exercise);

		Intent intent = getIntent();
		cd = new CircuitData(this);
		db = cd.getWritableDatabase();
		circuit_title = db.rawQuery("SELECT * FROM circuit_title WHERE _id=" + intent.getLongExtra("title", -1), null);
		int title_id = circuit_title.getColumnIndex("title");
		circuit_exercises = db.rawQuery("SELECT * FROM circuit_interval WHERE title_id="+title_id, null);
		timer = (ProgressBar) findViewById(R.id.circuit_timer);

		if (circuit_title != null && circuit_title.moveToFirst()) {
			title.setText(circuit_title.getString(title_id));
			if (circuit_exercises != null && circuit_exercises.moveToFirst()) {
				exercise.setText(circuit_exercises.getString(circuit_exercises.getColumnIndex("name")));
				timeInMilliseconds = (long) circuit_exercises.getInt(circuit_exercises.getColumnIndex("time")) * 1000;
				timer.setMax((int)(timeInMilliseconds));
				timer.setProgress(0);
			}
		}

		Animation an = new RotateAnimation(0.0f, -90.0f, 150.0f, 150.0f);
		an.setFillAfter(true);
		timer.startAnimation(an);

		start_pause_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				TextView startPauseText = (TextView) findViewById(R.id.start_pause_text);
				final TextView time = (TextView) findViewById(R.id.time);
				if (startPauseText.getText() == "Start"){
					startPauseText.setText("Pause");
					start_pause_button.setBackgroundResource(R.drawable.pause_button);
					countdownTimer(time, timeInMilliseconds);
				}else{
					startPauseText.setText("Start");
					start_pause_button.setBackgroundResource(R.drawable.start_button);
				}


			}
		});
	}

	protected void nextExercise(TextView time){
		if (circuit_exercises != null && circuit_exercises.moveToNext()) {
			exercise.setText(circuit_exercises.getString(circuit_exercises.getColumnIndex("name")));
			timeInMilliseconds = (long) circuit_exercises.getInt(circuit_exercises.getColumnIndex("time")) * 1000;
			timer.setMax((int) (timeInMilliseconds));
			timer.setProgress(0);
			countdownTimer(time, timeInMilliseconds);
		}
	}

	protected void countdownTimer(final TextView view, final long timeInMilliseconds) {

		new CountDownTimer(timeInMilliseconds, 100) {

			public void onTick(long millisUntilFinished) {
				float secondsTilFinished = millisUntilFinished / 1000f;
				view.setText(String.format("%.1f", secondsTilFinished));

				int secondsProgressed = (int) (timeInMilliseconds - millisUntilFinished);
				timer.setProgress(secondsProgressed);
			}

			public void onFinish() {
				view.setText("0");
				nextExercise(view);
			}
		}.start();
	}








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
