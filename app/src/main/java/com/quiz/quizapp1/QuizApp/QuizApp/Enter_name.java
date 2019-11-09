package com.quiz.quizapp1.QuizApp.QuizApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quiz.quizapp1.R;

import java.util.ArrayList;
import java.util.List;

public class Enter_name extends AppCompatActivity {

    EditText name;
    ImageView next;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference, databaseReference1;
    String number,username, avtar_id, url;
    String uid;
    int rank=1;
    int level=1;
    int xp=50;
    int star=4;
    int coin=0;
    int incr =50;
    int levelreached=150;
    int count=0;
    int points=0;
    int contiwin=0;
    int sheild=0;
    public static  final String MYPREFERENCES="MyPrefs";
    List<Avtar_model> avtar;
    int avtarcount;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_enter_name);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        name=findViewById(R.id.enter_username);
        next=findViewById(R.id.nextbutton);

        firebaseAuth=FirebaseAuth.getInstance();
   //     Utils.hasInternetAccess(getApplicationContext());
//       Utils.getDatabase();
        uid=firebaseAuth.getUid();
        username=name.getText().toString().trim();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference1=FirebaseDatabase.getInstance().getReference("Avtar");
        sharedPreferences=getApplicationContext().getSharedPreferences(MYPREFERENCES,MODE_PRIVATE);

        number=getIntent().getStringExtra("phoneno.");
        getavtar();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username =name.getText().toString().trim();
                if(TextUtils.isEmpty(username)){
                    name.setError("enter name");
                    name.requestFocus();
                    return;
                }
                else

                {
                    Intent intent=new Intent(Enter_name.this,Main_Screen.class);
                    startActivity(intent);


                    System.out.println(number+" "+username+" "+uid);
                   avtar_id= avtar.get(0).getId();
                   url=avtar.get(0).getUrl();

                    addExtras(rank,level,xp,star,coin, incr, levelreached, count, points, contiwin, sheild);
                    createnewUser(number,username,uid,avtar_id, url);
                    sharedPreferences.edit().putBoolean("islogin",true).commit();
                }


            }
        });


    }

    private void getavtar()
    {
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                avtar=new ArrayList<>();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Avtar_model avtarModel=snapshot.getValue(Avtar_model.class);
                    avtar.add(avtarModel);
                }
                avtarcount=avtar.size();
                System.out.println("avtarcount------------"+avtarcount);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void addExtras(int rank, int level, int xp, int star , int coin , int iswin ,int levelreached, int count, int points,
                           int contiwin, int sheild)
    {
        String uid= firebaseAuth.getUid();
        Extras_model extras_model=new Extras_model(rank,level,xp,star,coin, iswin, levelreached, count, points, contiwin, sheild);
        databaseReference.child("extras").child(uid).child(uid).setValue(extras_model);
    }

    private void createnewUser(String id,String name,String uid, String avtar_id, String url)
    {



        UserModel  userModel=new UserModel(id, name, uid,avtar_id,url);
        databaseReference.child("users").child(uid).child(id).setValue(userModel);
    }
}
