package com.quiz.quizapp1.QuizApp.QuizApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quiz.quizapp1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Frame_Activity extends AppCompatActivity implements GridViewAdapter.onRecyclerclicklistener, BillingProcessor.IBillingHandler {

    ImageView back;
    LinearLayout l1, l2, l3;
    DatabaseReference databaseReference, databaseReference1, databaseReference2, databaseReference3, databaseReference4;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    List<Avtar_model> avtar;
    List<Avtar_Status> avtar1;
    List<Avtar_Status> avtar2;
    List<Updateavtarmodel> updatedlist;

    String avtarurl, url, avtar_id;
    GridViewAdapter gridViewAdapter;
    RecyclerView recyclerView;
    Intent intent;
    int avtarsize = 0, avsize = 0, idsize;
    BillingProcessor bp;

    List<String> idlist;

    Boolean key=false;
String avatar_id, avatar_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_frame_);
  //      Utils.getDatabase().setPersistenceEnabled(true);
       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Avtar");
        databaseReference1 = FirebaseDatabase.getInstance().getReference();
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Avtar_bought").child(firebaseAuth.getUid());
        databaseReference4 = FirebaseDatabase.getInstance().getReference("Avtar_bought").child(firebaseAuth.getUid());
        databaseReference3 = FirebaseDatabase.getInstance().getReference("users").child(firebaseAuth.getUid());

        back = findViewById(R.id.backframe);
        l1 = findViewById(R.id.ftop1);
        l2 = findViewById(R.id.ftop2);
        l3 = findViewById(R.id.ftop3);
        bp = new BillingProcessor(this, "null", this);
        bp.initialize();
        intent = getIntent();
        avtar_id = intent.getStringExtra("id");
        url = intent.getStringExtra("url");
        System.out.println(" id " + avtar_id + " url " + url);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Frame_Activity.this, Main_Screen.class);
                startActivity(intent);
            }
        });
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Frame_Activity.this, Coins.class);
                startActivity(intent);
            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getApplicationContext();
            }
        });
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Frame_Activity.this, Booster.class);
                startActivity(intent);
            }
        });
        getavtar();
        avtarbought();


    }

    public void avtarbought() {
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                avtar1 = new ArrayList<>();
                idlist = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.getKey();
                    idlist.add(id);


                    Avtar_Status avtar_status = snapshot.getValue(Avtar_Status.class);
                    avtar1.add(avtar_status);
                    int status = avtar_status.getStatus();
                    System.out.println("  id--->" + id + "  status------>" + status);


                }

                avtarsize = avtar1.size();
                idsize = idlist.size();

                comparedata();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void comparedata() {


        for (int i = 0; i < avsize; i++) {
            for (int j = 0; j < idsize; j++) {
                if (avtar.get(i).getId().equals(idlist.get(j))) {

                    avtar.get(i).setStatus(2);


                }

            }
        }
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    String id = userModel.getAvtar_id();
                    for (int i = 0; i < avtar.size(); i++) {

                       /* if(avtar.get(i).getStatus()==0)
                        {
                            avtar.get(i).setStatus(0);
                        }*/
                        if (avtar.get(i).getId().equals(id)&& avtar.get(i).getStatus()!=0) {
                            avtar.get(i).setStatus(3);
                        }
                    }
                }
                gridViewAdapter.notifyData(avtar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        gridViewAdapter.notifyData(avtar);


    }



    public void getavtar() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                avtar = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Avtar_model avtarModel = snapshot.getValue(Avtar_model.class);
                    avtarurl = avtarModel.getUrl();
                    avtar.add(avtarModel);
                }
                avsize = avtar.size();
                System.out.println("avsize------------>" + avsize);
                setdata();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void setdata() {
        gridViewAdapter = new GridViewAdapter(getApplicationContext(), avtar, this);
        recyclerView.setAdapter(gridViewAdapter);

    }


    private  void setstatus(final String id, String url)
    {
        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Avtar_Status avtar_status=snapshot.getValue(Avtar_Status.class);



                        if(snapshot.getKey().equals(id))
                        {
                            avtar_status.setStatus(3);

                    }
                    avtar2.add(avtar_status);
                    gridViewAdapter.notifyDatastatus(avtar2);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void alertdialog(final String id, final String avtarurl) {
        System.out.println("avartar_id ="+id+ "avtarurl="+avtarurl);
        new SweetAlertDialog(Frame_Activity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You want to buy avtar")
                .setConfirmText("Confirm!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        int status = 2;
                        System.out.println("avtarurl---------->" + avtarurl);
                        final Avtar_Status avtar_status = new Avtar_Status(status);

                        databaseReference1.child("Avtar_bought").child(firebaseAuth.getUid()).child(id).setValue(avtar_status);
                        key=true;
                        updateprofile(id, avtarurl);
                        for(int i=0;i<avtar.size();i++)
                        {
                            if (avtar.get(i).getStatus() == 3) {
                                avtar.get(i).setStatus(2);
                            }

                        }
                        gridViewAdapter.notifyData(avtar);


                        databaseReference4.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                avtar2=new ArrayList<>();
                            for(DataSnapshot snapshot:dataSnapshot.getChildren())
                                {
                                    Avtar_Status avtar_status1=snapshot.getValue(Avtar_Status.class);
                                    String avtarkey=snapshot.getKey();
                                    if(avtarkey.equals(id))
                                    {
                                        avtar_status1.setStatus(3);

                                        databaseReference4.child(avtarkey).setValue(avtar_status1);

                                    }
                                    avtar2.add(avtar_status1);
                                    gridViewAdapter.notifyDatastatus(avtar2);

                                 /*   if(avtar_status1.getStatus()==3)
                                    {
                                        System.out.println("key"+avtarkey);
                                        avtar_status1.setStatus(2);
                                        databaseReference4.child(avtarkey).setValue(avtar_status1);
                                    }
*/

                                /*   avtar2.add(avtar_status1);
                                   gridViewAdapter.notifyDatastatus(avtar2);*/





                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });






                        /*for (int i = 0; i < avtar.size(); i++) {

                            if (avtar.get(i).getStatus()==3) {

                                Toast.makeText(Frame_Activity.this, "encountered", Toast.LENGTH_SHORT).show();

                                avtar.get(i).setStatus(2);

                            }
                            updateprofile(id,avtarurl);

                        }
                        gridViewAdapter.notifyData(avtar);

*/

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

    public void alertdialogchoose(final String id, final String avtarurl) {
        new SweetAlertDialog(Frame_Activity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure")
                .setContentText("U want to set this as profile picture")
                .setConfirmText("Confirm!").
                setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();

                       for (int i = 0; i < avtar.size(); i++) {

                            if (avtar.get(i).getStatus() == 3) {
                                key=true;
                                avtar.get(i).setStatus(2);
                            }

                            if (avtar.get(i).getId().equals(id)) {
                                 avtar.get(i).setStatus(3);
                                 updateprofile(id,avtarurl);

                            }
                       }

                        gridViewAdapter.notifyData(avtar);









                    }



                })

                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                }).show();


    }

    private void updatestatus(final String id , String url)
    {
        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                avtar2=new ArrayList<>();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    Avtar_Status avtar_status = snapshot.getValue(Avtar_Status.class);
                    String avtkey = snapshot.getKey();
                        int i;
                    for( i=0;i<avtar2.size();i++)
                    {

                        if(avtar2.get(i).getStatus()==3)
                        {
                            avtar2.get(i).setStatus(2);
                        }
                        if(avtkey.equals(id))
                        {
                            avtar2.get(i).setStatus(3);

                        }

                    }
                    avtar2.add(avtar_status);
                    gridViewAdapter.notifyDatastatus(avtar2);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateprofile(final String avid, final String url) {
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(key)
                {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UserModel userModel = snapshot.getValue(UserModel.class);


                        String name = userModel.getName();
                        String uid = userModel.getUid();
                        String id = userModel.getId();
                        userModel.setId(id);
                        userModel.setAvtar_id(avid);
                        userModel.setUrl(url);
                        userModel.setName(name);
                        userModel.setUid(uid);
                        databaseReference3.child(id).setValue(userModel);
                     //   updatestatus(avid, url);
                        key=false;
                    }



                    }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onBackPressed()
    { Intent intent=new Intent(Frame_Activity.this, Main_Screen.class);
        startActivity(intent);
    }


    @Override
    public void onClick(int position) {

        avtar.get(position);
        Avtar_model avtarModel = avtar.get(position);
        if (avtarModel.getStatus() == 1) {
            avatar_id=avtarModel.getId();
            avatar_url=avtarModel.getUrl();
            bp.purchase(Frame_Activity.this, "android.test.purchased");



        } else if (avtarModel.getStatus() == 2) {
            alertdialogchoose(avtarModel.getId(), avtarModel.getUrl());




        }
        else if (avtarModel.getStatus() == 0) {
            avatar_id=avtarModel.getId();
            avatar_url=avtarModel.getUrl();
            bp.purchase(Frame_Activity.this, "android.test.purchased");
        }





        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        boolean cousme = bp.consumePurchase(productId);
        alertdialog(avatar_id,avatar_url);
        System.out.println("avartar_id =="+avatar_id+ "avtarurl=="+avatar_url);
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        //Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
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
}
