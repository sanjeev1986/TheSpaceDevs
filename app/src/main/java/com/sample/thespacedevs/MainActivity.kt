package com.sample.thespacedevs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
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
import com.sample.feature.launches.UpcomingLaunches
import com.sample.thespacedevs.directions.MainMenu
import com.sample.thespacedevs.directions.Navigator
import com.sample.thespacedevs.directions.Path
import com.sample.thespacedevs.feature.spacecrafts.SpaceCraftsScreen
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource

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
        bottomBar = { HomeBottomNavigation(navController) }
    ) {
        NavigationGraph(navController = navController, modifier = Modifier.padding(it))
    }
}

@Composable
fun HomeBottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavMenu(R.string.launch_list_title, R.drawable.ic_launch_site, Path.Upcoming),
        BottomNavMenu(R.string.spacecrafts, R.drawable.ic_launch_marker, Path.Spacecrafts)
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.colorAccent),
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach {
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(it.iconId),
                        contentDescription = ""
                    )
                },
                label = { BottomNavLabel(stringResource(it.title)) },
                onClick = {
                    navController.navigate(it.path.route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                selected = currentRoute == it.path.route,
            )
        }
    }
}

@Composable
fun BottomNavLabel(label: String) {
    Text(label)
}

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = MainMenu.Upcoming.path.route,
        modifier = modifier
    ) {
        composable(MainMenu.Upcoming.path.route) {
            UpcomingLaunches(navController)
        }
        composable(MainMenu.Spacecrafts.path.route) {
            SpaceCraftsScreen(navController)
        }
    }
}

class BottomNavMenu(@StringRes val title: Int, @DrawableRes val iconId: Int, val path: Path)