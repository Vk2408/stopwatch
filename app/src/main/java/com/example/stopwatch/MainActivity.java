package com.example.stopwatch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
Chronometer chrono;
ImageButton start,stop,pause;
private boolean isResume;
Handler handle;
long mill,tstart,tbuff,tupdate=0L;
int sec,milsec,min,hours;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        chrono=findViewById(R.id.chrono);
        start=findViewById(R.id.start);
        stop=findViewById(R.id.stop);
        pause=findViewById(R.id.pause);

        handle=new Handler();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isResume) {
                    tstart = SystemClock.uptimeMillis();
                    handle.postDelayed(runnable, 0);
                    chrono.start();
                    isResume=true;
                    stop.setVisibility(View.GONE);
                    start.setImageDrawable(getResources().getDrawable(
                            R.drawable.pause_button
                    ));
                }
                else {
                    tbuff+=mill;
                    handle.removeCallbacks(runnable);
                    chrono.stop();
                    isResume=false;
                    stop.setVisibility(View.VISIBLE);
                    start.setImageDrawable(getResources().getDrawable(R.drawable.play_button_svgrepo_com));
                }

            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isResume)
                {
                    start.setImageDrawable(getResources().getDrawable(R.drawable.play_button_svgrepo_com));
                    mill=0L;
                    tstart=0L;
                    tbuff=0L;
                    tupdate=0L;
                    hours=0;
                    min=0;
                    sec=0;
                    milsec=0;
                    chrono.setText("00:00:00:00");

                }
            }
        });
    }
    public Runnable runnable=new Runnable(){

        @Override
        public void run() {
            mill= SystemClock.uptimeMillis()-tstart;
            tupdate=tbuff+mill;
            sec=(int)(tupdate/1000);
            min=sec/60;
            sec=sec%60;
            hours=min/60;
            min=min%60;
            milsec=(int)(tupdate%100);
            chrono.setText(String.format("%02d",hours)+":"
            +String.format("%02d",min)+":"+String.format("%02d",sec)+":"+String.format("%02d",milsec));
            handle.postDelayed(this,60);
        }
    };
}