package com.example.quickutility

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class CalculatorFragment : Fragment() {

    private lateinit var resultTextView: TextView
    private var currentInput = ""
    private var lastOperator = ""
    private var firstOperand: Double? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)

        // Initialize TextView
        resultTextView = view.findViewById(R.id.text_result)

        // Set up number buttons
        val numberButtons = listOf(
            view.findViewById<Button>(R.id.button_0),
            view.findViewById<Button>(R.id.button_1),
            view.findViewById<Button>(R.id.button_2),
            view.findViewById<Button>(R.id.button_3),
            view.findViewById<Button>(R.id.button_4),
            view.findViewById<Button>(R.id.button_5),
            view.findViewById<Button>(R.id.button_6),
            view.findViewById<Button>(R.id.button_7),
            view.findViewById<Button>(R.id.button_8),
            view.findViewById<Button>(R.id.button_9)
        )
        numberButtons.forEach { button ->
            button.setOnClickListener { onNumberClick(button.text.toString()) }
        }

        // Set up operation buttons
        view.findViewById<Button>(R.id.button_add).setOnClickListener { onOperatorClick("+") }
        view.findViewById<Button>(R.id.button_subtract).setOnClickListener { onOperatorClick("-") }
        view.findViewById<Button>(R.id.button_multiply).setOnClickListener { onOperatorClick("*") }
        view.findViewById<Button>(R.id.button_divide).setOnClickListener { onOperatorClick("/") }

        // Set up equal and clear buttons
        view.findViewById<Button>(R.id.button_equals).setOnClickListener { onEqualsClick() }
        view.findViewById<Button>(R.id.button_clear).setOnClickListener { onClearClick() }

        return view
    }

    private fun onNumberClick(number: String) {
        currentInput += number
        resultTextView.text = currentInput
    }

    private fun onOperatorClick(operator: String) {
        if (currentInput.isNotEmpty()) {
            firstOperand = currentInput.toDoubleOrNull()
            currentInput = ""
            lastOperator = operator
        }
    }

    private fun onEqualsClick() {
        val secondOperand = currentInput.toDoubleOrNull()
        if (firstOperand != null && secondOperand != null) {
            val result = when (lastOperator) {
                "+" -> firstOperand!! + secondOperand
                "-" -> firstOperand!! - secondOperand
                "*" -> firstOperand!! * secondOperand
                "/" -> if (secondOperand != 0.0) firstOperand!! / secondOperand else "Error"
                else -> "Error"
            }
            resultTextView.text = result.toString()
            currentInput = ""
            firstOperand = null
            lastOperator = ""
        }
    }

    private fun onClearClick() {
        currentInput = ""
        firstOperand = null
        lastOperator = ""
        resultTextView.text = "0"
    }
}

