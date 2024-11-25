package com.example.quickutility

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class ConverterFragment : Fragment() {

    private lateinit var inputValue: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var fromUnitSpinner: Spinner
    private lateinit var toUnitSpinner: Spinner
    private lateinit var outputValue: TextView
    private lateinit var convertButton: Button
    private lateinit var conversionInfoTextView: TextView // New TextView for conversion details

    // Data for categories and units
    private val lengthUnits = listOf("Meter", "Kilometer", "Centimeter")
    private val weightUnits = listOf("Kilogram", "Gram", "Pound")
    private val volumeUnits = listOf("Liter", "Milliliter", "Gallon")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        val rootView = inflater.inflate(R.layout.fragment_converter, container, false)

        // Initialize views
        inputValue = rootView.findViewById(R.id.input_value)
        categorySpinner = rootView.findViewById(R.id.spinner_category)
        fromUnitSpinner = rootView.findViewById(R.id.spinner_from_unit)
        toUnitSpinner = rootView.findViewById(R.id.spinner_to_unit)
        outputValue = rootView.findViewById(R.id.output_value)
        convertButton = rootView.findViewById(R.id.button_convert)
        conversionInfoTextView = rootView.findViewById(R.id.conversion_info_textview) // Initialize new TextView

        // Set up category spinner with categories
        val categories = listOf("Length", "Weight", "Volume")
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = categoryAdapter

        // Set default units for length
        updateUnitSpinners("Length")

        // Set listener for category selection
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCategory = parentView?.getItemAtPosition(position).toString()
                updateUnitSpinners(selectedCategory)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Do nothing
            }
        }

        // Set onClickListener for convert button
        convertButton.setOnClickListener {
            performConversion()
        }

        return rootView
    }

    private fun updateUnitSpinners(category: String) {
        val units = when (category) {
            "Length" -> lengthUnits
            "Weight" -> weightUnits
            "Volume" -> volumeUnits
            else -> emptyList()
        }

        // Set up unit spinners with appropriate units based on selected category
        val unitAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, units)
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromUnitSpinner.adapter = unitAdapter
        toUnitSpinner.adapter = unitAdapter
    }

    private fun performConversion() {
        val input = inputValue.text.toString()

        // Validate input
        if (input.isBlank()) {
            outputValue.text = "Please enter a value"
            return
        }

        val inputNumber = input.toDoubleOrNull()
        if (inputNumber == null) {
            outputValue.text = "Invalid input"
            return
        }

        val selectedCategory = categorySpinner.selectedItem.toString()
        val fromUnit = fromUnitSpinner.selectedItem.toString()
        val toUnit = toUnitSpinner.selectedItem.toString()

        // Update the conversion information label
        conversionInfoTextView.text = "Converting $input $fromUnit to $toUnit"

        // Perform conversion based on category, fromUnit, and toUnit
        val convertedValue = when (selectedCategory) {
            "Length" -> convertLength(inputNumber, fromUnit, toUnit)
            "Weight" -> convertWeight(inputNumber, fromUnit, toUnit)
            "Volume" -> convertVolume(inputNumber, fromUnit, toUnit)
            else -> 0.0
        }

        // Display the result
        outputValue.text = convertedValue.toString()
    }

    private fun convertLength(value: Double, fromUnit: String, toUnit: String): Double {
        return when (fromUnit to toUnit) {
            "Meter" to "Kilometer" -> value / 1000
            "Kilometer" to "Meter" -> value * 1000
            "Meter" to "Centimeter" -> value * 100
            "Centimeter" to "Meter" -> value / 100
            "Kilometer" to "Centimeter" -> value * 100000
            "Centimeter" to "Kilometer" -> value / 100000
            else -> value
        }
    }

    private fun convertWeight(value: Double, fromUnit: String, toUnit: String): Double {
        return when (fromUnit to toUnit) {
            "Kilogram" to "Gram" -> value * 1000
            "Gram" to "Kilogram" -> value / 1000
            "Kilogram" to "Pound" -> value * 2.205
            "Pound" to "Kilogram" -> value / 2.205
            "Gram" to "Pound" -> value / 454
            "Pound" to "Gram" -> value * 454
            else -> value
        }
    }

    private fun convertVolume(value: Double, fromUnit: String, toUnit: String): Double {
        return when (fromUnit to toUnit) {
            "Liter" to "Milliliter" -> value * 1000
            "Milliliter" to "Liter" -> value / 1000
            "Liter" to "Gallon" -> value / 3.785
            "Gallon" to "Liter" -> value * 3.785
            "Milliliter" to "Gallon" -> value / 3785
            "Gallon" to "Milliliter" -> value * 3785
            else -> value
        }
    }
}
