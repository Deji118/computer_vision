package com.example.doreopartners.scatterplot;
//this is the settinds databasehelper
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: 02/05/2019 update unique_id to field_id
public class settingsdatabase extends SQLiteAssetHelper {
    //Asset Helper that houses details of CMP. code is readable enough
    private static final String DATABASE_NAME = "fieldmapping.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "settings";
    //    public static final String TABLE_NAME_LGA = "LGA";
//    public static final String STATE = "state";
//    public static final String CMP = "cmp";
//private static final String TABLE_NAME = "memberdetails";
    private static final String COLUMN_USER_PHOTOS = "no_photos";

    private static final String COLUMN_USER_CARDS= "no_cards";
    private static final String COLUMN_USER_DISTANCE= "card_distance";
    private static final String COLUMN_USER_CARDSIZE= "card_size";










    public String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public settingsdatabase (Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
    }



    public  void getsettings(ArrayList<Map<String, String>> wordList)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        JSONArray jsonArray = new JSONArray(wordList);
        JSONObject jsonObject = null;
        try {

            for(int i = 0; i<jsonArray.length();i++){
                jsonObject = jsonArray.getJSONObject(i);
                db.execSQL("replace into "+TABLE_NAME+"("+COLUMN_USER_PHOTOS+","+COLUMN_USER_CARDS+","+COLUMN_USER_DISTANCE+","+COLUMN_USER_CARDSIZE+") values(\""+jsonObject.getString("no_photos")+"\"," +
                        "\""+jsonObject.getString("no_cards")+"\",\""+jsonObject.getString("card_distance")+"\",\""+jsonObject.getString("card_size")+"\") ");

            }
        } catch (JSONException e) {
            Log.d("result3",e.toString());
            e.printStackTrace();
        }



    }

    public String no_photos() {

        //List<String> list = new ArrayList<String>();
        //list.add("Select state...");
        String A = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select no_cards FROM " +TABLE_NAME +"",null);
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
    public String no_cards() {

        //List<String> list = new ArrayList<String>();
        //list.add("Select state...");
        String A = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select no_cards FROM " +TABLE_NAME +"",null);
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
    public String card_distance() {

        //List<String> list = new ArrayList<String>();
        //list.add("Select state...");
        String A = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select card_distance FROM " +TABLE_NAME +"",null);
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
    public String card_size() {

        //List<String> list = new ArrayList<String>();
        //list.add("Select state...");
        String A = null;
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.rawQuery("select card_size FROM " +TABLE_NAME +"",null);
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
}


