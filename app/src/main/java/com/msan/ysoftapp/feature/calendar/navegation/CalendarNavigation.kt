package com.msan.ysoftapp.feature.calendar.navegation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.msan.ysoftapp.core.YsoftNavigationDestination
import com.msan.ysoftapp.feature.calendar.CalendarRoute


object CalendarDestination : YsoftNavigationDestination {
    override val route = "calendar_route"
    override val destination = "calendar_destination"
}

fun NavGraphBuilder.calendarGraph() {
    composable(route = CalendarDestination.route) {
        CalendarRoute()
    }
}