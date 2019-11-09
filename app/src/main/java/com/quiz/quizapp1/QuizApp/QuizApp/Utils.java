package com.quiz.quizapp1.QuizApp.QuizApp;


import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {
        private static FirebaseDatabase mDatabase;
        private static Boolean isNetworkAvailable=true;

        public static FirebaseDatabase getDatabase() {
            if (mDatabase == null) {
                mDatabase = FirebaseDatabase.getInstance();
                mDatabase.setPersistenceEnabled(true);
            }
            return mDatabase;
        }

    public static boolean hasInternetAccess(Context context) {
        if (isNetworkAvailable) {
            try {
                HttpURLConnection urlc = (HttpURLConnection)
                        (new URL("http://clients3.google.com/generate_204")
                                .openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 204 &&
                        urlc.getContentLength() == 0);
            } catch (IOException e) {

                Toast.makeText(context, "Error checking internet connection", Toast.LENGTH_SHORT).show();
            }
        } else {

            Toast.makeText(context, "No network available!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    }


