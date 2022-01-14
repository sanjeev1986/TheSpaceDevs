package com.sample.thespacedevs.feature.details

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import com.sample.ds.*
import com.sample.ds.compose.ListItemSubTitle
import com.sample.ds.compose.ListItemTitle
import com.sample.ds.compose.Title
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.launch
import javax.inject.Inject

class LaunchDetailsFragment : Fragment(R.layout.fragment_launch_details) {

    private val args by navArgs<LaunchDetailsFragmentArgs>()
    private var timer: CountDownTimer? = null

    @Inject
    internal lateinit var viewModelFactory: LaunchDetailsViewModel.VMFactory
    private val viewModel by viewModels<LaunchDetailsViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ComposeView>(R.id.toolbar).setContent {
            val state = viewModel.missionName.observeAsState()
            state.value?.apply {
                TopAppBar(
                    title = {
                        Text(text = this, style = MaterialTheme.typography.Title)
                    },
                    backgroundColor = platformWhite
                )
            }
        }
        view.findViewById<ComposeView>(R.id.timerTxt).setContent {
            val state = viewModel.launchDate.observeAsState()
            state.value?.apply {
                Text(
                    text = this,
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.form_widget_margin)
                    ),
                    style = MaterialTheme.typography.ListItemTitle
                )
            }

        }
        view.findViewById<ComposeView>(R.id.mapFragment).setContent {
            PlotLaunchCoordinates()
        }
        view.findViewById<ComposeView>(R.id.launchDetails).setContent {
            LaunchDetails()
        }
        viewModel.fetchMissionDetails(args.missionId)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    @Composable
    fun LaunchDetails() {
        Column(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.screen_margin))) {
            LaunchDate()
            Divider(
                color = dividerGrey, modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.form_widget_margin_half),
                    top = dimensionResource(id = R.dimen.form_widget_margin_half),
                    bottom = dimensionResource(id = R.dimen.form_widget_margin_half)
                )
            )
            AgencyName()
            Divider(
                color = dividerGrey, modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.form_widget_margin_half),
                    top = dimensionResource(id = R.dimen.form_widget_margin_half),
                    bottom = dimensionResource(id = R.dimen.form_widget_margin_half)
                )
            )
            MissionDescription()
            Divider(
                color = dividerGrey, modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.form_widget_margin_half),
                    top = dimensionResource(id = R.dimen.form_widget_margin_half),
                    bottom = dimensionResource(id = R.dimen.form_widget_margin_half)
                )
            )
        }
    }

    @Composable
    private fun LaunchDate() {
        val state = viewModel.launchDate.observeAsState()
        LaunchRow(stringResource(id = R.string.lbl_launch_date), state.value.orEmpty())
    }

    @Composable
    private fun AgencyName() {
        val state = viewModel.missionName.observeAsState()
        LaunchRow(stringResource(id = R.string.lbl_agency_name), state.value.orEmpty())
    }

    @Composable
    private fun MissionDescription() {
        val state = viewModel.launchDescription.observeAsState()
        LaunchRow(stringResource(id = R.string.description), state.value.orEmpty())
    }

    @Composable
    fun LaunchRow(label: String, data: String) {
        Column {
            Text(
                text = label,
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.form_widget_margin)
                ),
                style = MaterialTheme.typography.ListItemTitle
            )
            Text(
                text = data,
                style = MaterialTheme.typography.ListItemSubTitle,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.form_widget_margin_half),
                    top = dimensionResource(id = R.dimen.form_widget_margin_half),
                    bottom = dimensionResource(id = R.dimen.form_widget_margin_half)
                )
            )
        }
    }

    @Composable
    fun PlotLaunchCoordinates() {
        val coordinates = viewModel.launchCoordinates.observeAsState()
        coordinates.value?.apply {
            MapAvailable(this)
        } ?: Text(
            text = stringResource(id = R.string.launch_location_not_available),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.Title
        )
    }

    @Composable
    fun MapAvailable(coordinates: LatLng) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val mapView = rememberMapViewWithLifecycle()
            val coroutineScope = rememberCoroutineScope()

            AndroidView({ mapView }, update = { mv ->
                coroutineScope.launch {
                    val googleMap = mv.awaitMap()
                    googleMap.apply {
                        uiSettings.isScrollGesturesEnabled = false
                        val cameraPosition = CameraPosition.Builder()
                            .target(coordinates)
                            .zoom(8f)
                            .build()
                        moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                        addMarker(
                            MarkerOptions()
                                .position(coordinates)
                                .icon(
                                    convertToBitmapDescriptor(
                                        requireActivity(),
                                        R.drawable.ic_launch_marker
                                    )
                                )
                        )
                    }
                }
            })
        }
    }
}