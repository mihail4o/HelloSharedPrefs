package com.balivo.hellosharedprefs

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView

/**
 * HelloSharedPrefs is an adaptation of the HelloToast app from chapter 1. It includes:
 * - Buttons for changing the background color.
 * - Maintenance of instance state.
 * - Themes and styles.
 * - Read and write shared preferences for the current count and the color.
 *
 *
 * This is the starter code for HelloSharedPrefs.
 */
class MainActivity : AppCompatActivity() {
    // Current count.
    private var mCount = 0
    // Current background color.
    private var mColor: Int = 0
    // Text view to display both count and color.
    private var mShowCountTextView: TextView? = null

    // Key for current count
    private val COUNT_KEY = "count"
    // Key for current color
    private val COLOR_KEY = "color"

    // Shared Preferences
    private lateinit var mPreferences : SharedPreferences
    private val sharedPrefFile = "com.balivo.hellosharedprefs"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views, color
        mShowCountTextView = findViewById(R.id.count_textview) as TextView
        mColor = ContextCompat.getColor(this, R.color.default_background)

        // Initialize Shared Preferences
        mPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        // Restore the saved state.
        mCount = mPreferences.getInt(COUNT_KEY, 0)
        mShowCountTextView!!.text = String.format("%s", mCount)
        mColor = mPreferences.getInt(COLOR_KEY, mColor)
        mShowCountTextView!!.setBackgroundColor(mColor)
    }

    /**
     * Handles the onClick for the background color buttons.  Gets background color of the button
     * that was clicked and sets the textview background to that color.
     *
     * @param view The view (Button) that was clicked.
     */
    fun changeBackground(view: View) {
        val color = (view.background as ColorDrawable).color
        mShowCountTextView!!.setBackgroundColor(color)
        mColor = color
    }

    /**
     * Handles the onClick for the Count button.  Increments the value of the mCount global and
     * updates the textview.
     *
     * @param view The view (Button) that was clicked.
     */
    fun countUp(view: View) {
        mCount++
        mShowCountTextView!!.text = String.format("%s", mCount)
    }

    /**
     * Handles the onClick for the Reset button.  Resets the global count and background
     * variables to the defaults, resets the views to those values, and clears the shared
     * preferences
     *
     * @param view The view (Button) that was clicked.
     */
    fun reset(view: View) {
        // Reset count
        mCount = 0
        mShowCountTextView!!.text = String.format("%s", mCount)

        // Reset color
        mColor = ContextCompat.getColor(this, R.color.default_background)
        mShowCountTextView!!.setBackgroundColor(mColor)

        // Clear Shared Preferences
        val preferencesEditor = mPreferences.edit() as SharedPreferences.Editor
        preferencesEditor.clear()
        preferencesEditor.apply()

    }

    override fun onPause() {
        super.onPause()

        val preferencesEditor = mPreferences.edit() as SharedPreferences.Editor
        preferencesEditor.putInt(COUNT_KEY, mCount)
        preferencesEditor.putInt(COLOR_KEY, mColor)
        preferencesEditor.apply()
    }
}
