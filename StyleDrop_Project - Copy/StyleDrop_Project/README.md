# StyleDrop Project

StyleDrop is a Kotlin/Jetpack Compose Android app for managing a wardrobe, creating outfits,
and preparing for online synchronization with an ASP.NET Core API hosted on Sive.Host.

## Structure

- `StyleDrop-Android/` - Android Studio Kotlin/Compose application
- `StyleDrop.Api/` - ASP.NET Core 9 Web API backend
- `docs/` - setup notes

## Important

The backend is configured for MySQL and JWT authentication. The Android app uses Room locally.
The Sive.Host VPS deployment is intentionally not performed by this folder generator.

Before production deployment:
1. Set the MySQL connection string.
2. Set a strong JWT key.
3. Run EF migrations.
4. Publish the API to the VPS.
5. Put Nginx/HTTPS in front of the API.
6. Point the Android Retrofit base URL to the HTTPS API.

Never put database passwords, JWT signing keys, or SSH credentials in the Android application.
