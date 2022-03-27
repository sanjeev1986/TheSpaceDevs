package com.sample.thespacedevs.feature.vehicles.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sample.ds.SearchWidget
import com.sample.ds.compose.dividerGrey
import com.sample.thespacedevs.feature.vehicles.SpacecraftRepository

@Composable
fun SpaceCraftsScreen() {
    /*SpaceCraftsScreenInternal(
        ViewModelProvider(
            LocalViewModelStoreOwner.current!!,
            SpacecraftListViewModelFactory(repository)
        ).get(SpacecraftListViewModel::class.java)
    )*/
    SpaceCraftsScreenInternal(viewModel = hiltViewModel<SpacecraftListViewModel>())
}

@Composable
internal fun SpaceCraftsScreenInternal(viewModel: SpacecraftListViewModel) {
    val isLoading by viewModel.loading.observeAsState()
    SwipeRefresh(
        state = rememberSwipeRefreshState(false),
        onRefresh = { viewModel.fetchSpacecrafts(true) }) {
        val state = viewModel.spaceCrafts.observeAsState()
        state.value?.apply {
            LazyColumn {
                item {
                    SearchWidget {
                        if (it.isEmpty()) {
                            viewModel.fetchSpacecrafts(false)
                        } else {
                            //viewModel.search(it)
                        }
                    }
                }
                items(this@apply) { item ->
                    SpacecraftItemWidget(
                        imageUrl = item.imageUrl,
                        name = item.name,
                        status = item.status,
                        description = item.description,
                        text3 = "",
                        onClick = { }
                    )
                    TabRowDefaults.Divider(color = dividerGrey)
                }
            }
        }
    }

}