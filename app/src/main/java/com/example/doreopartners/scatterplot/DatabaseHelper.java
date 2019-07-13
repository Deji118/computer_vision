package com.example.doreopartners.scatterplot;

//this is my final result table

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MLBG.db";

    private static final String TABLE_USER = "user";
    //
    private static final String COLUMN_USER_IKNUMBER = "ik_number";
    private static final String COLUMN_USER_FIRSTNAME = "first_name";
    private static final String COLUMN_USER_LASTNAME = "last_name";
    private static final String COLUMN_USER_MEMBERID = "member_id";

    private static final String COLUMN_USER_STAFFID = "staff_id";
    private static final String COLUMN_USER_FMUNIQUEID = "FMuniqueid";
    private static final String COLUMN_USER_FIELDSIZE = "field_size";
    //private static final String COLUMN_USER_LATLONGS = "latlongs";
    private static final String COLUMN_USER_TIMESTAMP = "timestamp";

    private static final String COLUMN_USER_MINLAT = "minlat";
    private static final String COLUMN_USER_MAXLAT = "maxlat";
    private static final String COLUMN_USER_MINLNG = "minlng";
    private static final String COLUMN_USER_MAXLNG = "maxlng";
    private static final String COLUMN_USER_DESCRIPTION = "description";
    private static final String COLUMN_USER_VILLAGE = "village";
//    private static final String COLUMN_USER_UNIQUEID = "unique_id";
    private static final String COlUMN_USER_VERSION = "version";
    private static final String COlUMN_USER_AVERAGESPACING = "average_spacing";
    private static final String COlUMN_USER_MAXIMUMSPACING= "maximum_spacing";
    private static final String COlUMN_USER_USERFEEDBACK = "user_feedback";
    private static final String COlUMN_USER_APPFEEDBACK= "app_feedback";

    private static final String COLUMNN_USER_UPLOAD_STATUS="status";
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" + COLUMN_USER_IKNUMBER + " TEXT, "
            + COLUMN_USER_FIRSTNAME + " TEXT,"+ COLUMN_USER_LASTNAME + " TEXT," +COLUMN_USER_MEMBERID + " TEXT,"
             + COLUMN_USER_STAFFID + " TEXT," + COLUMN_USER_FMUNIQUEID + " TEXT,"+ COLUMN_USER_FIELDSIZE + " TEXT," +
           COLUMN_USER_TIMESTAMP + " TEXT," +COLUMN_USER_MINLAT
            + " TEXT,"+COLUMN_USER_MAXLAT + " TEXT," +COLUMN_USER_MINLNG + " TEXT,"+COLUMN_USER_MAXLNG + " TEXT,"
             +COLUMN_USER_DESCRIPTION + " TEXT,"+COLUMN_USER_VILLAGE + " TEXT,"
            + COlUMN_USER_VERSION
            + " TEXT," + COlUMN_USER_AVERAGESPACING + " TEXT," + COlUMN_USER_MAXIMUMSPACING + " TEXT,"
            + COlUMN_USER_USERFEEDBACK + " TEXT," + COlUMN_USER_APPFEEDBACK
            + " TEXT,"
            +COLUMNN_USER_UPLOAD_STATUS
            + " INTEGER DEFAULT 0" +")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }



    public String saverecords(JSONArray jsonArray){
        SQLiteDatabase database = getWritableDatabase();
        JSONObject jsonObject = null;
        for(int i = 0; i< jsonArray.length(); i++){
            try {
                //int Check  = 0;
                jsonObject = jsonArray.getJSONObject(i);

                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_USER_IKNUMBER,jsonObject.getString("ik_number"));
                contentValues.put(COLUMN_USER_FIRSTNAME,jsonObject.getString("first_name"));
                contentValues.put(COLUMN_USER_LASTNAME,jsonObject.getString("last_name"));
                contentValues.put(COLUMN_USER_MEMBERID,jsonObject.getString("member_id"));
                contentValues.put(COLUMN_USER_STAFFID,jsonObject.getString("staff_id"));
                contentValues.put(COLUMN_USER_FMUNIQUEID, jsonObject.getString("FMuniqueid"));
                contentValues.put(COLUMN_USER_FIELDSIZE,jsonObject.getString("field_size"));
                //contentValues.put(COLUMN_USER_LATLONGS,jsonObject.getString("latlongs"));
                contentValues.put(COLUMN_USER_MINLAT,jsonObject.getString("minlat"));
                contentValues.put(COLUMN_USER_MAXLAT,jsonObject.getString("maxlat"));
                contentValues.put(COLUMN_USER_MINLNG,jsonObject.getString("minlng"));
                contentValues.put(COLUMN_USER_MAXLNG,jsonObject.getString("maxlng"));
                contentValues.put(COLUMN_USER_TIMESTAMP,jsonObject.getString("timestamp"));
                contentValues.put(COLUMN_USER_DESCRIPTION, jsonObject.getString("description"));
                contentValues.put(COLUMN_USER_VILLAGE, jsonObject.getString("village"));

                contentValues.put(COlUMN_USER_VERSION, jsonObject.getString("version"));
                contentValues.put(COlUMN_USER_AVERAGESPACING, jsonObject.getString("average_spacing"));
                contentValues.put(COlUMN_USER_MAXIMUMSPACING, jsonObject.getString("maximum_spacing"));
                contentValues.put(COlUMN_USER_USERFEEDBACK, jsonObject.getString("user_feedback"));
                contentValues.put(COlUMN_USER_APPFEEDBACK, jsonObject.getString("app_feedback"));






                database.insert(TABLE_USER,null,contentValues);
            } catch (JSONException e) {

                Log.d("deji4", e.toString());
                e.printStackTrace();
            }

        }





        return "";
    }
    public  JSONArray getResults()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        String searchQuery = "SELECT  * FROM " + TABLE_USER+" where status = '0'";
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
    public String getaverage(String field_id) {

        //List<String> list = new ArrayList<String>();
        //list.add("Select state...");
        String A = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select distinct average_spacing from " + TABLE_USER+" where field_id=\"" + field_id + "\"", null);

        if (cursor.moveToFirst()){
            do{
                A=(cursor.getString(0));
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return A;

    }    public String getmaximum(String field_id) {

        //List<String> list = new ArrayList<String>();
        //list.add("Select state...");
        String A = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select distinct maximum_spacing from " + TABLE_USER+" where field_id=\"" + field_id + "\"", null);

        if (cursor.moveToFirst()){
            do{
                A=(cursor.getString(0));
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return A;

    }

    public void updateSyncStatus(String id, String sync) {
        SQLiteDatabase db = this.getWritableDatabase();

        String updateQuery = "update user set status = '" + sync + "' where FMuniqueid = '" + id + "'";
        db.execSQL(updateQuery);
    }

    public Integer memberfields(String memberid_selected) {

        Integer A = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select  count (*)from user where member_id=\"" + memberid_selected   + "\"", null);

        if (cursor.moveToFirst()){
            do{
                A=(cursor.getInt(0));
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return A;

    }

    public ArrayList<Map<String, String>> load_member(String village,String iknumber) {
        Map<String, String> map = null;
        ArrayList<Map<String, String>> wordList;
        wordList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT distinct first_name,last_name, member_id FROM user  WHERE village = \""+village+"\" AND ik_number = \"" + iknumber + "\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            map = new HashMap<String, String>();
            map.put("first_name", cursor.getString(cursor.getColumnIndex("first_name")));
            map.put("last_name", cursor.getString(cursor.getColumnIndex("last_name")));
            map.put("member_id", cursor.getString(cursor.getColumnIndex("member_id")));


            wordList.add(map);
            Log.d("WordList",wordList.toString());
            cursor.moveToNext();

        }

        cursor.close();

        return wordList;
    }
    public Integer mapped_today(String date) {

        //List<String> list = new ArrayList<String>();
        //list.add("Select state...");
        Integer A = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select distinct count (*)from user where date=\"" + date   + "\"", null);

        if (cursor.moveToFirst()){
            do{
                A=(cursor.getInt(0));
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return A;

    }
    public Integer mapped_total() {

        //List<String> list = new ArrayList<String>();
        //list.add("Select state...");
        Integer A = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select  distinct count (*)from "+ TABLE_USER , null);

        if (cursor.moveToFirst()){
            do{
                A=(cursor.getInt(0));
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return A;

    }
    public Integer synced(String status) {

        //List<String> list = new ArrayList<String>();
        //list.add("Select state...");
        Integer A = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select  count (*)from user where status=\"" + status   + "\"", null);

        if (cursor.moveToFirst()){
            do{
                A=(cursor.getInt(0));
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return A;

    }
    public List<String> getvillage(String staffid_selected) {
        List<String> list = new ArrayList<String>();
        //list.add("Select lga...");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select distinct village from user where staff_id=\""+staffid_selected+"\"",null);

        if (cursor.moveToFirst()){
            do{
                list.add(cursor.getString(0));
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }  public ArrayList<Map<String, String>> load_ik(String staff_id,String village) {
        Map<String, String> map = null;
        ArrayList<Map<String, String>> wordList;
        wordList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT distinct ik_number FROM user  WHERE village = \""+village+"\" AND staff_id = \"" + staff_id + "\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            map = new HashMap<String, String>();

            map.put("ik_number", cursor.getString(cursor.getColumnIndex("ik_number")));

            wordList.add(map);
            Log.d("WordList",wordList.toString());
            cursor.moveToNext();

        }

        cursor.close();

        return wordList;
    }
    public ArrayList<Map<String, String>> load_activefields(String member_id) {
        Map<String, String> map = null;
        ArrayList<Map<String, String>> wordList;
        wordList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT distinct description, FMuniqueid,app_feedback,timestamp FROM user  WHERE member_id = \""+member_id+  "\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            map = new HashMap<String, String>();

            map.put("FMuniqueid", cursor.getString(cursor.getColumnIndex("FMuniqueid")));
            map.put("description", cursor.getString(cursor.getColumnIndex("description")));

            map.put("app_feedback", cursor.getString(cursor.getColumnIndex("app_feedback")));
            map.put("timestamp", cursor.getString(cursor.getColumnIndex("timestamp")));
            wordList.add(map);
            Log.d("WordList",wordList.toString());
            cursor.moveToNext();

        }

        cursor.close();

        return wordList;
    }
    public ArrayList<Map<String, String>> load_inactivefields(String member_id,String fieldstatus) {
        Map<String, String> map = null;
        ArrayList<Map<String, String>> wordList;
        wordList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT distinct description, FMuniqueid,field_size,date FROM user  WHERE member_id = \""+member_id+ "\"AND field_status = \"" + fieldstatus + "\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            map = new HashMap<String, String>();

            map.put("FMuniqueid", cursor.getString(cursor.getColumnIndex("FMuniqueid")));
            map.put("description", cursor.getString(cursor.getColumnIndex("description")));
            map.put("field_size", cursor.getString(cursor.getColumnIndex("field_size")));
            map.put("date", cursor.getString(cursor.getColumnIndex("date")));




            wordList.add(map);
            Log.d("WordList",wordList.toString());
            cursor.moveToNext();

        }

        cursor.close();

        return wordList;
    }


}
