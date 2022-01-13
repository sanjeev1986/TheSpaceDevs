package com.sample.thespacedevs.feature.details

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
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
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class LaunchDetailsFragment : Fragment(R.layout.fragment_launch_details) {

    private lateinit var veil: View
    private lateinit var timerTxt: TextView

    private val args by navArgs<LaunchDetailsFragmentArgs>()
    private var simpleDateFormat = SimpleDateFormat("dd:HH:mm:ss", Locale.getDefault())
    private var launchCountDown: Long = 0L
    private var launchCoordinates: LatLng? = null
    private var launchDate: String? = null
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
        veil = view.findViewById(R.id.veil)
        view.findViewById<ComposeView>(R.id.toolbar).setContent {
            val state = viewModel.launchDetails.observeAsState()
            state.value?.apply {
                TopAppBar(
                    title = {
                        Text(text = missionName, style = MaterialTheme.typography.Title)
                    },
                    backgroundColor = platformWhite
                )
            }
        }
        view.findViewById<ComposeView>(R.id.timerTxt).setContent {
            val state = viewModel.launchTimers.observeAsState()
            Text(
                text = state.value?.launchDate.orEmpty(),
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.form_widget_margin)
                ),
                style = MaterialTheme.typography.ListItemTitle
            )
        }
        //launchCountDown = windowStartDateFormat.parse(args.result.window_start).time
        //launchCoordinates = LatLng(args.result.pad.latitude, args.result.pad.longitude)
        view.findViewById<ComposeView>(R.id.mapFragment).setContent {
            PlotLaunchCoordinates()
        }
        view.findViewById<ComposeView>(R.id.launchDetails).setContent {
            LaunchDetails()
        }
        if (launchCountDown > 0) {//check if count down has began;count down is represented by "wsstamp" with a non zero value
            launchCountDown -= System.currentTimeMillis()
            if (launchCountDown > 0) {//ensure the event is in the future
                timer = object : CountDownTimer(launchCountDown, 1000) {

                    private val calendar = Calendar.getInstance()


                    @SuppressLint("SetTextI18n")
                    override fun onTick(millisUntilFinished: Long) {
                        calendar.timeInMillis = millisUntilFinished
                        timerTxt.text =
                            "T - " + simpleDateFormat.format(calendar.time)
                    }

                    override fun onFinish() {
                        timerTxt.text = getString(R.string.mission_concluded)
                    }
                }
                timer?.start()
            } else {
                //timerTxt.text = launchDate//display launch date if count down has'nt begun
            }
        } else {
            //timerTxt.text = launchDate//display launch date if count down has'nt begun
        }
        viewModel.fetchMissionDetails(args.missionId)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    @Composable
    fun LaunchDetails() {
        val state = viewModel.launchDetails.observeAsState()

        state.value?.apply {
            Column(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.screen_margin))) {
                LaunchRow(
                    label = stringResource(id = R.string.lbl_agency_name),
                    state.value!!.launchAgencyName
                )
                Divider(color = dividerGrey)
                LaunchRow(
                    label = stringResource(id = R.string.description),
                    state.value!!.launchDescription
                )
                Divider(color = dividerGrey)
            }
        }
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
        coordinates.value?.takeIf { it.isvalid() }?.apply {
            MapAvailable(latLng!!)
        } ?: MapNotAvailable()
    }

    @Composable
    fun MapNotAvailable() {
        Text(
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

            AndroidView({ mapView }, update = { mapView ->
                coroutineScope.launch {
                    val googleMap = mapView.awaitMap()
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