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
import com.sample.feature.launches.UpcomingLaunches
import com.sample.thespacedevs.directions.MainMenu
import com.sample.thespacedevs.directions.Path
import androidx.compose.ui.Modifier
import com.sample.ds.compose.platformBlack
import com.sample.ds.compose.translucentTeal
import com.sample.repositories.launch.LaunchRepository
import com.sample.repositories.spacecraft.SpacecraftRepository
import com.sample.thespacedevs.feature.vehicles.list.SpaceCraftsScreen
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var launchRepository: LaunchRepository

    @Inject
    lateinit var spacecraftRepository: SpacecraftRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContent {
            HomeScreen(launchRepository, spacecraftRepository)
        }
    }
}

@Composable
fun HomeScreen(
    launchRepository: LaunchRepository,
    spacecraftRepository: SpacecraftRepository
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { HomeBottomNavigation(navController) }
    ) {
        NavigationGraph(
            navController = navController,
            launchRepository,
            spacecraftRepository,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun HomeBottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavMenu(R.string.upcoming, R.drawable.sharp_rocket_launch_24, Path.Upcoming),
        BottomNavMenu(R.string.vehicles, R.drawable.sharp_rocket_24, Path.Spacecrafts)
    )
    BottomNavigation(
        backgroundColor = translucentTeal,
        contentColor = platformBlack
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
fun NavigationGraph(
    navController: NavHostController,
    launchRepository: LaunchRepository,
    spacecraftRepository: SpacecraftRepository,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MainMenu.Upcoming.path.route,
        modifier = modifier
    ) {
        composable(MainMenu.Upcoming.path.route) {
            UpcomingLaunches(launchRepository) {
            }
        }
        composable(MainMenu.Spacecrafts.path.route) {
            SpaceCraftsScreen(spacecraftRepository) {

            }
        }
    }
}

class BottomNavMenu(@StringRes val title: Int, @DrawableRes val iconId: Int, val path: Path)