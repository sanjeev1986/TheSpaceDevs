package com.sample.thespacedevs.upcoming

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.sample.thespacedevs.R
import com.sample.thespacedevs.TheSpaceDevApp
import com.sample.services.launch.Mission
import com.sample.services.launch.Pad
import com.sample.services.launch.Results
import com.sample.services.launch.UpcomingLaunchesResponse
import com.sample.thespacedevs.di.DaggerTestApplicationComponent
import com.sample.thespacedevs.repository.LaunchRepository
import com.sample.thespacedevs.utils.RepoResult
import io.mockk.coEvery
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class UpcomingLaunchesFragmentTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Inject
    lateinit var repository: LaunchRepository

    init {
        ((InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TheSpaceDevApp)
            .applicationComponent as DaggerTestApplicationComponent).inject(this)
    }


    private val successResponse = com.sample.services.launch.UpcomingLaunchesResponse(
        results = listOf(
            com.sample.services.launch.Results(
                id = "1",
                name = "New Shepard | NS-15",
                window_start = "2021-04-14T12:00:00Z",
                mission = com.sample.services.launch.Mission(
                    name = "NS-15",
                    description = "Fifteenth flight of Blue Origin's New Shepard rocket"
                ),
                pad = com.sample.services.launch.Pad(
                    name = "West Texas Suborbital Launch Site/ Corn Ranch",
                    latitude = 31.422878000000000,
                    longitude = -104.757121000000000
                )
            ),
            com.sample.services.launch.Results(
                id = "2",
                name = "Falcon 9 Block 5 | SpX USCV-2 (NASA Crew Flight 2)",
                window_start = "2021-04-22T10:11:00Z",
                mission = com.sample.services.launch.Mission(
                    name = "SpX USCV-2 (NASA Crew Flight 2)",
                    description = "SpaceX Crew-2 will be the second crewed operational flight of a Crew Dragon spacecraft, and the third overall crewed orbital flight. It will use the same Falcon 9 first stage as the Crew-1 mission and the same Crew Dragon capsule as the Demo-2 mission (Endeavour)."
                ),
                pad = com.sample.services.launch.Pad(
                    name = "Launch Complex 39A",
                    latitude = 28.60822681,
                    longitude = -80.60428186
                )
            ),
            com.sample.services.launch.Results(
                id = "3",
                name = "Soyuz 2.1b/Fregat-M | OneWeb 6",
                window_start = "2021-04-25T00:00:00Z",
                mission = com.sample.services.launch.Mission(
                    name = "OneWeb 6",
                    description = "A batch of 36 satellites for the OneWeb satellite constellation, which is intended to provide global Interned broadband service for individual consumers. The constellation is planned to have around 648 microsatellites (of which 60 are spares), around 150 kg each, operating in Ku-band from low Earth orbit."
                ),
                pad = com.sample.services.launch.Pad(
                    name = "Cosmodrome Site 1S",
                    latitude = 51.884395,
                    longitude = 128.333932
                )
            )
        )
    )

    @Test
    fun testConnectivityErrorHandling() {
        coEvery { repository.getUpcomingLaunches(any(), any()) } returns RepoResult.Failure(
            IOException()
        )
        launchFragmentInContainer<UpcomingLaunchesFragment>(null, R.style.AppTheme)
        onView(withText(R.string.no_network)).check(matches(isDisplayed()))
    }

    @Test
    fun testUpcomingListIsDisplayed() {
        coEvery { repository.getUpcomingLaunches(any(), any()) } returns RepoResult.Success(
            successResponse.results
        )
        launchFragmentInContainer<UpcomingLaunchesFragment>(null, R.style.AppTheme)
        onView(withHint(R.string.type_here_and_click_search)).check(matches(isDisplayed()))
        onView(withText("New Shepard | NS-15")).check(matches(isDisplayed()))
        onView(withText("Falcon 9 Block 5 | SpX USCV-2 (NASA Crew Flight 2)")).check(
            matches(
                isDisplayed()
            )
        )
        onView(withText("Soyuz 2.1b/Fregat-M | OneWeb 6")).check(matches(isDisplayed()))
    }

    @Test
    fun testSearchUpcomingLaunches() {
        coEvery { repository.getUpcomingLaunches(any(), any()) } returns RepoResult.Success(
            successResponse.results
        )
        launchFragmentInContainer<UpcomingLaunchesFragment>(null, R.style.AppTheme)
        onView(withId(R.id.etSearchText)).perform(typeText("S"))
        onView(withText("Soyuz 2.1b/Fregat-M | OneWeb 6")).check(matches(isDisplayed()))
    }


}