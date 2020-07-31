package com.kursivee.urbandictionary.search

import android.view.KeyEvent
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.kursivee.urbandictionary.MainActivity
import com.kursivee.urbandictionary.R
import com.kursivee.urbandictionary.common.di.ApplicationNetworkModule
import com.kursivee.urbandictionary.common.network.HttpClientProvider
import com.kursivee.urbandictionary.search.presentation.SearchFragment
import com.kursivee.util.recyclerview.RecyclerViewItemCountAssertion
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.internal.util.io.IOUtil
import retrofit2.Retrofit
import javax.inject.Singleton

@HiltAndroidTest
@UninstallModules(ApplicationNetworkModule::class)
class SearchIntegrationTest {

    @Module
    @InstallIn(ApplicationComponent::class)
    class TestApplicationNetworkModule {
        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return HttpClientProvider.provideRetrofit(
                "http://localhost:8080/",
                okHttpClient
            )
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient {
            return HttpClientProvider.provideOkHttpClient()
        }
    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val mockWebServer = MockWebServer()

    @Before
    fun before() {
        mockWebServer.start(8080)
    }

    @After
    fun after() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_hasSearchInput() {
        val resultsResponse = InstrumentationRegistry.getInstrumentation().context.assets.open("GetResultsResponse.json")
        val resultsResponseString = IOUtil.readLines(resultsResponse).joinToString("\n")
        val mockResultsResponse = MockResponse()
            .setResponseCode(200)
            .setBody(resultsResponseString)
        mockWebServer.enqueue(mockResultsResponse)
        val file = InstrumentationRegistry.getInstrumentation().context.assets.open("AutoCompleteResultResponse.json")
        val s = IOUtil.readLines(file).joinToString("\n")
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(s)
        mockWebServer.enqueue(mockResponse)
        mockWebServer.enqueue(mockResponse)
        onView(withId(R.id.search_menu)).perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("a"))
        // Using Thread.sleep here because I couldn't get IdlingResource working with debouncer
        Thread.sleep(SearchFragment.DEBOUNCE_DELAY + 100L)
        onView(withId(R.id.rv_autocomplete)).check(RecyclerViewItemCountAssertion(not(0)))
    }

    @Test
    fun test_hasSearchInputThenClear() {
        val resultsResponse = InstrumentationRegistry.getInstrumentation().context.assets.open("GetResultsResponse.json")
        val resultsResponseString = IOUtil.readLines(resultsResponse).joinToString("\n")
        val mockResultsResponse = MockResponse()
            .setResponseCode(200)
            .setBody(resultsResponseString)
        mockWebServer.enqueue(mockResultsResponse)
        val file = InstrumentationRegistry.getInstrumentation().context.assets.open("AutoCompleteResultResponse.json")
        val s = IOUtil.readLines(file).joinToString("\n")
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(s)
        mockWebServer.enqueue(mockResponse)
        mockWebServer.enqueue(mockResponse)
        onView(withId(R.id.search_menu)).perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("a"))
        Thread.sleep(SearchFragment.DEBOUNCE_DELAY + 100L)
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(pressKey(KeyEvent.KEYCODE_DEL))
        Thread.sleep(SearchFragment.DEBOUNCE_DELAY + 100L)
        onView(withId(R.id.rv_autocomplete)).check(RecyclerViewItemCountAssertion(`is`(0)))
        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("a"))
        Thread.sleep(SearchFragment.DEBOUNCE_DELAY + 100L)
        onView(withId(R.id.search_close_btn)).perform(click())
        Thread.sleep(SearchFragment.DEBOUNCE_DELAY + 100L)
    }

    @Test
    fun test_navigateToResults() {
        val autoResponse = InstrumentationRegistry.getInstrumentation().context.assets.open("AutoCompleteResultResponse.json")
        val resultsResponse = InstrumentationRegistry.getInstrumentation().context.assets.open("GetResultsResponse.json")
        val autoResponseString = IOUtil.readLines(autoResponse).joinToString("\n")
        val resultsResponseString = IOUtil.readLines(resultsResponse).joinToString("\n")
        val mockAutoResponse = MockResponse()
            .setResponseCode(200)
            .setBody(autoResponseString)
        mockWebServer.enqueue(mockAutoResponse)
        mockWebServer.enqueue(mockAutoResponse)
        val mockResultsResponse = MockResponse()
            .setResponseCode(200)
            .setBody(resultsResponseString)
        mockWebServer.enqueue(mockResultsResponse)
        onView(withId(R.id.search_menu)).perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("a"))
        Thread.sleep(SearchFragment.DEBOUNCE_DELAY + 200L)
        onView(withId(R.id.rv_autocomplete))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.rv_results)).check(RecyclerViewItemCountAssertion(not(0)))
    }
}
