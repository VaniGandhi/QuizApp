package com.quiz.quizapp1.QuizApp.QuizApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.quiz.quizapp1.R;


public class Launch_Screen extends AppCompatActivity {
    public static  final String MYPREFERENCES="MyPrefs";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_launch__screen);
        sharedPreferences=this.getSharedPreferences("MyPrefs",MODE_PRIVATE);
        if(sharedPreferences.getBoolean("islogin",false))
        {

            Thread thread = new Thread(){
                @Override
                public void run() {
                    try {
                        synchronized (this) {
                            wait(1000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Intent intent=new Intent(Launch_Screen.this,Main_Screen.class);
                                    startActivity(intent);

                                }
                            });

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                };
            };
            thread.start();




        }
        else
        {


            Thread thread = new Thread(){
                @Override
                public void run() {
                    try {
                        synchronized (this) {
                            wait(1000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Intent intent=new Intent(Launch_Screen.this,Login_Activity.class);
                                    startActivity(intent);

                                }
                            });

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                };
            };
            thread.start();
        }

    }
}
