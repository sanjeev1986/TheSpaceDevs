package com.sample.feature.launches

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

import androidx.compose.ui.platform.ComposeView

class UpcomingLaunchesFragment : DaggerFragment() {


    /*@Inject
    lateinit var viewModelFactory: UpcomingLaunchesViewModel.VMFactory

    private val viewModel by viewModels<UpcomingLaunchesViewModel> { viewModelFactory }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchUpcomingLaunches(false)
    }*/
}

