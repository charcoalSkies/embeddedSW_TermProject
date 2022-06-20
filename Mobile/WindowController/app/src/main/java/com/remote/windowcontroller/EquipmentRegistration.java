package com.remote.windowcontroller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.net.URL;

public class EquipmentRegistration extends AppCompatActivity {
    Thread thread;
    String Error = null;
    String intent_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_registration);

        EditText equipment_name = (EditText) findViewById(R.id.equipment_name);
        Button btn_registration = (Button) findViewById(R.id.btn_registration);

        try{
            Intent intent = getIntent();
            intent_user_id = intent.getStringExtra("user_id");
        }catch (Exception e){
            e.printStackTrace();
        }

        EquipmentInform equipment_inform = new EquipmentInform();

        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                equipment_inform.user_id = intent_user_id;
                equipment_inform.equipment_name = equipment_name.getText().toString();

                if (equipment_inform.equipment_name.equals("")) {
                    AlertDialog.Builder failRegister = new AlertDialog.Builder(EquipmentRegistration.this);
                    failRegister.setTitle("장비 이름 오류");
                    failRegister.setMessage("공백 문자는 등록할 수 없습니다.");
                    failRegister.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 아무것도 안함
                        }
                    });
                    failRegister.show();
                }else{
                    registerEquipmentThread(equipment_inform);
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try{
                        Log.i("ErrorCode", Error);
                    }catch (Exception e){
                        Error = "-1";
                    }
                    if (Error.equals("0")){
                        AlertDialog.Builder successRegister = new AlertDialog.Builder(EquipmentRegistration.this);
                        successRegister.setTitle("장비 등록 성공");
                        successRegister.setMessage("장비 등록에 성공하였습니다.");
                        successRegister.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(EquipmentRegistration.this, SensorControl.class);
                                intent.putExtra("user_id", intent_user_id);
                                startActivity(intent);
                            }
                        });
                        successRegister.show();

                    } else{
                        AlertDialog.Builder failRegister = new AlertDialog.Builder(EquipmentRegistration.this);
                        failRegister.setTitle("장비 등록 실패");
                        failRegister.setMessage("장비 등록에 실패하였습니다. \n네트워크 상태를 확인해 주세요");
                        failRegister.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 아무것도 안함
                            }
                        });
                        failRegister.show();
                    }
                }
            }
        });
    }

    public void registerEquipmentThread(EquipmentInform equipment_inform){
        thread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                try {
                    SendPost registerEquipment = new SendPost();
                    URL url = new URL("http://tera.dscloud.me:3000/login/registerEquipment");
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("user_id", equipment_inform.user_id);
                    jsonParam.put("equipment_name", equipment_inform.equipment_name);

                    Error = registerEquipment.sendAPI(url, jsonParam);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}