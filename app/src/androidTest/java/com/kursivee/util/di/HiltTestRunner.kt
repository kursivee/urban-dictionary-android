package com.kursivee.util.di

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * Used in app:build.gradle
 *
 *
 defaultConfig {
 ...
 testInstrumentationRunner "com.kursivee.util.di.HiltTestRunner"
 }
 */
class HiltTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}
