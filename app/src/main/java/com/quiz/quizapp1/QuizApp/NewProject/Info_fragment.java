package com.quiz.quizapp1.QuizApp.NewProject;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.quiz.quizapp1.R;

import java.io.Serializable;
import java.util.List;

public class Info_fragment extends Fragment implements List_Adapter.onRecyclerclicklistener {
        EditText name, address, phoneno, email, id;
        List<Model_Class> userlist;
        String username, useraddress, useremail, userphoneno, userid;
        ImageView edit_data;
        public static final String NAME="name";
    public static final String ADDRESS="address";
    public static final String PHONENO="phoneno";
    public static final String EMAIL="email";
    public static final String ID="id";








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_info_fragment, container,false);
        name=view.findViewById(R.id. nameedit);
        address=view.findViewById(R.id.addressedit);
        phoneno=view.findViewById(R.id. phoneedit);
        email=view.findViewById(R.id.emailedit);
        id=view.findViewById(R.id.idedit);
        edit_data=view.findViewById(R.id.edit_data);
        name.setEnabled(false);
        address.setEnabled(false);
        email.setEnabled(false);
        id.setEnabled(false);
        phoneno.setEnabled(false);
        name.setText(null);
        address.setText(null);
        email.setText(null);
        phoneno.setText(null);
        id.setText(null);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Model_Class model_class = (Model_Class) bundle
                    .getSerializable("userinfo");
            username=model_class.getName();
            useraddress=model_class.getAddress();
            userid=model_class.getId();
            useremail=model_class.getEmail();
            userphoneno=model_class.getPhone();
            System.out.println("fragmentdata  "+userphoneno+"  "+userid);


            name.setText(username);
            address.setText(useraddress);
            email.setText(useremail);
            phoneno.setText(userphoneno);
            id.setText(userid);


        }
        edit_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editdata();

            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        return view;
    }
    private void editdata()
    {
        name.setEnabled(true);
        address.setEnabled(true);
        email.setEnabled(true);
        id.setEnabled(true);
        phoneno.setEnabled(true);
    }


            @Override
    public void onClick(int position) {
        userlist.get(position);
        if(position==0){
            Toast.makeText(getContext(), "0", Toast.LENGTH_SHORT).show();
        }
        if(position==1){
            Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
        }
        if(position==2){
            Toast.makeText(getContext(), "2", Toast.LENGTH_SHORT).show();
        }if(position==3){
            Toast.makeText(getContext(), "3", Toast.LENGTH_SHORT).show();
        }if(position==4){
            Toast.makeText(getContext(), "4", Toast.LENGTH_SHORT).show();
        }








    }
}
