from pydantic import BaseModel


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
    username: str
    consumer_id: str
    enabled: bool
    device_name: str
    started_time: str
    working_time: str
    watt_consumption: str
    sum_consumption: str
    consumption_summary: str


class UserInDB(User):
    hashed_password: str
