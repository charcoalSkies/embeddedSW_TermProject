""" weather API 호출 코드 """

import requests
import json
from api.define import WeatherAPI

class API():
    """ Weather API 클래스 """

    @staticmethod
    def request_weather(lat = "37.3400026", lon = "126.7338608") -> dict:
        """ Weather API 요청 함수 """
    
        url = "https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&appid=%s&units=metric" % (lat, lon, WeatherAPI.key)

        response = requests.get(url)
        print(response.text)
        return json.loads(response.text)
        

