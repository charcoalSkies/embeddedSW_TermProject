""" weather API 호출 코드 """
import requests
import json
from api.define import WeatherAPI
from api.define import DataGoKr

class API():
    """ Weather API 클래스 """
    @staticmethod
    def request_weather(lat = "37.3400026", lon = "126.7338608") -> dict:
        """ Weather API 요청 함수 """
    
        url = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&lang=kr&units=metric" % (lat, lon, WeatherAPI.key)

        response = requests.get(url)
        return json.loads(response.text)
        
    @staticmethod
    def request_fine_dust() -> dict:
        """ 미세먼지 API 요청 함수 """
        
        url = 'http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty'
        params ={'serviceKey' : DataGoKr.key, 'returnType' : 'json', 'numOfRows' : '100', 'pageNo' : '1', 'sidoName' : '서울', 'ver' : '1.0' }
        
        response = requests.get(url, params=params)
        return json.loads(response.content.decode('utf-8'))
        

