package com.quiz.quizapp1.QuizApp.QuizApp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quiz.quizapp1.R;

import java.util.Calendar;
import java.util.Date;


public class Booster extends Activity implements BillingProcessor.IBillingHandler {
    BillingProcessor bp;
   ImageView  back;
   RelativeLayout threeboost, fiveboost, tenboost, fifteenboost;
   FirebaseAuth firebaseAuth;
   DatabaseReference databaseReference, databaseReference1, dbref;
    LinearLayout l1, l2, l3;
    long time;
     int id;
    String booster_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.billing_activity);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference();

        databaseReference1=FirebaseDatabase.getInstance().getReference("booster");
        dbref=FirebaseDatabase.getInstance().getReference("time").child(firebaseAuth.getUid()).child("booster_time");

        threeboost=findViewById(R.id.boosterthree);
        fiveboost=findViewById(R.id.boosterfive);
        tenboost=findViewById(R.id.boosterten);
        fifteenboost=findViewById(R.id.boosterfifteen);
        back=findViewById(R.id.backbooster);
        l1=findViewById(R.id.top1);
        l2=findViewById(R.id.top2);
        l3=findViewById(R.id.top3);
    //    purchase = findViewById(R.id.purchase);


        bp = new BillingProcessor(this, "null", this);
        bp.initialize();
        getdata();

        threeboost.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                time=30000;
                id=3;


                if(booster_time!=null) {
                    if (!booster_time.equals("0 : 0 : 0")) {
                        Toast.makeText(Booster.this, "You are already using a booster", Toast.LENGTH_SHORT).show();
                    } else {
                       bp.purchase(Booster.this,"android.test.purchased");

                    }
                }else{
                    bp.purchase(Booster.this,"android.test.purchased");


                }









            }
        });

        fiveboost.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
              time=18000000;
              id=5;

                if(booster_time!=null) {
                    if (!booster_time.equals("0 : 0 : 0")) {
                        Toast.makeText(Booster.this, "You are already using a booster", Toast.LENGTH_SHORT).show();
                    } else {
                        bp.purchase(Booster.this,"android.test.purchased");

                    }
                }else{
                    bp.purchase(Booster.this,"android.test.purchased");


                }





              /*  if(booster_time!=null) {
                if(!booster_time.equals("0 : 0 : 0"))
                {
                    Toast.makeText(Booster.this, "You are already using a booster", Toast.LENGTH_SHORT).show();
                }
                else {
                    savetime(time, 5);
                    bp.purchase(Booster.this, "android.test.purchased");

                }
                }else{
                    savetime(time, 3);
                    bp.purchase(Booster.this, "android.test.purchased");
                }

*/
            }
        });

        tenboost.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
             time=36000000;
             id=10;

                if(booster_time!=null) {
                    if (!booster_time.equals("0 : 0 : 0")) {
                        Toast.makeText(Booster.this, "You are already using a booster", Toast.LENGTH_SHORT).show();
                    } else {
                        bp.purchase(Booster.this,"android.test.purchased");

                    }
                }else{
                    bp.purchase(Booster.this,"android.test.purchased");


                }




             /*   if(booster_time!=null) {
                if(!booster_time.equals("0 : 0 : 0"))
                {
                    Toast.makeText(Booster.this, "You are already using a booster", Toast.LENGTH_SHORT).show();
                }
                else {
                    savetime(time, 10);
                    bp.purchase(Booster.this, "android.test.purchased");

                }
            }else{
                savetime(time, 3);
                    bp.purchase(Booster.this, "android.test.purchased");
            }*/


        }
        });
        fifteenboost.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
              time=54000000;
              id=15;

                if(booster_time!=null) {
                    if (!booster_time.equals("0 : 0 : 0")) {
                        Toast.makeText(Booster.this, "You are already using a booster", Toast.LENGTH_SHORT).show();
                    } else {
                        bp.purchase(Booster.this,"android.test.purchased");

                    }
                }else{
                    bp.purchase(Booster.this,"android.test.purchased");


                }





              /*  if(booster_time!=null) {
                if(!booster_time.equals("0 : 0 : 0"))
                {
                    Toast.makeText(Booster.this, "You are already using a booster", Toast.LENGTH_SHORT).show();
                }
                else {
                    savetime(time, 15);
                    bp.purchase(Booster.this, "android.test.purchased");

                }
                }else{
                    savetime(time, 3);
                    bp.purchase(Booster.this, "android.test.purchased");
                }
*/

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Booster .this, Main_Screen.class);
                startActivity(intent);
            }
        });
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Booster.this, Coins.class);
                startActivity(intent);
            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Booster.this, Frame_Activity.class);
                startActivity(intent);
            }
        });
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getApplicationContext();
            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void booster(long time, int id)
    {
        if(booster_time!=null) {
            if (!booster_time.equals("0 : 0 : 0")) {
                Toast.makeText(Booster.this, "You are already using a booster", Toast.LENGTH_SHORT).show();
            } else {
                savetime(time, id);

            }
        }else{
            savetime(time, id);

        }


    }

    private void getdata()

    {
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                booster_time= (String) dataSnapshot.getValue();
                System.out.println("timeeee--------->"+booster_time);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void savetime(Long boostertime, int id)
    {
        long currentime=System.currentTimeMillis();
        long extendedtime=currentime+boostertime;
        Booster_model booster_model=new Booster_model();

        if(id==3)
        {

            booster_model.setBooster_id("booster_3");

            booster_model.setTimebuy(currentime);
            booster_model.setExtended_time(extendedtime);
            booster_model.setTimespan(boostertime);
            databaseReference1.child(firebaseAuth.getUid()).child(booster_model.getBooster_id()).setValue(booster_model);

        }
        else if(id==5)
        {

            booster_model.setBooster_id("booster_5");


            booster_model.setTimebuy(currentime);
            booster_model.setExtended_time(extendedtime);
            booster_model.setTimespan(boostertime);
            databaseReference1.child(firebaseAuth.getUid()).child(booster_model.getBooster_id()).setValue(booster_model);
        }
        else if(id==10)
        {
            booster_model.setBooster_id("booster_10");
            booster_model.setTimebuy(currentime);
            booster_model.setExtended_time(extendedtime);
            booster_model.setTimespan(boostertime);
            databaseReference1.child(firebaseAuth.getUid()).child(booster_model.getBooster_id()).setValue(booster_model);
        }
        else if(id==15)
        {

            booster_model.setBooster_id("booster_15");
            booster_model.setTimebuy(currentime);
            booster_model.setExtended_time(extendedtime);
            booster_model.setTimespan(boostertime);
            databaseReference1.child(firebaseAuth.getUid()).child(booster_model.getBooster_id()).setValue(booster_model);
        }



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        boolean consume = bp.consumePurchase(productId);
        Toast.makeText(Booster.this, "uiii",Toast.LENGTH_SHORT).show();
      savetime(time, id);

    }
    @Override
    public void onPurchaseHistoryRestored() {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBillingError(int errorCode, Throwable error) {

        Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
       // savetime(time, id);


    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(Booster.this, Main_Screen.class);
        startActivity(intent);
    }
}
