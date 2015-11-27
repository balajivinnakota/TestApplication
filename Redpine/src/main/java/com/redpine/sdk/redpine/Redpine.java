package com.redpine.sdk.redpine;

import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by winbold4 on 11/27/2015.
 */
public class Redpine {

    public JSONObject getState(){

        Redpine redpine = new Redpine();
        String autherization  = redpine.getToken();
        Log.i("Autherization", autherization);
        try{

            String webserviceURL = "http://52.74.22.85:8080/wyzbee/api/system/state";
            URL url = new URL(webserviceURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", autherization);
            conn.setRequestProperty("X-Wyzbee-Tenant", "MyWyzBee@123%");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;

            while ((output = br.readLine()) != null) {
                JSONObject jsonObj = new JSONObject(output);
                Log.i("result",jsonObj.toString());
                return jsonObj;

            }

            conn.disconnect();

        }
        catch(Exception e){
            e.printStackTrace();
            Log.i("Error is", e.toString());
        }

        return null;
    }

    public String getToken(){
        String username = "MyWyzBee";
        String password = "MyWyzBee@123#";
        //String tenant = "MyWyzBee@123%";
        String token = username +":"+password;

        //byte[] data = token.getBytes("UTF-8");
        String encodedBytes = Base64.encodeToString(token.getBytes(), Base64.DEFAULT);
        //String encodedBytes = Base64.encodeBase64String(token.getBytes());
        String encodedToken = "Basic "+encodedBytes;
        return encodedToken;
    }
  
    public JSONObject getVersion(){
      return null;
    }
}
