package com.example.locationfetch.Fetchlocation

import android.location.Location
import com.google.android.gms.location.LocationResult

interface LocationData {
    fun onChangeLocation(location: Location)
    fun location(currentLocation: Location)
}