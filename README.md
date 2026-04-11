# Linear Interpolation Calculator

An Android utility for performing linear interpolation and extrapolation calculations.

## 🛠 Technical Stack

- **UI Framework:** Jetpack Compose for a fully declarative UI.
- **Language:** Kotlin with Coroutines and Flows.
- **Dependency Injection:** Hilt
- **Architecture:** MVVM (Model-View-ViewModel)
- **Navigation:** AndroidX Navigation 3 with type-safe route definitions for state-driven navigation.

## 🏗 Project Structure

The project follows a layered architecture with internal grouping by feature:

```text
├── app/
│   ├── src/main/kotlin/com/dimitriskatsikas/interpolator/
│   │   ├── di/                 # Dependency injection configuration
│   │   ├── domain/             # Core interpolation logic and calculation engine
│   │   ├── ui/                 # UI layers and state management
│   │   │   ├── calculator/     # Logic and UI for interpolation calculations
│   │   │   ├── info/           # Logic and UI for information and settings
│   │   │   └── theme/          # App-wide design system and Compose themes
│   │   ├── AppNavigation.kt    # Navigation 3 setup
│   │   └── MainActivity.kt     # App entry point
```

## 🚀 Key Engineering Highlights

- **Unidirectional Data Flow (UDF):** Predictable state management using `StateFlow` for persistent UI state and `Channels` for one-time side-effects (toasts, navigation).
- **Clean Architecture Principles:** Clear separation of the calculation engine (domain) from the presentation layer (UI) for high maintainability.
- **Unit Testing:** Comprehensive test coverage for the interpolation logic and ViewModels to ensure mathematical precision.

## 🛠 Setup & AdMob Configuration

This project uses AdMob for advertisements. For security reasons, production AdMob IDs are not stored in the repository.

### For Local Development
The project is configured to use **official Google Test IDs** by default. You can clone and run the app immediately without any extra setup.

### For Production/Release Builds
If you want to build the release version with your own AdMob production keys, follow these steps:

1. Open (or create) the `local.properties` file in the root directory of the project.
2. Add your production keys as follows:
   ```properties
   # Admob keys
   ADMOB_APP_ID=ca-app-pub-xxxxxxxxxxxxxxxx~xxxxxxxxxx
   BANNER_AD_UNIT_ID=ca-app-pub-xxxxxxxxxxxxxxxx/xxxxxxxxxx
   ```
