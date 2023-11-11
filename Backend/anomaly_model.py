import pandas as pd
import numpy as np
from sklearn.preprocessing import StandardScaler
from keras.models import Sequential
from keras.layers import Dense, LSTM
import joblib

WINDOW_SIZE = 5
THRESHOLD = 0.1

def create_windowed_dataset(data, window_size):
    dataset = []
    for i in range(len(data) - window_size):
        window = data[i:i + window_size]
        dataset.append(window)
    return np.array(dataset)

def detect_anomalies(new_data, model, scaler, threshold):
    new_data_normalized = scaler.transform(new_data)
    new_windowed_data = create_windowed_dataset(new_data_normalized, WINDOW_SIZE)
    reconstructed = model.predict(new_windowed_data)
    reconstruction_error = np.mean(np.abs(new_windowed_data - reconstructed), axis=1)
    return reconstruction_error > threshold

def train_model(windowed_data, input_shape):
    model = Sequential()
    model.add(LSTM(32, activation='relu', input_shape=input_shape, return_sequences=True))
    model.add(LSTM(16, activation='relu', return_sequences=True))
    model.add(Dense(input_shape[1]))

    model.compile(optimizer='adam', loss='mse')
    model.fit(windowed_data, windowed_data, epochs=5, batch_size=32)

    return model

# Main script logic
if __name__ == "__main__":
    data = pd.read_csv('data/temp_data.csv')
    scaler = StandardScaler()
    data_normalized = scaler.fit_transform(data)

    windowed_data = create_windowed_dataset(data_normalized, WINDOW_SIZE)

    model = train_model(windowed_data, (WINDOW_SIZE, data.shape[1]))

    model.save('anomaly_detection.h5')
    joblib.dump(scaler, 'anomaly_detection_scaler.pkl')

    # Anomaly detection
    anomalies = detect_anomalies(data, model, scaler, THRESHOLD)
    print('Anomalies:', anomalies)
