package com.example.finalproject.presentation.screen.sign_up

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.finalproject.databinding.FragmentSignUpBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.event.SignUpEvent
import com.example.finalproject.presentation.screen.auth.AuthContainerFragmentDirections
import com.example.finalproject.presentation.state.sign_up.SignUpState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun setUpListeners() {
        binding.btnSignUp.setOnClickListener {
            signUpViewModel.onEvent(
                with(binding) {
                    SignUpEvent.SignUp(
                        etSignUpEmail.text.toString(),
                        etSignUpPassword.text.toString(),
                        etSignUpFullName.text.toString())
                }
            )
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signUpViewModel.signUpStateFlow.collect {
                    handleState(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signUpViewModel.uiEvent.collect {
                    handleUiEvent(it)
                }
            }
        }
    }

    private fun handleState(state: SignUpState) {
        binding.progressBarSignUp.visibility = if (state.isLoading) View.VISIBLE else View.INVISIBLE

        with(binding) {
            tvSignUpError.visibility = View.VISIBLE
            if (state.errorMessage != "") {
                tvSignUpError.text = state.errorMessage
                progressBarSignUp.visibility = View.INVISIBLE
            }
        }
    }

    private fun handleUiEvent(event: SignUpViewModel.SignUpUiEvent) {
        when (event) {
            is SignUpViewModel.SignUpUiEvent.NavigateToWallpapers -> findNavController().navigate(
                AuthContainerFragmentDirections.actionAuthContainerFragmentToWallpaperFragment()
            )
        }
    }
}