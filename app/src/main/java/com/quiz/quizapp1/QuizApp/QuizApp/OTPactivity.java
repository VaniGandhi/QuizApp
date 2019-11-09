package com.quiz.quizapp1.QuizApp.QuizApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;
import com.quiz.quizapp1.R;

public class OTPactivity extends AppCompatActivity {

    EditText number;
    Button next;
    CountryCodePicker ccp;
    String selected_country_code="+91";
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_otpactivity);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        number=findViewById(R.id.enternumber);
        next=findViewById(R.id.btnnext);
        ccp=findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(number);
  //      Utils.hasInternetAccess(getApplicationContext());
//        Utils.getDatabase();
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getOtp();
            }
        });



    }
    public void onCountryPickerClick(View view) {

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                selected_country_code=ccp.getSelectedCountryCodeWithPlus();
                System.out.println("countrycode----------->"+selected_country_code);
            }
        });
    }




    private void getOtp()
    {
        String phoneno=number.getText().toString().trim();
        if(phoneno.isEmpty()||phoneno.length()<10)
        {
            number.setError("invalid number");
            number.requestFocus();
            return;

        }

        String completephoneno=  selected_country_code+phoneno;
        System.out.println(completephoneno);
        Intent intent=new Intent(OTPactivity.this,GetOtp.class);
        intent.putExtra("phoneno.",completephoneno);
        startActivity(intent);
    }


}
