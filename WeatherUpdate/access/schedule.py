from apscheduler.schedulers.background import BlockingScheduler
from access.SyncWeather import SyncWeather

class Scheduler():
    def action_schedule() -> None: 
        sched = BlockingScheduler()
        sched.add_job(SyncWeather.action,'interval', minutes=30)
        sched.start()