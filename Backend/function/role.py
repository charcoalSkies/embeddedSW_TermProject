""" 부가적 함수 정의 모음 코드 """
import json

from function.inform import UserSignUpInform
from function.inform import UserloginInform
from function.inform import UserControlSensor


class Function():
    """ 부가적 함수 정의 """

    @staticmethod
    def signup_parser(request:json) -> object:
        """ 회원가입 정보 데이터 파싱 """
        
        user_signup_inform = UserSignUpInform

        try:
            request_json = json.dumps(request)
            request_diction = json.loads(request_json)

        except Exception:
            return user_signup_inform
        
        try:
            user_signup_inform = UserSignUpInform(
                user_id = request_diction['user_id'],
                user_pwd = request_diction['user_pwd'],
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
        
        try:
            user_login_inform = UserloginInform(
                user_id = request_diction['user_id'],
                user_pwd = request_diction['user_pwd'],
                err_state = 0)

        except Exception:
            return user_login_inform
        
        return user_login_inform


    @staticmethod
    def sensor_control(request:json) -> object:
        """ 센서 제어 데이터 파싱 """

        user_control_data = UserControlSensor

        try:
            request_json = json.dumps(request)
            request_diction = json.loads(request_json)

        except Exception:
            return user_control_data
        
        try:
            user_control_data = UserControlSensor(
                window_control = request_diction['window_control'],
                err_state = 0)

        except Exception:
            return user_control_data
        
        return user_control_data
