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

//WELL, this is Koye's online settings table
public class Asynctask4 {
    public static class DownloadApplication extends AsyncTask<Void, Void, String> {

        @SuppressLint("StaticFieldLeak")
        Context context;
        StringBuilder result = null;
         SessionManagement sessionManagement;
        //OnlineDBHelper onlineDBHelper;
        String urlServer = "https://apps.babbangona.com/machine_learning/germination_analysis";

        //String urlServer = "http://9a592159.ngrok.io/";
        //String urlServer = "https://fpf.babbangona.com/field_mapping";


        public DownloadApplication(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected String doInBackground(Void... voids) {
            settingsdatabase settings= new settingsdatabase(context);

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
                return "";
            }

            try {
                url = new URL(urlServer + "/fetch_settings.php");

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
                            map.put("no_photos", json.getString("no_photos"));
                            map.put("no_cards", json.getString("no_cards"));
                            map.put("card_distance", json.getString("card_distance"));
                            map.put("card_size", json.getString("card_size"));









                            //map.put("latest_date", json.getString("latest_date"));

                            wordList.add(map);
                            j = 1;

                        }
                        if (j == 1) {
                            settings.getsettings(wordList);
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