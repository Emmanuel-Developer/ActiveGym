package com.linkersconsulting.activegym.features.init.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linkersconsulting.activegym.R
import androidx.navigation.fragment.findNavController

class SplashFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Espera 2 segundos y navega a Login
        view.postDelayed({
            findNavController().navigate(
                R.id.action_splash_to_login
            )
        }, 2000)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
}

