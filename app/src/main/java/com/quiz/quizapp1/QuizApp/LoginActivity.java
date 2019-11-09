package com.quiz.quizapp1.QuizApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.quiz.quizapp1.R;


public class LoginActivity extends AppCompatActivity {

    EditText  lemail, lpassword;
    Button login;
    TextView newhere;
    String lgetemail,lgetpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        lemail=findViewById(R.id.loginenter_email);
        lpassword=findViewById(R.id.loginenter_password);
        login=findViewById(R.id.loginButton);
        newhere=findViewById(R.id.logintextnewhere);
        lgetemail=lemail.getText().toString();
        lgetpassword=lpassword.getText().toString();


        newhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
