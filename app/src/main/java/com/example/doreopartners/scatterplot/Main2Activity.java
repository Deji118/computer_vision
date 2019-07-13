package com.example.doreopartners.scatterplot;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
//landing page after access control. I hosted all the basic functions here
public class Main2Activity extends AppCompatActivity {
    private static final String TAG="mappedfieldik";
    private ArrayList<String> mNames=new ArrayList<>();

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

    TextView staffid;
    Button examine;
    Button sync_down;
    Button sync_up;
    Button show_examinedfields;
    Button stats;
    Button export;

    SessionManagement sessionManagement;
    private RecyclerView recyclerView;
    SharedPreferences member;
    SharedPreferences prefs2;
    SharedPreferences.Editor memEdit;

    private JobScheduler jobScheduler;
    private JobInfo jobInfo;
    ComponentName componentName;
    private static final int JOB_ID =101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functionselect);
        member = getSharedPreferences("member", MODE_PRIVATE);
        prefs2 = getSharedPreferences("prefs", MODE_PRIVATE);
        memEdit = member.edit();
        settingsdatabase db3=new settingsdatabase(this);
        staffid=findViewById(R.id.staff_id);
        examine=findViewById(R.id.examine);
        sync_down=findViewById(R.id.members);
        sync_up=findViewById(R.id.fields);
        settingsdatabase settings=new settingsdatabase(this);
        //show_examinedfields=findViewById(R.id.examined_fields);
        //stats=findViewById(R.id.statistics);
        //export = (Button) findViewById(R.id.export);

        //staffid=findViewById(R.id.txtName);
        Log.d(TAG, "onCreate: started.");
        // Intent intent = getIntent();
        //mem_id="T-10000000000000AA";
        //memEdit.putString("staff_id",mem_id);


        //memEdit.commit();

        mem_id = getIntent().getStringExtra("staff_id");
        if (mem_id==null)
        {
            mem_id=member.getString("staff_id","001");
        }else
        {
            memEdit.putString("staff_id",mem_id);
            memEdit.commit();
        }
        staffid.setText(mem_id);

        Toast.makeText(this, mem_id, Toast.LENGTH_SHORT).show();

        try {//EXTRACTING USER DETAILS
            Intent intent = getIntent();
            Bundle b = intent.getExtras();
            staff_name = (String) b.get("staff_name");
            staff_role = (String) b.get("staff_role");
            staff_id = (String) b.get("staff_id");

            sessionManagement = new SessionManagement(getApplicationContext());
            sessionManagement.CreateLoginSession(staff_name, staff_id, staff_role);


        } catch (Exception e) {

        }
        staff_id="T-10000000000000AA";
        staff_name="Koye Sodipo";






    }
//Exporting to excel method

//    public void export(View v) {
////                InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(getActivity().getApplicationContext());
////                SQLiteDatabase db = inventoryDBHandler.getWritableDatabase();
////                Cursor c = null;
//        String directory_path = Environment.getExternalStorageDirectory().getPath();
//        Log.d("directory", "" + directory_path);
//        SQLiteToExcel sqliteToExcel;
//        final int[] count = {0};
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            // Do the file write
//            sqliteToExcel = new SQLiteToExcel(getApplicationContext(), "FieldMappingrevamp.db", directory_path + "/Download");
//            try {
//                sqliteToExcel.exportAllTables("FieldMappingrevamp.xls", new SQLiteToExcel.ExportListener() {
//                    @Override
//                    public void onStart() {
//                    }
//
//                    @Override
//                    public void onCompleted(String filePath) {
//                        count[0]++;
//                        Log.d("count", "" + count[0]);
//                        Toast.makeText(getApplicationContext(), "Exported Successfully", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        Log.d("eeudhhdh", "" + e);
//                        ;
//                    }
//                }, getApplicationContext());
//            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(), "Export failed. Sync down all databases on first installation",
//                        Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            // Request permission from the user
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
//
//        }
//
//    }

// TglDB Method



    public void examine_fields(View v)
    {
        // starting background task to update product
        Intent fp=new Intent(getApplicationContext(),selectTG.class);
        startActivity(fp);
    }
    public void sync_members(View v) {
        Toast.makeText(this, "please wait as download is in progress.", Toast.LENGTH_SHORT).show();
        // starting background task to update product
        @SuppressLint("StaticFieldLeak") Asynctask.DownloadApplication x = new Asynctask.DownloadApplication(Main2Activity.this) {
            protected void onPostExecute(String s) {
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                Log.d("RESULT__NOW", s);
            }
        };
        x.execute();
        @SuppressLint("StaticFieldLeak") Asynctask4.DownloadApplication z = new Asynctask4.DownloadApplication(Main2Activity.this) {
            protected void onPostExecute(String s) {
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                Log.d("RESULT__NOW", s);
            }
        };
        z.execute();

    }
    public void sync_fields(View v)
    { @SuppressLint("StaticFieldLeak") Asynctask3.UpploadTast z = new Asynctask3.UpploadTast(Main2Activity.this) {
        protected void onPostExecute(String s) {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            Log.d("RESULT__NOW", s);
        }
    };
        z.execute();
        // starting background task to update product
        @SuppressLint("StaticFieldLeak") Asynctask2.UpploadTast x = new Asynctask2.UpploadTast(Main2Activity.this) {
            protected void onPostExecute(String s) {
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                Log.d("RESULT__NOW", s);
            }
        };
        x.execute();
        @SuppressLint("StaticFieldLeak") Asynctask5.UpploadTast a = new Asynctask5.UpploadTast(Main2Activity.this) {
            protected void onPostExecute(String s) {
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                Log.d("RESULT__NOW", s);
            }
        };
        a.execute();



    }
//    public void show_statistics(View v)
//    {
//        // starting background task to update product
//        Intent fp=new Intent(getApplicationContext(),statistics.class);
//        startActivity(fp);
//    }
    public void show_fields(View v)
    {
        // starting background task to update product
        Intent fp=new Intent(getApplicationContext(),examinedfieldsTG.class);
        startActivity(fp);
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(Main2Activity.this, MainActivity.class));
        finish();

    }

}
