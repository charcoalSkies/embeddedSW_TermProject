package com.remote.windowcontroller;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.net.URL;

public class DeviceLogin extends AppCompatActivity {
    Thread thread_firebase;
    Thread thread_login;
    String deviceName = null;
    String Error = null;
    private static DatabaseReference mDatabase;
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

                if(equipment_inform.equipment_name.equals("") == true || equipment_inform.user_id.equals("") == true || equipment_inform.user_pwd.equals("") == true){
                    AlertDialog.Builder failLogin = new AlertDialog.Builder(DeviceLogin.this);
                    failLogin.setTitle("?????? ????????? ??????");
                    failLogin.setMessage("???????????? ?????? ????????? ???????????????.\n?????? ????????? ?????????.");
                    failLogin.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // ???????????? ??????
                        }
                    });
                    failLogin.show();
                }else{
//                    FirebaseLoginThread(equipment_inform);
                    loginThread(equipment_inform);

                    if(Error.equals("200") == true) {
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("users").child(equipment_inform.user_id).child("equipment_name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            String EqName = "";
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if(!task.isSuccessful()){
                                    Log.e("firebase", "Error getting data", task.getException());
                                }else{
                                    EqName = String.valueOf(task.getResult().getValue());
                                    if(EqName.equals(equipment_inform.equipment_name)){
                                        AlertDialog.Builder LoginSuccess = new AlertDialog.Builder(DeviceLogin.this);
                                        LoginSuccess.setTitle("?????? ????????? ??????");
                                        LoginSuccess.setMessage("??????????????? "+equipment_inform.user_id+"???");
                                        LoginSuccess.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(DeviceLogin.this, DevicePage.class);
                                                intent.putExtra("user_id", user_id.getText().toString());
                                                startActivity(intent);
                                            }
                                        });
                                        LoginSuccess.show();
                                    } else{
                                        AlertDialog.Builder failLogin = new AlertDialog.Builder(DeviceLogin.this);
                                        failLogin.setTitle("?????? ????????? ??????");
                                        failLogin.setMessage("???????????? ?????? ?????? ???????????????.\n?????? ????????? ?????????.");
                                        failLogin.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // ???????????? ??????
                                            }
                                        });
                                        failLogin.show();
                                    }
                                }
                            }
                        });
                    }else{
                        AlertDialog.Builder failLogin = new AlertDialog.Builder(DeviceLogin.this);
                        failLogin.setTitle("?????? ????????? ??????");
                        failLogin.setMessage("???????????? ?????? ?????? ?????? ?????? ???????????????.\n?????? ????????? ?????????.");
                        failLogin.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // ???????????? ??????
                            }
                        });
                        failLogin.show();
                    }
                }
            }
        });
    }
    public void loginThread(EquipmentInform equipment_inform){
        thread_login = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                try {
                    SendPost userLoin = new SendPost();
                    URL url = new URL("http://tera.dscloud.me:3000/login");
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("user_id", equipment_inform.user_id);
                    jsonParam.put("user_pwd", equipment_inform.user_pwd);

                    Error = userLoin.sendAPI(url, jsonParam);

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread_login.start();
        try {
            thread_login.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}