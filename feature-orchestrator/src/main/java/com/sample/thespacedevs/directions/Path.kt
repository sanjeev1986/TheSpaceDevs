package com.sample.thespacedevs.directions

import com.sample.thespacedevs.services.launch.Results

sealed class Path
class LaunchDetails(val results: Results) : Path()
