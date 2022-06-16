package com.example.je_attendancesystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.je_attendancesystem.R;
import com.example.je_attendancesystem.models.DateTimeModel;
import com.example.je_attendancesystem.models.ProjectModel;
import com.example.je_attendancesystem.models.TimesheetModel;

import java.util.ArrayList;

public class DateTimeAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<DateTimeModel> dateTimeModels;

    public DateTimeAdapter(Context context, ArrayList<DateTimeModel> dateTimeModels){
        this.context = context;
        this.dateTimeModels = dateTimeModels;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.date_time, parent, false);
        return new DateTimeAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DateTimeModel dateTimeModel = this.dateTimeModels.get(position);
        setAnimation(holder.itemView);
        ((DateTimeAdapterHolder) holder).bind(dateTimeModel);
    }

    @Override
    public int getItemCount() {
        return this.dateTimeModels.size();
    }

    private void setAnimation(View viewToAnimate)
    {
        // If the bound view wasn't previously displayed on screen, it's animated

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        viewToAnimate.startAnimation(animation);

    }

    public class DateTimeAdapterHolder extends  RecyclerView.ViewHolder{
        TextView date,day;
        ImageButton btn_drop_down;
        public DateTimeAdapterHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.text_view_date);
            day = itemView.findViewById(R.id.text_view_day);
            btn_drop_down = itemView.findViewById(R.id.btn_drop_down);
        }

        void bind(DateTimeModel dateTimeModel){
            btn_drop_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Create recyclerview
                    //get data
                }
            });
        }
    }
}
