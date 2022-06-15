""" 로그인 코드 """
from database.dbquery import DataBaseQuery

class Login():
    """ 로그인 클래스"""

    @staticmethod
    def action_login(user_login_inform:object) -> int:
        """ 로그인 함수 """
        db_pwd = DataBaseQuery.login_query(user_login_inform)
        if db_pwd == user_login_inform.user_pwd:
            result = 0
        else :
            result = -1
        
        return result
