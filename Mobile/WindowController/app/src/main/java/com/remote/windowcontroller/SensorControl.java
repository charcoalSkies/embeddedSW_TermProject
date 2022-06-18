package com.remote.windowcontroller;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.net.URL;

public class SensorControl extends AppCompatActivity {
    private TextView device_name;
    private TextView device_state;
    private Button btn_open;
    private Button btn_close;

    private String user_id;
    private String eq_name;
    private String w_state;
    private String Error;

    Thread sensor_thread;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_control);

        device_name = (TextView) findViewById(R.id.device_name);
        device_state = (TextView) findViewById(R.id.device_state);
        btn_open = (Button) findViewById(R.id.btn_open);
        btn_close = (Button) findViewById(R.id.btn_close);

        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorThread(user_id, "open");

            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorThread(user_id, "close");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference weatherRef = mRootRef.child("Weather").child("Seoul");
        weatherRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                weather_data weather = snapshot.getValue(weather_data.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");

        DatabaseReference usersRef = mRootRef.child("users").child(user_id);
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                equipment_data edata = snapshot.getValue(equipment_data.class);
                eq_name = edata.getEquipment_name();
                w_state = edata.getWindow_state();

                device_name.setText(eq_name);
                device_state.setText(w_state);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sensorThread(String user_id, String state){
        sensor_thread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                try {
                    SendPost openWindow = new SendPost();
                    URL url = new URL("http://tera.dscloud.me:3000/login/sensorControl");
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("user_id", user_id);
                    jsonParam.put("window_control", state);

                    Error = openWindow.sendAPI(url, jsonParam);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        sensor_thread.start();
        try {
            sensor_thread.join();
        }catch (Exception e){}
    }
}