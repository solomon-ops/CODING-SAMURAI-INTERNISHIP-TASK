package com.example.simplecalculator

import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private var input = ""
    private var lastOperator = ""
    private var operand = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)

        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)

        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.setOnClickListener { onButtonClick(button.text.toString()) }
        }
    }

    private fun onButtonClick(value: String) {
        when (value) {
            "C" -> {
                input = ""
                operand = 0.0
                lastOperator = ""
                tvResult.text = "0"
            }
            "=" -> {
                calculate()
                lastOperator = ""
            }
            "+", "-", "*", "/" -> {
                calculate()
                lastOperator = value
                input = ""
            }
            else -> {
                input += value
                tvResult.text = input
            }
        }
    }

    private fun calculate() {
        val number = input.toDoubleOrNull() ?: return
        operand = when (lastOperator) {
            "+" -> operand + number
            "-" -> operand - number
            "*" -> operand * number
            "/" -> if (number != 0.0) operand / number else {
                tvResult.text = "Error"
                return
            }
            "" -> number
            else -> operand
        }
        tvResult.text = operand.toString()
    }
}
