package com.quiz.quizapp1.QuizApp;

import android.content.Context;
import android.content.SharedPreferences;



public class User2SharedPrefences {

    private String NAME = "name";
    private String EMAIL = "email";
    private String PASSWORD = "password";
    private String IMGURL="imgurl";

    SharedPreferences sharedPreferences;
    static User2SharedPrefences user2SharedPrefences = null;
    Context context;

    public User2SharedPrefences() {

    }

    public User2SharedPrefences(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

    }
    public static User2SharedPrefences getInstance() {
        if (user2SharedPrefences == null) {
            user2SharedPrefences = new User2SharedPrefences(BaseApplication.getContext());
        }
        return user2SharedPrefences;
    }

    public void setname(String name) {
        sharedPreferences.edit().putString(NAME, name).apply();
    }

    public String getname() {
        return sharedPreferences.getString(NAME, "");
    }

    public void setemail(String email) {
        sharedPreferences.edit().putString(EMAIL, email).apply();
    }

    public String getemail() {
        return sharedPreferences.getString(EMAIL, "");
    }

    public void setpassword(String password) {
        sharedPreferences.edit().putString(PASSWORD, password).apply();
    }

    public String getpassword() {
        return sharedPreferences.getString(PASSWORD, "");
    }
    public void setimgurl(String imgurl) {
        sharedPreferences.edit().putString(IMGURL, imgurl).apply();
    }

    public String getimgurl() {
        return sharedPreferences.getString(IMGURL, "");
    }

}
