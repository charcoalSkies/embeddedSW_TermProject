package com.remote.windowcontroller;

public class UserInform {
    public String user_id;
    public String user_pwd;
}

class UserLoginInform extends UserInform {

}

class UserSignupInform extends UserInform{
    public String user_email;
}

class EquipmentInform extends UserInform{
    public String equipment_name;
    public EquipmentInform(){ }

    public EquipmentInform(String user_id, String user_pwd, String equipment_name){
        this.user_id = user_id;
        this.user_pwd = user_pwd;
        this.equipment_name = equipment_name;
    }

    public String getUserId()
    {
        return this.user_id;
    }

    public String getUserPwd()
    {
        return this.user_pwd;
    }

    public String getEquipmentName()
    {
        return this.equipment_name;
    }
}

class UserControlSensor extends UserInform{
    public String window_control;
}
