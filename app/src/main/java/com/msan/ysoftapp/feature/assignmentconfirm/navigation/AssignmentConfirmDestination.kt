package com.msan.ysoftapp.feature.assignmentconfirm.navigation


import android.os.Bundle
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.msan.ysoftapp.core.YsoftNavigationDestination
import com.msan.ysoftapp.domain.model.Assignment
import com.msan.ysoftapp.feature.assignmentconfirm.AssignmentConfirmRoute

const val ASSIGNMENT = "assignment"


object AssignmentConfirmDestination : YsoftNavigationDestination {
    override val route = "assignment_confirm_route"
    override val destination = "assignment_confirm_destination"
}

fun NavGraphBuilder.assignmentConfirmGraph(navController: NavController, bottomBarVisibility: MutableState<Boolean>, fabVisibility: MutableState<Boolean>, onBackClicked: () -> Unit, navigateToHome: () -> Unit) {
    composable(route = AssignmentConfirmDestination.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabVisibility.value = false
        }
        val assignmentBundle = navController.previousBackStackEntry?.savedStateHandle?.get<Bundle>(ASSIGNMENT)
        val assignment = assignmentBundle?.getParcelable<Assignment>(ASSIGNMENT)
        if (assignment != null) {
            AssignmentConfirmRoute(assignment, onBackClicked, navigateToHome)
        }
    }
}