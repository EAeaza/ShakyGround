package com.example.appv_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.Preference
import com.example.appv_3.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
//        setupActionBarWithNavController(findNavController(R.id.fragment_main))

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        if (supportFragmentManager.findFragmentById(R.id.nav)?.childFragmentManager?.fragments?.get(0) is SettingsFragment) {
//            return false
//        }
//
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.menu_settings -> {
//                findNavController(R.id.fragment_main).navigate(R.id.action_homeFragment_to_settingsFragment)
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }


}