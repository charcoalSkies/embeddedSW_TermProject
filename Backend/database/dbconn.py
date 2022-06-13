import pymysql
import json

class DataBaseConnection()
{
    @staticmethod
    def connection() -> object:
        try:
            conn = pymysql.connect(user = awsDefine.dbAccount, password = awsDefine.dbPassword, host = awsDefine.dbHost,
                                   port = awsDefine.dbPort, database = awsDefine.dbName, autocommit = False)
            log = f'connection to DB'
        except pymysql.Error as error:
            log = f'Error connection to DB : {error}'
        return conn
        
}
