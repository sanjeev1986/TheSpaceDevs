package com.sample.feature.launches.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import com.sample.ds.compose.DSTheme
import com.sample.ds.compose.dividerGrey
import com.sample.ds.compose.platformWhite
import com.sample.feature.launches.R
import kotlinx.coroutines.launch

@Composable
internal fun LaunchDetails(viewModel: LaunchDetailsViewModel) {
    val state = viewModel.observableState.observeAsState()
    state.value?.apply { LaunchDetailsInternal(this) }
}

@Composable
internal fun LaunchDetailsInternal(state: LaunchDetailViewState) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.missionName, style = DSTheme.typography.Title)
                },
                backgroundColor = platformWhite,
            )
        },
        content = {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(state = scrollState),
            ) {
                val (mapsConstraint, detailsConstraint, timeTextConstraint) = createRefs()
                state.apply {
                    Box(
                        modifier = Modifier
                            .constrainAs(mapsConstraint) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .aspectRatio(1.25f),
                    ) {
                        launchCoordinates?.apply {
                            MapAvailable(this)
                        } ?: Text(
                            text = stringResource(id = R.string.launch_location_not_available),
                            modifier = Modifier.fillMaxWidth(),
                            style = DSTheme.typography.Title,
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(id = R.dimen.screen_margin))
                            .constrainAs(detailsConstraint) {
                                top.linkTo(mapsConstraint.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                    ) {
                        launchDate.apply {
                            LaunchRow(stringResource(id = R.string.lbl_launch_date), this)
                        }

                        Divider(
                            color = dividerGrey,
                            modifier = Modifier.padding(
                                top = dimensionResource(id = R.dimen.form_widget_margin_half),
                                bottom = dimensionResource(id = R.dimen.form_widget_margin_half),
                            ),
                        )
                        launchAgencyName.apply {
                            LaunchRow(stringResource(id = R.string.lbl_agency_name), this)
                        }

                        Divider(
                            color = dividerGrey,
                            modifier = Modifier.padding(
                                top = dimensionResource(id = R.dimen.form_widget_margin_half),
                                bottom = dimensionResource(id = R.dimen.form_widget_margin_half),
                            ),
                        )
                        launchDescription.apply {
                            LaunchRow(stringResource(id = R.string.description), this)
                        }
                    }

                    launchDate.apply {
                        Text(
                            text = this,
                            modifier = Modifier
                                .padding(
                                    top = dimensionResource(id = R.dimen.form_widget_margin),
                                )
                                .constrainAs(timeTextConstraint) {
                                    bottom.linkTo(mapsConstraint.bottom)
                                    end.linkTo(parent.end)
                                },
                            style = DSTheme.typography.ListItemTitle,
                        )
                    }
                }
            }
        },
    )
}

@Composable
fun LaunchRow(label: String, data: String) {
    if (data.isNotEmpty()) {
        Column {
            Text(
                text = label,
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.form_widget_margin),
                ),
                style = DSTheme.typography.ListItemTitle,
            )
            Text(
                text = data,
                style = DSTheme.typography.ListItemSubTitle,
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.form_widget_margin_half),
                    bottom = dimensionResource(id = R.dimen.form_widget_margin_half),
                ),
            )
        }
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
                                mv.context,
                                R.drawable.ic_launch_marker,
                            ),
                        ),
                )
            }
        }
    })
}