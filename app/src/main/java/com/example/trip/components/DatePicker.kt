package com.example.trip.components

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import com.example.trip.TripConst
import java.time.LocalDate

object DatePicker {

    fun showDatePicker(context: Context, date: LocalDate, onSelectDate: (LocalDate) -> Unit ) {
        DatePickerDialog(
            context,
            {
                    datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                Log.i(TripConst.TAG, "${year} ${month} ${dayOfMonth}")
                onSelectDate (LocalDate.of(year, month + 1, dayOfMonth))

            },
            date.year,
            date.monthValue - 1,
            date.dayOfMonth
        ).show()
    }
}