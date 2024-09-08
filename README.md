# Notes App

A simple and intuitive notes app for Android built with Kotlin. This application leverages Firestore as its database to perform CRUD (Create, Read, Update, Delete) operations on notes.

## Features

- **Create Notes**: Easily add new notes with titles and content.
- **Read Notes**: View a list of all notes with their titles and content.
- **Update Notes**: Edit existing notes to update their titles and content.
- **Delete Notes**: Remove notes from the list.

## Technologies Used

- **Kotlin**: Primary language for Android development.
- **Firestore**: NoSQL cloud database from Firebase for storing and syncing notes.
- **Android SDK**: Core framework for building Android applications.

- ### Prerequisites

1. **Android Studio**: Ensure you have Android Studio installed on your machine.
2. **Firebase Account**: Create a Firebase account if you don’t have one and set up a new project.

### Setup

1. **Clone the Repository**

   ```bash
   git clone https://github.com/kepelivi/notes-app.git
   cd notes-app
   
2. **Open the Project**

   Open the project in Android Studio by selecting `File > Open` and navigating to the project directory.

3. **Configure Firebase**

   - Go to the [Firebase Console](https://console.firebase.google.com/).
   - Create a new project or use an existing one.
   - Add your Android app to the Firebase project and follow the setup instructions.
   - Download the `google-services.json` file and place it in the `app` directory of your project.

4. **Add Firebase Dependencies**

   Ensure the following dependencies are added to your `build.gradle` files:

   **Project-level `build.gradle`:**

   ```gradle
   classpath 'com.google.gms:google-services:4.3.15' // Check for the latest version
   ```
   
   **App-level `build.gradle`:**

   ```gradle
   implementation 'com.google.firebase:firebase-firestore-ktx:24.8.1' // Check for the latest version
   apply plugin: 'com.google.gms.google-services'

5. **Sync Project**

   Click `Sync Now` in Android Studio to synchronize your project with the Firebase services.

6. **Run the Application**

   Connect an Android device or use an emulator, then run the application from Android Studio by clicking the `Run` button.
