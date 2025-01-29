package com.msan.ysoftapp.feature.history


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.msan.ysoftapp.R
import com.msan.ysoftapp.domain.model.Assignment
import com.msan.ysoftapp.extention.hasPassed
import com.msan.ysoftapp.feature.history.viewmodel.HistoryState
import com.msan.ysoftapp.feature.history.viewmodel.HistoryViewModel
import com.msan.ysoftapp.feature.home.AssignmentCard

@Composable
fun HistoryRoute(
    navigateToAssignmentDetail: (Assignment) -> Unit,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val state = viewModel.state
    HistoryScreen(
        state = state,
        navigateToAssignmentDetail = navigateToAssignmentDetail
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    state: HistoryState,
    navigateToAssignmentDetail: (Assignment) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(top = 16.dp),
                title = {
                    Text(
                        text = stringResource(id = R.string.history),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.displaySmall,
                    )
                }
            )
        },
        bottomBar = { },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AssignmentList(
                state = state,
                navigateToAssignmentDetail = navigateToAssignmentDetail
            )
        }
    }
}

@Composable
fun AssignmentList(
    state: HistoryState,
    navigateToAssignmentDetail: (Assignment) -> Unit
) {

    val filteredAssignmentList = state.assignments.filter { it.startDate.hasPassed() }
    val sortedAssignmentList: List<AssignmentListItem> = filteredAssignmentList.sortedBy { it.startDate }.map { AssignmentListItem.AssignmentItem(it) }

    when (sortedAssignmentList.isEmpty()) {
        true -> EmptyView()
        false -> AssignmentLazyColumn(sortedAssignmentList, navigateToAssignmentDetail)
    }
}

@Composable
fun AssignmentLazyColumn(sortedAssignmentList: List<AssignmentListItem>, navigateToAssignmentDetail: (Assignment) -> Unit) {
    LazyColumn(
        modifier = Modifier,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            items = sortedAssignmentList,
            itemContent = {
                when (it) {
                    is AssignmentListItem.OverviewItem -> { }
                    is AssignmentListItem.HeaderItem -> {
                        Text(
                            modifier = Modifier
                                .padding(4.dp, 12.dp, 8.dp, 0.dp)
                                .fillMaxWidth(),
                            text = it.headerText.uppercase(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    is AssignmentListItem.AssignmentItem -> {
                        AssignmentCard(
                            assignment = it.assignment,
                            navigateToAssignmentDetail = { assignment ->
                                navigateToAssignmentDetail(assignment)
                            }
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun EmptyView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineMedium,
            text = stringResource(id = R.string.no_history_yet),
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

sealed class AssignmentListItem {
    data class OverviewItem(val assignmentsToday: List<Assignment>, val isAssignmentListEmpty: Boolean) : AssignmentListItem()
    data class AssignmentItem(val assignment: Assignment) : AssignmentListItem()
    data class HeaderItem(val headerText: String) : AssignmentListItem()
}

