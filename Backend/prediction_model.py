import numpy as np
import pandas as pd
from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error
from keras.models import Sequential
from keras.layers import LSTM, Dense
import os

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

def format_predictions(predictions):
    return np.array([["{:.1f}".format(num) for num in row] for row in predictions])

if __name__ == "__main__":
    os.environ['CUDA_VISIBLE_DEVICES'] = '-1'
    data_path = 'data/temp_data.csv'
    n_steps = 5

    data_scaled, scaler = load_and_scale_data(data_path)
    if data_scaled is not None:
        X, y = create_sequences(data_scaled, n_steps)
        X_train, X_val, y_train, y_val = train_test_split(X, y, test_size=0.2, random_state=42)

        model = build_and_train_model(X_train, y_train, X_val, y_val, n_steps, X.shape[2])

        predictions = model.predict(X_val)
        print('Predictions before scaling:', format_predictions(predictions))
        predictions = scaler.inverse_transform(predictions)
        print('Predictions after scaling:', format_predictions(predictions))

        mse = mean_squared_error(y_val, predictions)
        print(f'Mean Squared Error: {mse}')
