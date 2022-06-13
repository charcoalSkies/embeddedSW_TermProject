from apscheduler.schedulers.background import BlockingScheduler
from api.weatherApi import API

def action_schedule() -> None: 
    sched = BlockingScheduler()
    sched.add_job(API.request_weather,'interval', seconds=10, id='test',args=[''])
    sched.start()