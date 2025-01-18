package com.msan.ysoftapp.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.msan.ysoftapp.feature.addassignment.navigation.addAssignmentGraph
import com.msan.ysoftapp.feature.calendar.navegation.calendarGraph
import com.msan.ysoftapp.feature.home.navigation.HomeDestination
import com.msan.ysoftapp.feature.home.navigation.homeGraph


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
        homeGraph(bottomBarVisibility, fabVisibility)
        calendarGraph(bottomBarVisibility, fabVisibility)
        addAssignmentGraph(bottomBarVisibility, fabVisibility)


    }
}