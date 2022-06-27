""" firebase 관리 코드 """

import firebase_admin
from firebase_admin import credentials
from firebase_admin import db

class FireBase():
    """ firebase realtime database 관리 클래스 """

    @staticmethod
    def firebase_conn():
        """ firebase connection 연결 함수 """
        cred = credentials.Certificate('certificate/emsw-api-datasync-firebase-adminsdk-hfc9z-228817978f.json')
        firebase_admin.initialize_app(cred, {
        'databaseURL': 'https://emsw-api-datasync-default-rtdb.firebaseio.com/'
        })

    @staticmethod
    def firebase_insert_id(user_login_inform:object) -> int:
        """ 회원가입시 최초 아이디 insert 함수 """
        try:
            FireBase.firebase_conn()
        except Exception:
            pass
        ref = db.reference('/')
        users_ref = ref.child('users')
        users_ref.update({
            f'{user_login_inform.user_id}':
            {   
                "equipment_name" :  "None",
                "window_state" : 'None'
            }
        })

    @staticmethod
    def update_equipment_name(user_equipment_inform) -> None:
        """ 장비 이름 등록 """
        try:
            FireBase.firebase_conn()
        except Exception:
            pass
        
        ref = db.reference('users')
        users_ref = ref.child(f'{user_equipment_inform.user_id}')
        users_ref.update({
                "equipment_name" : f"{user_equipment_inform.equipment_name}"
            })
        
            
    @staticmethod
    def update_window_state(user_control_data:object) -> None:
        """ 창문 상태 업데이트 쿼리 """
        try:
            FireBase.firebase_conn()
        except Exception:
            pass
        

        ref = db.reference('users')
        users_ref = ref.child(f'{user_control_data.user_id}')
        users_ref.update({
                    'window_state': f'{user_control_data.window_control}'
                })

