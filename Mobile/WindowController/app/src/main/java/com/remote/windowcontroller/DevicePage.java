package com.remote.windowcontroller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DevicePage extends AppCompatActivity {
    private TextView tvCloud;
    private TextView tvFeels_like;
    private TextView tvHumidity;
    private TextView tvTemp;
    private TextView tvTemp_max;
    private TextView tvTemp_min;
    private TextView tvWeather;
    private TextView tvWind;
    private TextView tvState;

    private String Cloud;
    private String Weather;
    private String Feels_like;
    private String Humidity;
    private String Temp;
    private String Temp_max;
    private String Temp_min;
    private String Wind;

    private String equipment_name;
    private String window_state;

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
        tvWeather = (TextView) findViewById(R.id.weather);
        tvWind = (TextView) findViewById(R.id.wind);
        tvState = (TextView) findViewById(R.id.state);
    }

    @Override
    protected void onStart() {
        super.onStart();
        weatherRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                weather_data weather = snapshot.getValue(weather_data.class);
                Cloud = weather.getCloud();
                Weather = weather.getWeather();
                Feels_like = weather.getFeels_like();
                Humidity = weather.getHumidity();
                Temp = weather.getTemp();
                Temp_max = weather.getTemp_max();
                Temp_min = weather.getTemp_min();
                Wind = weather.getWind();

                tvCloud.setText(Cloud);
                tvWeather.setText(Weather);
                tvFeels_like.setText(Feels_like);
                tvHumidity.setText(Humidity);
                tvTemp.setText(Temp);
                tvTemp_max.setText(Temp_max);
                tvTemp_min.setText(Temp_min);
                tvWind.setText(Wind);
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
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}