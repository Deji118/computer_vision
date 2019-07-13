package com.example.doreopartners.scatterplot;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.tensorflow.BG.Static.sharedPrefs;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
// this is the final result activity. the last activity of the app
public class finalresult extends AppCompatActivity {
    TextView staffid,feedback, result;
    SharedPreferences member;
    SharedPreferences.Editor memEdit;
    String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    String selected;
    String recommendation;
    ArrayList<Double>finalspacing,finalmaxspacing;
    double avespacing,maxspacing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalresult);
        staffid=findViewById(R.id.staff_id);
        member = getSharedPreferences("member", MODE_PRIVATE);
        staffid.setText(member.getString("staff_id","001"));
        result=findViewById(R.id.spacing_text);

        memEdit = member.edit();
        feedback=findViewById(R.id.feedback);
        result=findViewById(R.id.spacingvalue);
        AIdatabase db=new AIdatabase(this);
        String field_id=member.getString("field_id","0000");

        finalspacing = new ArrayList<>();
        finalspacing=db.getaverage(field_id);
        AIdatabase db2=new AIdatabase(this);
        finalmaxspacing=db2.getmaximum(field_id);
         avespacing=calculateAverage(finalspacing);
         Float goodspacing=member.getFloat("good",20);
         Log.d("iiejdj",String.valueOf(goodspacing-2));
         if (avespacing>=goodspacing-1 && avespacing<=goodspacing+1)
         {

             recommendation="Good field (100%)";
         }
        else if (avespacing>=goodspacing-4 && avespacing<=goodspacing-1.01)
        {

            recommendation="Good field (110%)";
        } else if (avespacing>=goodspacing+1.01 && avespacing<=goodspacing+3)
         {

             recommendation="Fill in the gaps (90%)";
         }
         else if (avespacing>=goodspacing+3.01 && avespacing<=goodspacing+5)
         {

             recommendation="Fill in the gaps (80%)";
         }
         else if (avespacing>=goodspacing+5.01 && avespacing<=goodspacing+7)
         {

             recommendation="Fill in the gaps (70%)";
         }
         else if (avespacing>=goodspacing+7.01 && avespacing<=goodspacing+9)
         {

             recommendation="Replant field(60%)";
         }
         else if (avespacing>=goodspacing-6 && avespacing<=goodspacing+4.01)
         {

             recommendation=" Thin field(120%)";
         }
         else if (avespacing>=goodspacing-8 && avespacing<=goodspacing-6.01)
         {

             recommendation="Thin field(130%)";
         }
         else if (avespacing<goodspacing-8)
         {

             recommendation="Thin field( greater than 130%)";
         }
         else if (avespacing>goodspacing+9)
         {

             recommendation="Replant field (less than 60%)";
         }
        result.setText(String.valueOf(avespacing));
         feedback.setText(recommendation);
         maxspacing=calculateAverage(finalmaxspacing);
        memEdit.putString("average_spacing",String.valueOf(avespacing));
        Log.d("jsdnjsj",String.valueOf(avespacing));
        memEdit.putString("maximum_spacing",String.valueOf(maxspacing));
        memEdit.commit();
        alertDialog();
        // 90,80,70 fill in the gap
        //120 upwards thin
        //60% replant
        //19-21 100%
        //12-13.99 130%
        //14-15.99 120%
        //16-18.99 110%
        //21.01-23 90%
        //23.01-25 80%
        //25.01-27 70%
        //27.01-29 60%
    }
    public void alertDialog() {
        final Dialog dialog = new Dialog(this);
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View post = inflater.inflate(R.layout.alertdialogue, null);
        final Spinner textView = (Spinner)post.findViewById((R.id.recommendation));
        Button proceed=post.findViewById(R.id.proceed);

        String[] recommendations = {
                "pick recommendation","Good field", "Fill in the gaps, plants to far", "Replant field, bad field","Thin field, plants too close",
        };
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, recommendations);

        textView.setAdapter(adapter);
        textView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected=textView.getSelectedItem().toString();
                if (selected=="pick recommendation")
                {
                    Toast.makeText(getApplicationContext(), " Please pick a recommendation." , Toast.LENGTH_LONG).show();

                }
                else {
                    memEdit.putString("user_feedback", textView.getSelectedItem().toString());
                    memEdit.commit();
                    responses();

                    dialog.dismiss();
                    memEdit.clear();
                    new sharedPrefs(getApplication()).clearAllPrefs();
                }
            }
        });
////Autocomplete

//textView.setThreshold(2);
        dialog.setContentView(post);
        dialog.setTitle("Give your recommendation");
        dialog.setCancelable(false);
        dialog.show();
    }
    private double calculateAverage(ArrayList <Double> marks) {
        double sum = 0;
        if(!marks.isEmpty()) {
            for (Double mark : marks) {
                sum += mark;
            }double sumi=(sum*0.1)/marks.size();
            return Math.round(sumi*10)/10;
        }
        return Math.round(sum*10)/10;
    }
    public String responses() {
        ArrayList<HashMap<String, String>> wordList = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();

        String firstname = member.getString("first_name", "Koye");
        String lastname = member.getString("last_name", "Sodipo");
        String ik_number = member.getString("ik_number", "IK000123");
        //String z = member.getString("latlongs", "NULL,NULL");
        String staff_id = member.getString("staff_id", "IK000123");
        String member_id = member.getString("member_id", "10001");
        String minlat = member.getString("minlat", "notyet");
        String maxlat = member.getString("maxlat", "notyet");
        String minlng = member.getString("minlng", "notyet");
        String maxlng = member.getString("maxlng", "notyet");
        String FMuniqueid=member.getString("field_id", "notyet");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        mydate  = dateFormat.format(date);

        //latlong = z;

        String f = member.getString("fieldsize", "1");
        map.put("first_name", firstname);
        map.put("last_name", lastname);
        map.put("ik_number", ik_number);
        //map.put("test_Id", fieldId + "01");

        map.put("field_size",f);
        map.put("staff_id", staff_id);
        map.put("member_id", member_id);
        map.put("minlat", minlat);
        map.put("maxlat", maxlat);
        map.put("minlng", minlng);
        map.put("maxlng", maxlng);
        map.put("FMuniqueid",FMuniqueid);
        map.put("timestamp", mydate);
        map.put("average_spacing",String.valueOf(avespacing));
        map.put("maximum_spacing",String.valueOf(maxspacing));
        //todo use a conditional statement to recommend field status
        map.put("app_feedback",recommendation);
        map.put("user_feedback",member.getString("user_feedback","oo1"));


        map.put("description", member.getString("description", "good"));
        map.put("village", member.getString("village", ""));

        map.put("version",BuildConfig.VERSION_NAME);

        wordList.add(map);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);


        Gson gson = new GsonBuilder().create();
        Log.d("Deji2", gson.toJson(wordList));
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(gson.toJson(wordList));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        databaseHelper.saverecords(jsonArray);
        return gson.toJson(wordList);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(finalresult.this, Main2Activity.class));
        finish();

    }

}
