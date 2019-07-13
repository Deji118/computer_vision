package com.example.doreopartners.scatterplot;
//I am not using this for now. you might want to remove it
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class statistics extends AppCompatActivity {
    //TextView mapped_today;
    TextView mapped_todaynumber;
    //TextView total_mapped;
    TextView mapped_totalnumber;
    TextView synced;
    TextView synced_number;
    SharedPreferences member;
    SharedPreferences prefs2;
    SharedPreferences.Editor memEdit;
    SharedPreferences mSharedPreferences;
    TextView staffid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        //DatabaseHelper databaseOpenHelper = new DatabaseHelper(this);
        member = getSharedPreferences("member", MODE_PRIVATE);
        prefs2 = getSharedPreferences("prefs", MODE_PRIVATE);
        memEdit = member.edit();
        mapped_todaynumber=findViewById(R.id.number_today);
        mapped_totalnumber=findViewById(R.id.number_total);
        synced_number=findViewById(R.id.synced_number);
        staffid=findViewById(R.id.staff_id);
        synced=findViewById(R.id.last_sync);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String mydate  = dateFormat.format(date);
        Log.d("deji",mydate);
        memEdit.putString("today",mydate);
        memEdit.commit();
       // mapped_todaynumber.setText(String.valueOf(databaseOpenHelper.mapped_today(mydate)));
        //mapped_totalnumber.setText(String.valueOf(databaseOpenHelper.mapped_total()));
        //synced_number.setText(String.valueOf(databaseOpenHelper.synced("1")));
        staffid.setText(member.getString("staff_id","---"));
        String me=member.getString("date","---");
        synced.setText(member.getString("date","---"));
        Log.d("mellll",me);



    }

}
