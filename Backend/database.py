import sqlalchemy.sql.sqltypes
from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from sqlalchemy import Column, Integer, String, BOOLEAN, JSON


SQLALCHEMY_DATABASE_URL = "sqlite:///./databases/users.db"

engine = create_engine(
    SQLALCHEMY_DATABASE_URL, connect_args={"check_same_thread": False}
)

Base = sqlalchemy.orm.declarative_base()


class UserInDBSQL(Base):
    __tablename__ = "users"

    id = Column(Integer, primary_key=True, index=True)
    username = Column(String)
    disabled = Column(BOOLEAN)
    hashed_password = Column(String)


Base.metadata.create_all(bind=engine)
SessionLocal = sessionmaker(autoflush=False, bind=engine)
db = SessionLocal()

app = None
