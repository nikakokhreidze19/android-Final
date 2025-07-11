package com.example.finalproject.presentation.screen.splash

import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.finalproject.databinding.FragmentSplashBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.event.SplashEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun setUp() {
        splashViewModel.onEvent(SplashEvent.ReadSessionEvent)
        val logo = binding.imageSplashLogo
        logo.animate()
            .alpha(1f)
            .scaleX(1.1f)
            .scaleY(1.1f)
            .setDuration(1500)
            .setInterpolator(DecelerateInterpolator())
            .start()
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                splashViewModel.uiEvent.buffer(1, BufferOverflow.DROP_LATEST).collect {
                    handleNavigationEvents(event = it)
                }
            }
        }
    }

    private fun handleNavigationEvents(event: SplashViewModel.SplashUiEvent) {
        when (event) {
            is SplashViewModel.SplashUiEvent.NavigateToWallpapers -> findNavController().navigate(
                SplashFragmentDirections.actionSplashFragmentToWallpaperFragment()
            )

            is SplashViewModel.SplashUiEvent.NavigateToSignIn -> findNavController().navigate(
                SplashFragmentDirections.actionSplashFragmentToAuthContainerFragment()
            )
        }
    }

}