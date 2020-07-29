package com.kursivee.util.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import org.hamcrest.Matcher

class RecyclerViewItemCountAssertion(
    private val matcher: Matcher<Int>
) : ViewAssertion {
    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) throw noViewFoundException
        val rv = view as RecyclerView
        requireNotNull(rv.adapter)
        assertThat(rv.adapter!!.itemCount, matcher)
    }
}
