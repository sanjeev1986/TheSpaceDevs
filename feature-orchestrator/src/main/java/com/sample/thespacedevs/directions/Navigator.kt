package com.sample.thespacedevs.directions

import androidx.navigation.NavController
//import com.sample.feature.launches.UpcomingLaunchesFragmentDirections

interface Navigator {
    val navController: NavController

    /*fun navigateTo(path: Path) {
        when (path) {
            is Path.LaunchDetails -> {
                navController.navigate(
                    UpcomingLaunchesFragmentDirections.actionUpcomingLaunchesFragmentToLaunchDetailsFragment(
                        path.id
                    )
                )
            }
        }
    }*/
}