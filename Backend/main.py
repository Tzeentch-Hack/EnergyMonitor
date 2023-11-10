from typing import Annotated, List

from fastapi import Depends, FastAPI, File, UploadFile, Request, HTTPException
from fastapi.responses import FileResponse
import uvicorn
import models
import authorization
import os

app = FastAPI()
app.include_router(authorization.router)


@app.get("/", tags=["Help generation"])
def root():
    return {"message": "Energy monitor"}
