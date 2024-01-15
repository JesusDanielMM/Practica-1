package com.example.practica1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import android.Manifest



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object {
        const val PERMISSION_ID = 33
    }

    lateinit var mFusedLocationClient: FusedLocationProviderClient
    val tvLatitude: TextView = findViewById(R.id.tvLatitude)
    val tvLongitude: TextView = findViewById(R.id.tvLongitude)
    val btnLocate: Button = findViewById(R.id.btnLocate)
    private fun checkGranted(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(this, permission) ==
                PackageManager.PERMISSION_GRANTED
    }

    private fun checkPermissions() =
        checkGranted(Manifest.permission.ACCESS_COARSE_LOCATION) &&
                checkGranted(Manifest.permission.ACCESS_FINE_LOCATION)

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                    if (location != null) {
                        tvLatitude.text = location.latitude.toString()
                        tvLongitude.text = location.longitude.toString()
                    } else {
                        // Handle the case where location is null
                    }
                }
            } else {
                // Handle the case where location is not enabled
            }
        } else {
            requestPermissions()
        }
    }
}