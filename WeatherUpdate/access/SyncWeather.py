""" 날씨 동기화 코드 """

import json
from api.weatherApi import API
from api.weatherInform import WeatherInform
from firebase.firebase import FireBase

class SyncWeather():
    def action()->None:
        weather_data = SyncWeather.call_api()
        SyncWeather.sync_weather(weather_data)

    def call_api()->object:
        weather_data = API.request_weather()
        # weather_data = """{"coord":{"lon":126.7339,"lat":37.34},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],"base":"stations","main":{"temp":24.6,"feels_like":25.02,"temp_min":24.06,"temp_max":26.03,"pressure":1006,"humidity":73,"sea_level":1006,"grnd_level":1005},"visibility":10000,"wind":{"speed":5.5,"deg":246,"gust":7.21},"clouds":{"all":71},"dt":1655531534,"sys":{"type":1,"id":8105,"country":"KR","sunrise":1655496730,"sunset":1655549762},"timezone":32400,"id":1846918,"name":"Ansan-si","cod":200}"""
        # weather_data = json.loads(weather_data)
        weather_inform = WeatherInform()
        weather_inform.weather = weather_data["weather"][0]["main"]
        weather_inform.cloud = weather_data["weather"][0]["description"]
        weather_inform.temp = weather_data["main"]["temp"]
        weather_inform.feels_like = weather_data["main"]["feels_like"]
        weather_inform.temp_min = weather_data["main"]["temp_min"]
        weather_inform.temp_max = weather_data["main"]["temp_max"]
        weather_inform.humidity = weather_data["main"]["humidity"]
        weather_inform.wind = weather_data["wind"]["speed"]
        
        find_dust_data = API.request_fine_dust()
        fine_dust = int(find_dust_data['response']['body']['items'][0]['pm10Value'])
        
        
        if fine_dust < 31:
            weather_inform.fine_dust = "0"
            
        elif fine_dust > 30 and fine_dust < 81:
            weather_inform.fine_dust = "1"
            
        elif fine_dust > 80 and fine_dust < 151:
            weather_inform.fine_dust = "2"
            
        else : 
            weather_inform.fine_dust = "3"

        return weather_inform

    def sync_weather(weather_data:object)->None:
        """ firebase 날씨 동기화 """
        FireBase.firebase_sync_weather(weather_data)