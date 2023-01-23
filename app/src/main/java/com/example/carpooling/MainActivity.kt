package com.example.carpooling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.carpooling.databinding.ActivityMainBinding
import com.example.carpooling.ui.history.HistoryFragment
import com.example.carpooling.ui.myrides.MyRidesFragment
import com.example.carpooling.ui.publish.PublishFragment
import com.example.carpooling.ui.search.SearchFragment
import com.example.carpooling.ui.search.SearchResultFragment
import com.example.carpooling.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.startFragment, R.id.searchFragment, R.id.publishFragment,  R.id.myRidesFragment, R.id.historyFragment, R.id.profileFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
                when (f) {
                    is NavHostFragment, is SearchFragment, is PublishFragment, is SearchResultFragment, is HistoryFragment, is ProfileFragment, is MyRidesFragment -> {
                        binding.bottomNav.visibility = View.VISIBLE
                    }
                    else -> {
                        Log.d("bottomNav", "ok")
                        Log.d("bottomNav",f::class.java.name)
                        binding.bottomNav.visibility = View.GONE
                    }
                }
            }
        }, true)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}