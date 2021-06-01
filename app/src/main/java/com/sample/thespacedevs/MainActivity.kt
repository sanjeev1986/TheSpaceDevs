package com.sample.thespacedevs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.sample.thespacedevs.directions.Navigator

//import com.sample.feature.launches.UpcomingLaunchesFragmentDirections

class MainActivity : AppCompatActivity(), Navigator {
    override val navController: NavController
        get() = findNavController(R.id.hostFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}