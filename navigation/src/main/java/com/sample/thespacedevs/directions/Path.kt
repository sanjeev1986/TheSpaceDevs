package com.sample.thespacedevs.directions

import com.sample.ds.R as RDs

sealed class Path(val route: String) {
    object Upcoming : Path("Upcoming")
    object Spacecrafts : Path("Spacecrafts")
    object LaunchDetails : Path("LaunchDetails")
}

sealed class MainMenu(var title: String, var icon: Int, var path: Path) {
    object Upcoming : MainMenu("Upcoming", RDs.drawable.ic_launch_site, Path.Upcoming)
    object Spacecrafts : MainMenu("Spacecraft", RDs.drawable.ic_space_vehicle, Path.Spacecrafts)
}
