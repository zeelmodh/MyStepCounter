package com.example.locationfetch.Fetchlocation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class FetchLocation(private val context: Context, private val locationData: LocationData) :
    LocationListener {

    private val LOCATION_SETTING_REQUEST = 999
    private val permissionId: Int = 2
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var mLocationManager: LocationManager

    private var locationRequest: LocationRequest

    init {
        locationRequest = createRequest()
    }

    private fun createRequest(): LocationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000).apply {
            setMinUpdateDistanceMeters(20f)
            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            setWaitForAccurateLocation(true)
        }.build()


    /* Deprycated and updating on every 1 second
    val locationRequest = CurrentLocationRequest.Builder().apply {
        this.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        this.setDurationMillis(3000)
        this.setMaxUpdateAgeMillis(3000)
    }*/

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACTIVITY_RECOGNITION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    fun requestPermissions() {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACTIVITY_RECOGNITION
            ), permissionId
        )
    }

    fun showSettingAlert() {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)

        val result: Task<LocationSettingsResponse> =
            LocationServices.getSettingsClient(context)
                .checkLocationSettings(builder.build())

        result.addOnCompleteListener {
            try {
                val response: LocationSettingsResponse = it.getResult(ApiException::class.java)
                Toast.makeText(context, "GPS is On", Toast.LENGTH_SHORT).show()
                Log.d(ContentValues.TAG, "checkSetting: GPS On")
            } catch (e: ApiException) {

                when (e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        val resolvableApiException = e as ResolvableApiException
                        resolvableApiException.startResolutionForResult(
                            context as Activity,
                            LOCATION_SETTING_REQUEST
                        )
                        Log.d(ContentValues.TAG, "checkSetting: RESOLUTION_REQUIRED")
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        // USER DEVICE DOES NOT HAVE LOCATION OPTION
                    }
                }
            }
        }

    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult ?: return
            super.onLocationResult(locationResult)

            for (location in locationResult.locations) {
//                locationData.location(locationResult)
                Log.d("Location Result", locationResult.lastLocation?.latitude.toString())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("MissingPermission", "SetTextI18n")
    fun getLocation() {
        mFusedLocationClient =
            LocationServices.getFusedLocationProviderClient(context)
        mLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (checkPermissions()) {
            if (isLocationEnabled()) {
                getCurrentLocation()
                startLocationUpdates()
            } else {
                showSettingAlert()
            }
        } else {
            requestPermissions()
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        mFusedLocationClient.requestLocationUpdates(locationRequest, this, Looper.getMainLooper())

    }

    fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        /*Getting location from callback of location request
        mFusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )*/
        mFusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
        mFusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    Log.d("Current_Location ", location.latitude.toString())
                    locationData.location(location)
                }
            }
    }

    override fun onLocationChanged(location: Location) {
        locationData.onChangeLocation(location)
        Log.e("location_changed",
            location.latitude.toString() + ", " + location.longitude.toString()
        )
        Toast.makeText(context,
            location.latitude.toString() + "\n" + location.longitude.toString(),
            Toast.LENGTH_LONG
        ).show()
    }
}
