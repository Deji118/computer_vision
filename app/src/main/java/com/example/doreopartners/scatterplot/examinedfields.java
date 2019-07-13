package com.example.doreopartners.scatterplot;
//this is examined fields, corresponding recycler is examinedfieldsrecycler

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class examinedfields extends AppCompatActivity {
    private static final String TAG="viewmapped_fields";
    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<modelexaminedfields> memberList1=new ArrayList<>();
    //private ArrayList<modelmappedfield> memberList1=new ArrayList<>();
    private ArrayList<modelexaminedfields> number_list=new ArrayList<>();
    private ArrayList<Map<String,String>> memberList2;
    private ArrayList firstname;
    private ArrayList lastname;
    private ArrayList number;
    private ArrayList phonenumber;
    String lga;
    private RecyclerView.Adapter adapter;
    private RecyclerView mRecyclerView;
    String staff_name;
    String staff_role;
    String staff_id;
    String mem_id;
    Spinner spinner_lga;
    TextView staffid;
    Button next;

    //SessionManagement sessionManagement;
    private RecyclerView recyclerView;
    SharedPreferences member;
    SharedPreferences prefs2;
    SharedPreferences.Editor memEdit;

    private JobScheduler jobScheduler;
    private JobInfo jobInfo;
    ComponentName componentName;
    private static final int JOB_ID =101;

    //TextView staffid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examinedfields);
        member = getSharedPreferences("member", MODE_PRIVATE);
        // prefs2 = getSharedPreferences("prefs", MODE_PRIVATE);
        memEdit = member.edit();

        //spinner_lga = findViewById(R.id.spinner_lga);
        staffid=findViewById(R.id.txtName);
        //staffid=findViewById(R.id.txtName);
        Log.d(TAG, "onCreate: started.");

        Toast.makeText(this, mem_id, Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recyclerv_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //initiknumbers();

        //mem = findViewById(R.id.editText);
        String member_id2=getIntent().getStringExtra("member_id2");
        memEdit.putString("member_id3",member_id2);
        memEdit.commit();

        staffid.setText(member_id2);
        String active="active";
        loadRecyclerView(member_id2);

    }


    public void loadRecyclerView(String staff_id){
        memberList2 = new ArrayList<>();
        DatabaseHelper databaseOpenHelper = new DatabaseHelper(getApplicationContext());
        //memberList2 = databaseOpenHelper.load_ik(staff_id,lga);

        memberList2 = databaseOpenHelper.load_activefields(staff_id);


        Log.d("--HELLO--1",memberList2+"");
        recyclerController(memberList2);


    }

    private void recyclerController( ArrayList<Map<String,String>> wordList){
//        memberList1.clear();
        JSONArray jsonArray = new JSONArray(wordList);
        JSONObject jsonObject = null;
        for(int i = 0; i<jsonArray.length();i++) {
            try {
                jsonObject = jsonArray.getJSONObject(i);
                memberList1.add(new modelexaminedfields(
                        jsonObject.getString("FMuniqueid"),

                        jsonObject.getString("description"),
                        jsonObject.getString("app_feedback"),
                        jsonObject.getString("timestamp")

                        //jsonObject.getString("phone_number")



                ));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        examinedfieldsrecycler tfmAdapter = new examinedfieldsrecycler(memberList1,this);
        tfmAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(tfmAdapter);
        tfmAdapter.notifyDataSetChanged();
    }


    public void onDestroy() {
        super.onDestroy();

    }


    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(examinedfields.this, Main2Activity.class));
        finish();

    }

}
