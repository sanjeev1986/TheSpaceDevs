package com.sample.feature.launches

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.fragment.app.viewModels
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.sample.ds.SearchWidget
import com.sample.ds.dividerGrey
import com.sample.feature.launches.databinding.FragmentUpcomingLaunchesBinding
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import androidx.compose.runtime.getValue
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sample.thespacedevs.directions.LaunchDetails
import com.sample.thespacedevs.directions.Navigator

class UpcomingLaunchesFragment : DaggerFragment() {


    @Inject
    lateinit var viewModelFactory: UpcomingLaunchesViewModel.VMFactory
    private var _binding: FragmentUpcomingLaunchesBinding? = null
    private val binding get() = _binding!!


    private val viewModel by viewModels<UpcomingLaunchesViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingLaunchesBinding.inflate(inflater, container, false)
        binding.root.setContent {
            ScreenUI()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchUpcomingLaunches(false)
    }

    @Composable
    fun ScreenUI() {
        Column {
            SearchWidget {
                if (it.isEmpty()) {
                    viewModel.fetchUpcomingLaunches(false)
                } else {
                    viewModel.search(it)
                }
            }
            UpcomingLaunches()
        }
    }

    @Composable
    fun UpcomingLaunches() {
        val isLoading by viewModel.loading.observeAsState()
        SwipeRefresh(
            state = rememberSwipeRefreshState(isLoading?.isRefreshing ?: false),
            onRefresh = { viewModel.fetchUpcomingLaunches(true) }) {
            val state = viewModel.upcomingLaunches.observeAsState()
            state.value?.apply {
                LazyColumn {
                    this.items(this@apply) { item ->
                        UpcomingLaunchItem(item){
                            (requireActivity() as Navigator).navigateTo(LaunchDetails(item.id))
                        }
                        Divider(color = dividerGrey)
                    }
                }
            }
        }
    }
}

