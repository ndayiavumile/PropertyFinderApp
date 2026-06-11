# 🏠 PropertyFinderApp

An Android mobile application that combines **Computer Science and GIS (Geographic Information Systems)** to deliver a smart, map-based property discovery experience.

---

## Overview

PropertyFinderApp allows users to browse, search, and explore property listings through an interactive map interface. It integrates Firebase for authentication and real-time data storage, and the Google Maps API for location-based features — making it a practical demonstration of mobile development, cloud services, and geospatial thinking.

---

## Features

- 🗺️ **Interactive map view** — browse property listings pinned on a live map
- 🔍 **Search and filter** — find properties by location or criteria
- 📋 **Property detail view** — see full information for each listing
- 🔐 **User authentication** — secure sign-in and sign-up via Firebase Auth
- ☁️ **Cloud data storage** — property data stored and synced with Firebase Firestore
- 📱 **Android-native** — built for Android using Java and Android Studio

---

## Tech Stack

| Technology | Use |
|---|---|
| Java | Core application logic |
| Android Studio | Development environment |
| Firebase Authentication | User login and registration |
| Firebase Firestore | Real-time cloud database |
| Google Maps API | Interactive map and location features |

---

## Getting Started

### Prerequisites

- Android Studio (latest stable version)
- A Google account for Firebase and Maps API access
- An Android device or emulator (API level 21+)

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/ndayiavumile/PropertyFinderApp.git
   ```
2. Open the project in **Android Studio**
3. Set up Firebase:
   - Go to the [Firebase Console](https://console.firebase.google.com/)
   - Create a new project and add an Android app
   - Download `google-services.json` and place it in the `app/` directory
   - Enable **Authentication** (Email/Password) and **Firestore** in your Firebase project
4. Add your **Google Maps API key** to `AndroidManifest.xml`:
   ```xml
   <meta-data
       android:name="com.google.android.geo.API_KEY"
       android:value="YOUR_API_KEY_HERE" />
   ```
5. Build and run the project on your device or emulator

---

## Project Structure

```
PropertyFinderApp/
└── PropertyFinder/
    ├── app/
    │   ├── src/main/java/       # Java source files
    │   ├── src/main/res/        # Layouts, drawables, strings
    │   └── AndroidManifest.xml  # App configuration
    └── build.gradle
```

---

## Notes

- This app is a portfolio/demo project — property listings are for demonstration purposes
- Firestore security rules should be configured before any production deployment
- Ensure billing is enabled on your Google Cloud project to use the Maps API

---

## Author

**Katywa Aphelele** — [GitHub](https://github.com/ndayiavumile)
