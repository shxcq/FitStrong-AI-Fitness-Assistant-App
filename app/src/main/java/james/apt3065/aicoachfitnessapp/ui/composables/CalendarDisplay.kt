package james.apt3065.aicoachfitnessapp.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import james.apt3065.aicoachfitnessapp.ui.theme.*
import james.apt3065.aicoachfitnessapp.viewmodel.WorkoutViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

// Utility function to get the full list of dates for a month
fun getMonthDates(yearMonth: YearMonth): List<LocalDate> {
    val dates = mutableListOf<LocalDate>()
    val firstDayOfMonth = yearMonth.atDay(1)
    val lastDayOfMonth = yearMonth.atEndOfMonth()

    // Fill in previous month's dates to align the first day of the month
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek
    val daysToShowBefore = firstDayOfWeek.value % 7
    val previousMonth = yearMonth.minusMonths(1)

    for (i in 1..daysToShowBefore) {
        dates.add(previousMonth.atEndOfMonth().minusDays((daysToShowBefore - i).toLong()))
    }

    // Add current month's dates
    for (day in firstDayOfMonth.dayOfMonth..lastDayOfMonth.dayOfMonth) {
        dates.add(yearMonth.atDay(day))
    }

    // Fill in next month's dates to complete the grid
    val daysInGrid = 42
    val daysToShowAfter = daysInGrid - dates.size
    val nextMonth = yearMonth.plusMonths(1)

    for (i in 1..daysToShowAfter) {
        dates.add(nextMonth.atDay(i))
    }

    return dates
}

// Full calendar view composable
@Composable
fun CalendarDisplay(
    modifier: Modifier = Modifier,
    workoutDays: ArrayList<DayOfWeek>?,
    workoutViewModel: WorkoutViewModel = viewModel()
) {
    val yearMonth = YearMonth.now()
    val dates = getMonthDates(yearMonth)

    Column(modifier = modifier.padding(16.dp)) {
        // Days of the week header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DayOfWeek.values().forEach { day ->
                Text(
                    text = day.getDisplayName(TextStyle.SHORT, Locale.getDefault()).uppercase(),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Calendar grid
        val columns = 7
        val rows = dates.size / columns
        for (row in 0 until rows) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (col in 0 until columns) {
                    val date = dates[row * columns + col]
                    val isSelected = workoutViewModel.calendarSelection.toLocalDate().isEqual(date)
                    val isWorkoutDay = workoutDays?.contains(date.dayOfWeek) ?: false

                    CalendarDateCell(
                        date = date,
                        isSelected = isSelected,
                        isWorkoutDay = isWorkoutDay,
                        onDateSelected = { selectedDate ->
                            workoutViewModel.selectDay(selectedDate.atStartOfDay())
                            workoutViewModel.getDayString(selectedDate.atStartOfDay())
                        }
                    )
                }
            }
        }
    }
}

// Composable for each date cell in the calendar
@Composable
fun CalendarDateCell(
    date: LocalDate,
    isSelected: Boolean,
    isWorkoutDay: Boolean,
    onDateSelected: (LocalDate) -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF2979FF) else Color(0xFFF5F5F5)
    val textColor = if (isSelected) Color.White else Color.Black
    val dotColor = if (isWorkoutDay) Color(0xFFFF5722) else Color.Transparent

    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(4.dp)
            .size(48.dp)
            .clickable { onDateSelected(date) },
        border = BorderStroke(2.dp, if (isSelected) Color(0xFF66BB6A) else Color.Transparent),
        color = backgroundColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Dot Indicator
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(dotColor, shape = CircleShape)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Day of the Month
                Text(
                    text = date.dayOfMonth.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }
        }
    }
}
