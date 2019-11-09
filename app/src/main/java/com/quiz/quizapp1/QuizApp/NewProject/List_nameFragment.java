package com.quiz.quizapp1.QuizApp.NewProject;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quiz.quizapp1.R;

import java.util.ArrayList;
import java.util.List;


public class List_nameFragment extends Fragment  implements List_Adapter.onRecyclerclicklistener {
    private final static String SHARED_PREFERENCES_FILE_USER_INFO_LIST = "userInfoList";
    private final static String SHARED_PREFERENCES_KEY_USER_INFO_LIST = "User_Info_List";
    private final static String USER_INFO_LIST_TAG = "USER_INFO_LIST_TAG";
    private static final int MODE_PRIVATE = 0;
    RecyclerView recyclerView;
    List<list_model> usermodel;
    list_model list_model;
    Model_Class model_class;
    List_Adapter adapter;
    Model_Class modelclassarray[];
    private static final String STATE_ITEMS = "items";
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_list_name, container,false);
        recyclerView=view.findViewById(R.id.usernamelist);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return  view;
    }

    @Override
    public void onClick(int position) {
        Bundle bundle=new Bundle();
        Info_Fragment_demo info_fragment=new Info_Fragment_demo();


        if(position==0){
            bundle.putInt("position", 0);
            info_fragment.setArguments(bundle);
         getFragmentManager().beginTransaction().replace(R.id.conatiner,info_fragment).commit();

            // getdataacctolist(0);


        }
        if(position==1){
            bundle.putInt("position", 1);
            info_fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.conatiner,info_fragment).commit();
            //getdataacctolist(1);

        }
        if(position==2){
            bundle.putInt("position", 2);
            info_fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.conatiner,info_fragment).commit();
            //   getdataacctolist(2);

        }if(position==3){
            bundle.putInt("position", 3);
            info_fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.conatiner,info_fragment).commit();
            //  getdataacctolist(3);

        }if(position==4){
            bundle.putInt("position", 4);
            info_fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.conatiner,info_fragment).commit();
            // getdataacctolist(4);
            Toast.makeText(getContext(),"4", Toast.LENGTH_SHORT).show();
        }

    }
    private void getdata()
    {
        Context ctx = getContext();
        sharedPreferences = ctx.getSharedPreferences
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
            adapter=new List_Adapter(getContext(),usermodel,this);
            recyclerView.setAdapter(adapter);




            Log.d(USER_INFO_LIST_TAG, userInfoBuf.toString());
        }
    }
}
