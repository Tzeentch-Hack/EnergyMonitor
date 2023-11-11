import numpy as np
import pandas as pd
from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error
from keras.models import Sequential, load_model
from keras.layers import LSTM, Dense
import os
import joblib

def load_and_scale_data(file_path):
    try:
        data = pd.read_csv(file_path)
        print('Data shape:', data.shape)
        scaler = StandardScaler()
        return scaler.fit_transform(data), scaler
    except FileNotFoundError:
        print("File not found.")
        return None, None

def create_sequences(data, n_steps):
    X, y = [], []
    for i in range(len(data)):
        end_ix = i + n_steps
        if end_ix > len(data)-1:
            break
        seq_x, seq_y = data[i:end_ix], data[end_ix]
        X.append(seq_x)
        y.append(seq_y)
    return np.array(X), np.array(y)

def build_and_train_model(X_train, y_train, X_val, y_val, n_steps, n_features, epochs=5):
    model = Sequential()
    model.add(LSTM(50, activation='relu', input_shape=(n_steps, n_features)))
    model.add(Dense(10))
    model.compile(optimizer='adam', loss='mean_squared_error')

    model.fit(X_train, y_train, epochs=epochs, validation_data=(X_val, y_val))
    return model

def save_model_and_scaler(model, scaler, model_path, scaler_path):
    model.save(model_path)
    joblib.dump(scaler, scaler_path)

def load_model_and_scaler(model_path, scaler_path):
    model = load_model(model_path)
    scaler = joblib.load(scaler_path)
    return model, scaler

def predict_single_window(model, scaler, window_data):
    window_scaled = scaler.transform(window_data)
    window_reshaped = np.array([window_scaled])
    prediction = model.predict(window_reshaped)
    prediction_rescaled = scaler.inverse_transform(prediction)
    return prediction_rescaled

def format_predictions(predictions):
    return [round(float(num),1) for num in predictions[0]]

def train_and_save_model(data_path, model_path, scaler_path, n_steps, test_size=0.2, random_state=42, epochs=5):
    data_scaled, scaler = load_and_scale_data(data_path)
    if data_scaled is not None:
        X, y = create_sequences(data_scaled, n_steps)
        X_train, X_val, y_train, y_val = train_test_split(X, y, test_size=test_size, random_state=random_state)
        model = build_and_train_model(X_train, y_train, X_val, y_val, n_steps, X.shape[2], epochs)
        save_model_and_scaler(model, scaler, model_path, scaler_path)

if __name__ == "__main__":
    train_needed = False
    data_path = 'data/temp_data.csv'
    model_path = 'models/prediction_model.h5'
    scaler_path = 'models/prediction_scaler.pkl'
    n_steps = 5

    if train_needed:
        train_and_save_model(data_path, model_path, scaler_path, n_steps)
    else:
        model, scaler = load_model_and_scaler(model_path, scaler_path)
        new_window_data = pd.read_csv(data_path)[-n_steps:].values.tolist()
        print('new_window_data', new_window_data, type(new_window_data))
        prediction = predict_single_window(model, scaler, new_window_data)
        print('Prediction:', format_predictions(prediction))
