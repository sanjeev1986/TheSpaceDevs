package com.sample.thespacedevs.di

import com.sample.thespacedevs.upcoming.UpcomingLaunchesFragment


interface DaggerComponent {
    fun inject(fragment: UpcomingLaunchesFragment)
}
