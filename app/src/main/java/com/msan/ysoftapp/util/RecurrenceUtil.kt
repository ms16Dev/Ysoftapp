package com.msan.ysoftapp.util



enum class Recurrence {
    None,
    Weekly,
    BiWeekly,
    Monthly
}

fun getRecurrenceList(): List<Recurrence> {
    val recurrenceList = mutableListOf<Recurrence>()
    recurrenceList.add(Recurrence.None)
    recurrenceList.add(Recurrence.Weekly)
    recurrenceList.add(Recurrence.BiWeekly)
    recurrenceList.add(Recurrence.Monthly)

    return recurrenceList
}
