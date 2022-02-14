package com.example.stopwatch;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //Variable declaration
    Button btnStart, btnPause, btnReset;
    TextView runTime, msTime;

    //Stores all time-related data
    int lapsedTime, hours, minutes, seconds, milliseconds = 0;

    //Strings to store all time-related data
    String time, milliSec;

    //Checks if the stopwatch is running or not
    boolean isrunning;

    //Creating a handler
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Link Variables and XML Views
        runTime = (TextView) findViewById(R.id.timer);
        msTime = (TextView) findViewById(R.id.mSecTimer);
        btnStart = (Button) findViewById(R.id.StartBtn);
        btnPause = (Button) findViewById(R.id.PauseBtn);
        btnReset = (Button) findViewById(R.id.ResetBtn);

        //Listener for onClick Events on Start Button
        btnStart.setOnClickListener(view -> {
            //Set to true to show stopwatch running
            isrunning = true;
            //Method to flip Pause/Start Button States
            statusBtn();
        });


        //Listener for onClick Events on Pause Button
        btnPause.setOnClickListener(view -> {
            //Set to false to show stopwatch is not running
            isrunning = false;
            //Method to flip Pause/Start Button States
            statusBtn();
        });

        //Listener for onClick Events on Reset Button
        btnReset.setOnClickListener(view -> {
            //Set to false to show stopwatch is not running
            isrunning = false;
            //Set all time-related data back to 0
            lapsedTime = 0;
            milliseconds = 0;

            //Set strings back to 0
            time ="00:00:00";
            milliSec ="00";

            //Set all textViews back to 00:00:00
            runTime.setText(R.string.timer);
            msTime.setText(R.string.msTimer);

            //Method to flip the Pause/Start Button States
            statusBtn();
        });

        //Method to start the Stopwatch
        startTimer();
    }

    //Method to flip the Start/Pause Button
    private void statusBtn(){
        if (isrunning){
            btnStart.setEnabled(false);
            btnPause.setEnabled(true);
        }else{
            btnStart.setEnabled(true);
            btnPause.setEnabled(false);
        }
    }

    //Method to start the stopwatch
    private void startTimer(){
        //post() method to update the UI
        handler.post(new Runnable() {
            @Override
            public void run() {

                //Conversion of lapsedTime to seconds, minutes and hours
                hours = lapsedTime / 3600;
                minutes = (lapsedTime % 3600) / 60;
                seconds = lapsedTime % 60;

                //Check if the stopwatch is running
                if (isrunning){

                    if (milliseconds >= 99){
                        milliseconds = 0;
                        //Increment elapsed time
                        lapsedTime++;
                    }

                    //Increment elapsed time
                    milliseconds++;

                    //String containing the time-format used
                    time = String.format(Resources.getSystem().getConfiguration().locale, "%02d:%02d:%02d", hours, minutes, seconds);
                    milliSec = String.format(Resources.getSystem().getConfiguration().locale, "%02d", milliseconds);

                    //Set string to respective textview
                    runTime.setText(time);
                    msTime.setText(milliSec);
                }

                //postDelayed() method to run the code in intervals of milliseconds
                handler.postDelayed(this, 10);
            }
        });
    }

    //Method to save an instance of the stopwatch
    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        //Save data by put() method of a specific instance with the respective key and variable
        savedInstanceState.putInt("lapsedTime", lapsedTime);
        savedInstanceState.putInt("milliseconds", milliseconds);
        savedInstanceState.putBoolean("isrunning", isrunning);
        savedInstanceState.putString("time", time);
        savedInstanceState.putString("msTime", milliSec);
    }

    //Method to restore an instance if present
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //Retrieve data by get() method from the respective key assigned
        lapsedTime = savedInstanceState.getInt("lapsedTime");
        milliseconds = savedInstanceState.getInt("milliseconds");
        isrunning = savedInstanceState.getBoolean("isrunning");
        time = savedInstanceState.getString("time");
        milliSec = savedInstanceState.getString("msTime");

        //Check if the stopwatch was running or not
        if (isrunning){
            btnStart.setEnabled(false);
            btnPause.setEnabled(true);
        }else{
            btnPause.setEnabled(false);
            btnStart.setEnabled(true);
            runTime.setText(time);
            msTime.setText(milliSec);
        }
    }
}

