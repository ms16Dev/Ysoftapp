package com.msan.ysoftapp.feature.calendar.navegation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.msan.ysoftapp.core.YsoftNavigationDestination
import com.msan.ysoftapp.feature.calendar.CalendarRoute


object CalendarDestination : YsoftNavigationDestination {
    override val route = "calendar_route"
    override val destination = "calendar_destination"
}

fun NavGraphBuilder.calendarGraph(bottomBarVisibility: MutableState<Boolean>, fabVisibility: MutableState<Boolean>) {
    composable(route = CalendarDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = true
            fabVisibility.value = false
        }
        CalendarRoute()
    }
}