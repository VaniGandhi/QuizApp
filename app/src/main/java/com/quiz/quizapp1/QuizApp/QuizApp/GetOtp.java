package com.quiz.quizapp1.QuizApp.QuizApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.quiz.quizapp1.R;

import java.util.concurrent.TimeUnit;

public class GetOtp extends AppCompatActivity {

    ImageView sendagain,back,next;
    String verificationid;
    FirebaseAuth firebaseAuth;
    EditText OTP;
    DatabaseReference databaseReference;
    String phoneno;
    TextView countdowntimer;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_get_otp);
        sendagain=findViewById(R.id.sendagain);
        back=findViewById(R.id.backbtn);
        next=findViewById(R.id.nextbtn);
        OTP=findViewById(R.id.enter_otp);
        countdowntimer=findViewById(R.id.countdowntimer);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
         phoneno=getIntent().getStringExtra("phoneno.");
        sendverificationcode(phoneno);
        sendagain.setVisibility(View.GONE);
        countdown();

       /* sendagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               *//* String code=OTP.getText().toString().trim();
                if(code.isEmpty()||code.length()<6)
                {
                    OTP.setError("Invalid OTP");
                    OTP.requestFocus();
                    return;
                }
                verifycode(code);*//*
               sendverificationcode(phoneno);
            }
        });*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code=OTP.getText().toString().trim();
                if(code.isEmpty()||code.length()<6)
                {
                    OTP.setError("Invalid OTP");
                    OTP.requestFocus();
                    return;
                }
                    verifycode(code);
            }
        });

        sendagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GetOtp.this, "Sending OTP again", Toast.LENGTH_SHORT).show();
                sendverificationcode(phoneno);
                countdown();
                sendagain.setVisibility(View.GONE);
            }
        });
    }


   /* private void createnewUser(String id,String name,String uid)
    {
        UserModel  userModel=new UserModel(id, name, uid);
        databaseReference.child("users").child(id).setValue(userModel);
    }*/

    private void countdown()

    {
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdowntimer.setText( ""+ millisUntilFinished / 1000);
            }

            public void onFinish() {
                countdowntimer.setText("done!");
                sendagain.setVisibility(View.VISIBLE);
            }
        }.start();
    }


    private void verifycode(String code)
    {

        try{
        PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(verificationid,code);
        signinwithcredential(phoneAuthCredential,code);
        }catch (Exception e){
            Toast toast = Toast.makeText(this, "Verification Code is wrong", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }


    }
    private void signinwithcredential(final PhoneAuthCredential phoneAuthCredential, final String code)
    {
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            OTP.setText(code);
                            Thread thread = new Thread(){
                                @Override
                                public void run() {
                                    try {
                                        synchronized (this) {
                                            wait(1000);
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent intent = new Intent(GetOtp.this, Enter_name.class);
                                                    intent.putExtra("phoneno.", phoneno);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
                            Toast.makeText(GetOtp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            System.out.println("Exception============>"+task.getException().getMessage());
                        }
                    }
                }
        );

    }

    private void sendverificationcode(String number)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number,60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,mcallbacks);
    }


    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks
        mcallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                countdowntimer.setVisibility(View.GONE);
            String code=phoneAuthCredential.getSmsCode();
            if(code!=null)

            {
                verifycode(code);
                System.out.println("code--------------->"+code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
         //   Toast.makeText(GetOtp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.println("excetion------------>"+e.getMessage());

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationid=s;


        }


    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
