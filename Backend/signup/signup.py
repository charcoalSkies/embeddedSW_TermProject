""" 회원가입 코드 """
from database.dbquery import DataBaseQuery

class Singup():
    """ 회원가입 클래스"""
    @staticmethod
    def action_signup(user_signup_inform:object) -> int:
        """ 회원가입 함수 """
        return DataBaseQuery.signup_insert_query(user_signup_inform)

    @staticmethod
    def action_check_signup(user_signup_inform:object) -> int:
        """ 회원가입 체크 함수 """
        return DataBaseQuery.signup_check_query(user_signup_inform)
