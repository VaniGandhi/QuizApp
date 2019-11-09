package com.quiz.quizapp1.QuizApp.QuizApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.github.ybq.android.spinkit.style.Wave;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quiz.quizapp1.R;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class GamePlayingScreen extends AppCompatActivity implements View.OnClickListener {

    private static final String one = "A";
    private static final String two = "B";
    private static final String three = "C";
    private static final String four = "D";

    TextView question, opt1, opt2, opt3, opt4, countdown;
    RelativeLayout a, b, c, d;

    FirebaseAuth firebaseAuth;
    String booster_time;
    List<Avtar_model> avtar;
    DatabaseReference databaseReference, databaseReference1, dbref;
    List<QuestionsPojo> questions;
    String answer, avtarurl;
    int questioncount;
    String checknas;
    CountDownTimer timer;
    ProgressBar progressBar;
    int point, time = 0, incrementpoints = 0;
    int rank, level, xp, star, coins, newxp, newcoin, newstar, newrank, newlevel, incr, newincr, levelreached, newlevelrecahed,
            count, newcount, newpoints,points, contiwin, newcotiwin, sheild, newsheild;
    Boolean hashkey = false, win, lose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_game_playing_screen);

        question = findViewById(R.id.question);
        opt1 = findViewById(R.id.option1);
        opt2 = findViewById(R.id.option2);
        opt3 = findViewById(R.id.option3);
        opt4 = findViewById(R.id.option4);
        a = findViewById(R.id.A);
        b = findViewById(R.id.B);
        c = findViewById(R.id.C);
        d = findViewById(R.id.D);


        countdown = findViewById(R.id.countdown);
        progressBar =  findViewById(R.id.spin_kit);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("questions");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("extras").child(firebaseAuth.getUid());
        dbref=FirebaseDatabase.getInstance().getReference("time").child(firebaseAuth.getUid()).child("booster_time");

        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        d.setOnClickListener(this);
        getboostertime();
        getquestions();


        // upgradedata();


    }

    private void getboostertime()
    {
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                booster_time= (String) dataSnapshot.getValue();
                System.out.println("timeeee--------->"+time);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getquestions() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questions = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    QuestionsPojo questionsPojo = snapshot.getValue(QuestionsPojo.class);
                    questions.add(questionsPojo);
                }
                questioncount = questions.size();
                Wave wave = new Wave();
                progressBar.setIndeterminateDrawable(wave);
                setQuestion();
                System.out.println(questioncount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private int setQuestion() {
        progressBar.setVisibility(View.GONE);
        System.out.println("GamePlayingScreen.setQuestion.count----->" + questioncount);
        if (questioncount > 0) {
            question.setText(questions.get(questioncount - 1).getQ());
            opt1.setText(questions.get(questioncount - 1).getA());
            opt2.setText(questions.get(questioncount - 1).getB());
            opt3.setText(questions.get(questioncount - 1).getC());
            opt4.setText(questions.get(questioncount - 1).getD());
            answer = questions.get(questioncount - 1).getAns();
            countdown2();
        } else {
            Toast.makeText(this, "You have  reached the end of game", Toast.LENGTH_SHORT).show();
            checkresult();

        }
        return questioncount--;

    }


    private void countdown2() {
        if (timer != null) {
            timer.cancel();
            timer.start();
        } else {
            timer = new CountDownTimer(10000, 1000) {
                public void onTick(long millisUntilFinished) {
                    countdown.setText("" + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    setcolorwhite();
                    setQuestion();
                }
            }.start();
        }
    }

    private void checkanswer(String id) {
        System.out.println("ans------>" + answer);
        if (id.equals(answer)) {
            checknas = answer;
            checktime();

        } else {
            checknas = answer;

        }
    }

    private int checktime() {
        time = Integer.parseInt(countdown.getText().toString());

        if (time == 9) {
            incrementpoints = 30;
            point = point + incrementpoints;

            System.out.println("incrementpoints---------->" + incrementpoints);
            System.out.println("points---------->" + point);
        } else if ((time > 5) && (time <= 9)) {
            incrementpoints = 20;
            point = point + incrementpoints;

            System.out.println("incrementpoints---------->" + incrementpoints);
            System.out.println("points---------->" + point);
        } else if ((time >= 0) && (time <= 5)) {
            incrementpoints = 10;
            point = point + incrementpoints;
            System.out.println("incrementpoints---------->" + incrementpoints);
            System.out.println("points---------->" + point);
        }
        return point;
    }

    private void checkresult() {
        if (point >= 300) {
            win = true;
            lose = false;

            System.out.println("pointswin--------->" + point);
            getdata(win, lose);


        } else {
            lose = true;
            win = false;

            System.out.println("pointsloose--------->" + point);
            getdata(win, lose);


        }
    }

    private void getdata(final Boolean win, final Boolean lose) {
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!hashkey) {
                    //Getting the login user current data
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                        Extras_model extras_model = snapshot.getValue(Extras_model.class);
                        rank = extras_model.getRank();
                        level = extras_model.getLevel();
                        xp = extras_model.getXp();
                        star = extras_model.getStar();
                        coins = extras_model.getCoin();
                        incr = extras_model.getIncr();
                        levelreached = extras_model.getLevelreached();
                        count = extras_model.getCount();
                        points=extras_model.getPoints();
                        contiwin=extras_model.getContiwin();
                        sheild=extras_model.getSheild();

                        System.out.println("rank=" + rank + "  " + level + " " + xp + " " + star + " "
                                + coins + " " + incr + " " + levelreached + " " + count+ " "+ contiwin+ " "+ sheild);
                        hashkey = true;
                    }


                    upgradedata2(rank, level, xp, star, coins, incr, levelreached, count, win, lose, points,sheild);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void upgradedata2(int rank, int level, int xp, int star, int coins, int incr, int levelreached,
                              int count, Boolean win, Boolean lose, int points,int sheild) {
        if (win && !lose) {


            if (level == 1) {
                if(booster_time!=null){
                    if(!booster_time.equals("0 : 0 : 0"))
                    {
                        newxp = xp + (2*50);
                    }
                    else
                    {
                        newxp=xp+50;
                    }
                }
                else
                {
                    newxp=xp+50;
                }


                newrank = rank;
                newlevel = level + 1;
                newstar = star + 1;
                newpoints=point;
                newcotiwin=contiwin+1;


                newxp = 0;

                newincr = incr;
                newlevelrecahed = levelreached;
                newcount = count;
                newcoin = coins+1;
                newsheild=sheild;

            } else {
                newstar = star + 1;
                newsheild=sheild;
                newcotiwin=contiwin+1;
                if(booster_time!=null){
                if(!booster_time.equals("0 : 0 : 0"))
                {
                    newxp = xp + (2*incr);
                }
                else
                {
                    newxp=xp+incr;
                }
                }else
                {
                    newxp=xp+incr;
                }

                newlevel = level;
                newlevelrecahed = levelreached;
                newcoin = coins+1;
                newpoints=point;
                newincr = incr;
                newrank = rank;
                newcount = count;
                if (newxp >= levelreached) {
                    newxp = 0;
                    newlevelrecahed = levelreached + 50;
                    newlevel = level + 1;


                }
                if (newstar % 4 == 0) {
                    newrank = rank + 1;
                    newincr = incr + 5;
                }

                if(newcotiwin==3)
                {
                    newsheild=sheild+1;
                    newcotiwin=0;
                }

            }
        } else {
            if(sheild>0)
            {
                newstar = star;
                newxp = xp;
                newlevel = level;
                newlevelrecahed = levelreached;
                if(contiwin>0){
                    newcotiwin = contiwin - 1;
                }

                    newcotiwin=contiwin;


                newcoin = coins;
                newincr = incr;
                newrank = rank;
                newpoints = point;
                if (star % 4 == 0) {
                    if (rank > 1) {
                        newrank = rank ;
                        newincr = incr ;
                        newcount = 0;
                    } else {
                        newrank = rank;
                        newincr = incr;
                        newcount = 0;
                    }
                } else {
                    newcount = count + 1;
                    if (newcount % 4 == 0) {
                        if (rank > 1) {
                            newrank = rank ;
                            newincr = incr ;
                            newcount = 0;
                        } else {
                            newrank = rank;
                            newincr = incr;
                            newcount = 0;
                        }


                    }
                }

                    newsheild = sheild - 1;


            }
            else {

                if (star > 0) {
                    newstar = star - 1;
                } else {
                    newstar = star;
                }

                newxp = xp;
                newlevel = level;
                newlevelrecahed = levelreached;

                if(contiwin>0){
                    newcotiwin = contiwin - 1;
                }

                newcotiwin=contiwin;


                newcoin = coins;
                newincr = incr;
                newrank = rank;
                newpoints = point;
                if (star % 4 == 0) {
                    if (rank > 1) {
                        newrank = rank - 1;
                        newincr = incr - 5;
                        newcount = 0;
                    } else {
                        newrank = rank;
                        newincr = incr;
                        newcount = 0;
                    }
                } else {
                    newcount = count + 1;
                    if (newcount % 4 == 0) {
                        if (rank > 1) {
                            newrank = rank - 1;
                            newincr = incr - 5;
                            newcount = 0;
                        } else {
                            newrank = rank;
                            newincr = incr;
                            newcount = 0;
                        }


                    }
                }
            }

        }

        System.out.println(" newxp " + newxp + " newrank " + newrank + " newlevel " + newlevel +
                " newstar " + newstar + " newincr " + newincr + " newlevelreched " + newlevelrecahed);
        addExtras(newrank, newlevel, newxp, newstar, newcoin, newincr, newlevelrecahed, newcount, newpoints, newcotiwin, newsheild);


    }


    private void addExtras(int rank, int level, int xp, int star, int coin, int incr, int levelreached, int count, int points,
                           int contiwin, int sheild) {
        String uid = firebaseAuth.getUid();
        Extras_model extras_model = new Extras_model(rank, level, xp, star, coin, incr, levelreached, count, points, contiwin, sheild);
        databaseReference1.child(uid).setValue(extras_model);
        timer.cancel();
        Intent intent = new Intent(GamePlayingScreen.this, Result_Screen.class);
        startActivity(intent);


    }


    @SuppressLint("ResourceAsColor")
    private void setcolorwhite() {
        a.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        b.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        c.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        d.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.A:
                checkanswer(one);
                if (checknas.equals("A")) {
                    a.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.parrotgreen));
                } else {
                    a.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.Red));
                }
                b.setClickable(false);
                c.setClickable(false);
                d.setClickable(false);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {

                                wait(1000);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setQuestion();
                                        setcolorwhite();

                                        b.setClickable(true);
                                        c.setClickable(true);
                                        d.setClickable(true);
                                    }
                                });

                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    ;
                };
                thread.start();
                break;

            case R.id.B:
                checkanswer(two);
                if (checknas.equals("B")) {
                    b.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.parrotgreen));

                } else {
                    b.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.Red));
                }
                a.setClickable(false);
                c.setClickable(false);
                d.setClickable(false);
                Thread thread1 = new Thread() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {

                                wait(1000);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setQuestion();
                                        setcolorwhite();
                                        a.setClickable(true);
                                        c.setClickable(true);
                                        d.setClickable(true);
                                    }
                                });

                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    ;
                };
                thread1.start();
                break;

            case R.id.C:
                checkanswer(three);
                if (checknas.equals("C")) {
                    c.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.parrotgreen));
                } else {
                    c.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.Red));

                }
                a.setClickable(false);
                b.setClickable(false);
                d.setClickable(false);

                Thread thread2 = new Thread() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {

                                wait(1000);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setQuestion();
                                        setcolorwhite();
                                        a.setClickable(true);
                                        b.setClickable(true);
                                        d.setClickable(true);


                                    }
                                });

                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }


                };
                thread2.start();
                break;
            case R.id.D:
                checkanswer(four);
                if (checknas.equals("D")) {
                    d.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.parrotgreen));
                } else {
                    d.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.Red));

                }
                a.setClickable(false);
                c.setClickable(false);
                b.setClickable(false);

                Thread thread3 = new Thread() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {

                                wait(1000);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setQuestion();
                                        setcolorwhite();
                                        a.setClickable(true);
                                        c.setClickable(true);
                                        b.setClickable(true);


                                    }
                                });

                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }


                };
                thread3.start();
                break;
        }


    }
}
