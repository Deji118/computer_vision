package com.example.doreopartners.scatterplot;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.RectF;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import org.tensorflow.BG.MyMainActivity;
import org.tensorflow.BG.Static.CameraActivity;
import org.tensorflow.BG.Static.sharedPrefs;
import org.tensorflow.BG.database.DBHelper;

import java.util.ArrayList;
import java.util.Random;
//this is where everything happens. not the most efficient though. but this was the only way available
public class pictureMLactivity extends AppCompatActivity implements LocationListener {
    private static final String TAG = "pictureMLactivity";
    private static int REQUEST_GET_SINGLE_FILE = 97;

    //add PointsGraphSeries of DataPoint type
    PointsGraphSeries<DataPoint> xySeries;
    PointsGraphSeries<DataPoint>xySeries2,xySeries4,xySeries5,xySeries6,xySeries7,xySeries8,xySeries9,xySeries10,xySeries11
            ,xySeries12,xySeries13,xySeries14,xySeries15,xySeries16    ,xySeries17,xySeries18,xySeries19,xySeries20,xySeries21,xySeries22
            ,xySeries23,xySeries24,xySeries25,xySeries26,xySeries27,xySeries28,xySeries29,xySeries30,xySeries31;
    String minlat,maxlat,minlng,maxlng;
    PointsGraphSeries<DataPoint>xySeries3;
    double startx;
    double starty;
    double endx, endy;
    PointsGraphSeries<DataPoint> onClickSeries;
    LocationManager locationManager;
    //create graphview object
    GraphView mScatterPlot;
    //GraphView mScatterplot2;
    String provider;
    //make xyValueArray global
    ArrayList<XYValue> xyValueArray;
    ArrayList<XYValue> xyValueArray2, xyValueArray4, xyValueArray5, xyValueArray6, xyValueArray7, xyValueArray8,
            xyValueArray9, xyValueArray10, xyValueArray11, xyValueArray12, xyValueArray13, xyValueArray14, xyValueArray15,xyValueArray16,xyValueArray17
            ,xyValueArray18,xyValueArray19,xyValueArray20,xyValueArray21,xyValueArray22,xyValueArray23,xyValueArray24,xyValueArray25,xyValueArray26,xyValueArray27,xyValueArray28,xyValueArray29;
    ArrayList<XYValue>xyValueArray3;
    //    lats = (ArrayList<Double>) getIntent().getSerializableExtra("lats");
//    longs = (ArrayList<Double>) getIntent().getSerializableExtra("lngs");
    Button take_picture, done;
    SharedPreferences member;
    SharedPreferences.Editor memEdit;
    SharedPreferences prefs2;

    ArrayList<Double> lats1;
    ArrayList<Double> longs1;
    ArrayList<Double> time;
    ArrayList<Double> latlongs;
    ArrayList<Double> latsdouble;
    ArrayList<Double> longsdouble;
    final long MIN_LOC_UPDATE_TIME = 500;
    String walkOrBike;
    //these will be varied
    float MIN_LOC_UPDATE_DISTANCE;
    float MAX_WALKING_SPEED;
    float MAX_BIKE_SPEED;
    double randomx,randomx2,randomx3,randomx4,randomx5,randomx6,randomx7,randomx8,randomx9,randomx10,randomx11,randomx12,randomx13,randomx14;
    double randomy,randomy2,randomy3,randomy4,randomy5,randomy6,randomy7,randomy8,randomy9,randomy10,randomy11,randomy12,randomy13,randomy14;
    double randomX,randomX2,randomX3,randomX4,randomX5,randomX6,randomX7,randomX8,randomX9,randomX10,randomX11,randomX12,randomX13,randomX14;
    double randomY,randomY2,randomY3,randomY4,randomY5,randomY6,randomY7,randomY8,randomY9,randomY10,randomY11,randomY12,randomY13,randomY14;
    double latfirst;
    double longfirst;
    double field_size;
    Integer number;
    Integer count;
    ArrayList<Double>averagespacing1;
    ArrayList<Double>maximumspacing1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new sharedPrefs(this).clearAllPrefs();

        lats1 = new ArrayList<>();
        longs1 = new ArrayList<>();
        latsdouble=new ArrayList<>();
        longsdouble=new ArrayList<>();
        time = new ArrayList<>();
        latlongs = new ArrayList<>();
        member = getSharedPreferences("member", MODE_PRIVATE);
        prefs2 = getSharedPreferences("prefs", MODE_PRIVATE);
        memEdit = prefs2.edit();
        //declare graphview object
        mScatterPlot = (GraphView) findViewById(R.id.scatterplot);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        provider = locationManager.getBestProvider(criteria, false);
        //take_picture.setEnabled(false);
        MIN_LOC_UPDATE_DISTANCE = Float.valueOf(member.getString("MIN_LOC_UPDATE_DISTANCE", "2"));
        MAX_WALKING_SPEED = Float.valueOf(member.getString("MAX_WALKING_SPEED", "10"));
        MAX_BIKE_SPEED = Float.valueOf(member.getString("MAX_BIKE_SPEED", "20"));
        walkOrBike = member.getString("walkorbike", "W");


        //a big problem here. the maximum photos that should be taken based on field size is 14. for this activity, you can't plot a point in the negative x axis.
        //and you can't clear a series. so I generated all the random points at once. not so efficient though
        xySeries = new PointsGraphSeries<>();
        xySeries2=new PointsGraphSeries<>();
        xySeries3=new PointsGraphSeries<>();

        xySeries4=new PointsGraphSeries<>();
        xySeries5=new PointsGraphSeries<>();
        xySeries6=new PointsGraphSeries<>();
        xySeries7=new PointsGraphSeries<>();
        xySeries8=new PointsGraphSeries<>();
        xySeries9=new PointsGraphSeries<>();
        xySeries10=new PointsGraphSeries<>();
        xySeries11=new PointsGraphSeries<>();
        xySeries12=new PointsGraphSeries<>();
        xySeries13=new PointsGraphSeries<>();
        xySeries14=new PointsGraphSeries<>();
        xySeries15=new PointsGraphSeries<>();
        xySeries16=new PointsGraphSeries<>();
        xySeries17=new PointsGraphSeries<>();
        xySeries18=new PointsGraphSeries<>();
        xySeries19=new PointsGraphSeries<>();
        xySeries20=new PointsGraphSeries<>();
        xySeries21=new PointsGraphSeries<>();
        xySeries22=new PointsGraphSeries<>();
        xySeries23=new PointsGraphSeries<>();
        xySeries24=new PointsGraphSeries<>();
        xySeries25=new PointsGraphSeries<>();
        xySeries26=new PointsGraphSeries<>();
        xySeries27=new PointsGraphSeries<>();
        xySeries28=new PointsGraphSeries<>();
        xySeries29=new PointsGraphSeries<>();
        xySeries30=new PointsGraphSeries<>();
        xySeries31=new PointsGraphSeries<>();
        take_picture = findViewById(R.id.take_picture);
        //  done=findViewById(R.id.done);

        //generate two lists of random values, one for x and one for y.
        xyValueArray = new ArrayList<>();
        xyValueArray2=new ArrayList<>();

        xyValueArray4=new ArrayList<>();
        xyValueArray5=new ArrayList<>();
        xyValueArray6=new ArrayList<>();
        xyValueArray7=new ArrayList<>();
        xyValueArray8=new ArrayList<>();
        xyValueArray9=new ArrayList<>();
        xyValueArray10=new ArrayList<>();
        xyValueArray11=new ArrayList<>();
        xyValueArray12=new ArrayList<>();
        xyValueArray13=new ArrayList<>();
        xyValueArray14=new ArrayList<>();
        xyValueArray15=new ArrayList<>();
        xyValueArray16=new ArrayList<>();
        xyValueArray17=new ArrayList<>();
        xyValueArray18=new ArrayList<>();
        xyValueArray19=new ArrayList<>();
        xyValueArray20=new ArrayList<>();
        xyValueArray21=new ArrayList<>();
        xyValueArray22=new ArrayList<>();
        xyValueArray23=new ArrayList<>();
        xyValueArray24=new ArrayList<>();
        xyValueArray25=new ArrayList<>();
        xyValueArray26=new ArrayList<>();
        xyValueArray27=new ArrayList<>();
        xyValueArray28=new ArrayList<>();
        xyValueArray29=new ArrayList<>();

        xyValueArray3=new ArrayList<>();


        OnlineDatabase db= new OnlineDatabase(this);
        minlat=member.getString("minlat","0000");
       maxlat= member.getString("maxlat","0000");
        minlng=member.getString("minlng","0000");
        maxlng=member.getString("maxlng","0000");
        String field_id=member.getString("field_id","000");
        String latlongs1 =db.load_latlongs(field_id);
        String []latlongs2=latlongs1.split(",");
        Integer w=0;
        Log.d("latlongsss",latlongs2[1].toString());
        if (latlongs2.length>5) {
            for (w = 0; w < latlongs2.length; w++) {
                if (w == 0 || w % 2 == 0) {
                    latsdouble.add(Double.valueOf(latlongs2[w]));
                } else {
                    longsdouble.add(Double.valueOf(latlongs2[w]));

                }
            }
        }
        else {
            Toast.makeText(this,"field too small",Toast.LENGTH_LONG).show();
            Intent intent=new Intent (getApplicationContext(), Main2Activity.class);
            startActivity(intent);
        }

        field_size=Double.valueOf(member.getString("field_size","1"));
        number=Integer.valueOf(member.getString("no_photos","5"));

        if (field_size<=1)
        {
            number=5;
        } else if (field_size>1 && field_size<=2)
        {            number=7;
        } else if (field_size>2 && field_size<=3)
        {
            number=10;
        }
        else if (field_size>3 )
        {
            number=14;
        }

        new sharedPrefs(this).setMaximumPictures(number);

        //current locations
//the current location is likely inaccurate;so think think think
//        latfirst=Float.parseFloat(getIntent().getStringExtra("lat"));
//      longfirst=Float.parseFloat(getIntent().getStringExtra("lng"));
        //b.putIntegerArrayListExtra("lats",lats);
        //b.putParcelableArrayListExtra("lats", ArrayList<? extends android.os.Parcelable> value);
        //intent.putParcelableArrayListExtra("lats",  ArrayList<? extends Parcelable>lats);
        ArrayList<XYValue> dataVals=new ArrayList<XYValue>();
        for (w=0;w<latsdouble.size();w++)
        {
            xyValueArray.add(new XYValue(longsdouble.get(w),latsdouble.get(w)));

        }

        //sort it in ASC order
        xyValueArray = sortArray(xyValueArray);
        startx =xyValueArray.get(0).getX();
        endx = xyValueArray.get(xyValueArray.size()-1).getX();
        Log.d("starttt",String.valueOf(startx));
         starty=min();
         endy=max();
         randomX=new Random().nextDouble();
         randomY=new Random().nextDouble();
        randomX2=new Random().nextDouble();
        randomX3=new Random().nextDouble();
        randomX4=new Random().nextDouble();
        randomX5=new Random().nextDouble();
        randomX6=new Random().nextDouble();
        randomX7=new Random().nextDouble();
        randomX8=new Random().nextDouble();
        randomX9=new Random().nextDouble();
       randomX10=new Random().nextDouble();
        randomX11=new Random().nextDouble();
        randomX12=new Random().nextDouble();
        randomX13=new Random().nextDouble();
       randomX14=new Random().nextDouble();
        randomY2=new Random().nextDouble();
        randomY3=new Random().nextDouble();
       randomY4=new Random().nextDouble();
        randomY5=new Random().nextDouble();
        randomY6=new Random().nextDouble();
        randomY7=new Random().nextDouble();
        randomY8=new Random().nextDouble();
        randomY9=new Random().nextDouble();
       randomY10=new Random().nextDouble();
        randomY11=new Random().nextDouble();
       randomY12=new Random().nextDouble();
        randomY13=new Random().nextDouble();
        randomY14=new Random().nextDouble();



        randomx=startx+(randomX*(endx-startx));
        randomy=starty+(randomY*(endy-starty));



        Log.d("randommm",String.valueOf(randomx)+" "+String.valueOf(randomy));
        Log.d("randmm",String.valueOf(randomx2)+" "+String.valueOf(randomy2));
        Log.d("randmmmmm",String.valueOf(randomx3)+" "+String.valueOf(randomy3));


        xyValueArray2.add(new XYValue(randomx,randomy));
        xyValueArray4.add(new XYValue(randomx2,randomy2));
        xyValueArray5.add(new XYValue(randomx3,randomy3));
        xyValueArray6.add(new XYValue(randomx4,randomy4));
        xyValueArray7.add(new XYValue(randomx5,randomy5));
        xyValueArray8.add(new XYValue(randomx6,randomy6));
        xyValueArray9.add(new XYValue(randomx7,randomy7));
        xyValueArray10.add(new XYValue(randomx8,randomy8));
        xyValueArray11.add(new XYValue(randomx9,randomy9));
        xyValueArray12.add(new XYValue(randomx10,randomy10));
        xyValueArray13.add(new XYValue(randomx11,randomy11));
        xyValueArray14.add(new XYValue(randomx12,randomy12));
        xyValueArray15.add(new XYValue(randomx13,randomy13));
        xyValueArray16.add(new XYValue(randomx14,randomy14));


        xySeries2.appendData(new DataPoint(randomx,randomy),true, 1000);


        latfirst=Float.parseFloat(getIntent().getStringExtra("lat"));
        longfirst=Float.parseFloat(getIntent().getStringExtra("lng"));

        lats1.add(latfirst);
        longs1.add(longfirst);



        //prefs2.getFloat("")
        double lng=Double.valueOf(getIntent().getStringExtra("lng"));
        double lat=Double.valueOf(getIntent().getStringExtra("lat"));
        Log.d("exacttt",String.valueOf(lng)+" "+String.valueOf(lat));
        xyValueArray3.add(new XYValue(lng,lat));
        xySeries3.appendData(new DataPoint(lng,lat),true, 1000);


        //add the data to the series
        for(int i = 0;i <xyValueArray.size(); i++){
            double x = xyValueArray.get(i).getX();
            double y = xyValueArray.get(i).getY();
            xySeries.appendData(new DataPoint(x,y),true, 1000);
        }double max=0;

        double a= (randXYPoint(-1,1));
        Log.d("random",String.valueOf(a));
        createScatterPlot();

    }public double max()

    {
        double max = 0;

        for (int i = 0; i < xyValueArray.size(); i++) {
            if (xyValueArray.get(i).getY() > max) {
                max = xyValueArray.get(i).getY();
            }
        }
        return max;
    }
    public double min()

    {
        double max = xyValueArray.get(0).getY();

        for (int i = 0; i < xyValueArray.size(); i++) {
            if (xyValueArray.get(i).getY() < max) {
                max = xyValueArray.get(i).getY();
            }
        }
        return max;
    }

    public void writeCurrentcount(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        int count = preferences.getInt("CURRENT_COUNT", 0);
        ++count;
        editor.putInt("CURRENT_COUNT", count);
        editor.apply();
    }

    public int getCurrentCount(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getInt("CURRENT_COUNT", 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sharedPrefs sharedPrefs = new sharedPrefs(this);
        mScatterPlot.addSeries(xySeries);
      //  mScatterPlot.addSeries(xySeries2);

        //TODO PICTURE WAS ACCEPTED DO SOMETHING LIKE GET NEXT COORDINATE OR CHECK IF MAXIMUM PICTURES TAKEN
        Toast.makeText(this, "PICTURE NUMBER :==> " + sharedPrefs.getArraySpacingLen() , Toast.LENGTH_LONG).show();
        if (sharedPrefs.getArraySpacingLen()==0)
        {
            randomx=startx+(randomX*(endx-startx));
            randomy=starty+(randomY*(endy-starty));
            xySeries2.appendData(new DataPoint(randomx,randomy),true, 1000);
            mScatterPlot.addSeries(xySeries2);
            mScatterPlot.addSeries(xySeries3);

        }
        if (sharedPrefs.getArraySpacingLen()==1)
        {
            randomx=startx+(randomX2*(endx-startx));
            randomy=starty+(randomY2*(endy-starty));
            xySeries4.appendData(new DataPoint(randomx,randomy),true, 1000);
            mScatterPlot.addSeries(xySeries4);
            xyValueArray17.add(new XYValue(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)));
            xySeries17.appendData(new DataPoint(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)),true, 1000);
            mScatterPlot.addSeries(xySeries17);

        }
        if (sharedPrefs.getArraySpacingLen()==2)
        {  randomx=startx+(randomX3*(endx-startx));
            randomy=starty+(randomY3*(endy-starty));
            xySeries5.appendData(new DataPoint(randomx,randomy),true, 1000);
            mScatterPlot.addSeries(xySeries5);
            xyValueArray18.add(new XYValue(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)));
            xySeries18.appendData(new DataPoint(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)),true, 1000);
            mScatterPlot.addSeries(xySeries18);

        }
        if (sharedPrefs.getArraySpacingLen()==3)
        {randomx=startx+(randomX4*(endx-startx));
            randomy=starty+(randomY4*(endy-starty));
            xySeries6.appendData(new DataPoint(randomx,randomy),true, 1000);
            mScatterPlot.addSeries(xySeries6);
            xyValueArray19.add(new XYValue(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)));
            xySeries19.appendData(new DataPoint(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)),true, 1000);
            mScatterPlot.addSeries(xySeries19);
        }
        if (sharedPrefs.getArraySpacingLen()==4)
        {randomx=startx+(randomX5*(endx-startx));
            randomy=starty+(randomY5*(endy-starty));
            xySeries7.appendData(new DataPoint(randomx,randomy),true, 1000);
            mScatterPlot.addSeries(xySeries7);
            xyValueArray20.add(new XYValue(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)));
            xySeries20.appendData(new DataPoint(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)),true, 1000);
            mScatterPlot.addSeries(xySeries20);
        }
        if (sharedPrefs.getArraySpacingLen()==5)
        {randomx=startx+(randomX6*(endx-startx));
            randomy=starty+(randomY6*(endy-starty));
            xySeries8.appendData(new DataPoint(randomx,randomy),true, 1000);
            mScatterPlot.addSeries(xySeries8);
            xyValueArray21.add(new XYValue(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)));
            xySeries21.appendData(new DataPoint(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)),true, 1000);
            mScatterPlot.addSeries(xySeries21);
        }
        if (sharedPrefs.getArraySpacingLen()==6)
        {
            randomx=startx+(randomX7*(endx-startx));
            randomy=starty+(randomY7*(endy-starty));
            xySeries9.appendData(new DataPoint(randomx,randomy),true, 1000);
            mScatterPlot.addSeries(xySeries9);
            xyValueArray22.add(new XYValue(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)));
            xySeries22.appendData(new DataPoint(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)),true, 1000);
            mScatterPlot.addSeries(xySeries22);

        }
        if (sharedPrefs.getArraySpacingLen()==7)
        {
            randomx=startx+(randomX8*(endx-startx));
            randomy=starty+(randomY8*(endy-starty));
            xySeries10.appendData(new DataPoint(randomx,randomy),true, 1000);
            mScatterPlot.addSeries(xySeries10);
            xyValueArray23.add(new XYValue(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)));
            xySeries23.appendData(new DataPoint(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)),true, 1000);
            mScatterPlot.addSeries(xySeries23);

        }
        if (sharedPrefs.getArraySpacingLen()==8)
        {
            randomx=startx+(randomX9*(endx-startx));
            randomy=starty+(randomY9*(endy-starty));
            xySeries11.appendData(new DataPoint(randomx,randomy),true, 1000);
            mScatterPlot.addSeries(xySeries11);
            xyValueArray24.add(new XYValue(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)));
            xySeries24.appendData(new DataPoint(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)),true, 1000);
            mScatterPlot.addSeries(xySeries24);

        }
        if (sharedPrefs.getArraySpacingLen()==9)
        {
            randomx=startx+(randomX10*(endx-startx));
            randomy=starty+(randomY10*(endy-starty));
            xySeries12.appendData(new DataPoint(randomx,randomy),true, 1000);
            mScatterPlot.addSeries(xySeries12);
            xyValueArray25.add(new XYValue(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)));
            xySeries25.appendData(new DataPoint(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)),true, 1000);
            mScatterPlot.addSeries(xySeries25);
        }
        if (sharedPrefs.getArraySpacingLen()==10)
        {
            randomx=startx+(randomX11*(endx-startx));
            randomy=starty+(randomY11*(endy-starty));
            xySeries13.appendData(new DataPoint(randomx,randomy),true, 1000);
            mScatterPlot.addSeries(xySeries13);
            xyValueArray26.add(new XYValue(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)));
            xySeries26.appendData(new DataPoint(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)),true, 1000);
            mScatterPlot.addSeries(xySeries26);
        }
        if (sharedPrefs.getArraySpacingLen()==11)
        {
            randomx=startx+(randomX12*(endx-startx));
            randomy=starty+(randomY12*(endy-starty));
            xySeries14.appendData(new DataPoint(randomx,randomy),true, 1000);
            mScatterPlot.addSeries(xySeries14);
            xyValueArray27.add(new XYValue(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)));
            xySeries27.appendData(new DataPoint(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)),true, 1000);
            mScatterPlot.addSeries(xySeries27);
        }
        if (sharedPrefs.getArraySpacingLen()==12)
        {
            randomx=startx+(randomX13*(endx-startx));
            randomy=starty+(randomY13*(endy-starty));
            xySeries15.appendData(new DataPoint(randomx,randomy),true, 1000);
            mScatterPlot.addSeries(xySeries15);
            xyValueArray28.add(new XYValue(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)));
            xySeries28.appendData(new DataPoint(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)),true, 1000);

            mScatterPlot.addSeries(xySeries28);

        }
        if (sharedPrefs.getArraySpacingLen()==13)
        {
            randomx=startx+(randomX14*(endx-startx));
            randomy=starty+(randomY14*(endy-starty));
            xySeries16.appendData(new DataPoint(randomx,randomy),true, 1000);
            mScatterPlot.addSeries(xySeries16);
            xyValueArray29.add(new XYValue(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)));
            xySeries29.appendData(new DataPoint(longs1.get(longs1.size() - 1),lats1.get(lats1.size() - 1)),true, 1000);
            mScatterPlot.addSeries(xySeries29);

        }

        if(sharedPrefs.pictureAccepted())
        {
            ArrayList<RectF> cardRectangles = new DBHelper(this).getCardCoordinates();
            Toast.makeText(this,"NUMBER OF CARDS " + cardRectangles.size() , Toast.LENGTH_LONG).show();
            //TODO DO KOYE'S CARD CALCULATION
            RectF card1 = new RectF(0,0,0,0);
            RectF card2 = new RectF(0,0,0,0);
            if(cardRectangles.size() == 2)
            {
                card1 =  cardRectangles.get(0);
                card2 =  cardRectangles.get(1);
                double diagonal_1 = Math.hypot(card1.width(), card1.height());
                double diagonal_2 = Math.hypot(card2.width(), card2.height());
                double distanceBtwCards = Math.hypot((card1.centerX() - card2.centerX()), (card1.centerY() - card2.centerY()));
                double w= Double.valueOf(member.getString("card_distance","5"));
                if (distanceBtwCards<=w || (Math.abs(diagonal_1/diagonal_2)> 1.4 || Math.abs(diagonal_1/diagonal_2)< 0.7 ))
                { new sharedPrefs(this).rmvArraySpacing();
                }
            }
            else if (cardRectangles.size() == 1)
            {
                card1 =  cardRectangles.get(0);
            }



            //          new sharedPrefs(this).rmvArraySpacing();
//           new DBHelper(this).deleteRejectedPicture(new sharedPrefs(this).getPictureName());

        }

        else if(!sharedPrefs.pictureAccepted())
        {
            //TODO PICTURE WAS REJECTED : DO SOMETHING

        }

        if (sharedPrefs.getArraySpacingLen()==number)
        {            Toast.makeText(this, " Congrats, Examination completed. Please await result." , Toast.LENGTH_LONG).show();

            Intent intent=new Intent (getApplicationContext(), finalresult.class);
            startActivity(intent);
        }



        if (permissionGranted()) {
            try {
                Location location = locationManager.getLastKnownLocation(provider);
                locationManager.requestLocationUpdates(provider, MIN_LOC_UPDATE_TIME, 0, this);

                if (location != null) {
                    onLocationChanged(location);
                }

            } catch (SecurityException e) {

            }
        } else {


            ActivityCompat.requestPermissions(pictureMLactivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

    }

    public String getLatLongs() {


        String latnlongs = "";
        int size = latlongs.size();
        for (int i = 0; i < size - 1; i++) {
            latnlongs += String.valueOf(latlongs.get(i)) + ",";
        }
        latnlongs += String.valueOf(latlongs.get(size - 1));

        return latnlongs;
    }
    public double pointsDist(double lat1, double lon1, double lat2, double lon2) {
//        double R = 6.371; // Radius of the earth in m
//        double dLat = deg2rad(lat2 - lat1);  // deg2rad below
//        double dLon = deg2rad(lon2 - lon1);
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double d = R * c; // Distance in m
//        return d;
        return Math.sqrt((lat2 - lat1) * (lat2 - lat1) + (lon2 - lon1) * (lon2 - lon1)) * 110000;
    }
    @Override
    protected void onPause() {
        super.onPause();
        // locationManager.removeUpdates(this);
    }
    public boolean permissionGranted() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }
    @Override
    public void onLocationChanged(Location location) {

        final double lat = location.getLatitude();
        final double lng = location.getLongitude();

        int size = lats1.size();

        xyValueArray3.add(new XYValue(longfirst,latfirst));
        Log.d("latsss",String.valueOf(latfirst));
        Log.d("longss",String.valueOf(longfirst));
//       xySeries3.appendData(new DataPoint(longfirst,latfirst),true, 1000);

        double dist = pointsDist(lat, lng, lats1.get(size - 1), longs1.get(size - 1));

        if (dist >= MIN_LOC_UPDATE_DISTANCE     && lat>=Double.valueOf(minlat)-0.0002 && lat<=Double.valueOf(maxlat)+0.0002
                && lng>= Double.valueOf(minlng)-0.0002 && lng<=Double.valueOf(maxlng)+0.0002) {
            //xyValueArray2.add(new XYValue(lnging,lating));

            //longs.add(lngnow);
            lats1.add(lat);
            longs1.add(lng);
            time.add(Double.valueOf(System.currentTimeMillis() / 1000));



            //Log.d("TESTME", String.valueOf(xyValueArray2));

            return;
        }
        //TODO Alert dialog

        //+ Math.round(lat *100.0)/100 + "   "+Math.round(lng * 100.0)/100.0);

        //TextView response = (TextView) dialogView.findViewById(R.id.response);
        //response.setText( lat + "  "+ lng  );

        //Button save = dialogView.findViewById(R.id.ad_save);
        //final Button cancel = dialogView.findViewById(R.id.ad_exit);


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
    }

    private void createScatterPlot() {
        Log.d(TAG, "createScatterPlot: Creating scatter plot.");


        //set some properties
        xySeries.setShape(PointsGraphSeries.Shape.RECTANGLE);
        xySeries.setColor(Color.BLUE);
        xySeries.setSize(3f);
        xySeries2.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries2.setColor(Color.GREEN);
        xySeries2.setSize(7f);
        xySeries3.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries3.setColor(Color.RED);
        xySeries3.setSize(7f);
        xySeries17.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries17.setColor(Color.RED);
        xySeries17.setSize(7f);
        xySeries18.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries18.setColor(Color.RED);
        xySeries18.setSize(7f);
        xySeries19.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries19.setColor(Color.RED);
        xySeries19.setSize(7f);
        xySeries20.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries20.setColor(Color.RED);
        xySeries20.setSize(7f);
        xySeries21.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries21.setColor(Color.RED);
        xySeries21.setSize(7f);
        xySeries22.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries22.setColor(Color.RED);
        xySeries22.setSize(7f);
        xySeries23.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries23.setColor(Color.RED);
        xySeries23.setSize(7f);
        xySeries24.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries24.setColor(Color.RED);
        xySeries24.setSize(7f);
        xySeries25.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries25.setColor(Color.RED);
        xySeries25.setSize(7f);
        xySeries26.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries26.setColor(Color.RED);
        xySeries26.setSize(7f);
        xySeries27.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries27.setColor(Color.RED);
        xySeries27.setSize(7f);
        xySeries28.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries28.setColor(Color.RED);
        xySeries28.setSize(7f);
        xySeries29.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries29.setColor(Color.RED);
        xySeries29.setSize(7f);

        xySeries4.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries4.setColor(Color.GREEN);
        xySeries4.setSize(7f);
        xySeries5.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries5.setColor(Color.GREEN);
        xySeries5.setSize(7f);
        xySeries6.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries6.setColor(Color.GREEN);
        xySeries6.setSize(7f);
        xySeries7.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries7.setColor(Color.GREEN);
        xySeries7.setSize(7f);
        xySeries8.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries8.setColor(Color.GREEN);
        xySeries8.setSize(7f);
        xySeries9.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries9.setColor(Color.GREEN);
        xySeries9.setSize(7f);
        xySeries10.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries10.setColor(Color.GREEN);
        xySeries10.setSize(7f);
        xySeries11.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries11.setColor(Color.GREEN);
        xySeries11.setSize(7f);
        xySeries12.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries12.setColor(Color.GREEN);
        xySeries12.setSize(7f);
        xySeries13.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries13.setColor(Color.GREEN);
        xySeries13.setSize(7f);
        xySeries14.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries14.setColor(Color.GREEN);
        xySeries14.setSize(7f);
        xySeries15.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries15.setColor(Color.GREEN);
        xySeries15.setSize(7f);
        xySeries16.setShape(PointsGraphSeries.Shape.TRIANGLE);
        xySeries16.setColor(Color.GREEN);
        xySeries16.setSize(7f);
        //set Scrollable and Scaleable
        mScatterPlot.getViewport().setScalable(true);
        mScatterPlot.getViewport().setScalableY(true);
        mScatterPlot.getViewport().setScrollable(true);
        mScatterPlot.getViewport().setScrollableY(true);

        //set manual x bounds
        mScatterPlot.getViewport().setYAxisBoundsManual(true);
        Log.d("emini",String.valueOf(min()));
        Log.d("eminao",String.valueOf(max()));
        mScatterPlot.getViewport().setMinY(min());
        mScatterPlot.getViewport().setMaxY((max()));
//        mScatterPlot.getViewport().setMaxY(6);
//        mScatterPlot.getViewport().setMinY(7);

        //set manual y bounds
        mScatterPlot.getViewport().setXAxisBoundsManual(true);

        mScatterPlot.getViewport().setMinX(xyValueArray.get(0).getX());
        mScatterPlot.getViewport().setMaxX(xyValueArray.get(xyValueArray.size()-1).getX());
        //mScatterPlot.getViewport().setMinX(3);
        //mScatterPlot.getViewport().setMaxX(4);
        mScatterPlot.addSeries(xySeries);
        mScatterPlot.addSeries(xySeries2);
         mScatterPlot.addSeries(xySeries3);
//
    }

    /**
     * Sorts an ArrayList<XYValue> with respect to the x values.
     * @param array
     * @return
     */
    private ArrayList<XYValue> sortArray(ArrayList<XYValue> array){
        /*
        //Sorts the xyValues in Ascending order to prepare them for the PointsGraphSeries<DataSet>
         */
        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(),2))));
        int m = array.size()-1;
        int count = 0;
        Log.d(TAG, "sortArray: Sorting the XYArray.");


        while(true){
            m--;
            if(m <= 0){
                m = array.size() - 1;
            }
            Log.d(TAG, "sortArray: m = " + m);
            try{
                //print out the y entrys so we know what the order looks like
                //Log.d(TAG, "sortArray: Order:");
                //for(int n = 0;n < array.size();n++){
                //Log.d(TAG, "sortArray: " + array.get(n).getY());
                //}
                double tempY = array.get(m-1).getY();
                double tempX = array.get(m-1).getX();
                if(tempX > array.get(m).getX() ){
                    array.get(m-1).setY(array.get(m).getY());
                    array.get(m).setY(tempY);
                    array.get(m-1).setX(array.get(m).getX());
                    array.get(m).setX(tempX);
                }
                else if(tempY == array.get(m).getY()){
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                }

                else if(array.get(m).getX() > array.get(m-1).getX()){
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                }
                //break when factorial is done
                if(count == factor ){
                    break;
                }
            }catch(ArrayIndexOutOfBoundsException e){
                Log.e(TAG, "sortArray: ArrayIndexOutOfBoundsException. Need more than 1 data point to create Plot." +
                        e.getMessage());
                break;
            }
        }
        return array;
    }


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    public void track(View v) {

        int x = lats1.size() - 1;
        Log.d("jjjjjj", String.valueOf(longs1.get(x)) + " " + String.valueOf(lats1.get(x)));
        sharedPrefs sharedPrefs = new sharedPrefs(this);

        if (distancecheck(randomy, randomx, lats1.get(x), longs1.get(x)) < 200) {

            //TODO CHANGE BACK LATER Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
            //Intent intent = new Intent(getApplicationContext(), sampleDemoActivity.class);
//            Intent intent = new Intent(getApplicationContext(), sampleDemoActivity.class);
//            startActivity(intent);
               // Log.d("ruwuuu",randomx+"-"+randomy);
            mScatterPlot.removeAllSeries();
           startActivity(new Intent(this, CameraActivity.class));
            //startActivity(new Intent(this, MyMainActivity.class));
            //current location is first
        }else if(sharedPrefs.getArraySpacingLen()==number)
        {
            Toast.makeText(this, " Congrats, Examination completed. Please await result." , Toast.LENGTH_LONG).show();

            Intent intent=new Intent (getApplicationContext(), finalresult.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Please, move " + walkingdistance(randomy, randomx, lats1.get(x), longs1.get(x)) + " steps from your location", Toast.LENGTH_LONG).show();
        }



    }
    public void proceed(View v) {

        sharedPrefs sharedPrefs = new sharedPrefs(this);

        if (sharedPrefs.getArraySpacingLen()==number)
        {            Toast.makeText(this, " Congrats, Examination completed. Please await result." , Toast.LENGTH_LONG).show();

            Intent intent=new Intent (getApplicationContext(), finalresult.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, " Please, take the required numnber of pictures." , Toast.LENGTH_LONG).show();

        }


    }
//    }public void next(View v)
//    {
//        Intent intent=new Intent (getApplicationContext(), finalresult.class);
//
//        ArrayList<plantPicDetails> ArraySpacing = new ArrayList<>(0);
//       if (ArraySpacing.size()>=number)
//       {
//           startActivity(intent);
//       }
//       else
//       {
//          Integer x= Integer.valueOf(number)-Integer.valueOf(ArraySpacing.size());
//           Toast.makeText(this,"Please, take " + x+" more pictures",Toast.LENGTH_LONG).show();
//
//       }
//
//    }

    public void update(View v) {
        Intent intent = new Intent(getApplicationContext(), getlocation.class);

        startActivity(intent);
    }
    private double walkingdistance(double lat1, double lon1, double lat2, double lon2){  // generally used geo measurement function
        Integer R = 6371; // Radius of earth in KM
        double dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
        double dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        double x=d * 1000/0.7;
        return (int)Math.round(x); // meters

    }
    private double distancecheck(double lat1, double lon1, double lat2, double lon2){  // generally used geo measurement function
        Integer R = 6371; // Radius of earth in KM
        double dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
        double dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        double x=d * 1000;
        return (int)Math.round(x); // meters

    }

    public double randXYPoint(double min,double max){

        // generates x values
        double xValue = min + Math.random() * (max - min);
        // generates y values
        double yValue = min + Math.random() * (max - min);
        //returns and converts points to string
        //return  String.valueOf(xValue)+ ", "+ String.valueOf(yValue);
        return (xValue);

    }




    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(pictureMLactivity.this, Main2Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();

    }


}