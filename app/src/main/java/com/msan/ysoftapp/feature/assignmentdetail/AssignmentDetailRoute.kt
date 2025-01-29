package com.msan.ysoftapp.feature.assignmentdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.msan.ysoftapp.R
import com.msan.ysoftapp.domain.model.Assignment
import com.msan.ysoftapp.extention.toFormattedDateString
import com.msan.ysoftapp.extention.toFormattedTimeString
import com.msan.ysoftapp.feature.assignmentdetail.viewmodel.AssignmentDetailViewModel

@Composable
fun AssignmentDetailRoute(
    assignmentId: Long?,
    onBackClicked: () -> Unit,
    viewModel: AssignmentDetailViewModel = hiltViewModel()
) {
    val assignment by viewModel.assignment.collectAsState()

    LaunchedEffect(Unit) {
        assignmentId?.let {
            viewModel.getAssignmentById(it)
        }
    }

    assignment?.let {
        AssignmentDetailScreen(
            assignment = it,
            viewModel = viewModel,
            onBackClicked = onBackClicked
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentDetailScreen(
    assignment: Assignment,
    viewModel: AssignmentDetailViewModel,
    onBackClicked: () -> Unit
) {


    val context = LocalContext.current



    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(vertical = 16.dp),
                navigationIcon = {
                    FloatingActionButton(
                        onClick = {
                            onBackClicked()
                        },
                        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
                title = {}
            )
        },
        bottomBar = {
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge,
                text = assignment.startDate.toFormattedDateString(),
                color = MaterialTheme.colorScheme.primary
            )

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.assignment),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = assignment.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = stringResource(
                    id = R.string.assignment_detail,
                    assignment.timeAllowed,
                    assignment.startDate.toFormattedTimeString()
                ),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
