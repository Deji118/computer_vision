package com.example.doreopartners.scatterplot;
//this is the tg activity for examimed fields corresponding recycler is tgexaminedfield recycler

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class examinedfieldsTG extends AppCompatActivity {
    private static final String TAG="startmapping";
    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<modelclassikexamined> memberList1=new ArrayList<>();
    private ArrayList<modelclassikexamined> number_list=new ArrayList<>();
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
    Spinner spinner_village;
    TextView staffid;
    String selectedlga;

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
        setContentView(R.layout.activity_examinedfieldstg);
        member = getSharedPreferences("member", MODE_PRIVATE);
        prefs2 = getSharedPreferences("prefs", MODE_PRIVATE);
        memEdit = member.edit();
        DatabaseHelper db = new DatabaseHelper(this);
        spinner_village = findViewById(R.id.spinner_village);
        staffid=findViewById(R.id.txtName);
        Log.d(TAG, "onCreate: started.");

        mem_id= member.getString("staff_id","bayo");
        staffid.setText(mem_id);
      recyclerView = findViewById(R.id.recyclerv_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> list = db.getvillage(mem_id);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);

        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_village.setAdapter(dataAdapter1);
        spinner_village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedlga=spinner_village.getSelectedItem().toString().trim();

                String deji="Leader";
                loadRecyclerView(mem_id,spinner_village.getSelectedItem().toString());
                memEdit.putString("village1",selectedlga);
                Log.d("villageme",selectedlga);
                memEdit.commit();


            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void loadRecyclerView(String staff_id,String lga){
        memberList2 = new ArrayList<>();
        DatabaseHelper databaseOpenHelper = new DatabaseHelper(getApplicationContext());
        //memberList2 = databaseOpenHelper.load_ik(staff_id,lga);

        memberList2 = databaseOpenHelper.load_ik(staff_id,lga);


        Log.d("--HELLO--1",memberList2+"");
        recyclerController(memberList2);

    }

    private void recyclerController( ArrayList<Map<String,String>> wordList){
        memberList1.clear();
        JSONArray jsonArray = new JSONArray(wordList);
        JSONObject jsonObject = null;

        Log.d("DDEEJJII",wordList.toString());
        for(int i = 0; i<jsonArray.length();i++) {
            try {
                jsonObject = jsonArray.getJSONObject(i);
                memberList1.add(new modelclassikexamined(

                        jsonObject.getString("ik_number")

                ));
                ;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        Log.d("DDEEJJII",memberList1.toString());
        tgexaminedfieldrecycler tfmAdapter = new tgexaminedfieldrecycler(memberList1,this);
        tfmAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(tfmAdapter);
        tfmAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(examinedfieldsTG.this, Main2Activity.class));
        finish();

    }

}
