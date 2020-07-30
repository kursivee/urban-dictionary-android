# Urban Dictionary App
Utilizes urban dictionary API to lookup word definitions

## Technology
### Presentation
- [View Binding](https://developer.android.com/topic/libraries/view-binding) - generates binding classes which hold reference to XML id'd elements
- [List Adapter](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/ListAdapter) - Convenience wrapper that provides default behavior for item access and counting
- [Recycler View](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView) - Displays lists of elements
- [Swipe Refresh Layout](https://developer.android.com/training/swipe/add-swipe-interface) - Library that supports swipe to refresh on lists
- [Diff Util](https://developer.android.com/reference/androidx/recyclerview/widget/DiffUtil) - Utility class that effeciently calculates differences between two lists
- [Spannable String Builder](https://developer.android.com/reference/android/text/SpannableStringBuilder) - Convenience builder for spannable
- [Search View](https://developer.android.com/training/search/setup) - Adds search interface in app bar
- [Hilt](https://dagger.dev/hilt/) - Dependency Injection library based on dagger
- [Jetpack Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) - Navigation library
- [SafeArgs](https://developer.android.com/guide/navigation/navigation-getting-started#ensure_type-safety_by_using_safe_args) - Type safe navigation

### Networking
- [Retrofit](https://square.github.io/retrofit/) - Rest Client
- [okHttp](https://github.com/square/okhttp) - HTTP Client
- [moshi converter](https://github.com/square/retrofit/tree/master/retrofit-converters/moshi) - JSON Serialization library used for retrofit

### Utility
- [arrow-kt](https://github.com/arrow-kt/arrow) - Functional companion to kotlin std lib. But only using it for Either :D
- [Leak Canary](https://square.github.io/leakcanary/) - Memory leak detection library
- [Timber](https://github.com/JakeWharton/timber) - Logging utility class built on top of Android Log

### Code Analysis
- [ktlint](https://github.com/pinterest/ktlint) - kotlin linter
- [ktlint-gradle](https://github.com/jlleitschuh/ktlint-gradle) - gradle plugin that provides ktlint tasks
- [detekt](https://github.com/detekt/detekt) - code analysis tool

Run with `./gradlew check`

Auto ktlint with `./gradlew ktlintFormat`

### Testing
- [Junit](https://junit.org/junit4/) - unit testing library
- [kotlinx-coroutines-test](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/) - used for `runBlockingTest`
- [MockK](https://mockk.io/) - Kotlin mocking library
- [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) - webserver for unit testing retrofit
- [Espresso](https://developer.android.com/training/testing/espresso) - integration testing library
- [Espresso with Hilt](https://developer.android.com/training/dependency-injection/hilt-testing) - espresso with hilt support
