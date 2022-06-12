"""flask route main code"""
import json
from flask import Flask
from flask import request

from function.role import Function

app = Flask(__name__)

@app.route("/signup",  methods = ['POST'])
def signup():
    """회원가입 기능"""
    get_json = request.get_json()

    user_signup_inform:object = Function.signup_parser(get_json)
    
    if user_signup_inform.err_state == -1:
        response_singup = {
            "error" : "400"
        }

    else :
        response_singup = {
            "error" : "0"
        }
    
    return json.dumps(response_singup, ensure_ascii=False), 200


@app.route("/login", methods = ['POST'])
def login():
    """로그인 기능"""
    get_json = request.get_json()

    user_login_inform:object = Function.login_parser(get_json)
    
    if user_login_inform.err_state == -1:
        response_login = {
            "error" : "500"
        }

    else :
        response_login = {
            "error" : "0"
        }
    
    return json.dumps(response_login, ensure_ascii=False), 200



@app.route("/sensorControl", methods = ['POST'])
def sensor_control():
    """센서 제어 기능"""
    get_json = request.get_json()

    user_control_data:object = Function.sensor_control(get_json)
    
    if user_control_data.err_state == -1:
        response_sensor = {
            "error" : "600"
        }

    else :
        response_sensor = {
            "error" : "0"
        }

    return json.dumps(response_sensor, ensure_ascii=False), 200


if __name__ == "__main__":
    app.run(host='0.0.0.0', port=3000)
    # app.run()

