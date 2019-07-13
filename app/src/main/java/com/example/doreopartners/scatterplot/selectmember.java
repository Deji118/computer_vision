package com.example.doreopartners.scatterplot;
//you select the member whose field you want to examine here. coressponding recycler is directly under it.selectmemberrycler

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.TextView;
import android.widget.Toast;

import com.example.doreopartners.scatterplot.OnlineDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class selectmember extends AppCompatActivity {
    private static final String TAG="startmapping";
    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<modelselectmember> memberList1=new ArrayList<>();
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
    //SessionManagement sessionManagement;
    private RecyclerView recyclerView;
    SharedPreferences member;
    SharedPreferences prefs2;
    SharedPreferences.Editor memEdit;

    //TextView staffid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectmember);
        member = getSharedPreferences("member", MODE_PRIVATE);
        prefs2 = getSharedPreferences("prefs", MODE_PRIVATE);
        memEdit = member.edit();
        OnlineDatabase db = new OnlineDatabase(this);

        //staffid=findViewById(R.id.txtName);
        Log.d(TAG, "onCreate: started.");
        // Intent intent = getIntent();
        mem_id = member.getString("staff_id", "deji");
        //staffid.setText(mem_id);

        //String member_id=getIntent().getStringExtra("member_id");
        String lga = member.getString("lga", "deji");

        String ik_number=getIntent().getStringExtra("ik_number");
        //Toast.makeText(this, ik_number, Toast.LENGTH_SHORT).show();
        memEdit.putString("ik_number",ik_number);
        memEdit.commit();
//
//        memEdit.putString("staff_id",mem_id);
        // prefsEdit.putString("staff_id",getIntent().getStringExtra("staff_id"));


        //prefsEdit.commit();
        //member.getString("staff_id","default");
//
        //final OnlineDBHelper db = new OnlineDBHelper(startmapping.this);
        recyclerView = findViewById(R.id.recyclerv_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String staffID = getIntent().getStringExtra("staff_id");
        String LGA= member.getString("lga","deji");
        //String ik_number= member.getString("ik_number","deji");

        Log.d("LGA",LGA);
        Log.d("LGA",mem_id);
        //String me="T-1000000000001536";
        //String you="IK011296";


        //loadRecyclerView(mem_id,LGA);
        loadRecyclerView(mem_id,ik_number);
    }

    public void loadRecyclerView(String staff_id,String lga){

        memberList2 = new ArrayList<>();
        OnlineDatabase databaseOpenHelper = new OnlineDatabase(getApplicationContext());
        memberList2 = databaseOpenHelper.load_members(staff_id,lga);

        Log.d("--HELLO--1",memberList2+"");
        recyclerController(memberList2);
    }


    private void recyclerController( ArrayList<Map<String,String>> wordList){
        memberList1.clear();
        JSONArray jsonArray = new JSONArray(wordList);
        JSONObject jsonObject = null;
        for(int i = 0; i<jsonArray.length();i++){
            try {
                jsonObject = jsonArray.getJSONObject(i);
                memberList1.add(new modelselectmember(
                        jsonObject.getString("first_name"),
                        jsonObject.getString("last_name"),
                        jsonObject.getString("member_id"),
                        jsonObject.getString("phone_number")


                ));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        selectmemberrecycler tfmAdapter = new selectmemberrecycler(memberList1,this);
        tfmAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(tfmAdapter);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(selectmember.this, Main2Activity.class));
        finish();

    }
}
