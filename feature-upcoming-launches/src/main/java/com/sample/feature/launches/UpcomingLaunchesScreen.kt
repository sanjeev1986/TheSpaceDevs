package com.sample.feature.launches

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sample.ds.SearchWidget
import com.sample.ds.compose.dividerGrey
import com.sample.thespacedevs.directions.Navigator
import com.sample.thespacedevs.directions.Path


@Composable
fun UpcomingLaunches(navController: NavHostController) {
    Text("Tee-Tee 1")
}

@Composable
internal fun Upcoming(viewModel: UpcomingLaunchesViewModel, navController: NavHostController) {
    Column {
        val isLoading by viewModel.loading.observeAsState()
        SwipeRefresh(
            state = rememberSwipeRefreshState(isLoading?.isRefreshing ?: false),
            onRefresh = { viewModel.fetchUpcomingLaunches(true) }) {
            val state = viewModel.upcomingLaunches.observeAsState()
            state.value?.apply {
                LazyColumn {
                    item {
                        SearchWidget {
                            if (it.isEmpty()) {
                                viewModel.fetchUpcomingLaunches(false)
                            } else {
                                viewModel.search(it)
                            }
                        }
                    }
                    items(this@apply) { item ->
                        UpcomingLaunchItem(item) {
                            navController.navigate(
                                Path.LaunchDetails(item.id).route
                            )
                        }
                        TabRowDefaults.Divider(color = dividerGrey)
                    }
                }
            }
        }
    }
}