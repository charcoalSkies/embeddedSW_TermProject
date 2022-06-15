""" 부가적 함수 정의 모음 코드 """
import json
import hashlib
from function.inform import UserSignUpInform
from function.inform import UserloginInform
from function.inform import UserControlSensor


class Function():
    """ 부가적 함수 정의 """
    @staticmethod
    def sha256_hashing(user_pwd:str) -> str:
        """ 사용자 패스워드 sha256 암호화"""
        return hashlib.sha256(user_pwd.encode('utf-8')).hexdigest()

    @staticmethod
    def signup_parser(request:json) -> object:
        """ 회원가입 정보 데이터 파싱 """
        
        user_signup_inform = UserSignUpInform

        try:
            request_json = json.dumps(request)
            request_diction = json.loads(request_json)

        except Exception:
            return user_signup_inform
        
        sha256_pwd = Function.sha256_hashing(request_diction['user_id'])
        try:
            user_signup_inform = UserSignUpInform(
                user_id = request_diction['user_id'],
                user_pwd = sha256_pwd,
                user_email = request_diction['user_email'],
                err_state = 0)

        except Exception:
            return user_signup_inform
        
        return user_signup_inform



    @staticmethod
    def login_parser(request:json) -> object:
        """ 로그인 정보 데이터 파싱"""

        user_login_inform = UserloginInform

        try:
            request_json = json.dumps(request)
            request_diction = json.loads(request_json)

        except Exception:
            return user_login_inform
        
        sha256_pwd = Function.sha256_hashing(request_diction['user_id'])
        try:
            user_login_inform = UserloginInform(
                user_id = request_diction['user_id'],
                user_pwd = sha256_pwd,
                err_state = 0)

        except Exception:
            return user_login_inform
        
        return user_login_inform


    @staticmethod
    def data_integrity_check(state:dict) -> int:
        """ 센서 제어 데이터 무셜성 체크 """
        if state['window_control'] == "open" or state['window_control'] == "close":
            return 1
        else :
            return 0

    @staticmethod
    def sensor_control(request:json) -> object:
        """ 센서 제어 데이터 파싱 """
        user_control_data = UserControlSensor
        try:
            request_json = json.dumps(request)
            request_diction = json.loads(request_json)

        except Exception:
            return user_control_data
        
        integrity_check = Function.data_integrity_check(request_diction)
        if integrity_check == 1:
            try:
                user_control_data = UserControlSensor(
                    user_id = request_diction['user_id'],
                    window_control = request_diction['window_control'],
                    err_state = 0)

            except Exception:
                return user_control_data
        else:
            return user_control_data 
        
        return user_control_data