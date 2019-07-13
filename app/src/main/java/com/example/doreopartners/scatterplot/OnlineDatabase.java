package com.example.doreopartners.scatterplot;

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
//this is the database of the synced down details from clearance
// TODO: 02/05/2019 update unique_id to field_id 
public class OnlineDatabase extends SQLiteAssetHelper {
    //Asset Helper that houses details of CMP. code is readable enough
    private static final String DATABASE_NAME = "fieldmapping.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "fields";

    private static final String COLUMN_USER_MEMBERID = "member_id";

    private static final String COLUMN_USER_IKNUMBER = "ik_number";
    private static final String COLUMN_USER_FIRSTNAME = "first_name";
    private static final String COLUMN_USER_LASTNAME = "last_name";
    private static final String COLUMN_USER_PHONENUMBER = "phone_number";
    //private static final String COLUMN_USER_LGA = "lga";
    private static final String COLUMN_USER_FIELDID = "unique_id";
    private static final String COLUMN_USER_VILLAGE = "village";
    private static final String COLUMN_USER_FIELDSIZE = "field_size";
    private static final String COLUMN_USER_MEMBERROLE = "member_role";
    private static final String COLUMN_USER_STAFFID = "staff_id";
    private static final String COLUMN_USER_LATLONGS = "latlongs";
    private static final String COLUMN_USER_MINLAT = "minlat";
    private static final String COLUMN_USER_MAXLAT = "maxlat";
    private static final String COLUMN_USER_MINLNG = "minlng";
    private static final String COLUMN_USER_MAXLNG = "maxlng";
    private static final String COLUMN_USER_DESCRIPTION = "description";








    public String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public String STATE_TABLE = "select distinct staff_id from " + TABLE_NAME ;

    public OnlineDatabase (Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
    }


    public List<String> getvillage(String staffid_selected,String role) {
        List<String> list = new ArrayList<String>();
        //list.add("Select lga...");
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT distinct village FROM fields WHERE staff_id = '" + staffid_selected + "' AND member_role = '" + role + "'", null);


        if (cursor.moveToFirst()){
            do{
                list.add(cursor.getString(0));
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }


    public String getuniqueid(String member_selected) {

        //List<String> list = new ArrayList<String>();
        //list.add("Select state...");
        String A = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select distinct unique_id from fields where member_id=\"" + member_selected + "\"", null);

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
    public String load_minlat(String unique_id) {

        //List<String> list = new ArrayList<String>();
        //list.add("Select state...");
        String A = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select distinct minlat from fields where unique_id=\"" + unique_id + "\"", null);

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
    public String load_maxlat(String unique_id) {

        //List<String> list = new ArrayList<String>();
        //list.add("Select state...");
        String A = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select distinct maxlat from fields where unique_id=\"" + unique_id + "\"", null);

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
    public String load_minlng(String unique_id) {

        //List<String> list = new ArrayList<String>();
        //list.add("Select state...");
        String A = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select distinct minlng from fields where unique_id=\"" + unique_id + "\"", null);

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

    public String load_maxlng(String unique_id) {

        //List<String> list = new ArrayList<String>();
        //list.add("Select state...");
        String A = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select distinct maxlng from fields where unique_id=\"" + unique_id + "\"", null);

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
    public String load_latlongs(String unique_id) {

        //List<String> list = new ArrayList<String>();
        //list.add("Select state...");
        String A = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select distinct latlongs from fields where unique_id=\"" + unique_id + "\"", null);

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



    public ArrayList<Map<String, String>> load_members(String staff_id,String ik_number) {
        Map<String, String> map = null;
        ArrayList<Map<String, String>> wordList;
        wordList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT distinct first_name,last_name, member_id,phone_number FROM fields  WHERE ik_number = \""+ik_number+"\" AND staff_id = \"" + staff_id +  "\"" , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            map = new HashMap<String, String>();
            map.put("first_name", cursor.getString(cursor.getColumnIndex("first_name")));
            map.put("last_name", cursor.getString(cursor.getColumnIndex("last_name")));
            map.put("member_id", cursor.getString(cursor.getColumnIndex("member_id")));
            map.put("phone_number", cursor.getString(cursor.getColumnIndex("phone_number")));

            wordList.add(map);
            Log.d("WordList",wordList.toString());
            cursor.moveToNext();

        }

        cursor.close();

        return wordList;
    }
//    public Integer memberfields(String memberid_selected) {
//
//        //List<String> list = new ArrayList<String>();
//        //list.add("Select state...");
//        Integer A = 0;
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("select  distinct count (*)from fields where member_id=\"" + memberid_selected   + "\"", null);
//
//        if (cursor.moveToFirst()){
//            do{
//                A=(cursor.getInt(0));
//            }
//            while(cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//        return A;
//
//    }
    public ArrayList<Map<String, String>> load_ik(String staff_id,String village,String role) {
        Map<String, String> map = null;
        ArrayList<Map<String, String>> wordList;
        wordList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT distinct first_name,last_name, ik_number FROM fields  WHERE village = \""+village+"\" AND staff_id = \"" + staff_id +"\" AND member_role = \"" + role + "\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            map = new HashMap<String, String>();
            map.put("first_name", cursor.getString(cursor.getColumnIndex("first_name")));
            map.put("last_name", cursor.getString(cursor.getColumnIndex("last_name")));
            //map.put("member_id", cursor.getString(cursor.getColumnIndex("member_id")));
            map.put("ik_number", cursor.getString(cursor.getColumnIndex("ik_number")));

            wordList.add(map);
            Log.d("WordList",wordList.toString());
            cursor.moveToNext();

        }

        cursor.close();

        return wordList;
    }
    public ArrayList<Map<String, String>> load_fields(String member_selected) {
        Map<String, String> map = null;
        ArrayList<Map<String, String>> wordList;
        wordList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT distinct description,unique_id, field_size FROM fields  WHERE member_id=\"" + member_selected + "\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            map = new HashMap<String, String>();
            map.put("description", cursor.getString(cursor.getColumnIndex("description")));
            map.put("unique_id", cursor.getString(cursor.getColumnIndex("unique_id")));
            map.put("field_size", cursor.getString(cursor.getColumnIndex("field_size")));


            wordList.add(map);
            Log.d("WordList",wordList.toString());
            cursor.moveToNext();

        }

        cursor.close();

        return wordList;
    }

    public  void getResults(ArrayList<Map<String, String>> wordList)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        JSONArray jsonArray = new JSONArray(wordList);
        JSONObject jsonObject = null;
        try {

            for(int i = 0; i<jsonArray.length();i++){
                jsonObject = jsonArray.getJSONObject(i);
                db.execSQL("insert into "+TABLE_NAME+"("+COLUMN_USER_MEMBERID+","+COLUMN_USER_IKNUMBER+","+COLUMN_USER_FIRSTNAME+","+COLUMN_USER_LASTNAME+","+COLUMN_USER_PHONENUMBER
                        +","+COLUMN_USER_VILLAGE+","+COLUMN_USER_FIELDSIZE+","+COLUMN_USER_MEMBERROLE+","+COLUMN_USER_FIELDID+","+COLUMN_USER_LATLONGS+","+COLUMN_USER_MINLAT+","
                        +COLUMN_USER_MAXLAT+","+COLUMN_USER_MINLNG+","+COLUMN_USER_MAXLNG+","+COLUMN_USER_DESCRIPTION+","+COLUMN_USER_STAFFID+") values(\""+jsonObject.getString("member_id")+"\"," +
                        "\""+jsonObject.getString("ik_number")+"\",\""+jsonObject.getString("first_name")+"\",\""+jsonObject.getString("last_name")+"\",\""+jsonObject.getString("phone_number")
                        +"\",\""+jsonObject.getString("village")+"\",\""+jsonObject.getString("field_size")+"\",\""+jsonObject.getString("member_role")+"\",\""+jsonObject.getString("unique_id")
                        +"\",\""+jsonObject.getString("latlongs")+"\",\""+jsonObject.getString("minlat")+"\",\""+jsonObject.getString("maxlat")+"\",\""+jsonObject.getString("minlng")+"\",\""+jsonObject.getString("maxlng")
                        +"\",\""+jsonObject.getString("description")+"\",\""+jsonObject.getString("staff_id")+"\") ");

            }
        } catch (JSONException e) {
            Log.d("result3",e.toString());
            e.printStackTrace();
        }



    }
    public void updateSyncStatus(String id, String sync) {
        SQLiteDatabase db = this.getWritableDatabase();

        String updateQuery = "update user set status = '" + sync + "' where staff_id = '" + id + "'";
        db.execSQL(updateQuery);
    }
}


