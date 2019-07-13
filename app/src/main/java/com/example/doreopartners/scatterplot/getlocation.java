package com.example.doreopartners.scatterplot;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
//in this activity, the app gets your location and makes sure youa re on the field before you can proceed. if not the app will crash. dont expect it to crash sha

public class getlocation extends AppCompatActivity implements LocationListener {

    TextView txtLat;
    TextView txtLong;
    TextView txtName;
    TextView txtNumPoints;
    ProgressBar pbPB;
    LocationManager locationManager;
    SharedPreferences member;
    SharedPreferences.Editor memEdit;
    SharedPreferences prefs;
    String provider;
    String lating;
    String lnging;
    String minlat;
    String maxlat;
    String minlng;
    String maxlng;
    String latlongs1;
    ArrayList<Double> lats;
    ArrayList<Double> longs;
    ArrayList<Double> time;
    //this is constant
    //String mem_id2;
    //private DatabaseHelper2 databaseHelper2;
    //private User2 user2;

    final long MIN_LOC_UPDATE_TIME = 500;
    String walkOrBike;
    //these will be varied
    float MIN_LOC_UPDATE_DISTANCE;
    float MAX_WALKING_SPEED;
    float MAX_BIKE_SPEED;
    EditText mem;
    //    String Lat;
//    String lng;
    String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getlocation);
        txtLat = findViewById(R.id.txtLat);
        txtLong = findViewById(R.id.txtLong);
        txtNumPoints = findViewById(R.id.txtNumPoints);

        pbPB = findViewById(R.id.pb);
        lats = new ArrayList<>();
        longs = new ArrayList<>();
        time = new ArrayList<>();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        member = getSharedPreferences("member", MODE_PRIVATE);

        memEdit = member.edit();

        MIN_LOC_UPDATE_DISTANCE = Float.valueOf(member.getString("MIN_LOC_UPDATE_DISTANCE", "1"));
        MAX_WALKING_SPEED = Float.valueOf(member.getString("MAX_WALKING_SPEED", "10"));
        MAX_BIKE_SPEED = Float.valueOf(member.getString("MAX_BIKE_SPEED", "20"));
        walkOrBike = member.getString("walkorbike", "W");

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);

        provider = locationManager.getBestProvider(criteria, false);
        OnlineDatabase db= new OnlineDatabase(this);
        Intent intent=new Intent();
        String field_id= getIntent().getStringExtra("field_id");
        String description=getIntent().getStringExtra("description");
        String field_size=getIntent().getStringExtra("field_size");
        Log.d("foellld",description);


        memEdit.putString("field_id",field_id);
        memEdit.putString("description", description);
        memEdit.putString("field_size",field_size);

        latlongs1 =db.load_latlongs(field_id);
        minlat=db.load_minlat(field_id);
        maxlat=db.load_maxlat(field_id);
        minlng=db.load_minlng(field_id);
        maxlng=db.load_maxlng(field_id);
        memEdit.putString("minlat",minlat);
        memEdit.putString("maxlat",maxlat);
        memEdit.putString("minlng",minlng);
        memEdit.putString("maxlng",maxlng);

        memEdit.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (permissionGranted()) {
            try {
                Location location = locationManager.getLastKnownLocation(provider);
                locationManager.requestLocationUpdates(provider, 300, 100, this);

                if (location != null) {
                    onLocationChanged(location);
                }

            } catch (SecurityException e) {

            }
        } else {
            ActivityCompat.requestPermissions(getlocation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    public boolean permissionGranted() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    public double pointsDist(double lat1, double lon1, double lat2, double lon2) {
        return Math.sqrt((lat2 - lat1) * (lat2 - lat1) + (lon2 - lon1) * (lon2 - lon1)) * 110000;
    }


    @Override
    public void onLocationChanged(Location location) {

        final double lat = location.getLatitude();
        final double lng = location.getLongitude();
        txtLat.setText(String.valueOf(Math.round(lat * 100.0) / 100.0));

        txtLong.setText(String.valueOf(Math.round(lng * 100.0) / 100.0));

        //return;
        String []latlongs2=latlongs1.split(",");

        String latlongs3=latlongs2[0];

        String letter = Character.toString(latlongs3.charAt(0));

        int size=lats.size();
        //TODO Alert dialog
        //if it is the first point, add it
        if (size == 0) {
            lats.add(lat);
            longs.add(lng);
            time.add(Double.valueOf(System.currentTimeMillis() / 1000));
            txtNumPoints.setText(String.valueOf(size + 1));

            return;
        }
        double dist = pointsDist(lat, lng, lats.get(size - 1), longs.get(size - 1));

        if (dist >= MIN_LOC_UPDATE_DISTANCE ) {
            lats.add(lat);
            longs.add(lng);
            time.add(Double.valueOf(System.currentTimeMillis() / 1000));
            txtNumPoints.setText(String.valueOf(size + 1));

            if (size+1>=1 &&latlongs2.length>5 &&latlongs3.charAt(0)!='_'&&
            lats.get(size-1)>=Double.valueOf(minlat)-0.0002 && lats.get(size-1)<=Double.valueOf(maxlat)+0.0002
            && longs.get(size-1)>= Double.valueOf(minlng)-0.0002 && longs.get(size-1)<=Double.valueOf(maxlng)+0.0002)
                    //&&
                    //lats.get(size-1)>=Double.valueOf(minlat) && lats.get(size-1)<=Double.valueOf(minlat)
                    //&& longs.get(size-1)>= Double.valueOf(minlng) && longs.get(size-1)<=Double.valueOf(maxlng))
            {

                pbPB.setVisibility(View.GONE);

//
               lating=String.valueOf(lats.get(size-1));
                lnging=String.valueOf(longs.get(size-1));

                        Intent intent = new Intent(getApplicationContext(), pictureMLactivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("lat", lating);
                        intent.putExtra("lng", lnging);
                        startActivity(intent);

//                    }
//                    });
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(getApplicationContext(), Main2Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//                    }
//                });
//                try {
//                    dialogBuilder.show();
//                }
//                catch (WindowManager.BadTokenException e) {
//                    //use a log message
//                }
            }
            else if (size+1>=1 && lats.get(size-1)<=Double.valueOf(minlat) || lats.get(size-1)>=Double.valueOf(maxlat)
                    || longs.get(size-1)<= Double.valueOf(minlng) || longs.get(size-1)>=Double.valueOf(maxlng))
            {
                pbPB.setVisibility(View.GONE);
                try
                {
                Toast.makeText(this,"you are not on the field",Toast.LENGTH_SHORT).show();
                Log.i("BAKARE", "onLocationChanged: FIELD NIT YOUNG");
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();}
                catch (Exception e)
                {

                }
            }
            else {
                try
                {
                Toast.makeText(this,"Field too small, select another field",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                }
                catch (Exception e)
                {

                }
            }

            }



        return;



    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }  @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(getlocation.this, Main2Activity.class));
        finish();

    }
}
