package com.example.je_attendancesystem.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.journeyapps.barcodescanner.CaptureActivity;

public class Capture extends CaptureActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Capturing","");
    }
}
