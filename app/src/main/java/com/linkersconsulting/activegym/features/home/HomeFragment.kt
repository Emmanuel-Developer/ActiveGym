package com.linkersconsulting.activegym.features.home.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.setupWithNavController
import com.linkersconsulting.activegym.R

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.homeNavHost) as NavHostFragment

        val navController = navHostFragment.navController

        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottomNav)

        bottomNav.setupWithNavController(navController)
    }
}
