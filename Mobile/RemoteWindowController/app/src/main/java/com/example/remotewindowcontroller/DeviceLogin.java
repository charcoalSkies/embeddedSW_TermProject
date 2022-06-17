package com.example.remotewindowcontroller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.net.URL;

public class DeviceLogin extends AppCompatActivity {
    Thread thread;
    String deviceName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_login);

        Button signup_btn = (Button) findViewById(R.id.btn_login);
        EditText user_id = (EditText) findViewById(R.id.user_id);
        EditText user_pwd = (EditText) findViewById(R.id.user_pwd);
        EditText equipment_name = (EditText) findViewById(R.id.equipment_name);

        EquipmentInform equipment_inform = new EquipmentInform();

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equipment_inform.user_id = user_id.getText().toString();
                equipment_inform.user_pwd = user_pwd.getText().toString();
                equipment_inform.equipment_name = equipment_name.getText().toString();

                if(equipment_inform.user_id.equals("") == true || equipment_inform.user_id.equals("") == true || equipment_inform.user_pwd.equals("") == true){
                    AlertDialog.Builder failRegister = new AlertDialog.Builder(DeviceLogin.this);
                    failRegister.setTitle("장비 로그인 오류");
                    failRegister.setMessage("입력되지 않은 정보가 존재합니다.\n 다시 확인해 주세요.");
                    failRegister.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 아무것도 안함
                        }
                    });
                    failRegister.show();
                }else{
                    deviceName = FireBaseAction.getDivceName(equipment_inform);
                    try {
                        Log.i("res", deviceName);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}