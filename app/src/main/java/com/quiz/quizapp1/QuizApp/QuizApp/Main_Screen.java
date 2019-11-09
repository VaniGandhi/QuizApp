package com.quiz.quizapp1.QuizApp.QuizApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quiz.quizapp1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Main_Screen extends AppCompatActivity {
        ImageView settings, burger;
        FirebaseAuth firebaseAuth;
        DatabaseReference databaseReference, databaseReference1, databaseReference2, dbref;
        ImageView store, coins;

        Button play;
        int rank, level , xp , coin, star, levelreached;
        TextView ranktext, leveltext, xptext, cointext;
    public static  final String MYPREFERENCES="MyPrefs";

    SharedPreferences sharedPreferences;
    GoogleSignInClient googleSignInClient;
    ProgressBar progressbar1;
    List<Avtar_model> avtar;
    String avtarurl, coinvalue;
    TextView boostertimer;
    long timespan,currentime, extendedtime, currentresumetime, currentrestarttime,time;
    CountDownTimer timer;
    boolean datainbooster;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main__screen);
//        Utils.getDatabase();
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("extras").child(firebaseAuth.getUid());
        databaseReference1=FirebaseDatabase.getInstance().getReference("users").child(firebaseAuth.getUid());
        databaseReference2=FirebaseDatabase.getInstance().getReference("booster").child(firebaseAuth.getUid());
        dbref=FirebaseDatabase.getInstance().getReference().child("time").child(firebaseAuth.getUid());
        settings=findViewById(R.id.settingsicontopright);
        play=findViewById(R.id.play);
        ranktext=findViewById(R.id.rank);
       leveltext=findViewById(R.id.level);
        xptext=findViewById(R.id.xptext);
        cointext=findViewById(R.id.coinstext);
        store=findViewById(R.id.store);
        coins=findViewById(R.id.addcoins);
        burger=findViewById(R.id.burger);
        boostertimer=findViewById(R.id.countdownbooster);

       progressbar1 = new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
       progressbar1 =findViewById(R.id.xpbar);
       progressbar1.setProgress(0);
       progressbar1.setMax(100);

        getuser();
        getdata();

              getboosterdata();

      //  getboosterdata();


        sharedPreferences=getApplicationContext().getSharedPreferences(MYPREFERENCES,MODE_PRIVATE);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient= GoogleSignIn.getClient(this,gso);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
           googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Main_Screen.this, "logout pressed", Toast.LENGTH_SHORT).show();
                    }
                });

                sharedPreferences = getSharedPreferences("MyPrefs", 0);
                sharedPreferences.edit().clear().commit();
                Intent intent = new Intent(Main_Screen.this, Login_Activity.class);
                startActivity(intent);

            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_Screen.this, GamePlayingScreen.class);
                startActivity(intent);

            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Main_Screen.this, Booster.class);
                startActivity(intent);
            }
        });
        coins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Main_Screen.this, Coins.class);
                startActivity(intent);
            }
        });
    }

    private void getboosterdata()
    {
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Booster_model booster_model=snapshot.getValue(Booster_model.class);
                     currentime=booster_model.getTimebuy();
                    extendedtime=booster_model.getExtended_time();
                     timespan=booster_model.getTimespan();
                     if(dataSnapshot.getValue()!=null)
                     {
                         datainbooster=true;
                     }
                     else
                     {
                         datainbooster=false;
                     }


                }
                if(datainbooster) {

                    countdown();
                }else
                {
                    Toast.makeText(Main_Screen.this, "buy a booster", Toast.LENGTH_SHORT).show();
                    dbref.child(firebaseAuth.getUid()).child("booster_time").removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
        private void countdown()
        {
            long currenttime=System.currentTimeMillis();
            long timec=extendedtime-currenttime;
            if(databaseReference2.getKey()!=null){

                timer = new CountDownTimer(timec, 1000) {
                    public void onTick(long millisUntilFinished) {
                        int seconds = (int) (millisUntilFinished / 1000) % 60;
                        int minutes = (int) (millisUntilFinished / (1000 * 60)) % 60;
                        int hours = (int) (millisUntilFinished / (1000 * 60 * 60)) % 24;
                        String text = hours + " : " + minutes + " : " + seconds;
                        boostertimer.setText(text);


                        if (firebaseAuth.getUid() != null && datainbooster) {
                            if (timer != null) {
                                dbref.child("booster_time").setValue(text);
                            } else {
                                dbref.child("booster_time").setValue(null);
                            }


                        }


                    }

                    @Override
                    public void onFinish() {

                    }


                }.start();


            }
            }

    private void getuser()
    {
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    UserModel userModel=snapshot.getValue(UserModel.class);
                    avtarurl=userModel.getUrl();
                    Picasso.with(getApplicationContext()).load(avtarurl).
                            transform(new RoundedCornersTransformation(10,10)).into(burger);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void removetinmer()
    {
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()){
                   databaseReference2.getRef().removeValue();
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void getdata()

    {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    Extras_model extras_model = snapshot.getValue(Extras_model.class);
                    rank = extras_model.getRank();
                    level = extras_model.getLevel();
                    xp = extras_model.getXp();
                    coin = extras_model.getCoin();
                    levelreached =extras_model.getLevelreached();
                    ranktext.setText("" + rank);
                    leveltext.setText("" + level);
                    cointext.setText(""+coin);


                    if (level == 1) {

                        progressbar1.setProgress(xp);
                        xptext.setText("" + xp+"xp");


                    }
                    /*else if(level==2)
                    {
                        progressbar1.setProgress(100);
                        xptext.setText(""+100+"xp");
                    }
*/
                  else
                    {
                        try {
                            int x = xp * 100 / levelreached;
                            progressbar1.setProgress(x);

                            xptext.setText("" + x + "xp");
                            System.out.println("x is " + x + " l.r " + levelreached + " xp " + xp);

                        }catch (ArithmeticException e)
                        {

                        }

                    }



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }

    /* @Override
    protected void onResume() {
        super.onResume();
        currentresumetime=System.currentTimeMillis();
        time=extendedtime-currentresumetime;
        countdown();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        currentrestarttime=System.currentTimeMillis();
        time=extendedtime-currentrestarttime;
        countdown();
    }*/
}
