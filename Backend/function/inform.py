""" 데이터 구조체"""
from dataclasses import dataclass

@dataclass
class UserSignUpInform():
    """회원가입 유저 구조체"""
    user_id:str = "None"
    user_pwd:str = "None"
    user_email:str = "None"
    err_state:int = -1


@dataclass
class UserloginInform():
    """유저 로그인 구조체"""
    user_id:str = "None"
    user_pwd:str = "None"
    err_state:int = -1


@dataclass
class UserEquipmentInform():
    """ 유저 장비 등록 구조체 """
    user_id:str = "None"
    equipment_name:str = "None"
    err_state:int = -1
    

@dataclass
class UserControlSensor():
    """유저 센서 컨트롤 구조체"""
    user_id:str = "None"
    window_control:str = "None"
    err_state:int = -1
