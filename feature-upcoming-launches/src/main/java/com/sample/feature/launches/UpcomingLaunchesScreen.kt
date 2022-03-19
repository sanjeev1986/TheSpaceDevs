package com.sample.feature.launches

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sample.ds.SearchWidget
import com.sample.ds.compose.dividerGrey
import com.sample.repositories.launch.LaunchRepository


@Composable
fun UpcomingLaunches(
    repository: LaunchRepository,
    openLaunchDetails: (String) -> Unit,
) {
    UpcomingInternal(
        openLaunchDetails,
        ViewModelProvider(
            LocalViewModelStoreOwner.current!!,
            UpcomingLaunchesViewModelFactory(repository)
        ).get(UpcomingLaunchesViewModel::class.java)
    )
}

@Composable
internal fun UpcomingInternal(
    openLaunchDetails: (String) -> Unit,
    viewModel: UpcomingLaunchesViewModel
) {
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
                            openLaunchDetails(item.id)
                        }
                        TabRowDefaults.Divider(color = dividerGrey)
                    }
                }
            }
        }
    }
}