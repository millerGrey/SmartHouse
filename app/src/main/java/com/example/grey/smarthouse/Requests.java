package com.example.grey.smarthouse;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by GREY on 06.05.2018.
 */

public class Requests {

    private static final String TAG = "req";


    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }
    public  List<String> getTemp() {
        List<String> Values = new ArrayList<String>();
        String values = new String();
        try {
            String url = Uri.parse("http://vineryhome.ddns.net:8081/cgi-bin/ds1820/value/")
                    .buildUpon()
                    .build().toString();
            values = getUrlString(url);
            //Log.i(TAG, "Received JSON: " + jsonString);


        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
        Values = Arrays.asList(values.split("/"));
        return Values;
    }
    public  List<String> getStates() {
        List<String> Values = new ArrayList<String>();
        String states = new String();
        try {
            String url = Uri.parse("http://vineryhome.ddns.net:8081/cgi-bin/state")
                    .buildUpon()
                    .build().toString();
            states = getUrlString(url);
            //Log.i(TAG, "Received JSON: " + jsonString);


        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
        Values = Arrays.asList(states.split("/"));
        return Values;
    }

    public  List<String> getConfig(int number) {
        List<String> Values = new ArrayList<String>();
        String states = new String();
        try {
            String url = Uri.parse("http://vineryhome.ddns.net:8081/cgi-bin/relay"+number+"/config/")
                    .buildUpon()
                    .build().toString();
            states = getUrlString(url);
            //Log.i(TAG, "Received JSON: " + jsonString);


        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
        Values = Arrays.asList(states.split("/"));
        return Values;
    }
}
