package com.adedom.theegggame.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.adedom.theegggame.R
import com.adedom.utility.GameSwitch
import com.adedom.utility.switch
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng

abstract class MapActivity : AppCompatActivity(),
    OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    LocationListener {

    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var mLocationRequest: LocationRequest

    companion object {
        var sGoogleMap: GoogleMap? = null
        lateinit var sContext: Context
        var sIsCamera = false
        var sLatLng = LatLng(0.0, 0.0)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        sIsCamera = true
        sContext = baseContext
        setMapAndLocation()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setMapAndLocation() {
        val mapFragment = fragmentManager.findFragmentById(R.id.mapFragment) as MapFragment
        mapFragment.getMapAsync(this@MapActivity)

        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()
        mGoogleApiClient.connect()

        mLocationRequest = LocationRequest()
            .setInterval(5000)
            .setFastestInterval(2500)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdate() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
            mGoogleApiClient,
            mLocationRequest,
            this
        )
    }

    private fun stopLocationUpdate() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
    }

    override fun onStart() {
        super.onStart()
        mGoogleApiClient.connect()
    }

    override fun onStop() {
        if (mGoogleApiClient.isConnected) {
            mGoogleApiClient.disconnect()
        }
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        switch = GameSwitch.ON

        if (mGoogleApiClient.isConnected) {
            startLocationUpdate()
        }
    }

    override fun onPause() {
        super.onPause()
        switch = GameSwitch.OFF

        if (mGoogleApiClient.isConnected) {
            stopLocationUpdate()
        }
    }

    override fun onConnected(p0: Bundle?) {
        startLocationUpdate()
    }

    override fun onConnectionSuspended(p0: Int) {
        mGoogleApiClient.connect()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onLocationChanged(location: Location?) {
        if (sLatLng.latitude == location!!.latitude &&
            sLatLng.longitude == location.longitude
        ) return

        sLatLng = LatLng(location.latitude, location.longitude)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        sGoogleMap = googleMap
        sGoogleMap!!.isMyLocationEnabled = true
    }
}

