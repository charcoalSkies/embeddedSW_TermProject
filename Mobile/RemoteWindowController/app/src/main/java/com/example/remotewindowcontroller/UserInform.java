package com.example.remotewindowcontroller;

public class UserInform {
    public String user_id;
    public String user_pwd;
}

class UserLoginInform extends UserInform {

}

class UserSignupInform extends UserInform{
    public String user_email;
}

class UserControlSensor extends UserInform{
    public String window_control;
}
