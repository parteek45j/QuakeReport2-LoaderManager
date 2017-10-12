package com.example.parteek.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Parteek on 10/12/2017.
 */

//this clsas is used to reterieve the data
public class QueryUtils2 {
    static ArrayList<Earthquake> arrayList2;
    String[] list;



    public static ArrayList<Earthquake> getList(String url1) {

        URL url=crateUrl(url1);
        String response="";
        try {
            response=makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("response",response);
        arrayList2=extractFeatureFromJson(response);
        return arrayList2;
    }


    private static URL crateUrl(String stringUrl){
        URL url=null;
        try {
            url=new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            // TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    private static ArrayList<Earthquake> extractFeatureFromJson(String earthquakeJSON) {
       ArrayList<Earthquake> arrayList=new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
            JSONArray featureArray = baseJsonResponse.getJSONArray("features");
            for (int i=0;i<featureArray.length();i++) {
                JSONObject firstFeature = featureArray.getJSONObject(i);
                JSONObject properties = firstFeature.getJSONObject("properties");
                String title = properties.getString("place");
                double mag=properties.getDouble("mag");
                long time=properties.optLong("time");
                String url=properties.getString("url");
                arrayList.add(new Earthquake(mag,title,time,url));
            }
        } catch (JSONException e) {
            Log.e("QueryUtils2", "Problem parsing the earthquake JSON results", e);
        }
        return arrayList;
    }
}
