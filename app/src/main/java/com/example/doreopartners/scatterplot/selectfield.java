package com.example.doreopartners.scatterplot;
//you select the field you want to map here. corresponding recycler is fieldrecycler

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

public class selectfield extends AppCompatActivity {
    private static final String TAG="viewmapped_fields";
    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<modelselectfield> memberList1=new ArrayList<>();
    //private ArrayList<modelmappedfield> memberList1=new ArrayList<>();
    private ArrayList<modelselectfield> number_list=new ArrayList<>();
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
        setContentView(R.layout.activity_selectfield);
        member = getSharedPreferences("member", MODE_PRIVATE);
        // prefs2 = getSharedPreferences("prefs", MODE_PRIVATE);
        memEdit = member.edit();
        OnlineDatabase db = new OnlineDatabase(this);
        //spinner_lga = findViewById(R.id.spinner_lga);
        staffid=findViewById(R.id.txtName);
        //staffid=findViewById(R.id.txtName);
        Log.d(TAG, "onCreate: started.");
        // Intent intent = getIntent();
        String member_id = member.getString("member_id","001");

        staffid.setText(member_id);
        //memEdit.putString("staff_id",mem_id);
        //String member_id=getIntent().getStringExtra("member_id");
        // String lga = member.getString("lga", "deji");
        Toast.makeText(this, mem_id, Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recyclerv_view);
        //next=findViewById(R.id.next);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //mem = findViewById(R.id.editText);

        String mem_id=member.getString("member_id","00001");

        loadRecyclerView(mem_id);



        //loadRecyclerView(staffID,LGA,deji);
        //startActivity(new Intent(startmapping.this, startmapping.class));


    }


    public void loadRecyclerView(String member_id){
        memberList2 = new ArrayList<>();
        OnlineDatabase databaseOpenHelper = new OnlineDatabase(getApplicationContext());
        //memberList2 = databaseOpenHelper.load_ik(staff_id,lga);

        memberList2 = databaseOpenHelper.load_fields(member_id);


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
                memberList1.add(new modelselectfield(
                        jsonObject.getString("unique_id"),

                        jsonObject.getString("description"),
                        jsonObject.getString("field_size")



                ));
                ;
            } catch (JSONException e) {
                e.printStackTrace();
            }

//        }   for(int i = 0; i<jsonArray.length();i++){
//            try {
//                jsonObject = jsonArray.getJSONObject(i);
//                number_list.add(new modelclass3(
//                        jsonObject.getString("number")
//
//
//
//                ));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

//        }
        }
        fieldrecycler tfmAdapter = new fieldrecycler(memberList1,this);
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
        startActivity(new Intent(selectfield.this, Main2Activity.class));
        finish();

    }

}
