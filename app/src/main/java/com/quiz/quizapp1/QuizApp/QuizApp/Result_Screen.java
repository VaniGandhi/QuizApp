package com.quiz.quizapp1.QuizApp.QuizApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quiz.quizapp1.R;

public class Result_Screen extends AppCompatActivity {

  FirebaseAuth firebaseAuth;
  String booster_time;
    DatabaseReference databaseReference, dbref;
    int star, xp, level, points, rank, levelstar, incr, levelstard, sheild1;
    TextView rank1, rank2, text1, text2, incrtext;
    ImageView star1, star2, star3, star4, next, sheild, crown;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_result__screen);
        rank1=findViewById(R.id.rank1);
        text1=findViewById(R.id.text1);
        text2=findViewById(R.id.text2);
        star1=findViewById(R.id.star1);
        star2=findViewById(R.id.star2);
        star3=findViewById(R.id.star3);
        crown=findViewById(R.id.crown);

        star4=findViewById(R.id.star4);
        sheild=findViewById(R.id.shield);
        next=findViewById(R.id.next_button);
        incrtext=findViewById(R.id.incrzxp);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference("extras").child(firebaseAuth.getUid());
        dbref=FirebaseDatabase.getInstance().getReference("time").child(firebaseAuth.getUid()).child("booster_time");
        progressBar = new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
        progressBar=findViewById(R.id.xpbarred);
        progressBar.setProgress(0);
        progressBar.setMax(600);

        getboostertime();
        getdata();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Result_Screen.this, Main_Screen.class);
                startActivity(intent);

            }
        });


    }

    private void getboostertime()
    {
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                booster_time= (String) dataSnapshot.getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(Result_Screen.this, Main_Screen.class);
        startActivity(intent);



    }

    private void getdata()
    {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Extras_model extras_model=snapshot.getValue(Extras_model.class);
                    star=extras_model.getStar();
                    xp=extras_model.getXp();
                    level=extras_model.getLevel();
                    points=extras_model.getPoints();
                    rank=extras_model.getRank();
                    incr=extras_model.getIncr();
                    sheild1=extras_model.getSheild();
                    if(sheild1>0)
                    {
                        sheild.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        sheild.setVisibility(View.GONE);
                    }

                    rank1.setText(""+rank);
                    text1.setText(""+points+"points");

                    if(points>=300)
                    {       if(booster_time!=null){
                        if(!booster_time.equals("0 : 0 : 0 "))
                        {
                            incrtext.setText("+"+(2*incr));
                        }
                        else
                        {
                            incrtext.setText("+"+incr);
                        }}
                        else
                    {
                        incrtext.setText("+"+incr);
                    }

                    }
                 else
                    {
                        incrtext.setText("+0");
                    }
                 if(points>300)
                 {
                     crown.setVisibility(View.VISIBLE);
                 }
                 else
                 {
                     crown.setVisibility(View.GONE);
                 }

                    progressBar.setProgress(points);
                    levelstar=star%4;
                    levelstard=levelstar-1;
                    System.out.println(" levestar "+levelstar+" levelstad "+ levelstard+" star "+star);
                    if(level==1 && points<300)
                    {

                        star1.setImageResource(R.drawable.star4);
                        star2.setImageResource(R.drawable.star4);
                        star3.setImageResource(R.drawable.star4);
                        star4.setImageResource(R.drawable.star4);



                }

                    else if(level>1 && points>=300)
                    {

                        if(levelstar==1){
                            //Toast.makeText(Result_Screen.this, "7", Toast.LENGTH_SHORT).show();
                            star1.setImageResource(R.drawable.star1);
                            star2.setImageResource(R.drawable.star4);
                            star3.setImageResource(R.drawable.star4);
                            star4.setImageResource(R.drawable.star4);

                        }
                        else if(levelstar==2)
                        {

                            star1.setImageResource(R.drawable.star1);
                            star2.setImageResource(R.drawable.star1);
                            star3.setImageResource(R.drawable.star4);
                            star4.setImageResource(R.drawable.star4);

                        }

                        else if(levelstar==3) {

                            star1.setImageResource(R.drawable.star1);
                            star2.setImageResource(R.drawable.star1);
                            star3.setImageResource(R.drawable.star1);
                            star4.setImageResource(R.drawable.star4);
                        }
                        else if(levelstar==0)
                        {

                            star1.setImageResource(R.drawable.star1);
                            star2.setImageResource(R.drawable.star1);
                            star3.setImageResource(R.drawable.star1);
                            star4.setImageResource(R.drawable.star1);
                        }
                    }
                    else if(level>1 && points<300)
                    {


                        if(levelstar==1){
                           // Toast.makeText(Result_Screen.this, "71", Toast.LENGTH_SHORT).show();
                            star1.setImageResource(R.drawable.star1);
                            star2.setImageResource(R.drawable.star4);
                            star3.setImageResource(R.drawable.star4);
                            star4.setImageResource(R.drawable.star4);

                        }
                        else if(levelstar==2)
                        {

                            star1.setImageResource(R.drawable.star1);
                            star2.setImageResource(R.drawable.star1);
                            star3.setImageResource(R.drawable.star4);
                            star4.setImageResource(R.drawable.star4);

                        }

                        else if(levelstar==3) {

                            star1.setImageResource(R.drawable.star1);
                            star2.setImageResource(R.drawable.star1);
                            star3.setImageResource(R.drawable.star1);
                            star4.setImageResource(R.drawable.star4);
                        }
                        else if(levelstar==0)
                        {

                            star1.setImageResource(R.drawable.star4);
                            star2.setImageResource(R.drawable.star4);
                            star3.setImageResource(R.drawable.star4);
                            star4.setImageResource(R.drawable.star4);
                        }
                    }




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
