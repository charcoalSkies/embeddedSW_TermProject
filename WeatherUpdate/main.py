""" 날씨 동기화 서버 """

from access.schedule import Scheduler
from access.SyncWeather import SyncWeather
if __name__ == "__main__":
    Scheduler.action_schedule()

   