 <img src="https://upload.wikimedia.org/wikipedia/commons/b/b1/Rick_and_Morty.svg" alt="Rick Morty Logo"/>


## App Architecture
- Data (for database and remote API)
- Domain (for business logic and models)
- Presentation (for UI logic, with MVVM)
- Multi Module project(common, features and app)

    -app
    -buildSrc 
    -common
    -feature
     --character
     --episodes

 
## In this app you can
- List all Rick and Morty characters on a infinite scroll cached list 
- Check the details from the character by clicking in some character on list
- List all episodes from Rick and Morty on a infinite scroll cached list

 <img src="images/rickymortyapp.gif" alt="App Demonstration"/>


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
1. Download this repository extract and open the folder
2. On `app/build.gradle`, update the dependencies Android Studio suggests
3. Run the project 

And you're ready to see all Ricky and Morty informations.


# More
If you want to know more get in touch filipe.a.moises@gmail.com
