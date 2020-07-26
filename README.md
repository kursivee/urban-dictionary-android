# Urban Dictionary App
Utilizes urban dictionary API to lookup word definitions

## Technology
### Code Analysis
- [ktlint](https://github.com/pinterest/ktlint) - kotlin linter
- [ktlint-gradle](https://github.com/jlleitschuh/ktlint-gradle) - gradle plugin that provides ktlint tasks
- [detekt](https://github.com/detekt/detekt) - code analysis tool

Run with `./gradlew check`

### Unit Testing
- [Junit](https://junit.org/junit4/) - unit testing library
- [kotlinx-coroutines-test](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/) - used for `runBlockingTest`
- [MockK](https://mockk.io/) - Kotlin mocking library