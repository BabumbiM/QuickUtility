package com.example.quickutility

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CalendarFragment : Fragment() {

    private var calendarView: CalendarView? = null
    private var selectedDateText: TextView? = null
    private var fabAddEvent: FloatingActionButton? = null

    private val events = mutableMapOf<String, MutableList<String>>() // Store events temporarily

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        // Initialize CalendarView, TextView, and FloatingActionButton (FAB)
        calendarView = view.findViewById(R.id.calendar_view)
        selectedDateText = view.findViewById(R.id.text_selected_date)
        fabAddEvent = view.findViewById(R.id.fab_add_event)

        // Check if the views are initialized correctly
        if (calendarView == null || selectedDateText == null || fabAddEvent == null) {
            // Log or show an error
            return null
        }

        // Show current date initially
        val currentDate = getCurrentDate()
        selectedDateText?.text = "Selected Date: $currentDate"

        // Detect date selection
        calendarView?.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth-${month + 1}-$year"
            selectedDateText?.text = "Selected Date: $selectedDate"
            displayEventsForSelectedDate(selectedDate) // Display events for the selected date
        }

        // FAB to add an event
        fabAddEvent?.setOnClickListener {
            val selectedDate = selectedDateText?.text.toString().substringAfter(": ").trim()
            openEventCreationDialog(selectedDate)
        }

        return view
    }

    private fun getCurrentDate(): String {
        val current = java.util.Calendar.getInstance()
        val day = current.get(java.util.Calendar.DAY_OF_MONTH)
        val month = current.get(java.util.Calendar.MONTH) + 1
        val year = current.get(java.util.Calendar.YEAR)
        return "$day-$month-$year"
    }

    @SuppressLint("NewApi")
    private fun openEventCreationDialog(date: String) {
        // Open a dialog or new screen to add an event for the selected date.
        // For simplicity, we can just add a dummy event here
        events.computeIfAbsent(date) { mutableListOf() }.add("Event for $date")
        displayEventsForSelectedDate(date)
    }

    @SuppressLint("SetTextI18n")
    private fun displayEventsForSelectedDate(date: String) {
        // Here you can display events below the calendar for the selected date
        // This could be a RecyclerView or a simple TextView for now
        val eventText = events[date]?.joinToString("\n") ?: "No events for this date"
        selectedDateText?.text = "Selected Date: $date\nEvents:\n$eventText"
    }
}
