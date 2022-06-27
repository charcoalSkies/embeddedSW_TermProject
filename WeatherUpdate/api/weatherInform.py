""" 날씨 데이터 구조체"""
from dataclasses import dataclass

@dataclass
class WeatherInform():
    """날씨 구조체"""
    weather:str = "None"
    cloud:str = "None"
    temp:str = "None"
    feels_like:str = "None"
    temp_min:str = "None"
    temp_max:str = "None"
    humidity:str = "None"
    wind:str = "None"
    fine_dust:str = "None"
