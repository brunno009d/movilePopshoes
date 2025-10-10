package com.example.movilepopshoes.navigation

import android.health.connect.datatypes.ExerciseRoute

sealed class NavigationEvent {
    data class NavigateTo(
        val route: Screen,
        val popUpTORoute: Screen? = null,
        val inclusive: Boolean = false,
        val singleTop: Boolean = false
    ) : NavigationEvent()

    object PopBackStack : NavigationEvent()
    object NavigateUp : NavigationEvent()
}