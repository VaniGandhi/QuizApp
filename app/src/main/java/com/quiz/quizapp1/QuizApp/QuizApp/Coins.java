package com.quiz.quizapp1.QuizApp.QuizApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quiz.quizapp1.R;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Coins extends AppCompatActivity  implements  View.OnClickListener, BillingProcessor.IBillingHandler{
    BillingProcessor bp;
    ImageView back;
    RelativeLayout coin50, coin100, coin250, coin500;
    LinearLayout l1, l2, l3;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference, databaseReference1;
    int noofcoins, coin, incrementcoins=0, newcoins;
    List<Coin_model> coins;
    private  static final  int fifty=50;
    private  static final  int hundred=100;
    private  static final  int twofifty=250;
    private  static final  int fivehundred=500;
    Boolean hashkey = false;
    int rank, level, xp, star, newxp, newcoin, newstar, newrank, newlevel, incr, newincr, levelreached, newlevelrecahed,
            count, newcount, newpoints,points, contiwin, newcontiwin, sheild, newsheild;
    Boolean consume;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_coins);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
       //databaseReference=FirebaseDatabase.getInstance().getReference("coins");
        databaseReference1=FirebaseDatabase.getInstance().getReference("extras").child(firebaseAuth.getUid());
        back=findViewById(R.id.backcoin);
        l1=findViewById(R.id.ctop1);
        l2=findViewById(R.id.ctop2);
        l3=findViewById(R.id.ctop3);
        coin50=findViewById(R.id.coin50);
        coin100=findViewById(R.id.coin100);
        coin250=findViewById(R.id.coin250);
        coin500=findViewById(R.id.coin500);
        coin500.setOnClickListener(this);
        coin250.setOnClickListener(this);
        coin100.setOnClickListener(this);
        coin50.setOnClickListener(this);
        bp = new BillingProcessor(this, "null", this);
        bp.initialize();
        getdata();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Coins.this, Main_Screen.class);
                startActivity(intent);
            }
        });
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getApplicationContext();
            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Coins.this, Frame_Activity.class);
                startActivity(intent);
            }
        });
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Coins.this, Booster.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        consume = bp.consumePurchase(productId);
       /* if(consume)
        {*/
            incrementcoins=incrementcoins+noofcoins;
            alertdialog(noofcoins, incrementcoins);

//        }


        // Toast.makeText(this, "You have purchased something and counsumed", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();

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


    public void getdata()
    {

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!hashkey) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Extras_model extras_model = snapshot.getValue(Extras_model.class);
                        coin=extras_model.getCoin();
                        rank=extras_model.getRank();
                        level=extras_model.getLevel();
                        count=extras_model.getCount();
                        star=extras_model.getStar();
                        xp=extras_model.getXp();
                        incr=extras_model.getIncr();
                        levelreached=extras_model.getLevelreached();
                        points=extras_model.getPoints();
                        System.out.println("rank=" + rank + "  " + level + " " + xp + " " + star + " "
                                + coins + " " + incr + " " + levelreached + " " + count);
                        hashkey = true;
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void checkcoins(int coins)
    {
        System.out.println("rank=" + rank + "  " + level + " " + xp + " " + star + " "
                + " coins "+coins + " coin "+coin + incr + " " + levelreached + " " + count);

       newcoins=coin+coins;
       newrank=rank;
       newlevel=level;
       newcount=count;
       newstar=star;
       newxp=xp;
       newincr=incr;
       newlevelrecahed=levelreached;
       newpoints=points;
       newsheild=sheild;
        System.out.println(" newxp " + newxp + " newrank " + newrank + " newlevel " + newlevel +
                " newstar " + newstar + " newincr " + newincr + " newlevelreched " + newlevelrecahed+" newcoins "+newcoins);

        addextras(newrank, newlevel, newxp, newstar, newcoins, newincr, newlevelrecahed, newcount, newpoints, newcontiwin, newsheild);

    }

    public void addextras(int rank, int level, int xp, int star, int coin, int incr, int levelreached, int count, int points,
                          int contiwin, int sheild)
    {
        String uid = firebaseAuth.getUid();
        Extras_model extras_model = new Extras_model(rank, level, xp, star, coin, incr, levelreached, count, points, contiwin, sheild);
        databaseReference1.child(uid).setValue(extras_model);
    }

    public void alertdialog(int coins, final int coin)
    {
        new SweetAlertDialog(Coins.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You want to buy "+coins+" coins.")
                .setConfirmText("Confirm!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                        checkcoins(coin);

                    }
                })
                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(Coins.this, Main_Screen.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.coin50:
                noofcoins=50;
                bp.purchase(Coins.this, "android.test.purchased");
                Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();


                //oast.makeText(this, "hii", Toast.LENGTH_SHORT).show();
              /* incrementcoins=incrementcoins+50;
                alertdialog(fifty, incrementcoins);*/



                break;
            case R.id.coin100:
                noofcoins=100;
                bp.purchase(Coins.this, "android.test.purchased");
                Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();

                   /* incrementcoins = incrementcoins + 100;
                    alertdialog(hundred, incrementcoins);*/

                break;
            case R.id.coin250:
                noofcoins=250;
                bp.purchase(Coins.this, "android.test.purchased");
                Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();

               /* incrementcoins=incrementcoins+250;
                alertdialog(twofifty, incrementcoins);*/
                break;
                case R.id.coin500:
                    noofcoins=500;
                    bp.purchase(Coins.this, "android.test.purchased");

                    /*incrementcoins=incrementcoins+500;
                   checkcoins(incrementcoins);
                   alertdialog(fivehundred, incrementcoins);*/
            break;


        }

    }

}
