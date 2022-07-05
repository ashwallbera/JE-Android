package com.example.je_attendancesystem.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.je_attendancesystem.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Constants;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    EditText username, password;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getPreferences(MODE_PRIVATE);
        editor = sharedPref.edit();
       username = (EditText) findViewById(R.id.username);
       password = (EditText) findViewById(R.id.password) ;

       Log.d("hasExtra",getIntent().hasExtra("logout")+"");
       if(getIntent().hasExtra("logout")){
           editor.clear();
           editor.commit();
           Log.d("logout",""+getIntent().getStringExtra("logout"));
       }else
       {
           if(checkCached() >= 2){
               Intent intent = new Intent(MainActivity.this, MainMenu.class);
               startActivity(intent);
               intent.putExtra("username",sharedPref.getAll().get("username")+"");
               intent.putExtra("password",sharedPref.getAll().get("password")+"");
               finish();
           }

       }

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase = FirebaseDatabase.getInstance().getReference();

                mDatabase.child("users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot data: snapshot.getChildren()){
                            if(data.child("username").getValue().toString().equals(""+username.getText())
                            &&
                                    data.child("password").getValue().toString().equals(""+password.getText())
                            ){
                                //Write to cache the username password
                                MainActivity.this.writeString();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public int checkCached(){
        Log.d("cacheData",""+sharedPref.getAll().size());
        Log.d("cacheData",""+sharedPref.getAll().get("username"));
        return sharedPref.getAll().size();
    }

    public void writeString() {
        editor.putString("username", ""+username.getText());
        editor.putString("password", ""+password.getText());
        editor.apply();
        Intent intent = new Intent(MainActivity.this, MainMenu.class);
        startActivity(intent);
        intent.putExtra("username",sharedPref.getAll().get("username")+"");
        intent.putExtra("password",sharedPref.getAll().get("password")+"");
        finish();
    }
}