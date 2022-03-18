package com.sample.thespacedevs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sample.feature.launches.UpcomingLaunches
import com.sample.thespacedevs.directions.MainMenu
import com.sample.thespacedevs.directions.Navigator
import com.sample.thespacedevs.directions.Path

class MainActivity : AppCompatActivity(), Navigator {
    override val navController: NavController
        get() = findNavController(R.id.hostFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }
}

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { HomeBottomNavigation(listOf(MainMenu.Upcoming.path), navController) }
    ) {

    }
}

@Composable
fun HomeBottomNavigation(items: List<Path>, navController: NavController) {

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach {
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launch_marker),
                        contentDescription = ""
                    )
                },
                label = { BottomNavLabel(stringResource(id = R.string.launch_list_title)) },
                onClick = {
                    navController.navigate(it.route)
                },
                selected = currentRoute == it.route
            )
        }
    }
}

@Composable
fun BottomNavLabel(label: String) {
    Text(label)
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = MainMenu.Upcoming.path.route) {
        composable(MainMenu.Upcoming.path.route) {
            UpcomingLaunches(navController)
        }
        composable(MainMenu.Upcoming.path.route) {
            UpcomingLaunches(navController)
        }
    }
}