package com.sample.thespacedevs.feature.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.MaterialToolbar
import java.text.SimpleDateFormat
import java.util.*

class LaunchDetailsFragment : Fragment(R.layout.fragment_launch_details),
    OnMapReadyCallback {

    private lateinit var veil: View
    private lateinit var toolbar: MaterialToolbar
    private lateinit var missionDescription: TextView
    private lateinit var agencyTitle: TextView
    private lateinit var timerTxt: TextView

    private val args by navArgs<LaunchDetailsFragmentArgs>()
    private var simpleDateFormat = SimpleDateFormat("dd:HH:mm:ss", Locale.getDefault())
    private var windowStartDateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    private lateinit var launchTitle: String
    private var launchCountDown: Long = 0L
    private var launchCoordinates: LatLng? = null
    private var launchDescription: String? = null
    private var launchAgencyName: String? = null
    private var launchDate: String? = null
    private var timer: CountDownTimer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        veil = view.findViewById(R.id.veil)
        toolbar = view.findViewById(R.id.toolbar)
        missionDescription = view.findViewById(R.id.missionDescription)
        agencyTitle = view.findViewById(R.id.agencyTitle)
        timerTxt = view.findViewById(R.id.timerTxt)
        launchTitle = args.result.name
        launchCountDown = windowStartDateFormat.parse(args.result.window_start).time
        launchCoordinates = LatLng(args.result.pad.latitude, args.result.pad.longitude)
        launchDescription = args.result.mission?.name
        launchAgencyName = args.result.pad.name
        toolbar.title = launchTitle
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        missionDescription.text = launchDescription
        agencyTitle.text = launchAgencyName
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

    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }


    override fun onMapReady(googleMap: GoogleMap?) {
        launchCoordinates?.apply {
            if (latitude == 0.0 && longitude == 0.0) {
                veil.visibility = View.VISIBLE
            } else {
                val ll = this
                googleMap?.run {
                    val cameraPosition = CameraPosition.Builder()
                        .target(ll)
                        .zoom(8f)
                        .build()
                    moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                    addMarker(
                        MarkerOptions()
                            .position(ll)
                            .title(launchTitle)
                            .icon(
                                convertToBitmapDescriptor(
                                    requireActivity(),
                                    R.drawable.ic_launch_marker
                                )
                            )
                    )
                }
            }
        }
    }
}