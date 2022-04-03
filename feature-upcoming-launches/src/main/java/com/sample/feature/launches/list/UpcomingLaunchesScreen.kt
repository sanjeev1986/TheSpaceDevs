package com.sample.feature.launches.list

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sample.ds.SearchWidget
import com.sample.ds.compose.dividerGrey
import com.sample.ds.compose.widget.ErrorWidget
import com.sample.feature.launches.R
import com.sample.feature.launches.details.LaunchDetailsActivity.Companion.createLaunchDetailsIntent

@Composable
fun UpcomingLaunches() {
    val context = LocalContext.current
    UpcomingInternal(
        viewModel = hiltViewModel()
    ) {
        context.startActivity(createLaunchDetailsIntent(it, context))
    }
}

@Composable
internal fun UpcomingInternal(
    viewModel: UpcomingLaunchesViewModel,
    openLaunchDetails: (String) -> Unit
) {
    val isLoading by viewModel.loading.observeAsState()
    SwipeRefresh(
        modifier = Modifier.fillMaxHeight(),
        state = rememberSwipeRefreshState(isLoading ?: false),
        onRefresh = { viewModel.fetchUpcomingLaunches(true) }) {
        val state by viewModel.upcomingLaunches.observeAsState(emptyList())
        state.apply {
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
    ErrorWidget(
        viewModel.error.observeAsState(false),
        message = stringResource(id = R.string.connectivity_error)
    ) {
        viewModel.dismissError()
    }
}