package com.example.je_attendancesystem.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.je_attendancesystem.R;
import com.example.je_attendancesystem.models.TimesheetModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public ArrayList<TimesheetModel> getTimesheetModels() {
        return timesheetModels;
    }

    public class TimesheetAdapterHolder extends RecyclerView.ViewHolder{
        TextView timesheet_full_name, position,time_in,time_out;
        DatabaseReference mDatabase;
        public TimesheetAdapterHolder(@NonNull View itemView) {
            super(itemView);
            timesheet_full_name = itemView.findViewById(R.id.timesheet_full_name);
            position = itemView.findViewById(R.id.timesheet_position);
            time_in = itemView.findViewById(R.id.time_in);
            time_out = itemView.findViewById(R.id.time_out);
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }
        void bind(TimesheetModel timesheetModel){

            //READ account
            mDatabase.child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("TIMESHEETUSERS: "+timesheetModel.getUserid(),""+snapshot.getValue());
                    for(DataSnapshot data: snapshot.getChildren()){
                        Log.d("TIMESHEETUSERS: "+timesheetModel.getUserid(),""+data.getValue());
                        String fullname = data.child("lname").getValue()+" "+data.child("fname").getValue()+", "+data.child("mname").getValue();
                        timesheet_full_name.setText(""+fullname);
                        position.setText(""+data.child("position").getValue());
                        time_in.setText(timesheetModel.getTimeIn());
                        time_out.setText(timesheetModel.getTimeOut());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

}
