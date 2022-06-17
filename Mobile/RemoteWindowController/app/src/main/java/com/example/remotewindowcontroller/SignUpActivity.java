package com.example.remotewindowcontroller;

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

public class SignUpActivity extends AppCompatActivity {
    Thread thread;
    String Error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        EditText user_id = (EditText) findViewById(R.id.user_id);
        EditText user_pwd = (EditText) findViewById(R.id.user_pwd);
        EditText user_pwd_check = (EditText) findViewById(R.id.user_pwd_check);
        EditText user_email = (EditText) findViewById(R.id.user_email);
        Button signUp_btn = (Button) findViewById(R.id.btn_signup);

        UserSignupInform user_signup_inform = new UserSignupInform();

        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUserId = user_id.getText().toString();
                String strUserPwd = user_pwd.getText().toString();
                String strUserPwdCheck = user_pwd_check.getText().toString();
                String strUserEmail = user_email.getText().toString();

                if (strUserPwd.equals(strUserPwdCheck) == false)
                {
                    AlertDialog.Builder pwdWarning = new AlertDialog.Builder(SignUpActivity.this);
                    pwdWarning.setTitle("비밀번호 오류");
                    pwdWarning.setMessage("입력된 비밀번호가 일치하지 않습니다.");
                    pwdWarning.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            user_pwd_check.setText("");
                            user_pwd.setText("");
                        }
                    });
                    pwdWarning.show();
                }
                else{
                    user_signup_inform.user_id = strUserId;
                    user_signup_inform.user_pwd = strUserPwd;
                    user_signup_inform.user_email = strUserEmail;
                    if (strUserId.equals("") == true || strUserPwd.equals("") == true || strUserEmail.equals("") == true)
                    {
                        AlertDialog.Builder nullWarning = new AlertDialog.Builder(SignUpActivity.this);
                        nullWarning.setTitle("공백 오류");
                        nullWarning.setMessage("입력되지 않은 값이 존재합니다.");
                        nullWarning.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 아무것도 안함
                            }
                        });
                        nullWarning.show();
                    }else{
                        signupThread(user_signup_inform);
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.i("ErrorCode", Error);
                        if (Error.equals("0")){
                            AlertDialog.Builder success = new AlertDialog.Builder(SignUpActivity.this);
                            success.setTitle("회원가입 성공");
                            success.setMessage("환영합니다 " + user_id.getText().toString()+" 님");
                            success.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    intent.putExtra("user_id", user_id.getText().toString());
                                    intent.putExtra("user_pwd", user_pwd.getText().toString());
                                    startActivity(intent);
                                }
                            });
                            success.show();
                        }
                        else{
                            AlertDialog.Builder fail = new AlertDialog.Builder(SignUpActivity.this);
                            fail.setTitle("회원가입 실패");
                            fail.setMessage("누군가 이미 아이디를 사용하고 있어요");
                            fail.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    user_id.setText("");
                                }
                            });
                            fail.show();
                        }
                    }
                }
            }
        });
    }

    public void signupThread(UserSignupInform user_Signup_inform){
        thread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                try {
                    sendPost userSignUp = new sendPost();
                    URL url = new URL("http://tera.dscloud.me:3000/signup");
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("user_id", user_Signup_inform.user_id);
                    jsonParam.put("user_pwd", user_Signup_inform.user_pwd);
                    jsonParam.put("user_email", user_Signup_inform.user_email);

                    Error = userSignUp.sendAPI(url, jsonParam);

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}