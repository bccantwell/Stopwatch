package com.example.stopwatch;

import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;
import android.os.Handler;
import android.util.Log;
import java.util.Locale;

/*
StopwatchActivity creates a stopwatch timer that can be started, paused, resumed, and stopped.
 */

public class StopwatchActivity extends Activity {
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;
    private static final int SECS_PER_MIN = 60;
    private static final int MINS_PER_HOUR = 60;
    private static final int SECS_PER_HOUR = SECS_PER_MIN * MINS_PER_HOUR;
    private static final int DELAY_MILLIS = 1000;

    //Creates (or restores) an instance of the stopwatch activity using bundle values of "seconds" and "running"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
        Log.d("StopwatchActivity", "In onCreate");
    }

    //Pause the stopwatch by moving the activity to the background
    @Override
    protected void onPause(){
        super.onPause();
        wasRunning = running;
        running = false;
        Log.d("StopwatchActivity", "In onPause");
    }

    //Resume the stopwatch by moving the activity to the foreground
    @Override
    protected void onResume(){
        super.onResume();
        if (wasRunning) {
            running = true;
        }
        Log.d("StopwatchActivity", "In onResume");
    }

    //Preserves the state of the stopwatch
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    //When the start button is pushed, runs the stopwatch
    public void onClickStart(View view){
        running = true;
    }

    //When the stop button is pushed, stops the stopwatch
    public void onClickStop(View view){
        running = false;
    }

    //When the reset button is pushed, stops the stopwatch and sets the timer to 0
    public void onClickReset(View view){
        running = false;
        seconds = 0;
    }

    //Sets the number of seconds to be displayed on the timer, and runs the timer
    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / SECS_PER_HOUR;
                int minutes = (seconds % SECS_PER_HOUR) / MINS_PER_HOUR;
                int secs = seconds % SECS_PER_MIN;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    Log.d("Time between increments", "Incremented seconds: " + seconds);
                    seconds++;

                }
                handler.postDelayed(this, DELAY_MILLIS);
            }
        });
    }

}
