""" DataBase 쿼리 실행 코드 """

import pymysql
from database.dbconn import DataBaseConnection

class DataBaseQuery(DataBaseConnection):
    """ DataBase 쿼리 실행 클래스"""

    @staticmethod
    def signup_insert_query(user_signup_inform:object) -> None:
        """ 회원가입 DB insert 함수 """
        connection:object = DataBaseConnection.connection()
        cursor = connection.cursor()
        sql_query = 'INSERT INTO EMSW.users (user_id, user_pwd, user_email) VALUES (%s, %s, %s);'
        cursor.execute(sql_query, (user_signup_inform.user_id, user_signup_inform.user_pwd, user_signup_inform.user_email))
        connection.commit()
        print(cursor._last_executed)
        connection.close()

    @staticmethod
    def signup_check_query(user_singup_inform:object) -> int:
        """ 회원가입 DB 체크 함수 """

        connection:object = DataBaseConnection.connection()
        cursor = connection.cursor()
        sql_query = 'SELECT user_id FROM EMSW.users where user_id = %s;'
        try:
            cursor.execute(sql_query,(user_singup_inform.user_id))
            data_exist = list(cursor)[0]

            print(data_exist)

        except Exception :
            return -1
        finally:
            connection.close()
        return 1
        
        