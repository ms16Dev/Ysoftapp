package com.msan.ysoftapp.feature.home.navigation


import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.msan.ysoftapp.core.YsoftNavigationDestination
import com.msan.ysoftapp.domain.model.Assignment
import com.msan.ysoftapp.feature.home.HomeRoute


object HomeDestination : YsoftNavigationDestination {
    override val route = "home_route"
    override val destination = "home_destination"
}


fun NavGraphBuilder.homeGraph(navController: NavController, bottomBarVisibility: MutableState<Boolean>, fabVisibility: MutableState<Boolean>, navigateToAssignmentDetail: (Assignment) -> Unit) {
    composable(route = HomeDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = true
            fabVisibility.value = true
        }
        HomeRoute(navigateToAssignmentDetail)
    }
}