package com.example.myapplication.framework

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.widget.Toast
import com.example.myapplication.R
import java.text.SimpleDateFormat
import java.util.*

abstract class UtilCalendarDialog {

    abstract fun onDateSet(
        date: Date?,
        string: String?,
        timeInMillis: Long
    )

    val START_TIME = "00:00:00"
    val END_TIME = "23:59:59"


    private var datePickerDialog: DatePickerDialog? = null

    fun CalendarDialog(context: Context?) {
        val dateFormatter =
            SimpleDateFormat(UtilDateTime.DATE_FORMATTER_TYPE_1, Locale.US)
        val newCalendar = Calendar.getInstance()
        DatePickerDialog(
            context!!,
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                if (view.isShown) {
                    val newDate = Calendar.getInstance()
                    newDate[year, monthOfYear] = dayOfMonth
                    onDateSet(
                        newDate.time,
                        dateFormatter.format(newDate.time),
                        newDate.timeInMillis
                    )
                }
            },
            newCalendar[Calendar.YEAR],
            newCalendar[Calendar.MONTH],
            newCalendar[Calendar.DAY_OF_MONTH]
        ).show()
    }

    fun CalendarDialog(
        context: Context,
        maxDate: Date?,
        minDate: Date?
    ) {
        val dateFormatter =
            SimpleDateFormat(UtilDateTime.DATE_FORMATTER_TYPE_1, Locale.US)
        val newCalendar = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(
            context,
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                if (view.isShown) {
                    val newDate = Calendar.getInstance()
                    newDate[year, monthOfYear] = dayOfMonth
                    val newDateMax = Calendar.getInstance()
                    newDateMax[year, monthOfYear] = dayOfMonth
                    newDateMax[Calendar.DAY_OF_YEAR] = newDate[Calendar.DAY_OF_YEAR] - 1
                    /** to handler certain phone  */
                    /** to handler certain phone  */
                    /** to handler certain phone  */
                    /** to handler certain phone  */
                    /** to handler certain phone  */
                    /** to handler certain phone  */
                    /** to handler certain phone  */
                    /** to handler certain phone  */
                    if (view.minDate > newDate.timeInMillis || maxDate != null && maxDate.time < newDateMax.timeInMillis) {
                        Toast.makeText(
                            context,
                            "Invalid Date",
                            Toast.LENGTH_LONG
                        ).show()
                        if (datePickerDialog != null) datePickerDialog!!.show()
                    } else {
                        onDateSet(
                            newDate.time,
                            dateFormatter.format(newDate.time),
                            newDate.timeInMillis
                        )
                    }
                }
            },
            newCalendar[Calendar.YEAR],
            newCalendar[Calendar.MONTH],
            newCalendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog!!.datePicker.calendarViewShown = false
        if (null != minDate) {
            val calMin = Calendar.getInstance()
            calMin.time = minDate
            calMin[Calendar.HOUR_OF_DAY] = 0
            calMin[Calendar.MINUTE] = 0
            calMin[Calendar.SECOND] = 0
            calMin[Calendar.MILLISECOND] = 0
            datePickerDialog!!.datePicker.minDate = calMin.timeInMillis
        }
        if (null != maxDate) {
            datePickerDialog!!.datePicker.maxDate = maxDate.time
        }
        datePickerDialog!!.show()
    }

    fun addStartTime(date: Date): Date? {
        return addTime(date, START_TIME)
    }

    fun addEndTime(date: Date): Date? {
        return addTime(date, END_TIME)
    }


    private fun addTime(date: Date, time: String): Date? {
        val dateString: String =
            UtilDateTime.Formatter.toFormat(date, UtilDateTime.DATE_FORMATTER_TYPE_1)
        val stringBuilder = StringBuilder()
        val dateStringNew =
            stringBuilder.append(dateString).append(" ").append(time).toString()
        return UtilDateTime.Formatter.toDate(dateStringNew, UtilDateTime.DATE_FORMATTER_TYPE_16)
    }
}