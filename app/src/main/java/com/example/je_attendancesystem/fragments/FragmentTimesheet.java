package com.example.je_attendancesystem.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.je_attendancesystem.R;
import com.example.je_attendancesystem.adapter.DateTimeAdapter;
import com.example.je_attendancesystem.models.DateTimeModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTimesheet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTimesheet extends Fragment {
    private Button mPickDateButton;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timesheet, container, false);

        //get the project object
        String objFromCard = getArguments().getString("projectObj");

        Toast.makeText(this.getContext(), "Timesheet "+objFromCard, Toast.LENGTH_SHORT).show();

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
                        utc.setTimeInMillis(selected.second);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String formatted = format.format(utc.getTime());

                        Toast toast = Toast.makeText(context, formatted+"", duration);
                        toast.show();

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

        // models

        ArrayList<DateTimeModel> dateTimeModels = new ArrayList<>();
        dateTimeModels.add(new DateTimeModel());
        dateTimeModels.add(new DateTimeModel());
        dateTimeModels.add(new DateTimeModel());
        dateTimeModels.add(new DateTimeModel());
        dateTimeModels.add(new DateTimeModel());
        dateTimeModels.add(new DateTimeModel());
        dateTimeModels.add(new DateTimeModel());
        dateTimeModels.add(new DateTimeModel());
        dateTimeModels.add(new DateTimeModel());

        //Recyclerview instance
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(manager);

        //Scroll down
        //recyclerView.smoothScrollToPosition(dateTimeModels.size());
        DateTimeAdapter adapter = new DateTimeAdapter(this.getContext(), dateTimeModels);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }
}