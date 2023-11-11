from typing import Annotated, List, Any

from fastapi import Depends, FastAPI, File, UploadFile, Request, HTTPException
from fastapi.responses import FileResponse
import uvicorn
import models
import authorization
import os
from pydantic import BaseModel
import prediction_model
import anomaly_model

import monitoring_core
from fastapi import Body, FastAPI

app = FastAPI()
app.include_router(authorization.router)

model_prediction, scaler_prediction = prediction_model.load_model_and_scaler('models/prediction_model.h5',
                                                                             'models/prediction_scaler.pkl')
model_anomaly, scaler_anomaly = anomaly_model.load_model_and_scaler('models/anomaly_detection.h5',
                                                                    'models/anomaly_detection_scaler.pkl')


# Endpoint for predict_single_window in prediction_model.py
@app.get("/predict-window")
def predict_window(data: models.PredictWindowData):
    prediction = prediction_model.predict_single_window(model_prediction, model_anomaly, data.data)
    return {"prediction": prediction}


@app.get("/detect-anomaly")
def detect_anomaly(data: models.AnomalyDetectionData):
    anomaly_detected = anomaly_model.detect_anomaly_in_window(data.data, model_anomaly, scaler_anomaly,
                                                              data.THRESHOLD)
    return {"anomaly_detected": anomaly_detected}


@app.get("/", tags=["Main info"])
def root():
    return {"message": "Energy monitor"}


@app.post("/device_statistics", tags=["Energy"])
def post_device_statistics(interval: int, list_consumers: models.ConsumerList):
    print(list_consumers.data)
    monitoring_core.calculate(list_consumers.data, interval)
    return {"message": "Devices have been updated"}


@app.get("/device_statistics", tags=["Energy"], response_model=list[models.Consumer])
def get_device_statistics(current_user: Annotated[models.User, Depends(authorization.get_current_active_user)]):
    return monitoring_core.get_all_consumers(current_user.username)


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8080)

