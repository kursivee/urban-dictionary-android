package com.kursivee.urbandictionary

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.kursivee.urbandictionary.search.presentation.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchFragment.newInstance())
                .commitNow()
        }
        findViewById<FrameLayout>(R.id.container).isFocusableInTouchMode = true
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        handleHideKeyboard(ev)
        return super.dispatchTouchEvent(ev)
    }

    /**
     * Determines whether or not to hide the keyboard
     * From https://stackoverflow.com/a/32149756
     */
    private fun handleHideKeyboard(ev: MotionEvent?) {
        if (ev == null) return
        val focusedView = currentFocus

        if (focusedView != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) &&
            focusedView is EditText &&
            !focusedView.javaClass.name.startsWith("android.webkit.")
        ) {
            val sourceCoordinates = IntArray(2)
            focusedView.getLocationOnScreen(sourceCoordinates)
            val x = ev.rawX + focusedView.getLeft() - sourceCoordinates[0]
            val y = ev.rawY + focusedView.getTop() - sourceCoordinates[1]
            if (
                x < focusedView.getLeft() ||
                x > focusedView.getRight() ||
                y < focusedView.getTop() ||
                y > focusedView.getBottom()
            ) {
                focusedView.hideKeyboard()
            }
        }
    }

    private fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(android.content.Context.INPUT_METHOD_SERVICE)
            as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
        clearFocus()
    }
}
