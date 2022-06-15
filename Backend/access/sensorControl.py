""" RealTime Firebase 제어 코드 """
from database.firebase import FireBase

class SensorControl():
    """ 센서 제어 클래스 """
    @staticmethod
    def sensor_control(user_control_data) -> None:
        """ 센서 제어 함수 """
        FireBase.update_window_state(user_control_data)
        
