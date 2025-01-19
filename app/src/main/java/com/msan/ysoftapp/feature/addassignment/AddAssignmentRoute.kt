package com.msan.ysoftapp.feature.addassignment


import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.msan.ysoftapp.R
import com.msan.ysoftapp.domain.model.Assignment
import com.msan.ysoftapp.util.Difficulty
import com.msan.ysoftapp.util.getRecurrenceList
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddAssignmentRoute(
    onBackClicked: () -> Unit,
    navigateToAssignmentConfirm: (Assignment) -> Unit,
    //viewModel: CalendarViewModel = hiltViewModel()
) {
    AddAssignmentScreen(onBackClicked,navigateToAssignmentConfirm)
}

@Composable
fun AddAssignmentScreen(onBackClicked: () -> Unit, navigateToAssignmentConfirm: (Assignment) -> Unit) {
    var assignmentName by rememberSaveable { mutableStateOf("") }
    var assignmentDetails by rememberSaveable { mutableStateOf("") }
    var assignmentMarks by rememberSaveable { mutableStateOf("") }
    var selectedRecurrence by rememberSaveable { mutableStateOf("") }
    var selectedStartDate by rememberSaveable { mutableLongStateOf(0) }
    var selectedDifficulty by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current


    Column(
        modifier = Modifier.padding(0.dp, 16.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FloatingActionButton(
            onClick = {
                onBackClicked()
            },
            elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp)
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Text(
            text = stringResource(id = R.string.add_assignment),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = stringResource(id = R.string.assignment_name),
            style = MaterialTheme.typography.bodyLarge
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = assignmentName,
            onValueChange = { assignmentName = it },
            placeholder = { Text(text = "Assignment") },
        )

        Spacer(modifier = Modifier.padding(4.dp))


        Text(
            text = stringResource(id = R.string.assignment_detail),
            style = MaterialTheme.typography.bodyLarge
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp, max = 200.dp), // Minimum and maximum height
            value = assignmentDetails,
            onValueChange = { assignmentDetails = it },
            placeholder = { Text(text = "Add assignment details") },
            maxLines = Int.MAX_VALUE,
            minLines = 3,
            singleLine = false,
            shape = MaterialTheme.shapes.medium,
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.assignment_marks),
                    style = MaterialTheme.typography.bodyLarge
                )
                TextField(
                    modifier = Modifier.width(128.dp),
                    value = assignmentMarks,
                    onValueChange = { input ->
                        assignmentMarks = input
                    },

                    placeholder = { Text(text = "3") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

            }



            RecurrenceDropdownMenu { recurrence ->
                selectedRecurrence = recurrence
            }

        }


        Spacer(modifier = Modifier.padding(4.dp))


    StartDateTextField { startDate ->
        selectedStartDate = startDate
    }


        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = stringResource(id = R.string.difficulty_level),
            style = MaterialTheme.typography.bodyLarge
        )
        var difficulty by rememberSaveable { mutableStateOf<Difficulty?>(null) }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                selected = difficulty == Difficulty.Hard,
                onClick = {
                    difficulty = if (difficulty == Difficulty.Hard) null else Difficulty.Hard
                    selectedDifficulty = difficulty.toString()
                },
                label = { Text(text = Difficulty.Hard.name) },
                leadingIcon = {
                    if (difficulty == Difficulty.Hard) {
                        Icon(Icons.Default.Check, contentDescription = "Selected")
                    }
                }
            )
            FilterChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                selected = difficulty == Difficulty.Moderate,
                onClick = {
                    difficulty = if (difficulty == Difficulty.Moderate) null else Difficulty.Moderate
                    selectedDifficulty = difficulty.toString()
                },
                label = { Text(text = Difficulty.Moderate.name) },
                leadingIcon = {
                    if (difficulty == Difficulty.Moderate) {
                        Icon(Icons.Default.Check, contentDescription = "Selected")
                    }
                }
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                selected = difficulty == Difficulty.Basic,
                onClick = {
                    difficulty = if (difficulty == Difficulty.Basic) null else Difficulty.Basic
                    selectedDifficulty = difficulty.toString()

                },
                label = { Text(text = Difficulty.Basic.name) },
                leadingIcon = {
                    if (difficulty == Difficulty.Basic) {
                        Icon(Icons.Default.Check, contentDescription = "Selected")
                    }
                }
            )
            FilterChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                selected = difficulty == Difficulty.Trivial,
                onClick = {
                    difficulty = if (difficulty == Difficulty.Trivial) null else Difficulty.Trivial
                    selectedDifficulty = difficulty.toString()

                },
                label = { Text(text = Difficulty.Trivial.name) },
                leadingIcon = {
                    if (difficulty == Difficulty.Trivial) {
                        Icon(Icons.Default.Check, contentDescription = "Selected")
                    }
                }
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.CenterHorizontally),

            onClick = {
                validateAssignment(
                    assignmentName = assignmentName,
                    assignmentDetails = assignmentDetails,
                    assignmentMarks = assignmentMarks.toIntOrNull() ?: 0,
                    recurrence = selectedRecurrence,
                    startDate = selectedStartDate,
                    difficulty = selectedDifficulty,
                    onInvalidate = {
                        Toast.makeText(context, context.getString(R.string.value_is_empty, context.getString(it)), Toast.LENGTH_LONG).show()
                    },
                    onValidate = {
                        navigateToAssignmentConfirm(it)
                    }
                )
            },
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Text(
                text = stringResource(id = R.string.save),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecurrenceDropdownMenu(recurrence: (String) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.recurrence),
            style = MaterialTheme.typography.bodyLarge
        )

        val options = getRecurrenceList().map { it.name } // Replace this with your actual options
        var expanded by remember { mutableStateOf(false) }
        var selectedOptionText by remember { mutableStateOf(options[0]) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }, // Toggles the dropdown when the box is clicked
        ) {
            TextField(
                readOnly = true, // Prevent manual text entry
                value = selectedOptionText,
                onValueChange = {}, // No action needed as it's read-only
                label = { Text("Select Option") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor() // Ensures the menu is anchored correctly to the TextField
                    .fillMaxWidth(), // Optional: Adjust width if needed
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            // The dropdown menu
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }, // Close the dropdown when dismissed
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOptionText = selectionOption // Update the selected option
                            recurrence(selectedOptionText)
                            expanded = false // Close the dropdown
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun StartDateTextField(startDate: (Long) -> Unit) {
    Text(
        text = stringResource(id = R.string.start_date),
        style = MaterialTheme.typography.bodyLarge
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    val sdf = SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault())
    val currentDate = sdf.format(Date())
    var selectedDate by rememberSaveable { mutableStateOf(currentDate) }


    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year: Int = calendar.get(Calendar.YEAR)
    val month: Int = calendar.get(Calendar.MONTH)
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(context, { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
        selectedDate = "${month.toMonthName()} $dayOfMonth, $year"
        val selectedCalendar = Calendar.getInstance()
        selectedCalendar.set(year, month, dayOfMonth)
        val selectedTimeInMillis = selectedCalendar.timeInMillis

        startDate(selectedTimeInMillis)

    }, year, month, day)


    TextField(
        modifier = Modifier.fillMaxWidth(),
        readOnly = true,
        value = selectedDate,
        onValueChange = {},
        trailingIcon = { Icons.Default.DateRange },
        interactionSource = interactionSource
    )

    if (isPressed) {
        mDatePickerDialog.show()
    }
}

fun Int.toMonthName(): String {
    return DateFormatSymbols().months[this]
}



private fun validateAssignment(
    assignmentName: String,
    assignmentDetails: String,
    assignmentMarks: Int,
    recurrence: String,
    startDate: Long,
    difficulty: String,
    onInvalidate: (Int) -> Unit,
    onValidate: (Assignment) -> Unit,
) {
    if (assignmentName.isEmpty()) {
        onInvalidate(R.string.assignment_name)
        return
    }

    if (assignmentDetails.isEmpty()) {
        onInvalidate(R.string.assignment_detail)
        return
    }

    if (recurrence.isEmpty()) {
        onInvalidate(R.string.recurrence)
        return
    }

    if (assignmentMarks < 1) {
        onInvalidate(R.string.assignment_marks)
        return
    }

    if (startDate < 1) {
        onInvalidate(R.string.start_date)
        return
    }

    if (difficulty.isEmpty()) {
        onInvalidate(R.string.difficulty_level)
        return
    }


    val newAssignment =
        Assignment(
            id = 1231,
            name = assignmentName,
            details = assignmentDetails,
            marks = assignmentMarks,
            recurrence = recurrence,
            startDate = Date(startDate),
            difficulty = difficulty,
        )
    onValidate(newAssignment)
}