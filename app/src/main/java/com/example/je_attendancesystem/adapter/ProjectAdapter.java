package com.example.je_attendancesystem.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.je_attendancesystem.R;
import com.example.je_attendancesystem.activity.MainMenu;
import com.example.je_attendancesystem.fragments.FragmentTimesheet;
import com.example.je_attendancesystem.models.ProjectModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<ProjectModel> projectModels;

    public ProjectAdapter(Context context, ArrayList<ProjectModel> projectModels){
        this.context = context;
        this.projectModels = projectModels;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_card_inflater, parent, false);
        return new ProjectAdapterHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProjectModel projectModel = this.projectModels.get(position);
        ((ProjectAdapterHolder)holder).bind(projectModel);
    }

    @Override
    public int getItemCount() {
        return this.projectModels.size();
    }

    private void setAnimation(View viewToAnimate)
    {
        // If the bound view wasn't previously displayed on screen, it's animated

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        viewToAnimate.startAnimation(animation);

    }


    public class ProjectAdapterHolder extends  RecyclerView.ViewHolder{
        MaterialCardView card;
        TextView project_name, total, location;
        public ProjectAdapterHolder(@NonNull View itemView) {
            super(itemView);
            project_name = itemView.findViewById(R.id.project_name);
            location = itemView.findViewById(R.id.project_location);
            total = itemView.findViewById(R.id.total_workers);
            card = itemView.findViewById(R.id.card);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putString("projectObj", "From Activity");
                    FragmentTimesheet timesheet = new FragmentTimesheet();
                    timesheet.setArguments(bundle);
                    ((MainMenu)context).replaceFragment(timesheet);
                }
            });
        }
        void bind(ProjectModel projectModel){
            project_name.setText(projectModel.getName());
            location.setText(projectModel.getLocation());
            //total.setText(projectModel.);
        }
    }


}
