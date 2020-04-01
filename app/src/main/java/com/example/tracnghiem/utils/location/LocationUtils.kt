package com.example.tracnghiem.utils.location
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class LocationUtils(val activity: Activity, permissionDeniedListener: PermissionDeniedListener) {

    private val mLocationManager: LocationManager =
        activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private var locationLiveData = MutableLiveData<Location?>()
    private var permissionDeniedListener: PermissionDeniedListener? = null

    private var isRequestLastKnownLocation = false

    private val listener = object : MultiplePermissionsListener {
        @SuppressLint("MissingPermission")
        override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
            if (report?.deniedPermissionResponses?.size ?: -1 > 0) {
                permissionDeniedListener.onDenied()
                removeLocationUpdates()
            } else {
                requestMyLocation()
            }
        }

        override fun onPermissionRationaleShouldBeShown(
            permissions: MutableList<PermissionRequest>?,
            token: PermissionToken?
        ) {
            token?.continuePermissionRequest()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestMyLocation() {
        if (!isRequestLastKnownLocation) {
            val gpsEnabled =
                mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (!gpsEnabled) {
                permissionDeniedListener?.onDenied()
                removeLocationUpdates()
            }
            val providers = mLocationManager.getProviders(true)
            for (provider in providers) {
                mLocationManager.requestLocationUpdates(
                    provider,
                    0,
                    0f,
                    locationRequestListener
                )
            }
        } else {
            var location =
                mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location != null) {
                locationLiveData.value = location
                return
            }
            location =
                mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            if (location != null) {
                locationLiveData.value = location
            } else {
                locationLiveData.value = null
            }
            isRequestLastKnownLocation = false
        }
    }


    private val locationRequestListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            if (location == null) {
                // Do Nothing
            } else {
                locationLiveData.value = location
                removeLocationUpdates()
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

        override fun onProviderEnabled(provider: String?) {

        }

        override fun onProviderDisabled(provider: String?) {

        }
    }

    init {
        this.permissionDeniedListener = permissionDeniedListener
        checkPermission(activity)
    }

    private fun checkPermission(activity: Activity) {
        Dexter.withActivity(activity)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .withListener(listener)
            .onSameThread()
            .check()
    }


    fun getLastKnownLocation() {
        isRequestLastKnownLocation = true
        checkPermission(activity)
    }

    fun getLocation() = locationLiveData

    private fun removeLocationUpdates() {
        mLocationManager.removeUpdates(locationRequestListener)
    }
}

