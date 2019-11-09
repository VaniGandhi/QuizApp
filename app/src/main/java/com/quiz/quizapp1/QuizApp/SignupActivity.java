package com.quiz.quizapp1.QuizApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.quiz.quizapp1.R;


public class SignupActivity extends AppCompatActivity {

    EditText name, email, password;
    Button signup;
    TextView alreadyRegistered;
    String getname,getemail,getpassword;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name=findViewById(R.id.signupenter_name);
        email=findViewById(R.id.signupenter_email);
        password=findViewById(R.id.signupenter_password);
        signup=findViewById(R.id.signupbutton);
        alreadyRegistered=findViewById(R.id.signuptextnalready);
        getemail=email.getText().toString();
        getname=name.getText().toString();
        getpassword=password.getText().toString();


        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignupActivity.this,LoginActivity.class);

                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signup();

            }
        });

    }

    private void Signup()
    {

    }


}
