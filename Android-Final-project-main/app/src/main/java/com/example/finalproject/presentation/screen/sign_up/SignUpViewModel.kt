package com.example.finalproject.presentation.screen.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.domain.usecase.validation.EmailValidatorUseCase
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.sign_up.SignUpWithEmailAndPasswordUseCase
import com.example.finalproject.domain.usecase.sign_up.UpdateDisplayNameUseCase
import com.example.finalproject.domain.usecase.validation.FullNameValidationUseCase
import com.example.finalproject.domain.usecase.validation.RepeatPasswordValidatorUseCase
import com.example.finalproject.presentation.event.SignUpEvent
import com.example.finalproject.presentation.state.sign_up.SignUpState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpWithEmailAndPasswordUseCase,
    private val emailValidator: EmailValidatorUseCase,
    private val passwordValidator: RepeatPasswordValidatorUseCase,
    private val fullNameValidator: FullNameValidationUseCase,
    private val updateDisplayNameUseCase: UpdateDisplayNameUseCase
) : ViewModel() {
    private val _signUpStateFlow = MutableStateFlow(SignUpState())
    val signUpStateFlow get() = _signUpStateFlow.asStateFlow()

    private val _uiEvent = MutableSharedFlow<SignUpUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.SignUp -> validateForm(
                email = event.email,
                password = event.password,
                fullName = event.fullName
            )
        }
    }

    private fun signUpWithEmailAndPassword(email: String, password: String, fullName: String) =
        viewModelScope.launch {
            signUpUseCase(email, password).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _signUpStateFlow.update { currentState ->
                        currentState.copy(
                            isLoading = resource.loading
                        )
                    }

                    is Resource.Success -> {
                        _signUpStateFlow.update { currentState -> currentState.copy(data = resource.response) }
                        if (Firebase.auth.currentUser != null) {
                            updateDisplayNameUseCase(fullName = fullName).collect {
                                when (it) {
                                    is Resource.Loading -> _signUpStateFlow.update { currentState ->
                                        currentState.copy(
                                            isLoading = it.loading
                                        )
                                    }

                                    is Resource.Success -> _uiEvent.emit(SignUpUiEvent.NavigateToWallpapers)
                                    is Resource.Error -> _signUpStateFlow.update { currentState ->
                                        currentState.copy(
                                            errorMessage = it.errorMessage
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is Resource.Error -> _signUpStateFlow.update { currentState ->
                        currentState.copy(
                            errorMessage = resource.errorMessage
                        )
                    }
                }
            }
        }

    private fun validateForm(
        email: String,
        password: String,
        fullName: String
    ) {
        if (!fullNameValidator(fullName)) {
            _signUpStateFlow.update { it.copy(errorMessage = "Enter your full name") }
        } else if (!emailValidator(email)) {
            _signUpStateFlow.update { it.copy(errorMessage = "Email is not valid!") }
        } else if (!passwordValidator(password)) {
            _signUpStateFlow.update { it.copy(errorMessage = "Password is not valid!") }
        }  else {
            signUpWithEmailAndPassword(email = email, password = password, fullName = fullName)
        }
    }

    sealed interface SignUpUiEvent {
        data object NavigateToWallpapers : SignUpUiEvent
    }
}