package com.example.appv_3

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.location.LocationManager
import androidx.appcompat.app.AlertDialog

class SettingsFragment : PreferenceFragmentCompat() {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }

    private lateinit var locationSwitch: SwitchPreferenceCompat

    @Suppress("DEPRECATION")
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        locationSwitch = findPreference("location")!!

        locationSwitch.setOnPreferenceChangeListener { _, newValue ->
            val locationEnabled = newValue as Boolean

            if (locationEnabled) {
                if (context?.let {
                        ActivityCompat.checkSelfPermission(
                            it,
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    } != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        LOCATION_PERMISSION_REQUEST_CODE
                    )
                    false
                } else {
                    enableLocationServices()
                    true
                }
            } else {
                disableLocationServices()
                true
            }
        }
    }

    private fun enableLocationServices() {
        // Enable location services
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    private fun disableLocationServices() {
        // Disable location services
        // You can also show a toast to let the user know that location services have been disabled
        Toast.makeText(context, "Location services disabled", Toast.LENGTH_SHORT).show()
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableLocationServices()
            } else {
                locationSwitch.isChecked = false
                Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // This method is called when the view is created.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Enable the back button on the app bar.
        // This allows the user to navigate back to the previous fragment or activity.
        val activity = requireActivity()
        if (activity is AppCompatActivity) {
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Disable the back button on the app bar when leaving the settings fragment.
        val activity = requireActivity()
        if (activity is AppCompatActivity) {
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }
}