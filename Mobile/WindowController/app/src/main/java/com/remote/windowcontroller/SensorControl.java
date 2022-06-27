package com.remote.windowcontroller;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.net.URL;

public class SensorControl extends AppCompatActivity {
    private TextView tvCloud;
    private TextView tvFeels_like;
    private TextView tvHumidity;
    private TextView tvTemp;
    private TextView tvTemp_max;
    private TextView tvTemp_min;
    private TextView tvWind;
    private TextView tvState;
    private TextView tvFine_dust;
    private TextView tvDevice_name;
    private ImageView ivWeather;

    private String Cloud;
    private String Weather;
    private String Feels_like;
    private String Humidity;
    private String Temp;
    private String Temp_max;
    private String Temp_min;
    private String Wind;
    private String Fine_dust;
    private String Device_name;

    private String equipment_name;
    private String window_state;

    private Button btn_open;
    private Button btn_close;

    private String user_id;
    private String eq_name;
    private String w_state;
    private String Error;

    Thread sensor_thread;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference weatherRef = mRootRef.child("Weather").child("Seoul");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_control);

        tvCloud = (TextView) findViewById(R.id.cloud);
        tvFeels_like = (TextView) findViewById(R.id.feels_like);
        tvHumidity = (TextView) findViewById(R.id.humidity);
        tvTemp = (TextView) findViewById(R.id.temp);
        tvTemp_max = (TextView) findViewById(R.id.temp_max);
        tvTemp_min = (TextView) findViewById(R.id.temp_min);
        tvWind = (TextView) findViewById(R.id.wind);
        tvState = (TextView) findViewById(R.id.state);
        tvFine_dust = (TextView) findViewById(R.id.fine_dust);
        tvDevice_name = (TextView) findViewById(R.id.device_name);
        ivWeather = (ImageView) findViewById(R.id.weather_image);


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

//    @Override
//    public void onBackPressed() {
////        super.onBackPressed();
//    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference weatherRef = mRootRef.child("Weather").child("Seoul");
        weatherRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Weather_data weather = snapshot.getValue(Weather_data.class);

                Cloud = weather.getCloud();
                Weather = weather.getWeather();
                Feels_like = weather.getFeels_like();
                Humidity = weather.getHumidity();
                Temp = weather.getTemp();
                Temp_max = weather.getTemp_max();
                Temp_min = weather.getTemp_min();
                Wind = weather.getWind();
                Fine_dust = weather.getFine_dust();


                tvCloud.setText(Cloud);
                tvFeels_like.setText("체감: "+Feels_like);
                tvHumidity.setText(Humidity);
                tvTemp.setText(Temp);
                tvTemp_max.setText(Temp_max);
                tvTemp_min.setText(Temp_min);
                tvWind.setText(Wind);

                int Dust = Integer.parseInt(Fine_dust);

                switch (Dust){
                    case 0: tvFine_dust.setText("좋음");
                        tvFine_dust.setTextColor(0xAA5bff29);
                        break;

                    case 1: tvFine_dust.setText("보통");
                        tvFine_dust.setTextColor(0xAA1c8eff);
                        break;

                    case 2: tvFine_dust.setText("나쁨");
                        tvFine_dust.setTextColor(0xAAff911c);
                        break;

                    case 3: tvFine_dust.setText("매우나쁨");
                        tvFine_dust.setTextColor(0xAAff371c);
                        break;
                }

                if(Weather.equals("Clear")){
                    ivWeather.setImageResource(R.drawable.sun);
                }else if(Weather.equals("Clouds")){
                    ivWeather.setImageResource(R.drawable.cloudy);
                }else if(Weather.equals("Rain")){
                    ivWeather.setImageResource(R.drawable.rain);
                }else{
                    ivWeather.setImageResource(R.drawable.x);
                }

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
                Equipment_data equipment_data = snapshot.getValue(Equipment_data.class);
                equipment_name = equipment_data.getEquipment_name();
                window_state = equipment_data.getWindow_state();

                tvDevice_name.setText(equipment_name+": ");
                tvState.setText(window_state);

                if (window_state.equals("open")) {
                    tvState.setTextColor(0xAA5bff29);

                } else if (window_state.equals("close")) {
                    tvState.setTextColor(0xAAff551c);
                }
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
                    SendPost Window = new SendPost();
                    URL url = new URL("http://tera.dscloud.me:3000/login/sensorControl");
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("user_id", user_id);
                    jsonParam.put("window_control", state);

                    Error = Window.sendAPI(url, jsonParam);
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