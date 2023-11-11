import pandas as pd
import numpy as np
from sklearn.preprocessing import StandardScaler
from keras.models import Sequential, load_model
from keras.layers import Dense, LSTM
import joblib

# Constants
WINDOW_SIZE = 5
THRESHOLD = 0.1

def create_windowed_dataset(data, window_size):
    dataset = []
    for i in range(len(data) - window_size):
        window = data[i:i + window_size]
        dataset.append(window)
    return np.array(dataset)

def train_and_save_model(data_path, model_path, scaler_path):
    data = pd.read_csv(data_path)
    scaler = StandardScaler()
    data_normalized = scaler.fit_transform(data)

    windowed_data = create_windowed_dataset(data_normalized, WINDOW_SIZE)
    if windowed_data.shape[0] == 0:
        raise ValueError("Insufficient data for training.")

    model = Sequential()
    model.add(LSTM(32, activation='relu', input_shape=(WINDOW_SIZE, data.shape[1]), return_sequences=True))
    model.add(LSTM(16, activation='relu', return_sequences=True))
    model.add(Dense(data.shape[1]))

    model.compile(optimizer='adam', loss='mse')
    model.fit(windowed_data, windowed_data, epochs=5, batch_size=32)

    model.save(model_path)
    joblib.dump(scaler, scaler_path)

def load_model_and_scaler(model_path, scaler_path):
    model = load_model(model_path)
    scaler = joblib.load(scaler_path)
    return model, scaler

# Function to prepare a single window of data
def create_single_window(data, window_size):
    if len(data) < window_size:
        raise ValueError("Data length is less than window size.")
    return np.array(data[-window_size:])

def detect_anomaly_in_window(window, model, scaler, threshold):
    window_normalized = np.expand_dims(scaler.transform(window), axis=0)
    reconstructed = model.predict(window_normalized)
    reconstruction_error = np.mean(np.abs(window_normalized - reconstructed), axis=1)
    return reconstruction_error > threshold

if __name__ == "__main__":
    #train_and_save_model('data/temp_data.csv', 'anomaly_detection.h5', 'anomaly_detection_scaler.pkl')

    loaded_model, loaded_scaler = load_model_and_scaler('models/anomaly_detection.h5',
                                                        'models/anomaly_detection_scaler.pkl')

    new_data_chunk = pd.read_csv('data/temp_data.csv')
    print('new_data_chunk', new_data_chunk)
    if len(new_data_chunk) >= WINDOW_SIZE:
        window = create_single_window(new_data_chunk.values, WINDOW_SIZE)
        anomaly_detected = detect_anomaly_in_window(window, loaded_model, loaded_scaler, THRESHOLD)
        print('Anomaly Detected:', anomaly_detected[0])
    else:
        print("Insufficient data for anomaly detection.")
