package com.example.doreopartners.scatterplot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.BG.Static.plantInfo;
import org.tensorflow.BG.Static.plantPicDetails;
import org.tensorflow.BG.Static.sharedPrefs;
import org.tensorflow.BG.database.DBAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleToLongFunction;

import static org.tensorflow.BG.database.DBAdapter.*;
//This database is controlled by daniel. I just et details from it and send online
public class AIdatabase {
    private Context context;
    private SQLiteDatabase db;

    public AIdatabase(@NonNull Context ctx) {
        context = ctx;
        final DBAdapter mDbHelper = new DBAdapter(context);
        db = mDbHelper.getWritableDatabase();
    }

    public  JSONArray getplantdetails()
    {
        String searchQuery = "SELECT  * FROM " + TABLE_NAME+" where SYNC_FLAG = '0'";
        Cursor cursor = db.rawQuery(searchQuery, null );

        JSONArray resultSet     = new JSONArray();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        if( cursor.getString(i) != null )
                        {
                            Log.d("TAG_NAME", cursor.getString(i) );
                            rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                        }
                        else
                        {
                            rowObject.put( cursor.getColumnName(i) ,  "" );
                        }
                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();

        return resultSet;

    }
    public  JSONArray getcardinfo()
    {


        String searchQuery = "SELECT  * FROM " + TABLE_NAME_CARDS+ " where SYNC_FLAG = '0'";
        Cursor cursor = db.rawQuery(searchQuery, null );

        JSONArray resultSet     = new JSONArray();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        if( cursor.getString(i) != null )
                        {
                            Log.d("TAG_NAME", cursor.getString(i) );
                            rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                        }
                        else
                        {
                            rowObject.put( cursor.getColumnName(i) ,  "" );
                        }
                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();

        return resultSet;

    }
    public ArrayList<Double> getaverage(String field_id) {

        ArrayList<Double> list = new ArrayList<Double>();
        Cursor cursor = db.rawQuery("select AVG_SPACING FROM " +TABLE_NAME_SPACING +" where FIELD_ID=\""+field_id+"\"",null);
        if (cursor.moveToFirst()){
            do{
                list.add(cursor.getDouble(0));
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        db.close();


        return list;

    }
    public ArrayList<Double> getmaximum(String field_id) {

        ArrayList<Double> list = new ArrayList<Double>();
        Cursor cursor = db.rawQuery("select MAX_SPACING FROM " +TABLE_NAME_SPACING +" where FIELD_ID=\""+field_id+"\"",null);
        if (cursor.moveToFirst()){
            do{
                list.add(cursor.getDouble(0));
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        db.close();


        return list;


    }
        public void updateSyncStatus(String id, String sync) {

        String updateQuery = "update " + TABLE_NAME + " set SYNC_FLAG = '" + sync + "' where id = '" + id + "'";
        db.execSQL(updateQuery);
    }

}