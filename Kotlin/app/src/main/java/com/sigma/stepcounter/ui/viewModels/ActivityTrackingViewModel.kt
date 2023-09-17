package com.sigma.stepcounter.ui.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

/**
 * ViewModel for tracking the progress of an activity.
 *
 * Provides a flow of integers to represent the progress towards a goal, and methods to get and
 * set the goal value. Used to fake step data
 */
class ActivityTrackingViewModel : ViewModel() {
    private var goalValue: Int = 1000

    /**
     * A Flow that emits the current progress towards the goal every second.
     *
     * Starts at 0 and increments by 1 every second until it reaches the current goal value.
     */
    val countUpFlow = flow<Int> {
        val startingValue = 0
        var currentValue = startingValue
        emit(startingValue)
        while (currentValue < goalValue) {
            delay(1000L)
            currentValue++
            emit(currentValue)
        }
    }

    /**
     * Updates the goal value.
     *
     * @param newGoal The new goal value.
     */
    fun setGoalValue(newGoal: Int) {
        goalValue = newGoal
    }

    /**
     * Returns the current goal value.
     *
     * @return The current goal value.
     */
    fun getGoalValue(): Int {
        return goalValue
    }
}