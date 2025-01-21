package com.msan.ysoftapp.feature.addassignment.navigation


import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.msan.ysoftapp.core.YsoftNavigationDestination
import com.msan.ysoftapp.domain.model.Assignment
import com.msan.ysoftapp.feature.addassignment.AddAssignmentRoute


object AddAssignmentDestination : YsoftNavigationDestination {
    override val route = "add_medication_route"
    override val destination = "add_medication_destination"
}

fun NavGraphBuilder.addAssignmentGraph(navController: NavController,bottomBarVisibility: MutableState<Boolean>, fabVisibility: MutableState<Boolean>, onBackClicked: () -> Unit, navigateToAssignmentConfirm: (Assignment) -> Unit) {
    composable(route = AddAssignmentDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabVisibility.value = false
        }
        AddAssignmentRoute(onBackClicked, navigateToAssignmentConfirm)
    }
}