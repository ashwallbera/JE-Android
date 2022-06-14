package com.example.je_attendancesystem.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.je_attendancesystem.models.DateTimeModel;
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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return this.dateTimeModels.size();
    }
}
