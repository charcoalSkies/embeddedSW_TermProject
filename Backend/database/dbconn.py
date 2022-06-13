""" db connection 생성 코드 """
import pymysql
from database.define import DataBaseDefine

class DataBaseConnection():
    """ DataBase Connection 생성 클래스 """
    @staticmethod
    def connection() -> object:
        """ DataBase Connection 생성 함수 """
        try:
            conn = pymysql.connect(user = DataBaseDefine.user, 
                                    password = DataBaseDefine.passwd, 
                                    host = DataBaseDefine.host,
                                    port = DataBaseDefine.port, 
                                    database = DataBaseDefine.database, 
                                    autocommit = DataBaseDefine.autocommit)
            print("connected")
        except pymysql.Error as err:
            print(f"Error connection to MariaDB : {err}")
            return err
        return conn
