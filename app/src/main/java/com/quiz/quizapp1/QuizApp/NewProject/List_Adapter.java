package com.quiz.quizapp1.QuizApp.NewProject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quiz.quizapp1.QuizApp.QuizApp.GridViewAdapter;
import com.quiz.quizapp1.R;

import java.util.ArrayList;
import java.util.List;

public class List_Adapter extends RecyclerView.Adapter<List_Adapter.ViewHolder>  {
    private Context context;
    private List<list_model> usermodel;
    private List_Adapter.onRecyclerclicklistener recyclerclicklistener;


    public List_Adapter(Context context, List<list_model> usermodel, List_Adapter.onRecyclerclicklistener recyclerclicklistener)
    {
        this. context=context;
        this.usermodel=usermodel;
        this.recyclerclicklistener = recyclerclicklistener;
    }

    @NonNull
    @Override
    public List_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_view_item, parent, false);
        return new List_Adapter.ViewHolder(view, recyclerclicklistener);
    }

    @Override
    public void onBindViewHolder(@NonNull List_Adapter.ViewHolder holder, int position) {

        list_model model=usermodel.get(position);
        holder.username.setText(model.getName());

    }

    @Override
    public int getItemCount() {
        return usermodel.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        public TextView username;
        List_Adapter.onRecyclerclicklistener recyclerclicklistener;


        public ViewHolder(@NonNull View itemView, List_Adapter.onRecyclerclicklistener recyclerclicklistener) {


            super(itemView);
            username=itemView.findViewById(R.id.listviewusername);
            this.recyclerclicklistener = recyclerclicklistener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            recyclerclicklistener.onClick(getAdapterPosition());
        }
    }

   interface onRecyclerclicklistener {
        void onClick(int position);
    }
}
