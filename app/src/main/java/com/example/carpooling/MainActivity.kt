package com.example.carpooling

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.carpooling.databinding.ActivityMainBinding
import com.example.carpooling.utils.SessionManager
import com.example.carpooling.viewmodels.UserViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private val userViewModel: UserViewModel by viewModels {
        ViewModelFactory()
    }
    private var menu: Menu? = null

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
                        setMenuVisibility(true)
                    }
/*                    R.id.publishDatetimeFragment -> {
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
                    }*/
                    R.id.loginFragment, R.id.settingsFragment -> {
                        binding.bottomNav.visibility = View.GONE
                        setMenuVisibility(false)
                    }
                    else -> {
                        binding.bottomNav.visibility = View.GONE
                        setMenuVisibility(true)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_bar_settings) {
            val action = MainNavGraphDirections.toSettingsFragment()
            navController.navigate(action)
            return true
        } else if (id == R.id.action_bar_logout) {
            MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_App_MaterialAlertDialog)
                .setTitle(resources.getString(R.string.dialog_logout_confirmation_title))
                .setNegativeButton(resources.getString(R.string.dialog_confirmation_back)) { _, _ ->
                }
                .setPositiveButton(resources.getString(R.string.dialog_logout_confirmation_yes)) { _, _ ->
                    userViewModel.logout()
                    val sessionManager = SessionManager(this)
                    sessionManager.deleteAuthToken()
                    val action = MainNavGraphDirections.logout()
                    navController.navigate(action)
                }
                .show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setMenuVisibility(visible: Boolean) {
        menu?.setGroupVisible(R.id.action_bar_group, visible)
    }
}