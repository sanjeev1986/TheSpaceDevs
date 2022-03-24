package com.sample.thespacedevs.directions

import com.sample.common.R

sealed class Path(val route: String) {
    object Upcoming : Path("Upcoming")
    object Spacecrafts : Path("Spacecrafts")
    object LaunchDetails: Path("LaunchDetails")
}

sealed class MainMenu(var title: String, var icon: Int, var path: Path) {
    object Upcoming : MainMenu("Upcoming", R.drawable.ic_launch_site, Path.Upcoming)
    object Spacecrafts : MainMenu("Spacecraft", R.drawable.ic_space_vehicle, Path.Spacecrafts)
}
