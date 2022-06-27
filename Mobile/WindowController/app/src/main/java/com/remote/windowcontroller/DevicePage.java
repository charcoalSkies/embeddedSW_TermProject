package com.remote.windowcontroller;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DevicePage extends AppCompatActivity {
    private TextView tvCloud;
    private TextView tvFeels_like;
    private TextView tvHumidity;
    private TextView tvTemp;
    private TextView tvTemp_max;
    private TextView tvTemp_min;
    private TextView tvWind;
    private TextView tvState;
    private TextView tvFine_dust;

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

    private String equipment_name;
    private String window_state;

    private String user_id;

    Thread fndThread;
    Thread motorThread;
    Thread buzzerThread;
    Thread closeThread;

    static {
        System.loadLibrary("setStepMotor");
        System.loadLibrary("setDotMatrix");
        System.loadLibrary("setTextLcd");
        System.loadLibrary("setFnd");
        System.loadLibrary("setBuzzer");

    }

    public native int SetMotorState(int direction);
    public native int SetDotMatrix(int face);
    public native int SetTextLcd(String str1, String str2);
    public native int SetFnd(String str);
    public native int SetBuzzer(int sound);

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference weatherRef = mRootRef.child("Weather").child("Seoul");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_page);

        tvCloud = (TextView) findViewById(R.id.cloud);
        tvFeels_like = (TextView) findViewById(R.id.feels_like);
        tvHumidity = (TextView) findViewById(R.id.humidity);
        tvTemp = (TextView) findViewById(R.id.temp);
        tvTemp_max = (TextView) findViewById(R.id.temp_max);
        tvTemp_min = (TextView) findViewById(R.id.temp_min);
        tvWind = (TextView) findViewById(R.id.wind);
        tvState = (TextView) findViewById(R.id.state);
        tvFine_dust = (TextView) findViewById(R.id.fine_dust);

        ivWeather = (ImageView) findViewById(R.id.weather_image);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");

    }

//    @Override
//    public void onBackPressed() {
////        super.onBackPressed();
//    }

    @Override
    protected void onStart() {
        super.onStart();
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
                    closeThread(user_id, "close");
                    ivWeather.setImageResource(R.drawable.rain);
                }else{
                    ivWeather.setImageResource(R.drawable.x);
                }

                try{
                    SetDotMatrix(Dust);
                    SetTextLcd(Weather, "Temp: "+Temp);
                    fndThread(Humidity);
                }catch(Exception e){}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Intent intent = getIntent();
        String intent_user_id = intent.getStringExtra("user_id");

        DatabaseReference usersRef = mRootRef.child("users").child(intent_user_id);
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Equipment_data equipment_data = snapshot.getValue(Equipment_data.class);
                equipment_name = equipment_data.getEquipment_name();
                window_state = equipment_data.getWindow_state();
                tvState.setText(window_state);

                try{
                    if(window_state.equals("open")){
                        try {
                            motorThread(0);
                            buzzerThread();

                            motorThread.join();
                            buzzerThread.join();
                            tvState.setTextColor(0xAA5bff29);
                        }catch (Exception e){}

                    }else if(window_state.equals("close")){
                        try {
                            motorThread(0);
                            buzzerThread();

                            motorThread.join();
                            buzzerThread.join();
                            tvState.setTextColor(0xAAff551c);
                        }catch (Exception e){}
                    }else{
                        // 아무것도 하지 않음
                    }
                }catch (Exception e){ }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void buzzerThread(){
        buzzerThread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                try {
                    SetBuzzer(1);
                    Thread.sleep(3000);
                    SetBuzzer(0);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        buzzerThread.start();
    }

    public void motorThread(int direction){
        motorThread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                try {
                    SetMotorState(direction);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        motorThread.start();
    }

    public void fndThread(String Humidity){
        fndThread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                try {
                    SetFnd("00"+Humidity);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        fndThread.start();
        try{
            fndThread.join();
        }catch (Exception e){};
    }

    public void closeThread(String user_id, String state){
        closeThread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                try {
                    SendPost closeWindow = new SendPost();
                    URL url = new URL("http://tera.dscloud.me:3000/login/sensorControl");
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("user_id", user_id);
                    jsonParam.put("window_control", state);
                    closeWindow.sendAPI(url, jsonParam);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        closeThread.start();
        try {
            closeThread.join();
        }catch (Exception e){}
    }

}