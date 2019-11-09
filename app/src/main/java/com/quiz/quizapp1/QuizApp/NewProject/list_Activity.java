package com.quiz.quizapp1.QuizApp.NewProject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quiz.quizapp1.QuizApp.QuizApp.GridViewAdapter;
import com.quiz.quizapp1.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class list_Activity extends AppCompatActivity implements  List_Adapter.onRecyclerclicklistener {

    private final static String SHARED_PREFERENCES_FILE_USER_INFO_LIST = "userInfoList";
    private final static String SHARED_PREFERENCES_KEY_USER_INFO_LIST = "User_Info_List";
    private final static String USER_INFO_LIST_TAG = "USER_INFO_LIST_TAG";
    List<list_model> usermodel;
    List<Model_Class> modellist;
    List_Adapter adapter;
    RecyclerView recyclerView;
    FrameLayout frameLayout;
    Model_Class model_class;
    Model_Class modelclassarray[];
    private static final String STATE_ITEMS = "items";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //savedInstanceState.putInt("position", r());
        setContentView(R.layout.activity_list_2);
        recyclerView=findViewById(R.id.usernamelist);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(findViewById(R.id.conatiner)!=null)
        {
            if(savedInstanceState!=null)
            {
               model_class= (Model_Class) savedInstanceState.getSerializable(STATE_ITEMS);

            }
            getSupportFragmentManager().beginTransaction().add(R.id.conatiner,new Info_fragment()).commit();
        }



        List<Model_Class> list1=adddata();
        Gson gson = new Gson();
        String userInfoListJsonString = gson.toJson(list1);
        Context ctx = getApplicationContext();
        System.out.println("List   "+userInfoListJsonString );



        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE_USER_INFO_LIST, MODE_PRIVATE);


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_KEY_USER_INFO_LIST, userInfoListJsonString);
        editor.commit();
        getdata();
       //Toast.makeText(ctx, "User info DTO list has been saved in sharedpreferences file.", Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Make sure to call the super method so that the states of our views are saved
        super.onSaveInstanceState(outState);
        // Save our own state now
        outState.putSerializable(STATE_ITEMS, model_class);
    }

    private void getdata()
        {
            Context ctx = getApplicationContext();
                SharedPreferences sharedPreferences = ctx.getSharedPreferences
                        (SHARED_PREFERENCES_FILE_USER_INFO_LIST, MODE_PRIVATE);


        String userInfoListJsonString = sharedPreferences.getString
                (SHARED_PREFERENCES_KEY_USER_INFO_LIST, "");


          Gson gson = new Gson();
              modelclassarray = gson.fromJson(userInfoListJsonString, Model_Class[].class);

            usermodel=new ArrayList<>();
          int length = modelclassarray.length;
         for (int i = 0; i < length; i++) {



         model_class = modelclassarray[i];
        StringBuffer userInfoBuf = new StringBuffer();
        String name=model_class.getName();
        list_model model=new list_model();
        model.setName(name);
        usermodel.add(model);
             adapter=new List_Adapter(getApplicationContext(),usermodel,this);
             recyclerView.setAdapter(adapter);




        Log.d(USER_INFO_LIST_TAG, userInfoBuf.toString());
    }
}
    private List<Model_Class> adddata()
    {
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
        private void getdataacctolist(int position)
        {
            Context ctx = getApplicationContext();
            SharedPreferences sharedPreferences = ctx.getSharedPreferences
                    (SHARED_PREFERENCES_FILE_USER_INFO_LIST, MODE_PRIVATE);


            String userInfoListJsonString = sharedPreferences.getString
                    (SHARED_PREFERENCES_KEY_USER_INFO_LIST, "");


            Gson gson = new Gson();
            modelclassarray = gson.fromJson(userInfoListJsonString, Model_Class[].class);

            usermodel=new ArrayList<>();
            int length = modelclassarray.length;
            for (int i = 0; i < length; i++) {
                if(position==i)
                {
                    model_class=modelclassarray[i];
                    String name=model_class.getName();
                    String address=model_class.getAddress();
                    String email=model_class.getEmail();
                    String phoneno=model_class.getPhone();
                    String id=model_class.getId();
                    model_class.setName(name);
                    model_class.setAddress(address);
                    model_class.setPhone(phoneno);
                    model_class.setEmail(email);
                    model_class.setId(id);
                    System.out.println("name=="+name+ "id=="+id);
                    Info_fragment info_fragment=new Info_fragment();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("userinfo", model_class);
                    info_fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.conatiner,info_fragment).commit();


                    ;
                }

            }



        }

    @Override
    public void onClick(int position) {


        if(position==0){
            getdataacctolist(0);

        }
        if(position==1){
            getdataacctolist(1);

        }
        if(position==2){
            getdataacctolist(2);

        }if(position==3){
            getdataacctolist(3);

        }if(position==4){
            getdataacctolist(4);
            Toast.makeText(list_Activity.this, "4", Toast.LENGTH_SHORT).show();
        }

    }
}
