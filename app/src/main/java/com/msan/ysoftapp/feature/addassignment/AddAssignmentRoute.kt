package com.msan.ysoftapp.feature.addassignment


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun AddAssignmentRoute(
    modifier: Modifier = Modifier,
    //viewModel: CalendarViewModel = hiltViewModel()
) {
    AddAssignmentScreen()
}

@Composable
fun AddAssignmentScreen() {
    Text(
        text = "Coming Soon \uD83D\uDEA7",
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.displaySmall
    )
}