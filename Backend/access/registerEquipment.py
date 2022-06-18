""" RealTime Firebase 제어 코드 """
from database.firebase import FireBase

class RegisterEquipment():
    """ 센서 제어 클래스 """
    @staticmethod
    def regist_equipment(UserEquipmentInform) -> None:
        """ 센서 제어 함수 """
        FireBase.update_equipment_name(UserEquipmentInform)