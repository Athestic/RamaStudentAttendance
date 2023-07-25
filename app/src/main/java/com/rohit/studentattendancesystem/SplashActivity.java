package com.rohit.studentattendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashActivity extends Activity {

    private ProgressBar pbar;
    private TextView tv;
    private int counter;
    private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

     //   pbar=findViewById(R.id.progressBar2);
      //  tv=findViewById(R.id.textView2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // on below line we are
                // creating a new intent
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);

                // on below line we are
                // starting a new activity.
                startActivity(i);

                // on the below line we are finishing
                // our current activity.
                finish();
            }
        }, 1000);

    }
    }
  /*  Runnable run=new Runnable() {
        @Override
        public void run() {
            while(true){
                counter++;
                if(counter==100) break;
                try {
                    Thread.sleep(100);
                }catch (Exception e){}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        pbar.setProgress(counter);
                        tv.setText(counter+"% loading please wait..");
                        if(counter==100){
                            tv.setText("loading complete..");
                            finish();
                            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                        }
                    }
                });
            }

        }
    };*/


