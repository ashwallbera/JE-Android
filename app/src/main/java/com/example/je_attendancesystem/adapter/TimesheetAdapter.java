package com.example.je_attendancesystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.je_attendancesystem.R;
import com.example.je_attendancesystem.models.TimesheetModel;

import java.util.ArrayList;

public class TimesheetAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<TimesheetModel> timesheetModels;

    public TimesheetAdapter(Context context, ArrayList<TimesheetModel> timesheetModels){
        this.context = context;
        this.timesheetModels = timesheetModels;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timesheet_inflater, parent, false);
        return new TimesheetAdapterHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TimesheetModel timesheetModel = this.timesheetModels.get(position);
        setAnimation(holder.itemView);
        ((TimesheetAdapterHolder)holder).bind(timesheetModel);
    }

    @Override
    public int getItemCount() {
        return timesheetModels.size();
    }

    private void setAnimation(View viewToAnimate)
    {
        // If the bound view wasn't previously displayed on screen, it's animated

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        viewToAnimate.startAnimation(animation);

    }
    public class TimesheetAdapterHolder extends RecyclerView.ViewHolder{

        public TimesheetAdapterHolder(@NonNull View itemView) {
            super(itemView);
        }
        void bind(TimesheetModel timesheetModel){

        }
    }

}
