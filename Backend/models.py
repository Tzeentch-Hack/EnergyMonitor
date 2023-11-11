from pydantic import BaseModel
from typing import List


class Token(BaseModel):
    access_token: str
    token_type: str


class TokenData(BaseModel):
    username: str | None = None


class User(BaseModel):
    username: str
    disabled: bool | None = None


class UserInDB(User):
    hashed_password: str


class Consumer(BaseModel):
    username: str | None = None
    consumer_id: str
    enabled: str | None = None
    device_name: str | None = None
    started_time: str | None = None
    working_time: str | None = None
    watt_consumption: str | None = None
    sum_consumption: str | None = None
    consumption_summary: str | None = None


class ConsumerList(BaseModel):
    data: List[Consumer]


class TestModel(BaseModel):
    test_data: int


class UserInDB(User):
    hashed_password: str


class PredictWindowData(BaseModel):
    data: List[List[float]]


class AnomalyDetectionData(BaseModel):
    data: List[List[float]]
    THRESHOLD: float
