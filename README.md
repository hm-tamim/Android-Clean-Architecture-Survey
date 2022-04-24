# Android Clean Architecture - Survey Application

A demo project for those who want to learn Clean Architecture in Android. Hope it helps!

## Build Guide:

Before running the application, add client id and secret in **local.properties** file like
this.

```
CLIENT_ID = "6GbE..."
CLIENT_SECRET = "_ayfI..."
```

# Project Overview

- The application was written in Kotlin
- Multi-module Clean Architecture (MVVM in presentation layer)
- Android Jetpack libraries were utilized
- Dagger Hilt was used for dependency injection
- Navigation graph was used to maintain single activity architecture
- Room persistence ORM was used to cache API responses
- Coroutine for threading, asynchronous and database actions
- Retrofit okhttp client was used for networking
- Response handler with Sealed Class
- API response to Entity Mapper
- Automatic Token Refresh authenticator
- EncryptedSharedPreferences was used to store auth tokens securely
- Secured client id and secret with secrets_gradle_plugin
- Glide was used to load images
- Android animations were used
- ViewPager2 for left/right swipe
- Dynamic navigation indicator list (bullets)
- Shimmer skeleton during loading
- Junit4, Robolectric, MockK were used for testing
