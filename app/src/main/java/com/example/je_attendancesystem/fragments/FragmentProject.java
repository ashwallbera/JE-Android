package com.example.je_attendancesystem.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.je_attendancesystem.R;
import com.example.je_attendancesystem.adapter.ProjectAdapter;
import com.example.je_attendancesystem.models.ProjectModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProject#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProject extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentProject() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProject.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProject newInstance(String param1, String param2) {
        FragmentProject fragment = new FragmentProject();
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
        View view =  inflater.inflate(R.layout.fragment_project, container, false);
        // Write a message to the database
        //FirebaseApp.initializeApp(this.getContext());
        DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();



        //Create List of Project
        ArrayList<ProjectModel> projectModels = new ArrayList<>();

        //Get data from firebase
        // Read from the database
        mDatabase.child("project").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("firebaseread",""+snapshot.getValue());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("firebaseread","");
            }
        });

        projectModels.add(new ProjectModel());
        projectModels.add(new ProjectModel());
        projectModels.add(new ProjectModel());
        projectModels.add(new ProjectModel());
        
        //Recyclerview instance
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        GridLayoutManager manager  = new GridLayoutManager(this.getActivity(),2);
        recyclerView.setLayoutManager(manager);

        //Scroll down
        recyclerView.smoothScrollToPosition(projectModels.size());
        ProjectAdapter adapter = new ProjectAdapter(this.getContext(), projectModels);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }
}