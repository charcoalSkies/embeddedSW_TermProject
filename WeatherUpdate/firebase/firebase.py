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
    def firebase_sync_weather(weather_inform:object) -> None:
        """ 회원가입시 최초 아이디 insert 함수 """
        try:
            FireBase.firebase_conn()
        except Exception:
            pass
        ref = db.reference('/')
        users_ref = ref.child('Weather')
        users_ref.update({
            'Seoul':
            {
                'weather' : f'{weather_inform.weather}',
                'cloud' : f'{weather_inform.cloud}',
                'temp' : f'{weather_inform.temp}',
                'temp_min' : f'{weather_inform.temp_min}',
                'temp_max' : f'{weather_inform.temp_max}',
                'feels_like' : f'{weather_inform.feels_like}',
                'humidity' : f'{weather_inform.humidity}',
                'wind' : f'{weather_inform.wind}'
            }
        })

