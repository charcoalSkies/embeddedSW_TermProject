package com.remote.windowcontroller;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class SendPost {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String sendAPI(URL url, JSONObject userParam) {
        String Error = null;
        try{
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            Log.i("JSON", userParam.toString());
            os.writeBytes(userParam.toString());

            os.flush();
            os.close();
            BufferedReader br = null;

            if (100 <= conn.getResponseCode() && conn.getResponseCode() <= 399) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            conn.disconnect();

//            String responseData = br.lines().collect(Collectors.joining());

//            JSONObject jsonObject = new JSONObject(responseData);
//            Error = jsonObject.getString("error");
            Error = String.valueOf(conn.getResponseCode());
            Log.i("STATUS", Error);
//            Log.i("ServerResponse", responseData);
            Log.i("MSG" , conn.getResponseMessage());


        }catch (Exception e) {
            e.printStackTrace();
        }

        return Error;
    }
}