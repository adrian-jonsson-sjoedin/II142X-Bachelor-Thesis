/**
 * The ProfileUiState.kt file contains the data class and enum class needed to represent the user's
 * profile state in the app.
 *
 * @file: ProfileUiState.kt
 * @author: Adrian Jonsson Sjödin
 * @version: 1.0
 * @since: 1.0
 */
package com.sigma.stepcounter.ui.viewModels

/**
 * A data class representing the user's profile state in the app. It stores the user's weight,
 * height, age, and gender.
 *
 * @property userWeight The user's weight as a [Double], with a default value of 0.0.
 * @property userHeight The user's height as a [Int], with a default value of 0.
 * @property userAge The user's age as an [Int], with a default value of 0.
 * @property userGender The user's gender as a [Gender] enum value, with a default value of null.
 * @property userName The user's name as a [String], with a default value of Name.
 *
 */
data class ProfileUiState(
    val userWeight: Double = 75.8,
    val userHeight: Int = 178,
    val userAge: Int = 29,
    val userGender: Gender? = null,
    val userName: String = "Name"
)
/**
 * An enum class representing the user's gender options within the app.
 */
enum class Gender {
    MALE, FEMALE
}