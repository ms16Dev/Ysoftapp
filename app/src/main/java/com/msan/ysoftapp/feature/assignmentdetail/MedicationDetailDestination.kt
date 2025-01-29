package com.msan.ysoftapp.feature.assignmentdetail

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.msan.ysoftapp.core.NavigationConstants.ASSIGNMENT_ID
import com.msan.ysoftapp.core.NavigationConstants.DEEP_LINK_URI_PATTERN
import com.msan.ysoftapp.core.YsoftNavigationDestination


object AssignmentDetailDestination : YsoftNavigationDestination {
    override val route = "assignment_detail_route/{$ASSIGNMENT_ID}"
    override val destination = "assignment_detail_destination"

    fun createNavigationRoute(assignmentId: Long) = "assignment_detail_route/$assignmentId"
}

fun NavGraphBuilder.assignmentDetailGraph(
    bottomBarVisibility: MutableState<Boolean>,
    fabVisibility: MutableState<Boolean>,
    onBackClicked: () -> Unit
) {
    composable(
        route = AssignmentDetailDestination.route,
        arguments = listOf(
            navArgument(ASSIGNMENT_ID) { type = NavType.LongType }
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern = DEEP_LINK_URI_PATTERN
            }
        )
    ) { backStackEntry ->
        LaunchedEffect(null) {
            bottomBarVisibility.value = false
            fabVisibility.value = false
        }

        val assignmentId = backStackEntry.arguments?.getLong(ASSIGNMENT_ID)

        AssignmentDetailRoute(
            assignmentId = assignmentId,
            onBackClicked = onBackClicked
        )
    }
}
