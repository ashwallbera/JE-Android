package com.example.je_attendancesystem.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.je_attendancesystem.R;
import com.example.je_attendancesystem.activity.Capture;
import com.example.je_attendancesystem.adapter.DateTimeAdapter;
import com.example.je_attendancesystem.models.DateTimeModel;
import com.example.je_attendancesystem.models.ProjectModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTimesheet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTimesheet extends Fragment {
    private Button mPickDateButton, btn_time_in;
    ArrayList<DateTimeModel> dateTimeModels;
    //Firebase instance
    DatabaseReference mDatabase;
    String startDate;
    String endDate;
    DateTimeAdapter adapter;
    ProjectModel projectModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentTimesheet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCalendar.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTimesheet newInstance(String param1, String param2) {
        FragmentTimesheet fragment = new FragmentTimesheet();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timesheet, container, false);

        //GET TIME NOW
        Calendar utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat format = new SimpleDateFormat("M/dd/yyyy");
        String formatted = format.format(utc.getTime());
        startDate = formatted;
        endDate = formatted;

        //get the project object
        String objFromCard = getArguments().getString("projectObj");
        projectModel = new Gson().fromJson(objFromCard, ProjectModel.class);
        Toast.makeText(this.getContext(), "Timesheet ", Toast.LENGTH_SHORT).show();

        //Time in btn
        btn_time_in = (Button) view.findViewById(R.id.btn_timein);
        btn_time_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                intentIntegrator.setPrompt("HELLO");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();

            }
        });

        // now register the text view and the button with
        // their appropriate IDs
        mPickDateButton = view.findViewById(R.id.btn_pick);
        // mShowSelectedDateText = findViewById(R.id.show_selected_date);

        // now create instance of the material date picker
        // builder make sure to add the "dateRangePicker"
        // which is material date range picker which is the
        // second type of the date picker in material design
        // date picker we need to pass the pair of Long
        // Long, because the start date and end date is
        // store as "Long" type value
        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();

        // now define the properties of the
        // materialDateBuilder
        materialDateBuilder.setTitleText("SELECT A DATE");

        // now create the instance of the material date
        // picker
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();


        // handle select date button which opens the
        // material design date picker
        mPickDateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // getSupportFragmentManager() to
                        // interact with the fragments
                        // associated with the material design
                        // date picker tag is to get any error
                        // in logcat
                        mPickDateButton.setEnabled(false);
                        materialDatePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");
                    }
                });

        // now handle the positive button click from the
        // material design date picker
        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        // if the user clicks on the positive
                        // button that is ok button update the
                        // selected date
                        Context context = getContext();
                        CharSequence text = "Hello toast!";
                        int duration = Toast.LENGTH_SHORT;

                        Pair<Long, Long> selected = (Pair<Long, Long>) selection;

                        Calendar utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        utc.setTimeInMillis(selected.first);
                        SimpleDateFormat format = new SimpleDateFormat("M/dd/yyyy");
                        String formatted = format.format(utc.getTime());

                        Calendar utc2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        utc2.setTimeInMillis(selected.second);
                        String formatted2 = format.format(utc2.getTime());

                        Toast toast = Toast.makeText(context, formatted+" "+formatted2, duration);
                        toast.show();

                        //Get list of dates in date range
                        DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("M/dd/yyyy");
                        LocalDate startDate = LocalDate.parse(""+formatted,parseFormat);
                        LocalDate endDate = LocalDate.parse(""+formatted2,parseFormat);

                        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);

                        List<LocalDate> listOfDates = Stream.iterate(startDate, date -> date.plusDays(1))
                                .limit(numOfDays+1)
                                .collect(Collectors.toList());

                        dateTimeModels.clear();
                        for(LocalDate date: listOfDates){
                          //  System.out.println(date.format(parseFormat));
                            dateTimeModels.add(new DateTimeModel(""+date.format(parseFormat),""+projectModel.getId()));
                        }
                        adapter.notifyDataSetChanged();

                        // materialDatePicker.getSelection();
                        //materialDatePicker.getHeaderText()
                        mPickDateButton.setEnabled(true);
                        //mShowSelectedDateText.setText("Selected Date is : " + materialDatePicker.getHeaderText());
                        // in the above statement, getHeaderText
                        // will return selected date preview from the
                        // dialog
                    }


                });

        // Enable the button true
        materialDatePicker.addOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                mPickDateButton.setEnabled(true);
            }
        });
        materialDatePicker.addOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mPickDateButton.setEnabled(true);
            }
        });


        //Firebase instance
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // models

         dateTimeModels = new ArrayList<>();

        //setAttendance(projectModel.getId());
        dateTimeModels.add(new DateTimeModel(startDate,projectModel.getId())); // initial date
        //Recyclerview instance
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(manager);

        //Scroll down
        //recyclerView.smoothScrollToPosition(dateTimeModels.size());
        adapter = new DateTimeAdapter(this.getContext(), dateTimeModels);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

    private void setAttendance(String projectid){
        mDatabase.child("attendance").orderByChild("datecreated")
                .startAt(""+startDate).endAt(""+endDate)
                .addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("datecreated",""+snapshot.getValue());
                        for(DataSnapshot data: snapshot.getChildren() ){
                            Log.d("project",""+data.child("projectid").getValue());
                            //Check if the project is equal to child
                            if(data.child("projectid").getValue().toString().equals(projectid)){
                                dateTimeModels.add(new DateTimeModel(data.child("datecreated").getValue().toString(),projectModel.getId()));
                                adapter.notifyDataSetChanged();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


}