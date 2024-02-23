package com.kagiya.roamio.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat


class GPSUtils {

    private lateinit var locationManager: LocationManager
    private var latitude: String? = null
    private var longitude: String? = null

    fun initPermissions(activity: Activity?) {
        ActivityCompat.requestPermissions(
            activity!!,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION
        )
    }

    fun findDeviceLocation(activity: Activity) {

        locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //Check gps is enable or not
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Write Function To enable gps
            gpsEnable(activity)
        } else {
            //GPS is already On then
            getLocation(activity)
        }
    }

    private fun getLocation(activity: Activity) {
        try {
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION
                )
            } else {
                val locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                val locationNetwork =
                    locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                val locationPassive =
                    locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)



                if (locationGps != null) {
                    val lat = locationGps.latitude
                    val longi = locationGps.longitude
                    latitude = lat.toString()
                    longitude = longi.toString()

                } else if (locationNetwork != null) {
                    val lat = locationNetwork.latitude
                    val longi = locationNetwork.longitude
                    latitude = lat.toString()
                    longitude = longi.toString()

                } else if (locationPassive != null) {
                    val lat = locationPassive.latitude
                    val longi = locationPassive.longitude
                    latitude = lat.toString()
                    longitude = longi.toString()
                } else {
                    Toast.makeText(activity, "Can't Get Your Location", Toast.LENGTH_SHORT).show()
                }

                Log.d("GPS", "$latitude,  $longitude")
            }
        }
        catch (ex: Exception){
            Toast.makeText(activity, "Error at getting location", Toast.LENGTH_SHORT).show()
            Log.e("GPS","Error at getting location", ex)
        }
    }

    private fun gpsEnable(activity: Activity) {
        val builder = AlertDialog.Builder(activity)

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton(
            "YES"
        ) { dialog, which -> activity.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton(
                "NO"
            ) { dialog, which -> dialog.cancel() }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun getLatitude(): String? {
        return latitude
    }

    fun getLongitude(): String?{
        return longitude
    }
    companion object {
        const val REQUEST_LOCATION = 1
        val instance = GPSUtils()
    }
}