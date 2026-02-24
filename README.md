# Linear Interpolation Calculator

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
