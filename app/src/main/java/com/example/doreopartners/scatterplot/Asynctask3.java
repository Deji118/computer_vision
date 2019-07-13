package com.example.doreopartners.scatterplot;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

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
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
// this asynctasks sends the results of field examination online
public class Asynctask3 {
    public static class UpploadTast extends AsyncTask<Void, Void, String> {
//I UPLOAD DETAILS OF TRACKED FIELDS ONLINE

        NetworkInfo net;

        //Assets uActivity;
        Context context;
        ArrayList wordlist;
        //DatabaseHelper db;

        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        DataInputStream inputStream = null;
        DatabaseHelper databaseHelper;
        String date;
        public static final String TAG = "AsyncTask2";

        SharedPreferences member;
        //SharedPreferences prefs2;
        SharedPreferences.Editor memEdit;
        String folderPath;
        String arrayOfFiles[];
        File root;
        File allFiles;

        //String urlServer = "https://apps.babbangona.com/tgl_test/tgl_sync/field_mapping";
        //String urlServer = "https://fpf.babbangona.com/field_mapping";
        String urlServer = "https://apps.babbangona.com/machine_learning/germination_analysis";
        //String urlServer = "https://dd25b1d8.ngrok.io";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        URL url;


        public UpploadTast (Context mctx){
            this.context = mctx;
            //this.getSharedPreferences=get

        }

        //ProgressDialog pDialog = new ProgressDialog(context);


        @Override
        protected void onPreExecute() {


            Log.d(TAG, "onPreRequest");

//            pDialog.setMessage("Uploading GPS Data. Please wait...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();

        }

        @Override
        protected String doInBackground(Void... voids) {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);

            //databaseHelper.open();
            JSONArray x = null;
            x =  databaseHelper.getResults();




            HttpURLConnection httpURLConnection = null;
            try {
                URL url = new URL(urlServer+"/syncspacings.php");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("wordlist", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(x), "UTF-8")+"&"+
                        URLEncoder.encode("versionNo", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8");
                bufferedWriter.write(data_string); // writing information to Database
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                httpURLConnection.connect();
            } catch (IOException e) {e.printStackTrace();}
            try{
                int response_code = httpURLConnection.getResponseCode();
                Log.d(TAG, String.valueOf(response_code));
                if(response_code == HttpURLConnection.HTTP_OK){
                    InputStream input = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {result.append(line);}


                    Log.d(TAG, String.valueOf(result));
//                    if(result.toString().equalsIgnoreCase("done")){
//
//                    }
//                    return result.toString();
                    try {


                        /*
This is the point where i update the upload_status column on my table depending on the syncing success/failure of the individual
products

*/

                        JSONArray arr1 = new JSONArray(String.valueOf(result));
                        Log.d(TAG,String.valueOf(result));

                        databaseHelper = new DatabaseHelper(context);
                        //Statistics=new statistics(context);
                        int j = 0;

                        for (int i = 0; i < arr1.length(); i++) {
                            JSONObject obj =  arr1.getJSONObject(i);

                            databaseHelper.updateSyncStatus(obj.get("FMuniqueid").toString(), obj.get("status").toString());

                            j=1;

                        }
                        //return "done";
                        if (j == 1) {
                            return "done";
                        } else {
                            return "All fields have been uploaded";
                        }
                    } catch (JSONException e) {

                        Log.d(TAG,e+"");
                        return e.toString();
                    }
                } else{return("connection error");}
            }
            catch (IOException e){
                e.printStackTrace(); return "Sync failed due to internal error. Most likely a network error"; //why is sync failing
            }
            finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

        }



        protected void onPostExecute(String result) {

            Log.d(" UploadGpsData", "onPost");


            super.onPostExecute(result);


            //pDialog.dismiss();


        }
    }
}

