package com.sample.thespacedevs.directions

import androidx.navigation.NavController
import com.sample.feature.launches.UpcomingLaunchesFragmentDirections

interface Navigator {
    val navController: NavController

    fun navigateTo(path: Path) {
        when (path) {
            is LaunchDetails -> {
                navController.navigate(
                    UpcomingLaunchesFragmentDirections.actionUpcomingLaunchesFragmentToLaunchDetailsFragment(
                        path.results
                    )
                )
            }
        }
    }
}