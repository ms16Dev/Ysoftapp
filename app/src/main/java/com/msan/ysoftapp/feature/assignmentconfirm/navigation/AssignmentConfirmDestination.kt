package com.msan.ysoftapp.feature.assignmentconfirm.navigation


import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.msan.ysoftapp.core.YsoftNavigationDestination
import com.msan.ysoftapp.feature.assignmentconfirm.AssignmentConfirmRoute

object AssignmentConfirmDestination : YsoftNavigationDestination {
    override val route = "medication_confirm_route"
    override val destination = "medication_confirm_destination"
}

fun NavGraphBuilder.assignmentConfirmGraph(bottomBarVisibility: MutableState<Boolean>, fabVisibility: MutableState<Boolean>, onBackClicked: () -> Unit, navigateToHome: () -> Unit) {
    composable(route = AssignmentConfirmDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabVisibility.value = false
        }
        AssignmentConfirmRoute(onBackClicked, navigateToHome)
    }
}