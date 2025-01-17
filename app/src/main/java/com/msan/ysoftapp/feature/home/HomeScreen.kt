package com.msan.ysoftapp.feature.home
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    //viewModel: HomeViewModel = hiltViewModel()
) {
    HomeScreen()
}

@Composable
fun HomeScreen() {
    Greeting("Android")
}

@Composable
fun Greeting(name: String) {
    Column {
        Text(
            text = "Hello,",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = "[Edit Name]",
            style = MaterialTheme.typography.displayMedium
        )
    }
}