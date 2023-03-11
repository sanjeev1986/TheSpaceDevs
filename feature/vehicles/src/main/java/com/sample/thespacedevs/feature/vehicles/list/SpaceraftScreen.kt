package com.sample.thespacedevs.feature.vehicles.list

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sample.ds.SearchWidget
import com.sample.ds.compose.dividerGrey
import com.sample.ds.compose.widget.ErrorWidget
import com.sample.thespacedevs.feature.spacecrafts.R

@Composable
fun SpaceCraftsScreen() {
    SpaceCraftsScreenInternal(viewModel = hiltViewModel())
}

@Composable
internal fun SpaceCraftsScreenInternal(viewModel: SpacecraftListViewModel) {
    val isLoading by viewModel.loading.observeAsState()
    val state by viewModel.spaceCrafts.observeAsState(emptyList())
    val error by viewModel.error.observeAsState(false)
    SwipeRefresh(
        modifier = Modifier.fillMaxHeight(),
        state = rememberSwipeRefreshState(isLoading ?: false),
        onRefresh = { viewModel.fetchSpacecrafts(true) },
    ) {
        LazyColumn {
            item {
                SearchWidget {
                    if (it.isEmpty()) {
                        viewModel.fetchSpacecrafts(false)
                    } else {
                        // viewModel.search(it)
                    }
                }
            }
            items(state) { item ->
                SpacecraftItemWidget(
                    imageUrl = item.imageUrl,
                    name = item.name,
                    status = item.status,
                    description = item.description,
                    text3 = "",
                    onClick = { },
                )
                TabRowDefaults.Divider(color = dividerGrey)
            }
        }

        ErrorWidget(
            error,
            message = stringResource(id = R.string.connectivity_error),
        ) {
            viewModel.dismissError()
        }
    }
}
