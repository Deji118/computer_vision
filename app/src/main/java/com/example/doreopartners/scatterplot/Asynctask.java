package com.example.doreopartners.scatterplot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//WELL, YOU KNOW WHAT I DO NOW, DONT YOU? I SYNC ONLINE. BUT THIS PARTICULAR ASYNCTASK IS USED FOR SYNCING DOWN TO GET DETAILS OF THE FIELD TO BE TRACKED
public class Asynctask {
    public static class DownloadApplication extends AsyncTask<Void, Void, String> {

        @SuppressLint("StaticFieldLeak")
        Context context;
        StringBuilder result = null;
        SessionManagement sessionManagement;
        //OnlineDBHelper onlineDBHelper;
        String urlServer = "https://apps.babbangona.com/machine_learning/germination_analysis";

        //String urlServer = "http://ba0a0c99.ngrok.io/";
        //String urlServer = "https://fpf.babbangona.com/field_mapping";


        public DownloadApplication(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected String doInBackground(Void... voids) {
            OnlineDatabase onlineDatabase= new OnlineDatabase(context);

            HttpURLConnection conn;
            URL url = null;
            JSONArray x=null;


            String staff_id="";


            try {
                sessionManagement = new SessionManagement(context);
                HashMap<String, String> user = sessionManagement.getUserDetails();
                staff_id = user.get(SessionManagement.KEY_STAFF_ID);
                staff_id = user.get(SessionManagement.KEY_STAFF_ID);
                Log.d("staffidme",staff_id);
            } catch (Exception e) {
                System.out.println("Exception caught: Session Management didnt work");
            }

            if (staff_id == null) {
                return "Your members are up to date";
            }

            try {
                url = new URL(urlServer + "/download_data.php");

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "exception 1";
            }

            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(20000);
                conn.setConnectTimeout(30000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);

                conn.setDoOutput(true);
                String latest_date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                //Date date = new Date();

                //latest_date  = dateFormat.format(date);

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("staff_id", staff_id).appendQueryParameter("latest_date", latest_date);
                String query = builder.build().getEncodedQuery();
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                e1.printStackTrace();
                return "Operation failed, kindly check your internet connection";
            }

            String output = "Operation failed, kindly check your internet connection";
            try {
                int response_code = conn.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    result = new StringBuilder();
                    String line;


                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    Map<String, String> map = null;
                    ArrayList<Map<String, String>> wordList;
                    wordList = new ArrayList();
                    Log.d("deji", result+"");
                    try {
                        JSONArray JA = new JSONArray(result.toString());
                        JSONObject json = null;
                        int j = 0;
                        for (int i = 0; i < JA.length(); i++) {

                            json = JA.getJSONObject(i);
                            map = new HashMap<String, String>();
                            map.put("member_id", json.getString("member_id"));
                            map.put("ik_number", json.getString("ik_number"));
                            map.put("first_name", json.getString("first_name"));
                            map.put("last_name", json.getString("last_name"));
                            map.put("phone_number", json.getString("phone_number"));
                            map.put("village", json.getString("village"));
                            map.put("field_size", json.getString("field_size"));
                            map.put("member_role", json.getString("member_role"));
                            map.put("staff_id", json.getString("staff_id"));
                            map.put("unique_id",json.getString("unique_id"));
                            map.put("latlongs",json.getString("latlongs"));
                            map.put("minlat",json.getString("minlat"));
                            map.put("maxlat",json.getString("maxlat"));
                            map.put("minlng",json.getString("minlng"));
                            map.put("maxlng",json.getString("maxlng"));
                            map.put("description",json.getString("description"));



                            wordList.add(map);
                            j = 1;

                        }
                        if (j == 1) {
                            onlineDatabase.getResults(wordList);
                            output = "Records downloaded successfully";

                        } else {
                            output = "No record found";
                        }

                    } catch (Exception e) {
                        Log.e("Fail 3", e.toString());
                    }

                    return output;
                } else {
                    return ("Connection error");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }
    }
}