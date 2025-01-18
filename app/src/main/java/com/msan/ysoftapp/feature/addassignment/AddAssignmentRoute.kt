package com.msan.ysoftapp.feature.addassignment


import android.app.DatePickerDialog
import android.content.Context
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
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.msan.ysoftapp.R
import com.msan.ysoftapp.util.Difficulty
import com.msan.ysoftapp.util.getRecurrenceList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddAssignmentRoute(
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
    //viewModel: CalendarViewModel = hiltViewModel()
) {
    AddAssignmentScreen(onBackClicked)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAssignmentScreen(onBackClicked: () -> Unit) {
    var assignmentName by rememberSaveable { mutableStateOf("") }
    var numberOfDosageSaveable by rememberSaveable { mutableStateOf("") }

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
            placeholder = { Text(text = "Hexamine") },
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
            value = assignmentName,
            onValueChange = { assignmentName = it },
            placeholder = { Text(text = "Add assignment details") },
            maxLines = Int.MAX_VALUE, // Allows unlimited lines
            minLines = 3, // Ensures at least 3 lines are visible
            singleLine = false, // Enables multi-line input
            shape = MaterialTheme.shapes.medium, // Optional: adjust the shape
            colors = TextFieldDefaults.textFieldColors() // Optional: customize colors
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            var isMaxDoseError by rememberSaveable { mutableStateOf(false) }
            val maxDose = 3

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.assignment_marks),
                    style = MaterialTheme.typography.bodyLarge
                )
                TextField(
                    modifier = Modifier.width(128.dp),
                    value = numberOfDosageSaveable,
                    onValueChange = {
                        if (it.length < maxDose) {
                            isMaxDoseError = false
                            numberOfDosageSaveable = it
                        } else {
                            isMaxDoseError = true
                        }
                    },
                    trailingIcon = {
                        if (isMaxDoseError) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Error",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    placeholder = { Text(text = "3") },
                    isError = isMaxDoseError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                if (isMaxDoseError) {
                    Text(
                        text = "You cannot have more than 99 dosage per day.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
            RecurrenceDropdownMenu()
        }

        Spacer(modifier = Modifier.padding(4.dp))
        UntilTextField()


        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = stringResource(id = R.string.difficulty_level),
            style = MaterialTheme.typography.bodyLarge
        )
        var selectedTime by rememberSaveable { mutableStateOf<Difficulty?>(null) }
        val scope = rememberCoroutineScope()
        val context = LocalContext.current

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                selected = selectedTime == Difficulty.Hard,
                onClick = {
                    selectedTime = if (selectedTime == Difficulty.Hard) null else Difficulty.Hard
                },
                label = { Text(text = Difficulty.Hard.name) },
                leadingIcon = {
                    if (selectedTime == Difficulty.Hard) {
                        Icon(Icons.Default.Check, contentDescription = "Selected")
                    }
                }
            )
            FilterChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                selected = selectedTime == Difficulty.Moderate,
                onClick = {
                    selectedTime = if (selectedTime == Difficulty.Moderate) null else Difficulty.Moderate
                },
                label = { Text(text = Difficulty.Moderate.name) },
                leadingIcon = {
                    if (selectedTime == Difficulty.Moderate) {
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
                selected = selectedTime == Difficulty.Basic,
                onClick = {
                    selectedTime = if (selectedTime == Difficulty.Basic) null else Difficulty.Basic
                },
                label = { Text(text = Difficulty.Basic.name) },
                leadingIcon = {
                    if (selectedTime == Difficulty.Basic) {
                        Icon(Icons.Default.Check, contentDescription = "Selected")
                    }
                }
            )
            FilterChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                selected = selectedTime == Difficulty.Trivial,
                onClick = {
                    selectedTime = if (selectedTime == Difficulty.Trivial) null else Difficulty.Trivial
                },
                label = { Text(text = Difficulty.Trivial.name) },
                leadingIcon = {
                    if (selectedTime == Difficulty.Trivial) {
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
fun RecurrenceDropdownMenu() {
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
                            expanded = false // Close the dropdown
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun UntilTextField() {
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