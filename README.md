# Wearable Sensor Vest Mobile App

## Overview
This mobile application is part of a senior-year project featuring a wearable sensor vest. The vest collects movement and activity data, which is processed by a machine learning model running on a central Raspberry Pi server. The mobile app connects to the server via sockets to provide real-time activity monitoring, historical data visualization, and alert notifications in case of anomalies or medical emergencies.

## Features
- **Real-time Activity Monitoring**: Displays the current detected activity of the user based on ML model predictions.
- **Alert System**: Sends notifications for anomalies or medical alerts via socket-based commands.
- **Historical Activity Graph**: Allows users to view past activity trends.
- **Sensor Data View**: Technicians can inspect individual sensor readings for debugging.
- **User Roles**:
  - **Caretaker User**: Receives alerts and must log in via the server IP.
  - **Technician User**: Has access to individual sensor readings and debugging tools.

## Tech Stack
- **Frontend & Backend**: Kotlin (Android) with Jetpack Compose
- **Architecture**: Model-View-Controller (MVC)
- **Server**: Raspberry Pi running a machine learning model
- **Communication**: Socket-based connection between mobile app and server

## Installation & Setup
1. Clone the repository:
   ```sh
   git clone https://github.com/barisuu/HealthActivityApp.git
   cd HealthActivityApp
   ```
2. Open the project in Android Studio.
3. Configure the server IP in the app settings.
4. Build and run the app on an Android device.

## Usage
1. Log in with the appropriate user role (Caretaker/Technician).
2. Monitor the detected activity in real time.
3. Receive alerts in case of anomalies.
4. View historical activity data and individual sensor readings (Technician only).

## Future Improvements
- Enhance UI/UX for better user experience.
- Improve ML model accuracy and response time.

