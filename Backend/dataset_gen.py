import numpy as np
import pandas as pd

def generate(n_samples, n_devices, disconnect_prob, n_always_disconnected, n_probabilistically_disconnected, interval_length, means, stds):
    np.random.seed(0)

    # Initialize an empty array for the data
    data = np.empty((n_samples, n_devices))

    # Generate data for each device with its own mean and std
    for i in range(n_devices):
        data[:, i] = np.round(np.random.normal(loc=means[i], scale=stds[i], size=n_samples), 2)
    # Select random devices for always and probabilistic disconnection
    all_devices = np.arange(n_devices)
    np.random.shuffle(all_devices)
    always_disconnected_devices = all_devices[:n_always_disconnected]
    probabilistically_disconnected_devices = all_devices[n_always_disconnected:n_always_disconnected + n_probabilistically_disconnected]

    for i in range(n_samples):
        # Set always disconnected devices to 0
        for j in always_disconnected_devices:
            data[i, j] = 0

        # Randomly disconnect some devices based on probability
        for j in probabilistically_disconnected_devices:
            # Define intervals for disconnection
            if (i // interval_length) % 2 == 0:  # Adjust this condition for the desired interval pattern
                if np.random.rand() < disconnect_prob:
                    data[i, j] = 0

    columns = [f'Device_{i+1}' for i in range(n_devices)]
    synthetic_data = pd.DataFrame(data, columns=columns)
    synthetic_data.to_csv('temp_data.csv', index=False)
    return synthetic_data

if __name__ == "__main__":
    n_samples = 10000
    n_devices = 10
    disconnect_prob = 0.1
    n_always_disconnected = 3
    n_probabilistically_disconnected = 2
    interval_length = 100

    # Define the means and standard deviations for each device
    means = [50, 60, 70, 80, 90, 100, 110, 120, 130, 140]
    stds = [10, 15, 20, 25, 10, 5, 30, 35, 40, 45]

    generate(n_samples, n_devices, disconnect_prob, n_always_disconnected, n_probabilistically_disconnected, interval_length, means, stds)
