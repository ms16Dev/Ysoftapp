package com.msan.ysoftapp.feature.home.navigation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.msan.ysoftapp.core.YsoftNavigationDestination
import com.msan.ysoftapp.feature.home.HomeRoute


object HomeDestination : YsoftNavigationDestination {
    override val route = "home_route"
    override val destination = "home_destination"
}

fun NavGraphBuilder.homeGraph() {
    composable(route = HomeDestination.route) {
        HomeRoute()
    }
}