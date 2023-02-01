package com.example.carpooling

import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.carpooling.databinding.ActivityMainBinding


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
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.startFragment) {
                binding.bottomNav.visibility = View.GONE
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()
                when (destination.id) {
                    R.id.searchFragment, R.id.publishFragment, R.id.searchResultFragment, R.id.historyFragment, R.id.profileFragment, R.id.myRidesFragment -> {
                        binding.bottomNav.visibility = View.VISIBLE
                    }
/*                    R.id.publishDatetimeFragment -> {
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
                    }*/
                    else -> {
                        binding.bottomNav.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}