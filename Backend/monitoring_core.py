import models
import database
import datetime


def calculate(consumers_list: list[models.Consumer], interval: int):
    for consumer in consumers_list:
        consumer_from_db = database.db.query(database.DeviceInDBSQL).filter_by(deviceID=consumer.consumer_id).first()
        if consumer_from_db:
            consumer_from_db.username = consumer.username
            consumer_from_db.enabled = consumer.enabled
            consumer_from_db.deviceName = consumer_from_db.deviceName
            consumer_from_db.workingTime = consumer_from_db.workingTime
            consumer_from_db.wattConsumption = consumer.watt_consumption
            consumer_from_db.sum_consumption = consumer.sum_consumption
            consumer_from_db.consumptionSummary = consumer.consumption_summary
            database.db.merge(consumer_from_db)
        else:
            new_device = database.DeviceInDBSQL(
                username=consumer.username,
                deviceID=consumer.consumer_id,
                enabled=consumer.enabled,
                deviceName=consumer.device_name,
                startedTime=consumer.started_time,
                workingTime=consumer.working_time,
                wattConsumption=consumer.watt_consumption,
                sum_consumption=consumer.sum_consumption,
                consumptionSummary=consumer.consumption_summary
            )
            database.db.add(new_device)
        database.db.commit()


def get_all_consumers(username: str):
    consumers = database.db.query(database.DeviceInDBSQL).filter_by(username=username).all()
    converted_сonsumers = []
    for consumer_sql in consumers:
        converted_сonsumers.append(convert_consumer(consumer_sql))
    return converted_сonsumers


def convert_consumer(consumer_from_sql: database.DeviceInDBSQL):
    converted_consumer = models.Consumer(username=consumer_from_sql.username,
                                         consumer_id=consumer_from_sql.deviceID,
                                         enabled=consumer_from_sql.enabled,
                                         device_name=consumer_from_sql.deviceName,
                                         started_time=consumer_from_sql.startedTime,
                                         working_time=consumer_from_sql.workingTime,
                                         watt_consumption=consumer_from_sql.wattConsumption,
                                         sum_consumption=consumer_from_sql.sum_consumption,
                                         consumption_summary=consumer_from_sql.consumptionSummary)
    return converted_consumer
