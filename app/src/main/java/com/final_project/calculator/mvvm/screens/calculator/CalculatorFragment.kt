package com.final_project.calculator.mvvm.screens.calculator

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.final_project.calculator.R
import com.google.android.material.button.MaterialButton

class CalculatorFragment : Fragment(R.layout.fragment_calculator) {

    private val viewModel : CalculatorViewModel by lazy {
        ViewModelProvider(this)[CalculatorViewModel::class.java]
    }

    private lateinit var currentText : EditText
    private lateinit var previousText : TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentText = view.findViewById(R.id.edit_text_current_calculations)
        previousText = view.findViewById(R.id.text_previous_calculations)

        previousText.text = viewModel.getPreviousText()

        val button0 : MaterialButton = view.findViewById(R.id.button_0)
        val button1 : MaterialButton = view.findViewById(R.id.button_1)
        val button2 : MaterialButton = view.findViewById(R.id.button_2)
        val button3 : MaterialButton = view.findViewById(R.id.button_3)
        val button4 : MaterialButton = view.findViewById(R.id.button_4)
        val button5 : MaterialButton = view.findViewById(R.id.button_5)
        val button6 : MaterialButton = view.findViewById(R.id.button_6)
        val button7 : MaterialButton = view.findViewById(R.id.button_7)
        val button8 : MaterialButton = view.findViewById(R.id.button_8)
        val button9 : MaterialButton = view.findViewById(R.id.button_9)
        val buttonPoint : MaterialButton = view.findViewById(R.id.button_point)

        val buttonRemove : MaterialButton = view.findViewById(R.id.button_remove)
        val buttonC : MaterialButton = view.findViewById(R.id.button_c)

        val buttonPlus : MaterialButton = view.findViewById(R.id.button_plus)
        val buttonMinus : MaterialButton = view.findViewById(R.id.button_minus)
        val buttonDivide : MaterialButton = view.findViewById(R.id.button_divide)
        val buttonMultiply : MaterialButton = view.findViewById(R.id.button_multiply)

        val buttonPercent : MaterialButton = view.findViewById(R.id.button_percent)

        val buttonSign : MaterialButton = view.findViewById(R.id.button_sign)

        val buttonEquals : MaterialButton = view.findViewById(R.id.button_equals)

        button0.setOnClickListener{
            currentText.setText(viewModel.addDigit(button0, currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
        }
        button1.setOnClickListener{
            currentText.setText(viewModel.addDigit(button1, currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
        }
        button2.setOnClickListener{
            currentText.setText(viewModel.addDigit(button2, currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
        }
        button3.setOnClickListener{
            currentText.setText(viewModel.addDigit(button3, currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
        }
        button4.setOnClickListener{
            currentText.setText(viewModel.addDigit(button4, currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
        }
        button5.setOnClickListener{
            currentText.setText(viewModel.addDigit(button5, currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
        }
        button6.setOnClickListener{
            currentText.setText(viewModel.addDigit(button6, currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
        }
        button7.setOnClickListener{
            currentText.setText(viewModel.addDigit(button7, currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
        }
        button8.setOnClickListener{
            currentText.setText(viewModel.addDigit(button8, currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
        }
        button9.setOnClickListener{
            currentText.setText(viewModel.addDigit(button9, currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
        }
        buttonPoint.setOnClickListener{
            currentText.setText(viewModel.addDigit(buttonPoint, currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
        }
        buttonPercent.setOnClickListener{
            currentText.setText(viewModel.addPercent(buttonPercent, currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
        }
        buttonPlus.setOnClickListener{
            currentText.setText(viewModel.addOperation(buttonPlus, currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
        }
        buttonMinus.setOnClickListener{
            currentText.setText(viewModel.addOperation(buttonMinus, currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
        }
        buttonDivide.setOnClickListener{
            currentText.setText(viewModel.addOperation(buttonDivide, currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
        }
        buttonMultiply.setOnClickListener{
            currentText.setText(viewModel.addOperation(buttonMultiply, currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
        }
        buttonRemove.setOnClickListener{
            currentText.setText(viewModel.remove(currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
        }
        buttonC.setOnClickListener{
            if(currentText.text.isNotEmpty()) currentText.setText(viewModel.clear())
            else previousText.text = ""
        }
        buttonSign.setOnClickListener{
            currentText.setText(viewModel.changeSign(currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
        }
        buttonEquals.setOnClickListener {
            currentText.setText(viewModel.equals(currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
            previousText.text = viewModel.getPreviousText()
        }
    }


}