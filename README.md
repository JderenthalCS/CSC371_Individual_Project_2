**CSC 371 – Individual Project 2**
**Product:** CodeIQ

---

### Overview

This Android application, built with Jetpack Compose, fulfills all project requirements for the CSC 371 Individual Project 2 assignment. It demonstrates proper use of navigation, data validation, persistent storage, and user interface design principles.

---

### Features

* **Splash Screen:** Displays app name and logo before navigating to authentication.
* **Authentication Flow:**

  * Registration screen collects First Name, Family Name, Date of Birth, Email, and Password.
  * Validates inputs with regex and length checks.
  * Stores user data locally with SharedPreferences via `UserPrefs`.
  * Login verifies against stored data.
* **Rules Screen:** Explains how the quiz works and provides options to start the quiz or view history.
* **Quiz Screen:**

  * Five questions total including single and multiple-select types.
  * Requires confirmation before recording answers.
  * Includes a 20-second countdown timer per question.
  * Automatically records results and advances on timeout.
* **Results Screen:**

  * Displays score, progress bar, feedback message, high score, and recent attempts.
  * Options to retry or return home.
* **Persistent Storage:** Saves scores and user history using key-value storage.
* **Design:** Implemented with Material 3 components, consistent spacing, and responsive layouts for phone and tablet modes.

---

### Testing

* Tested on Pixel 7 (phone) and Pixel C (tablet) emulators.
* Verified in portrait and landscape orientations.
* Confirmed data persistence between sessions.

---

### Tools Used

* Kotlin
* Jetpack Compose (Material 3)
* Android Studio Hedgehog
* SharedPreferences for local data storage

---

### How to Run

1. Clone the repository.
2. Open in Android Studio.
3. Build and run on an emulator or physical device.
4. Register a new user, log in, read the rules, and start the quiz.

---

