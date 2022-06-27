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
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONObject;

import java.net.URL;

public class UserLogin extends AppCompatActivity {
    Thread thread;
    String Error = null;
    String intent_user_id = null;
    String intent_user_pwd = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        Button login_btn = (Button) findViewById(R.id.btn_login);
        Button signup_btn = (Button) findViewById(R.id.btn_signup);
        EditText user_id = (EditText) findViewById(R.id.user_id);
        EditText user_pwd = (EditText) findViewById(R.id.user_pwd);
        CheckBox equipment_registration = (CheckBox) findViewById(R.id.equipment_registration);

        try{
            Intent intent = getIntent();
            String intent_user_id = intent.getStringExtra("user_id");
            String intent_user_pwd = intent.getStringExtra("user_pwd");
            if(intent_user_id.equals(null) == false && intent_user_pwd.equals(null) == false){
                user_id.setText(intent_user_id);
                user_pwd.setText(intent_user_pwd);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        UserLoginInform user_login_inform = new UserLoginInform();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_login_inform.user_id = user_id.getText().toString();
                user_login_inform.user_pwd = user_pwd.getText().toString();
                loginThread(user_login_inform);
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

                if (Error.equals("200")){
                    if(equipment_registration.isChecked()) {
                        Intent intent = new Intent(UserLogin.this, EquipmentRegistration.class);
                        intent.putExtra("user_id", user_id.getText().toString());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(UserLogin.this, SensorControl.class);
                        intent.putExtra("user_id", user_id.getText().toString());
                        startActivity(intent);
                    }
                }
                else
                {
                    AlertDialog.Builder failLogin = new AlertDialog.Builder(UserLogin.this);
                    failLogin.setTitle("로그인 실패");
                    failLogin.setMessage("아이디 또는 비밀번호가 틀렸습니다.");
                    failLogin.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 아무것도 안함
                        }
                    });
                    failLogin.show();
                }
            }
        });
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLogin.this, SignUp.class));
            }
        });
    }

    public void loginThread(UserLoginInform user_login_inform){
        thread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                try {
                    SendPost userLoin = new SendPost();
                    URL url = new URL("http://tera.dscloud.me:3000/login");
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("user_id", user_login_inform.user_id);
                    jsonParam.put("user_pwd", user_login_inform.user_pwd);

                    Error = userLoin.sendAPI(url, jsonParam);

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}