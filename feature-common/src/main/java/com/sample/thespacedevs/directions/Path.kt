package com.sample.thespacedevs.directions

import com.sample.services.launch.Results

sealed class Path
class LaunchDetails(val results: Results) : Path()
