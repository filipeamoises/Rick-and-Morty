 <img src="https://upload.wikimedia.org/wikipedia/commons/b/b1/Rick_and_Morty.svg" alt="Rick Morty Logo"/>
 
 
## Authors:

<a href="https://www.instagram.com/filipemoises_/" target="_blank">
  <img src="https://s.gravatar.com/avatar/fdbb8498a65449571683981c1efc1dff?s=400" width="70" align="left">
</a>

**Filipe Moises**

  [![Linkedin](https://img.shields.io/badge/-linkedin-0A66C2?logo=linkedin&style=for-the-badge&logoColor=white)](https://www.linkedin.com/in/filipe-moises-18393378/)
  [![GitHub](https://img.shields.io/badge/-github-E4405F?&logo=github&message=github&style=for-the-badge&logoColor=white&label=filipeamoises)](https://github.com/filipeamoises)


## App Architecture
- Data (for database and remote API)
- Domain (for business logic and models)
- Presentation (for UI logic, with MVVM)
- Multi Module project(common, features and app)


 
## In this app you can
- List all Rick and Morty characters on a infinite scroll cached list 
- Check the details from the character by clicking in some character on list
- List all episodes from Rick and Morty on a infinite scroll cached list

 <img src="images/rickymortyapp.gif" alt="App Demonstration" width="200"/>


## Tests
- Unit tests
- Activity tests (with [Espresso](https://google.github.io/android-testing-support-library/docs/espresso/))(Work in progress)
- Check Memory Leaks with [Leak Canary](https://square.github.io/leakcanary/)

## Under the hood:
- Dependency injection (with [Hilt](http://google.github.io/hilt/))
- Reactive programming with [Kotlin Flows](https://kotlinlang.org/docs/reference/coroutines/flow.html)
- Google Paging 3 library [Paging 3 library](https://developer.android.com/topic/libraries/architecture/paging/v3-overview?) library
- Glide to load and cache images [Glide](https://github.com/bumptech/glide)
- SQLite Database with [Room](https://developer.android.com/jetpack/androidx/releases/room)
- Rest API consume with [Retrofit](https://square.github.io/retrofit/)
- View binding with [Data Binding](https://developer.android.com/topic/libraries/data-binding)


# Getting started

### Steps 
1. Download this repository extract 
2. Open the project folder on Android Studio
3. On `app/build.gradle`, update the dependencies Android Studio suggests
4. Run the project 

And you're ready to see all Ricky and Morty informations.


# More
If you want to know more please get in touch filipe.a.moises@gmail.com
