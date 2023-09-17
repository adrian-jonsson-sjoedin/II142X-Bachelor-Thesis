package com.sigma.stepcounter.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel for managing and storing user profile data.
 *
 * Updates to the profile are immediately reflected in the UI.
 */
class UserProfileViewModel : ViewModel() {
    // Profile settings UI state
    private val _profileUiState = MutableStateFlow(ProfileUiState())


    // Backing property to avoid state updates from other classes
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState.asStateFlow()
    // asStateFlow() makes this mutable state flow a read-only state flow.

    /**
     * Updates the user's weight.
     *
     * @param newWeight The user's new weight.
     */
    fun updateUserWeight(newWeight: Double) {
        _profileUiState.value = _profileUiState.value.copy(userWeight = newWeight)
        Log.d("StateValues", "Current weight ${profileUiState.value.userWeight}")
    }

    /**
     * Updates the user's height.
     *
     * @param newHeight The user's new height.
     */
    fun updateUserHeight(newHeight: Double) {
        val height = newHeight.toInt()
        _profileUiState.value = _profileUiState.value.copy(userHeight = height)
    }

    /**
     * Updates the user's age.
     *
     * @param newAge The user's new age.
     */
    fun updateUserAge(newAge: Double) {
        val age = newAge.toInt()
        _profileUiState.value = _profileUiState.value.copy(userAge = age)
    }

    /**
     * Updates the user's gender.
     *
     * @param newGender The user's new gender.
     */
    fun updateUserGender(newGender: Gender) {
        _profileUiState.value = _profileUiState.value.copy(userGender = newGender)
    }

    /**
     * Updates the user's name.
     *
     * @param newUserName The user's new name.
     */
    fun updateUserName(newUserName: String) {
        _profileUiState.value = _profileUiState.value.copy(userName = newUserName)
    }
}
