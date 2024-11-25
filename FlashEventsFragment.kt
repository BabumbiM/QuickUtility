package com.example.quickutility

import android.widget.*
import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment


class FlashEventsFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var addEventFAB: View
    private val eventList = mutableListOf<String>()
    private lateinit var eventAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_flash_events, container, false)

        listView = rootView.findViewById(R.id.eventListView)
        addEventFAB = rootView.findViewById(R.id.addEventFAB)

        // Adapter to manage the event list
        eventAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, eventList)
        listView.adapter = eventAdapter

        // Add Event FAB functionality
        addEventFAB.setOnClickListener {
            addEventInline()
        }

        // Click listener for editing or deleting
        listView.setOnItemClickListener { _, _, position, _ ->
            editEventInline(position)
        }

        return rootView
    }

    // Add event inline using EditText
    private fun addEventInline() {
        val inputLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
        }

        val titleInput = EditText(requireContext()).apply {
            hint = "Enter Event Title"
            inputType = InputType.TYPE_CLASS_TEXT
        }

        val timeInput = EditText(requireContext()).apply {
            hint = "Enter Event Time"
            inputType = InputType.TYPE_CLASS_DATETIME
        }

        inputLayout.addView(titleInput)
        inputLayout.addView(timeInput)

        val saveButton = Button(requireContext()).apply {
            text = "Save Event"
            setOnClickListener {
                val title = titleInput.text.toString()
                val time = timeInput.text.toString()

                if (title.isNotEmpty() && time.isNotEmpty()) {
                    eventList.add("$title at $time")
                    eventAdapter.notifyDataSetChanged()

                    // Confirmation Toast
                    Toast.makeText(requireContext(), "Event Added Successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    // Acknowledgment Toast for incomplete fields
                    Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_SHORT).show()
                }

                // Remove the input layout after adding the event
                (view as? ViewGroup)?.removeView(inputLayout)
            }
        }

        inputLayout.addView(saveButton)

        // Add the input layout to the fragment's root view
        (view as? ViewGroup)?.addView(inputLayout)
    }

    // Edit an existing event inline
    private fun editEventInline(position: Int) {
        val selectedEvent = eventList[position]
        val eventDetails = selectedEvent.split(" at ")

        val inputLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
        }

        val titleInput = EditText(requireContext()).apply {
            setText(eventDetails[0])
            hint = "Edit Event Title"
            inputType = InputType.TYPE_CLASS_TEXT
        }

        val timeInput = EditText(requireContext()).apply {
            setText(eventDetails[1])
            hint = "Edit Event Time"
            inputType = InputType.TYPE_CLASS_DATETIME
        }

        inputLayout.addView(titleInput)
        inputLayout.addView(timeInput)

        val updateButton = Button(requireContext()).apply {
            text = "Update Event"
            setOnClickListener {
                val title = titleInput.text.toString()
                val time = timeInput.text.toString()

                if (title.isNotEmpty() && time.isNotEmpty()) {
                    eventList[position] = "$title at $time"
                    eventAdapter.notifyDataSetChanged()

                    // Confirmation Toast
                    Toast.makeText(requireContext(), "Event Updated Successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    // Acknowledgment Toast for incomplete fields
                    Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_SHORT).show()
                }

                (view as? ViewGroup)?.removeView(inputLayout)
            }
        }

        val deleteButton = Button(requireContext()).apply {
            text = "Delete Event"
            setOnClickListener {
                eventList.removeAt(position)
                eventAdapter.notifyDataSetChanged()

                // Confirmation Toast
                Toast.makeText(requireContext(), "Event Deleted Successfully!", Toast.LENGTH_SHORT).show()

                (view as? ViewGroup)?.removeView(inputLayout)
            }
        }

        inputLayout.addView(updateButton)
        inputLayout.addView(deleteButton)

        (view as? ViewGroup)?.addView(inputLayout)
    }
}
