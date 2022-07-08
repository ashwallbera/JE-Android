package com.example.je_attendancesystem.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.je_attendancesystem.R;
import com.example.je_attendancesystem.api.Api;
import com.example.je_attendancesystem.fragments.FragmentTimesheet;
import com.example.je_attendancesystem.fragments.FragmentProject;
import com.example.je_attendancesystem.models.ProjectModel;
import com.example.je_attendancesystem.models.TimesheetModel;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainMenu extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navDrawer;
    private FragmentProject fragmentProject;
    private FragmentTimesheet fragmentCalendar;
    public IntentResult intentResult;
    FragmentManager fragmentManager;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Button declaration and listeners
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Projects");


        //Fragments
        fragmentProject = new FragmentProject();
        fragmentCalendar = new FragmentTimesheet();

        //set project first fragment
        replaceFragment(fragmentProject);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        navDrawer = findViewById(R.id.nav_drawer);

        setSupportActionBar(toolbar);

        //Get Drawer content
        //View headerView = navDrawer.getHeaderView(0);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_menu);
        }

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        navDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_logout:
                        sharedPref = getPreferences(MODE_PRIVATE);
                        sharedPref.getAll().clear();
                        Intent intent = new Intent(MainMenu.this, MainActivity.class);
                        intent.putExtra("logout","logout");
                        startActivity(intent);
                        finish();
                        break;
                }
                return false;
            }
        });

    }

    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void replaceFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        //Check if empty
        if(intentResult.getContents() != null ){
            //Get current project object from fragments
            String objFromCard = fragmentManager.getFragments().get(0).getArguments().getString("projectObj");
            ProjectModel projectModel = new Gson().fromJson(objFromCard, ProjectModel.class);


            AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
            builder.setTitle("Result");
            builder.setMessage(intentResult.getContents());

            if(fragmentManager.getFragments().get(0).getArguments().getString("timein").equals("timein")){
                createAttendance(new TimesheetModel("",""+projectModel.getId(),""+intentResult.getContents(),"","",""));
                Log.d("DATAFROMFRAGMENT",""+fragmentManager.getFragments().get(0).getArguments().getString("action"));
            }else{
                Log.d("DATAFROMFRAGMENT",""+fragmentManager.getFragments().get(0).getArguments().getString("action"));
            }

            //Update attendance for timeout


            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
        else{
            Toast.makeText(getApplicationContext(), "OOPS ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public IntentResult getIntentResult() {
        return intentResult;
    }

    private void createAttendance(TimesheetModel model){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = Api.server+"/api/attendances";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("id", "");
            jsonBody.put("projectid", ""+model.getProjectid());
            jsonBody.put("userid", ""+model.getUserid());
            jsonBody.put("timeIn", "");
            jsonBody.put("timeOut", "");
            jsonBody.put("datecreated", "");
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };


            // To retry if connection is slow
            stringRequest.setShouldCache(false);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Add the request to the RequestQueue.
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}