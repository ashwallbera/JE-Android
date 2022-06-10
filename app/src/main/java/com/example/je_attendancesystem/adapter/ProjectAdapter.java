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
import com.example.je_attendancesystem.models.ProjectModel;
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
                .inflate(R.layout.project_card, parent, false);
        return new ProjectAdapterHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProjectModel projectModel = this.projectModels.get(position);

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

        public ProjectAdapterHolder(@NonNull View itemView) {
            super(itemView);
        }
        void bind(ProjectModel projectModel){
            
        }
    }


}
