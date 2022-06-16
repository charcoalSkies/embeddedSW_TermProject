package com.example.remotewindowcontroller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    Thread thread;
    String responseData = null;
    String Error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login_btn = (Button) findViewById(R.id.btn_login);
        EditText user_Id = (EditText) findViewById(R.id.user_id);
        EditText user_pwd = (EditText) findViewById(R.id.user_pwd);

        UserLoginInform user_login_inform = new UserLoginInform();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_login_inform.user_id = user_Id.getText().toString();
                user_login_inform.user_pwd = user_pwd.getText().toString();
                sendPost(user_login_inform);
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("ErrorCode", Error);
            }
        });
    }

    public void sendPost(UserLoginInform user_login_inform) {
         thread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                try {
                    URL url = new URL("http://tera.dscloud.me:3000/login");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("user_id", user_login_inform.user_id);
                    jsonParam.put("user_pwd", user_login_inform.user_pwd);


                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();
                    BufferedReader br = null;

                    if (100 <= conn.getResponseCode() && conn.getResponseCode() <= 399) {
                        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    } else {
                        br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    }
                    responseData = br.lines().collect(Collectors.joining());
                    conn.disconnect();

                    JSONObject jsonObject = new JSONObject(responseData);
                    Error = jsonObject.getString("error");
                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("ServerResponse", responseData);
                    Log.i("MSG" , conn.getResponseMessage());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}