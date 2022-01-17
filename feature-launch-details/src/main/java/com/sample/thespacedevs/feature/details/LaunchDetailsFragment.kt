package com.sample.thespacedevs.feature.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import com.sample.ds.compose.DSTheme
import com.sample.ds.compose.dividerGrey
import com.sample.ds.compose.platformWhite
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.launch
import javax.inject.Inject

class LaunchDetailsFragment : Fragment() {

    private val args by navArgs<LaunchDetailsFragmentArgs>()

    @Inject
    internal lateinit var viewModelFactory: LaunchDetailsViewModel.VMFactory
    private val viewModel by viewModels<LaunchDetailsViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ScreenUI()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchMissionDetails(args.missionId)
    }

    @Composable
    fun ScreenUI() {
        val scrollState = rememberScrollState()
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(state = scrollState)
        ) {
            val (toolbarConstraint, mapsConstraint, detailsConstraint, timeTextConstraint) = createRefs()
            val toolBarState = viewModel.missionName.observeAsState()
            toolBarState.value?.apply {
                TopAppBar(
                    title = {
                        Text(text = this, style = DSTheme.typography.Title)
                    },
                    backgroundColor = platformWhite,
                    modifier = Modifier
                        .constrainAs(toolbarConstraint) {
                            top.linkTo(parent.top)
                        }
                        .fillMaxWidth()
                )
            }
            Box(
                modifier = Modifier
                    .constrainAs(mapsConstraint) {
                        top.linkTo(toolbarConstraint.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .aspectRatio(1.25f)
            ) {
                val coordinates = viewModel.launchCoordinates.observeAsState()

                coordinates.value?.apply {
                    MapAvailable(this)
                } ?: Text(
                    text = stringResource(id = R.string.launch_location_not_available),
                    modifier = Modifier.fillMaxWidth(),
                    style = DSTheme.typography.Title
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.screen_margin))
                    .constrainAs(detailsConstraint) {
                        top.linkTo(mapsConstraint.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
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
            }

            val state = viewModel.launchDate.observeAsState()
            state.value?.apply {
                Text(
                    text = this,
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(id = R.dimen.form_widget_margin)
                        )
                        .constrainAs(timeTextConstraint) {
                            bottom.linkTo(mapsConstraint.bottom)
                            end.linkTo(parent.end)
                        },
                    style = DSTheme.typography.ListItemTitle
                )
            }
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
                style = DSTheme.typography.ListItemTitle
            )
            Text(
                text = data,
                style = DSTheme.typography.ListItemSubTitle,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.form_widget_margin_half),
                    top = dimensionResource(id = R.dimen.form_widget_margin_half),
                    bottom = dimensionResource(id = R.dimen.form_widget_margin_half)
                )
            )
        }
    }

    @Composable
    fun MapAvailable(coordinates: LatLng) {

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