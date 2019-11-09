package com.quiz.quizapp1.QuizApp.NewProject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.quiz.quizapp1.R;

import java.util.ArrayList;
import java.util.List;

public class list_Activity_demo extends AppCompatActivity implements List_Adapter.onRecyclerclicklistener {

    private final static String SHARED_PREFERENCES_FILE_USER_INFO_LIST = "userInfoList";
    private final static String SHARED_PREFERENCES_KEY_USER_INFO_LIST = "User_Info_List";
    private final static String USER_INFO_LIST_TAG = "USER_INFO_LIST_TAG";
    List<list_model> usermodel;
    List<Model_Class> modellist;
    List_Adapter adapter;
    RecyclerView recyclerView;
    FrameLayout frameLayout;
    Model_Class model_class;
    list_model list_model;
    Model_Class modelclassarray[];
    private static final String STATE_ITEMS = "items";
    private static final String STATE_POSITION = "items";
    SharedPreferences sharedPreferences;
    Bundle saveinstancestate;
    int pp;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //savedInstanceState.putInt("position", r());
        setContentView(R.layout.activity_list_2);
        recyclerView = findViewById(R.id.usernamelist);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (findViewById(R.id.conatiner) != null) {
            if (savedInstanceState != null) {
                model_class = (Model_Class) savedInstanceState.getSerializable(STATE_ITEMS);


            }
            getSupportFragmentManager().beginTransaction().add(R.id.conatiner, new Info_Fragment_demo()).commit();
        }


        List<Model_Class> list1 = adddata();
        Gson gson = new Gson();
        String userInfoListJsonString = gson.toJson(list1);
        Context ctx = getApplicationContext();
        System.out.println("List   " + userInfoListJsonString);


        sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE_USER_INFO_LIST, MODE_PRIVATE);


        if (sharedPreferences == null) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(SHARED_PREFERENCES_KEY_USER_INFO_LIST, userInfoListJsonString);
            editor.apply();

        } else {
            getdata();
        }

    /*SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_KEY_USER_INFO_LIST, userInfoListJsonString);
        editor.commit();
        getdata();

*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Make sure to call the super method so that the states of our views are saved
        super.onSaveInstanceState(outState);
        // Save our own state now
        outState.putSerializable(STATE_ITEMS, model_class);
        //outState.putSerializable(STATE_POSITION, pp);
    }


    private void getdata() {
        Context ctx = getApplicationContext();
        sharedPreferences = ctx.getSharedPreferences
                (SHARED_PREFERENCES_FILE_USER_INFO_LIST, MODE_PRIVATE);


        String userInfoListJsonString = sharedPreferences.getString
                (SHARED_PREFERENCES_KEY_USER_INFO_LIST, "");


        Gson gson = new Gson();
        modelclassarray = gson.fromJson(userInfoListJsonString, Model_Class[].class);


        usermodel = new ArrayList<>();


        if (modelclassarray != null) {
            int length = modelclassarray.length;
            for (int i = 0; i < length; i++) {


                model_class = modelclassarray[i];
                StringBuffer userInfoBuf = new StringBuffer();
                String name = model_class.getName();
                list_model model = new list_model();
                model.setName(name);
                usermodel.add(model);
                adapter = new List_Adapter(getApplicationContext(), usermodel, this);
                recyclerView.setAdapter(adapter);


                Log.d(USER_INFO_LIST_TAG, userInfoBuf.toString());
            }
        }


    }

    private List<Model_Class> adddata() {
        List<Model_Class> userlist = new ArrayList<>();

        Model_Class userModel1 = new Model_Class();
        userModel1.setId("1");
        userModel1.setName("vani");
        userModel1.setEmail("abc@gmail.com");
        userModel1.setAddress("abc");
        userModel1.setPhone("1234567890");
        userlist.add(userModel1);

        Model_Class userModel2 = new Model_Class();
        userModel2.setId("2");
        userModel2.setName("simar");
        userModel2.setEmail("def@gmail.com");
        userModel2.setAddress("def");
        userModel2.setPhone("0987654545");
        userlist.add(userModel2);

        Model_Class userModel3 = new Model_Class();
        userModel3.setId("3");
        userModel3.setName("arti");
        userModel3.setEmail("ijk@gmail.com");
        userModel3.setAddress("ijk");
        userModel3.setPhone("6487263478");
        userlist.add(userModel3);

        Model_Class userModel4 = new Model_Class();
        userModel4.setId("4");
        userModel4.setName("monika");
        userModel4.setEmail("lmn@gmail.com");
        userModel4.setAddress("lmn");
        userModel4.setPhone("2436283462");
        userlist.add(userModel4);

        Model_Class userModel5 = new Model_Class();
        userModel5.setId("5");
        userModel5.setName("nikita");
        userModel5.setEmail("opq@gmail.com");
        userModel5.setAddress("opq");
        userModel5.setPhone("4383758934");
        userlist.add(userModel5);


        return userlist;
    }


    @Override
    public void onClick(int position) {
        Bundle bundle = new Bundle();
        Info_Fragment_demo info_fragment = new Info_Fragment_demo();
        pp = position;


        bundle.putInt("position", position);
        info_fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.conatiner, info_fragment)
                .addToBackStack(Info_Fragment_demo.class.getSimpleName()).commit();


    }





  /*  @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
      *//*  Bundle bundle = new Bundle();
        Info_Fragment_demo info_fragment = new Info_Fragment_demo();



        bundle.putInt("position", pp);
        info_fragment.setArguments(bundle);*//*
   //
    //getSupportFragmentManager().beginTransaction().replace(R.id.conatiner, new Info_Fragment_demo()).commit();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment removeFragment = getSupportFragmentManager().findFragmentByTag(Info_Fragment_demo.class.getSimpleName());
        if (removeFragment!=null) {
            System.out.println("list_Activity_demo.onConfigurationChanged--->Found");
            transaction.detach(removeFragment);
        }
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(Info_Fragment_demo.class.getSimpleName());
        if (fragment != null) {
           // getSupportFragmentManager().beginTransaction().replace(R.id.conatiner, new Info_Fragment_demo()).commit();
            transaction.attach(fragment);
        } else {
            fragment = new Info_Fragment_demo();


            transaction.replace(R.id.conatiner, fragment,
                    Info_Fragment_demo.class.getSimpleName());
        }
        transaction.commit();



        System.out.println("newconfig   " + newConfig);



    }*/


}
