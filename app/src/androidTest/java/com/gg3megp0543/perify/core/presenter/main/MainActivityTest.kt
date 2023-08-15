package com.gg3megp0543.perify.core.presenter.main

import androidx.appcompat.app.AppCompatDelegate
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.ComponentNameMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.gg3megp0543.perify.EspressoIdlingResource
import com.gg3megp0543.perify.R
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class MainActivityTest {
    private val threadTwoSec = Thread.sleep(2000)

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun loadDisasterInRecyclerView() {
        Espresso.onView(ViewMatchers.withId(R.id.rv_disaster))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun loadDisasterBasedOnDisasterType() {
        Espresso.onView(ViewMatchers.withId(R.id.chip_earthquake))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.rv_disaster))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun loadDisasterBasedOnProvince() {
        val provinceDummy = "Jaka"
        Espresso.onView(ViewMatchers.withId(R.id.sv_province))
            .perform(ViewActions.typeText(provinceDummy))
            .perform(ViewActions.closeSoftKeyboard())

        threadTwoSec

        Espresso.onView(ViewMatchers.withText("Jakarta"))
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.rv_disaster))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun loadDisasterBasedOnProvinceAndDisasterType() {
        val provinceDummy = "Jaka"
        Espresso.onView(ViewMatchers.withId(R.id.sv_province))
            .perform(ViewActions.typeText(provinceDummy))
            .perform(ViewActions.closeSoftKeyboard())

        threadTwoSec

        Espresso.onView(ViewMatchers.withText("Jakarta"))
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(ViewActions.click())

        threadTwoSec

        Espresso.onView(ViewMatchers.withId(R.id.chip_fire))
            .perform(ViewActions.click())

        threadTwoSec

        Espresso.onView(ViewMatchers.withId(R.id.rv_disaster))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun showMessageWhenDataIsEmpty() {
        val unlikelyProvinceDummy = "Sumatera Selat"
        Espresso.onView(ViewMatchers.withId(R.id.sv_province))
            .perform(ViewActions.typeText(unlikelyProvinceDummy))
            .perform(ViewActions.closeSoftKeyboard())

        threadTwoSec

        Espresso.onView(ViewMatchers.withText("Sumatera Selatan"))
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(ViewActions.click())

        threadTwoSec

        Espresso.onView(ViewMatchers.withId(R.id.chip_haze))
            .perform(ViewActions.click())

        Thread.sleep(3000)

        Espresso.onView(ViewMatchers.withId(R.id.tv_empty_data))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun navigateToSettingsActivityWhenIconIsClicked() {
        Intents.init()
        Espresso.onView(ViewMatchers.withId(R.id.iv_setting))
            .perform(ViewActions.click())

        Intents.intended(
            IntentMatchers.hasComponent(ComponentNameMatchers.hasShortClassName(".core.presenter.setting.SettingsActivity"))
        )
        Intents.release()
    }

    @Test
    fun darkModePreferenceIsWorking() {
        Espresso.onView(ViewMatchers.withId(R.id.iv_setting))
            .perform(ViewActions.click())

        threadTwoSec

        Espresso.onView(ViewMatchers.withText("Automatic")).perform(ViewActions.click())

        threadTwoSec

        Espresso.onView(ViewMatchers.withText("Dark mode")).perform(ViewActions.click())

        threadTwoSec

        val currentNightMode = AppCompatDelegate.getDefaultNightMode()
        assert(currentNightMode == AppCompatDelegate.MODE_NIGHT_YES)
    }
}
