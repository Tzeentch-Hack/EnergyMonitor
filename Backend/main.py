from typing import Annotated, List

from fastapi import Depends, FastAPI, File, UploadFile, Request, HTTPException
from fastapi.responses import FileResponse
import uvicorn
import models
import authorization
import os

import monitoring_core

app = FastAPI()
app.include_router(authorization.router)


@app.get("/", tags=["Main info"])
def root():
    return {"message": "Energy monitor"}


@app.post("/device_statistics", tags=["Energy"])
def post_device_statistics(current_user: Annotated[models.User, Depends(authorization.get_current_active_user)],
                           consumers_list: list[models.Consumer],
                           interval: int):
    monitoring_core.calculate(consumers_list)
    return {"message": "Devices have been updated"}


@app.get("/device_statistics", tags=["Energy"], response_model=list[models.Consumer])
def get_device_statistics(current_user: Annotated[models.User, Depends(authorization.get_current_active_user)]):
    return monitoring_core.get_all_consumers(current_user.username)


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8080)
