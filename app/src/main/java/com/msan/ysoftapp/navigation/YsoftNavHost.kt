package com.msan.ysoftapp.navigation


import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.msan.ysoftapp.feature.addassignment.navigation.addAssignmentGraph
import com.msan.ysoftapp.feature.assignmentconfirm.navigation.ASSIGNMENT
import com.msan.ysoftapp.feature.assignmentconfirm.navigation.AssignmentConfirmDestination
import com.msan.ysoftapp.feature.assignmentconfirm.navigation.assignmentConfirmGraph
import com.msan.ysoftapp.feature.assignmentdetail.AssignmentDetailDestination
import com.msan.ysoftapp.feature.assignmentdetail.assignmentDetailGraph
import com.msan.ysoftapp.feature.history.navegation.historyGraph
import com.msan.ysoftapp.feature.home.navigation.HomeDestination
import com.msan.ysoftapp.feature.home.navigation.homeGraph
import java.util.ArrayList


@Composable
fun YsoftNavHost(
    bottomBarVisibility: MutableState<Boolean>,
    fabVisibility: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HomeDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabVisibility = fabVisibility,
            navigateToAssignmentDetail = { medication ->
                navController.navigate(
                    AssignmentDetailDestination.createNavigationRoute(medication.id)
                )
            }
        )
        historyGraph(
            bottomBarVisibility = bottomBarVisibility,
            fabVisibility = fabVisibility,
            navigateToMedicationDetail = { medication ->
                navController.navigate(
                    AssignmentDetailDestination.createNavigationRoute(medication.id)
                )
            }
        )
        assignmentDetailGraph(
            bottomBarVisibility = bottomBarVisibility,
            fabVisibility = fabVisibility,
            onBackClicked = { navController.navigateUp() }
        )
        addAssignmentGraph(
            navController = navController,
            bottomBarVisibility,
            fabVisibility = fabVisibility,
            onBackClicked = { navController.navigateUp() },
            navigateToAssignmentConfirm = {
                val bundle = Bundle()
                bundle.putParcelableArrayList(ASSIGNMENT, ArrayList(it))
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(ASSIGNMENT, bundle)
                }
                navController.navigate(AssignmentConfirmDestination.route)
            }
        )
        assignmentConfirmGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabVisibility = fabVisibility,
            onBackClicked = { navController.navigateUp() },
            navigateToHome = {
                navController.navigate(startDestination) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }
        )


    }
}