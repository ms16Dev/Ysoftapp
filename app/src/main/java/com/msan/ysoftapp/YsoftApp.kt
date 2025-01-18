package com.msan.ysoftapp


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.msan.ysoftapp.feature.addassignment.navigation.AddAssignmentDestination
import com.msan.ysoftapp.navigation.YsoftTopLevelNavigation
import com.msan.ysoftapp.navigation.TOP_LEVEL_DESTINATIONS
import com.msan.ysoftapp.navigation.TopLevelDestination
import com.msan.ysoftapp.navigation.YsoftNavHost
import com.msan.ysoftapp.ui.theme.YsoftappTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun YsoftApp() {
    YsoftappTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            val navController = rememberNavController()
            val doseTopLevelNavigation = remember(navController) {
                YsoftTopLevelNavigation(navController)
            }

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination


            val bottomBarVisibility = rememberSaveable { (mutableStateOf(true)) }
            val fabVisibility = rememberSaveable { (mutableStateOf(true)) }


            Scaffold(
                modifier = Modifier.padding(16.dp),
                containerColor = Color.Transparent,
                floatingActionButton = {
                    AnimatedVisibility(
                        visible = fabVisibility.value,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it }),
                        content = {
                            YsoftFAB(navController)
                        })

                },
                contentColor = MaterialTheme.colorScheme.onBackground,
                bottomBar = {
                    AnimatedVisibility(
                        visible = bottomBarVisibility.value,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it }),
                        content = {
                            YsoftBottomBar(
                                onNavigateToTopLevelDestination = doseTopLevelNavigation::navigateTo,
                                currentDestination = currentDestination
                            )
                        })
                }
            ) { padding ->
                Row(
                    Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal
                            )
                        )
                ) {
                    YsoftNavHost(
                        bottomBarVisibility = bottomBarVisibility,
                        fabVisibility = fabVisibility,
                        navController = navController,
                        modifier = Modifier
                            .padding(padding)
                            .consumeWindowInsets(padding)
                    )
                }
            }
        }
    }
}


@Composable
private fun YsoftBottomBar(
    onNavigateToTopLevelDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    // Wrap the navigation bar in a surface so the color behind the system
    // navigation is equal to the container color of the navigation bar.
    Surface(color = MaterialTheme.colorScheme.surface) {
        NavigationBar(
            modifier = Modifier.windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                )
            ),
            tonalElevation = 0.dp
        ) {

            TOP_LEVEL_DESTINATIONS.forEach { destination ->
                val selected =
                    currentDestination?.hierarchy?.any { it.route == destination.route } == true
                NavigationBarItem(
                    selected = selected,
                    onClick = { onNavigateToTopLevelDestination(destination) },
                    icon = {
                        Icon(
                            if (selected) {
                                destination.selectedIcon
                            } else {
                                destination.unselectedIcon
                            },
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(destination.iconTextId)) }
                )
            }
        }
    }
}

@Composable
fun YsoftFAB(navController: NavController) {
    ExtendedFloatingActionButton(
        text = { Text(text = stringResource(id = R.string.add_assignment)) },
        icon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Add") },
        onClick = {
            navController.navigate(AddAssignmentDestination.route)
        },
        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp))
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    YsoftappTheme {
        YsoftApp()
    }
}