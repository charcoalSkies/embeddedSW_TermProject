"""flask route main code"""
import json
import ssl
from flask import Flask
from flask import request
from function.role import Function
from access.signup import Singup
from access.login import Login
from access.sensorControl import SensorControl
from access.registerEquipment import RegisterEquipment
from database.firebase import FireBase
app = Flask(__name__)

@app.route("/signup",  methods = ['POST'])
def signup():
    """회원가입 기능"""
    get_json = request.get_json()

    user_signup_inform:object = Function.signup_parser(get_json)
    
    response_singup = {}
    if user_signup_inform.err_state == -1:
        response_singup["error"] = "400"
    else :
        response_singup["error"] = "200"
    
    id_check = Singup.action_check_signup(user_signup_inform)
    
    if id_check == -1 :
        Singup.action_signup(user_signup_inform)
        FireBase.firebase_insert_id(user_signup_inform)
    else : 
        response_singup["error"] = "401"

    return json.dumps(response_singup, ensure_ascii=False), response_singup["error"]


@app.route("/login", methods = ['POST'])
def login():
    """로그인 기능"""
    get_json = request.get_json()

    user_login_inform:object = Function.login_parser(get_json)
    
    response_login = {}
    if user_login_inform.err_state == -1:
        response_login["error"] = "500"
    else :
        response_login["error"] = "200"

    if Login.action_login(user_login_inform) == 0:
        response_login["error"] = "200"
    else :
        response_login["error"] = "501"

    return json.dumps(response_login, ensure_ascii=False), response_login["error"]


@app.route("/login/registerEquipment", methods = ['POST'])
def regist_equipment():
    """ 장비 등록 기능 """
    get_json = request.get_json()
    user_equipment_inform = Function.register_equipment_parser(get_json)
    response_equipment= {}

    if user_equipment_inform.err_state == -1:
        response_equipment["error"] = "600"
    else :
        response_equipment["error"] = "200"
        id_check = Singup.action_check_signup(user_equipment_inform)
        if id_check == 1:
            RegisterEquipment.regist_equipment(user_equipment_inform)
        else :
            response_equipment["error"] = "601"
    return json.dumps(response_equipment, ensure_ascii=False), response_equipment["error"]
    
    
@app.route("/login/sensorControl", methods = ['POST'])
def sensor_control():
    """센서 제어 기능"""
    get_json = request.get_json()

    user_control_data:object = Function.sensor_control(get_json)
    
    response_sensor = {}

    if user_control_data.err_state == -1:
        response_sensor["error"] = "700"
    else :
        response_sensor["error"] = "200"
        id_check = Singup.action_check_signup(user_control_data)
        if id_check == 1:
            SensorControl.sensor_control(user_control_data)
        else :
            response_sensor["error"] = "701"
    return json.dumps(response_sensor, ensure_ascii=False), response_sensor["error"]


if __name__ == "__main__":
    # ssl_context = ssl.SSLContext(ssl.PROTOCOL_TLS)
    # ssl_context.load_cert_chain(certfile='certificate/cert.pem', keyfile='certificate/key.pem', password='EMSW')
    # app.run(host='0.0.0.0', port=3000, ssl_context=ssl_context)
    app.run(host='0.0.0.0', port=3000)
    
    # app.run()

