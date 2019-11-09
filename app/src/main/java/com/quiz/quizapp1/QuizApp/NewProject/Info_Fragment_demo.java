package com.quiz.quizapp1.QuizApp.NewProject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.quiz.quizapp1.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Info_Fragment_demo extends Fragment implements List_Adapter.onRecyclerclicklistener, View.OnClickListener {
    EditText name, address, phoneno, email, id;

    String sname, saddress, sphoneno, semail, sid;
    List<Model_Class> userlist1;
    String username, useraddress, useremail, userphoneno, userid;
    ImageView edit_data;
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String PHONENO = "phoneno";
    public static final String EMAIL = "email";
    public static final String ID = "id";
    private final static String SHARED_PREFERENCES_FILE_USER_INFO_LIST = "userInfoList";
    private final static String SHARED_PREFERENCES_KEY_USER_INFO_LIST = "User_Info_List";
    private final static String USER_INFO_LIST_TAG = "USER_INFO_LIST_TAG";
    List<Model_Class> usermodel;
    List<Model_Class> modellist;
    List<Model_Class> userlist;

    List_Adapter adapter;
    RecyclerView recyclerView;
    FrameLayout frameLayout;
    Model_Class model_class;
    Model_Class model;

    Model_Class modelclassarray[];
    private static final String STATE_ITEMS = "items";
    int position;
    Button done;
    SharedPreferences sharedPreferences;
    List<Model_Class> updatedlist;
    View view;
    int pp;
    Boolean edit = false;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            try {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (Build.VERSION.SDK_INT >= 26) {
                    ft.setReorderingAllowed(false);
                }
                ft.detach(this).attach(this).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_info_fragment, container, false);

        /*if (savedInstanceState != null) {
            model_class = (Model_Class) savedInstanceState.getSerializable(STATE_ITEMS);
            edit = (Boolean) savedInstanceState.getSerializable("editable");
            if (edit) {
                name.setEnabled(true);
                address.setEnabled(true);
                email.setEnabled(true);

                phoneno.setEnabled(true);
            }
            // position = (int) savedInstanceState.getSerializable(STATE_POSITION);

        }
*/

        System.out.println("Info_Fragment_demo.onCreateView--------->" + getContext().getResources().getDisplayMetrics().density);


        name = view.findViewById(R.id.nameedit);
        address = view.findViewById(R.id.addressedit);
        phoneno = view.findViewById(R.id.phoneedit);
        email = view.findViewById(R.id.emailedit);
        id = view.findViewById(R.id.idedit);
        edit_data = view.findViewById(R.id.edit_data);
        done = view.findViewById(R.id.buttondone);
        sharedPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES_FILE_USER_INFO_LIST, MODE_PRIVATE);
        usermodel = new ArrayList<>();


        name.setEnabled(false);
        address.setEnabled(false);
        email.setEnabled(false);
        id.setEnabled(false);
        phoneno.setEnabled(false);
        Bundle bundle = getArguments();

        if (bundle != null) {
            position = bundle.getInt("position");
            getdataacctolist(position);


        }

        edit_data.setOnClickListener(this);
        done.setOnClickListener(this);


        return view;
    }


    @Override
    public void onResume() {

        super.onResume();


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putSerializable("editable", edit);
        outState.putSerializable(STATE_ITEMS, model_class);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        System.out.println("savedinstance " + savedInstanceState);
        done = view.findViewById(R.id.buttondone);
        edit_data = view.findViewById(R.id.edit_data);
        edit_data.setOnClickListener(this);

        if (savedInstanceState != null) {
            model_class = (Model_Class) savedInstanceState.getSerializable(STATE_ITEMS);


            edit = (Boolean) savedInstanceState.getSerializable("editable");
            if (edit) {
                name.setEnabled(true);
                address.setEnabled(true);
                email.setEnabled(true);

                phoneno.setEnabled(true);
            }

        }


        super.onViewStateRestored(savedInstanceState);
    }


    private void updatedata1(int position, String editext1, String editext2, String editext3, String editext4, String editext5) {
        String userInfoListJsonString = sharedPreferences.getString
                (SHARED_PREFERENCES_KEY_USER_INFO_LIST, "");
        System.out.println("daata" + userInfoListJsonString);
        Gson gson = new Gson();
        modelclassarray = gson.fromJson(userInfoListJsonString, Model_Class[].class);
        System.out.println("size" + modelclassarray.length);
        usermodel = new ArrayList<>();

        int length = modelclassarray.length;
        System.out.println("array" + length);
        for (int i = 0; i < length; i++) {

            model_class = modelclassarray[i];
            usermodel.add(model_class);
            System.out.println("usermodelsize" + usermodel.size());
            if (i == position) {

                model_class.setId(editext1);
                model_class.setEmail(editext3);
                model_class.setPhone(editext5);
                model_class.setAddress(editext4);
                model_class.setName(editext2);
                usermodel.set(position, model_class);
                System.out.println("afterupdate" + usermodel.size());


            }
            List<Model_Class> list1 = usermodel;
            System.out.println("updatedlist" + list1);
            gson = new Gson();
            String userInfoListJsonString1 = gson.toJson(list1);

            System.out.println("updatredList   " + userInfoListJsonString1);
            sharedPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES_FILE_USER_INFO_LIST, MODE_PRIVATE);


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(SHARED_PREFERENCES_KEY_USER_INFO_LIST, userInfoListJsonString1);
            editor.apply();


        }

    }

    private void getdataacctolist(int pos) {

        sharedPreferences = getContext().getSharedPreferences
                (SHARED_PREFERENCES_FILE_USER_INFO_LIST, MODE_PRIVATE);


        String userInfoListJsonString = sharedPreferences.getString
                (SHARED_PREFERENCES_KEY_USER_INFO_LIST, "");
        System.out.println("updated list   " + userInfoListJsonString);


        Gson gson = new Gson();
        modelclassarray = gson.fromJson(userInfoListJsonString, Model_Class[].class);


        model_class = modelclassarray[pos];
        username = model_class.getName();
        useraddress = model_class.getAddress();
        useremail = model_class.getEmail();
        userphoneno = model_class.getPhone();
        userid = model_class.getId();
        name.setText(username);
        address.setText(useraddress);
        email.setText(useremail);
        phoneno.setText(userphoneno);
        id.setText(userid);


       /* int length = modelclassarray.length;
        for (int i = 0; i < length; i++) {
            if (pos == i) {


            }

        }
*/

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    private void editdata() {
        name.setEnabled(true);
        address.setEnabled(true);
        email.setEnabled(true);

        phoneno.setEnabled(true);
    }


    @Override
    public void onClick(int position) {
        userlist.get(position);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttondone:
                edit = false;
                String textname = name.getText().toString();

                name.setEnabled(false);
                address.setEnabled(false);
                email.setEnabled(false);
                id.setEnabled(false);
                phoneno.setEnabled(false);

                String textemail = email.getText().toString();
                String textphoneno = phoneno.getText().toString();
                String textid = id.getText().toString();
                String textaddress = address.getText().toString();

                System.out.println("Info_Fragment_demo.onClick------>" + textname);

                updatedata1(position, textid, textname, textemail, textaddress, textphoneno);
                break;

            case R.id.edit_data:
                edit = true;
                editdata();
                break;

        }

    }
}
