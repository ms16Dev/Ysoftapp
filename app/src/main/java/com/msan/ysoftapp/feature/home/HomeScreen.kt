package com.msan.ysoftapp.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.msan.ysoftapp.R
import com.msan.ysoftapp.domain.model.Assignment
import com.msan.ysoftapp.extention.toFormattedDateShortString
import com.msan.ysoftapp.extention.toFormattedDateString
import com.msan.ysoftapp.extention.toFormattedMonthDateString
import com.msan.ysoftapp.feature.home.data.CalendarDataSource
import com.msan.ysoftapp.feature.home.model.CalendarModel
import com.msan.ysoftapp.feature.home.viewmodel.HomeState
import com.msan.ysoftapp.feature.home.viewmodel.HomeViewModel
import java.util.Calendar
import java.util.Date

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.homeUiState.collectAsState()
    HomeScreen(
        modifier = modifier,
        state = state,
        onSelectedDate = { viewModel.updateSelectedDate(it) },
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier,
    state: HomeState,
    onSelectedDate: (Date) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DailyAssignments(
            state = state,
            onSelectedDate = onSelectedDate,
        )
    }
}


@Composable
fun DailyAssignments(
    state: HomeState,
    onSelectedDate: (Date) -> Unit,
) {

    DatesHeader(
        lastSelectedDate = state.lastSelectedDate,
        onDateSelected = { selectedDate ->
            onSelectedDate(selectedDate.date)
        }
    )

    if (state.assignments.isEmpty()) {
        EmptyCard(

        )
    } else {
        LazyColumn(
            modifier = Modifier,
        ) {
            items(
                items = state.assignments,
                itemContent = {
                    AssignmentCard(
                        assignment = it
                    )
                }
            )
        }

    }
}


@Composable
fun AssignmentCard(assignment: Assignment) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(30.dp),
        colors = cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    ) {

        Row(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = assignment.name,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Next assignment in 2 minutes"
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.End
            ) {

                Button(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = {
                        // TODO: Mark assignment as taken
                        // TODO: Update DB with assignment as taken and store with time.
                        // TODO: Cross the name of the token and disable the button with text "Taken"
                    }
                ) {

                    Text(
                        text = "Take now"
                    )
                }
            }
        }
    }
}


@Composable
fun DatesHeader(
    lastSelectedDate: String,
    onDateSelected: (CalendarModel.DateModel) -> Unit, // Callback to pass the selected date){}
) {
    val dataSource = CalendarDataSource()
    var calendarModel by remember {
        mutableStateOf(
            dataSource.getData(lastSelectedDate = dataSource.getLastSelectedDate(lastSelectedDate))
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        DateHeader(
            data = calendarModel,
            onPrevClickListener = { startDate ->
                // refresh the CalendarModel with new data
                // by get data with new Start Date (which is the startDate-1 from the visibleDates)
                val calendar = Calendar.getInstance()
                calendar.time = startDate

                calendar.add(Calendar.DAY_OF_YEAR, -2) // Subtract one day from startDate
                val finalStartDate = calendar.time

                calendarModel = dataSource.getData(
                    startDate = finalStartDate,
                    lastSelectedDate = calendarModel.selectedDate.date
                )
            },
            onNextClickListener = { endDate ->
                // refresh the CalendarModel with new data
                // by get data with new Start Date (which is the endDate+2 from the visibleDates)
                val calendar = Calendar.getInstance()
                calendar.time = endDate

                calendar.add(Calendar.DAY_OF_YEAR, 2)
                val finalStartDate = calendar.time

                calendarModel = dataSource.getData(
                    startDate = finalStartDate,
                    lastSelectedDate = calendarModel.selectedDate.date
                )
            }
        )
        DateList(
            data = calendarModel,
            onDateClickListener = { date ->
                calendarModel = calendarModel.copy(
                    selectedDate = date,
                    visibleDates = calendarModel.visibleDates.map {
                        it.copy(
                            isSelected = it.date.toFormattedDateString() == date.date.toFormattedDateString()
                        )
                    }
                )
                onDateSelected(date)
            }
        )
    }
}

@Composable
fun DateHeader(
    data: CalendarModel,
    onPrevClickListener: (Date) -> Unit,
    onNextClickListener: (Date) -> Unit
) {
    Row(
        modifier = Modifier.padding(vertical = 16.dp),
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = if (data.selectedDate.isToday) {
                stringResource(R.string.today)
            } else {
                data.selectedDate.date.toFormattedMonthDateString()
            },
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.tertiary
        )
        IconButton(onClick = {
            onPrevClickListener(data.startDate.date)
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = "Back"
            )
        }
        IconButton(onClick = {
            onNextClickListener(data.endDate.date)
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = "Next"
            )
        }
    }
}

@Composable
fun DateList(
    data: CalendarModel,
    onDateClickListener: (CalendarModel.DateModel) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(items = data.visibleDates) { date ->
            DateItem(date, onDateClickListener)
        }
    }
}

@Composable
fun DateItem(
    date: CalendarModel.DateModel,
    onClickListener: (CalendarModel.DateModel) -> Unit,
) {
    Column {
        Text(
            text = date.day, // day "Mon", "Tue"
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.outline
        )
        Card(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 4.dp),
            onClick = { onClickListener(date) },
            colors = cardColors(
                // background colors of the selected date
                // and the non-selected date are different
                containerColor = if (date.isSelected) {
                    MaterialTheme.colorScheme.tertiary
                } else {
                    MaterialTheme.colorScheme.surface
                }
            ),
        ) {
            Column(
                modifier = Modifier
                    .width(42.dp)
                    .height(42.dp)
                    .padding(8.dp)
                    .fillMaxSize(), // Fill the available size in the Column
                verticalArrangement = Arrangement.Center, // Center vertically
                horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
            ) {
                Text(
                    text = date.date.toFormattedDateShortString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = if (date.isSelected) {
                        FontWeight.Medium
                    } else {
                        FontWeight.Normal
                    }
                )
            }
        }
    }
}


@Composable
fun EmptyCard(

) {

    LaunchedEffect(Unit) {
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(156.dp),
        shape = RoundedCornerShape(36.dp),
        colors = cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.tertiary
        ),
        onClick = {

        }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(24.dp, 24.dp, 0.dp, 16.dp)
                    .fillMaxWidth(.50F)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {

                Text(
                    text = stringResource(R.string.assignment_break),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                )

                Text(
                    text = stringResource(R.string.home_screen_empty_card_message),
                    style = MaterialTheme.typography.titleSmall,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                Image(
                    painter = painterResource(id = R.drawable.assignment), contentDescription = ""
                )
            }
        }
    }
}
