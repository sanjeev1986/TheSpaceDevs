package com.sample.feature.launches

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.material.snackbar.Snackbar
import com.sample.ds.dividerGrey
import com.sample.ds.platformBlack
import com.sample.feature.launches.databinding.FragmentUpcomingLaunchesBinding
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etSearchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(newText: Editable?) {
                newText?.trim()
                    ?.let {
                        if (it.isEmpty()) {
                            binding.refreshLayout.isEnabled = true
                            viewModel.fetchUpcomingLaunches(false)
                        } else {
                            binding.refreshLayout.isEnabled = false
                            viewModel.search(it.toString())
                        }
                    }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
        binding.toolbar.title = getString(R.string.launch_list_title)
        binding.refreshLayout.setContent {
            UpcomingLaunches()
        }
        viewModel.upcomingLaunchesLiveData.observe(viewLifecycleOwner, Observer { result ->
            if (result.isSuccess) {
                binding.refreshLayout.setContent {
                    UpcomingLaunches(
                        result.getOrDefault(emptyList())
                            .map { LaunchItem(it.name, it.mission?.description ?: "") })
                }
            } else {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.no_network),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })
        viewModel.fetchUpcomingLaunches(false)
    }

    @Composable
    fun UpcomingLaunches(items: List<LaunchItem> = emptyList()) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(viewModel.isRefreshing),
            onRefresh  = { viewModel.fetchUpcomingLaunches(true) }) {
            LazyColumn {
                this.items(items) { item ->
                    UpcomingLaunchItem(item)
                    Divider(color = dividerGrey)
                }
            }
        }

    }
}

