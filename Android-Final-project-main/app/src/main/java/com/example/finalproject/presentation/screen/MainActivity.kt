package com.example.finalproject.presentation.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homePage -> {
                    if (navController.currentDestination?.id != R.id.wallpaperFragment){
                        navController.navigate(R.id.wallpaperFragment)
                    }
                    true
                }
                R.id.profilePage -> {
                    if (navController.currentDestination?.id != R.id.profileFragment)
                        navController.navigate(R.id.profileFragment)
                    true
                }
                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.signInFragment || destination.id == R.id.signUpFragment || destination.id == R.id.authContainerFragment) {
                binding.bottomNavigation.visibility = View.GONE
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
            with(binding.bottomNavigation) {
                when (destination.id) {
                    R.id.wallpaperFragment -> selectedItemId = R.id.homePage
                    R.id.profileFragment -> selectedItemId = R.id.profilePage
                    R.id.categoriesFragment -> selectedItemId = R.id.categoriesFragment
                }
            }
        }
    }
}