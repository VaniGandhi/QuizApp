package com.quiz.quizapp1.QuizApp.QuizApp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quiz.quizapp1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.ViewHolder> {
    private Context context;
    private List<Avtar_model> avtar;
    private List<Avtar_Status> avtarstatus;
    private onRecyclerclicklistener recyclerclicklistener;

    public GridViewAdapter(Context context, List<Avtar_model> avtar, onRecyclerclicklistener recyclerclicklistener) {
        this.context = context;
        this.avtar = avtar;
        this.recyclerclicklistener = recyclerclicklistener;
    }

    public GridViewAdapter(Context context, List<Avtar_model> avtar) {
        this.context=context;
        this.avtar=avtar;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_layout_item, parent, false);
        return new GridViewAdapter.ViewHolder(view, recyclerclicklistener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Avtar_model avtarModel = avtar.get(position);
        Picasso.with(context).load(avtarModel.getUrl()).
                transform(new RoundedCornersTransformation(10, 10)).into(holder.avtarimage);
        int status = avtarModel.getStatus();
        if (status == 1) {
            holder.price.setText("$0.99");

        } else if (status == 2) {
            holder.price.setText("Purchased");
        } else if (status == 3) {
            holder.price.setText("choosen");
        }
        else if(status==0)
        {
            holder.price.setText("Default");
        }


    }

    void notifyData(List<Avtar_model> avtar) {
        this.avtar = avtar;
        notifyDataSetChanged();
    }
    void notifyDatastatus(List<Avtar_Status> avtarstatus) {
      this.avtarstatus=avtarstatus;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return avtar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView avtarimage;
        public TextView price;
        onRecyclerclicklistener recyclerclicklistener;

        public ViewHolder(@NonNull View itemView, onRecyclerclicklistener recyclerclicklistener) {
            super(itemView);
            avtarimage = itemView.findViewById(R.id.image);
            price = itemView.findViewById(R.id.price);
            this.recyclerclicklistener = recyclerclicklistener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            recyclerclicklistener.onClick(getAdapterPosition());


        }
    }

    public interface onRecyclerclicklistener {
        void onClick(int position);
    }
}
