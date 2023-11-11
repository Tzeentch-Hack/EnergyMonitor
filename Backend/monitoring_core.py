import models
import database
import datetime

tarif = 195


def calculate(consumers_list: list[models.Consumer], interval: int):
    for consumer in consumers_list:
        consumer_from_db = database.db.query(database.DeviceInDBSQL).filter_by(deviceID=consumer.consumer_id).first()
        if consumer_from_db:
            consumer_from_db.username = consumer.username
            consumer_from_db.enabled = consumer.enabled
            consumer_from_db.deviceName = consumer_from_db.deviceName
            consumer_from_db.workingTime = consumer_from_db.workingTime
            watt = float(consumer.watt_consumption.replace(',', '.'))
            working_time_float = float(consumer_from_db.workingTime.replace(',', '.'))
            summary_consumption = working_time_float / 1000 * watt

            consumer_from_db.wattConsumption = consumer.watt_consumption
            consumer_from_db.sum_consumption = summary_consumption * tarif / 3600 / 1000
            consumer_from_db.consumptionSummary = str(summary_consumption)
            consumer.sum_consumption = consumer_from_db.sum_consumption
            consumer.consumption_summary = consumer_from_db.consumptionSummary
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


def disable_consumer(device_id: str):
    consumer_from_db = database.db.query(database.DeviceInDBSQL).filter_by(deviceID=device_id).first()
    if consumer_from_db:
        consumer_from_db.enabled = "False"
        consumer_from_db.wattConsumption = 0
        database.db.merge(consumer_from_db)
        database.db.commit()


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


def save_history(consumers_list: list[models.Consumer]):
    for consumer in consumers_list:
        watt = float(consumer.watt_consumption.replace(',', '.'))
        history_stamp = database.DeviceWattHistory(
            username=consumer.username,
            deviceID=consumer.consumer_id,
            wattConsumption=consumer.watt_consumption,
            sumConsumption=str(watt * tarif / 3600 / 1000),
            dateTime=datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
        )
        database.db.add(history_stamp)
        database.db.commit()


def get_history(username: str):
    history = database.db.query(database.DeviceWattHistory).filter_by(username=username).all()
    result = []
    for raw in history:
        converted_raw = models.ConsumptionHistoryRaw(
            username=raw.username,
            device_id=raw.deviceID,
            watt_consumption=raw.wattConsumption,
            sum_consumption=raw.sumConsumption,
            date_time=raw.dateTime
        )
        result.append(converted_raw)
    return result


def get_history_by_device_id(device_id: str):
    history = database.db.query(database.DeviceWattHistory).filter_by(deviceID=device_id).all()
    result = []
    for raw in history:
        converted_raw = models.ConsumptionHistoryRaw(
            username=raw.username,
            device_id=raw.deviceID,
            watt_consumption=raw.wattConsumption,
            sum_consumption=raw.sumConsumption,
            date_time=raw.dateTime
        )
        result.append(converted_raw)
    return result
