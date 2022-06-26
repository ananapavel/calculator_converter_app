package com.final_project.calculator.mvvm.screens.converter

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.final_project.calculator.R
import com.final_project.calculator.mvvm.model.Currency
import com.google.android.material.button.MaterialButton

class ConverterFragment : Fragment(R.layout.fragment_converter) {

    private val viewModel : ConverterViewModel by lazy {
        ViewModelProvider(this)[ConverterViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var currencyData : Currency? = null

        val currentText : EditText = view.findViewById(R.id.edit_text_base_currency_value)
        val result : TextView = view.findViewById(R.id.text_result_currency_value)

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

        val buttonShow : MaterialButton = view.findViewById(R.id.button_show)
        val buttonRepeat : MaterialButton = view.findViewById(R.id.button_repeat)

        val buttonBaseCurrency : MaterialButton = view.findViewById(R.id.button_base_currency)
        val buttonResultCurrency : MaterialButton = view.findViewById(R.id.button_result_currency)

        viewModel.currenciesLiveData.observe(viewLifecycleOwner){ response ->
            if(response != null){
                currencyData = response
                if(currentText.text.toString() == "") currentText.setText("1")
                when(buttonResultCurrency.text) {
                    "USD" -> result.text = viewModel.getResult(response.data.USD.value, currentText.text.toString())
                    "EURO" -> result.text = viewModel.getResult(response.data.EUR.value, currentText.text.toString())
                    "KZT" -> result.text = viewModel.getResult(response.data.KZT.value, currentText.text.toString())
                    "RUB" -> result.text = viewModel.getResult(response.data.RUB.value, currentText.text.toString())
                }
            }
        }

        setCurrencies(buttonBaseCurrency = buttonBaseCurrency, buttonResultCurrency = buttonResultCurrency)

        buttonBaseCurrency.setOnClickListener {
            viewModel.changeBaseCurrency()
            buttonBaseCurrency.text = viewModel.getBaseCurrency()
        }

        buttonResultCurrency.setOnClickListener {
            viewModel.changeResultCurrency()
            buttonResultCurrency.text = viewModel.getResultCurrency()
        }

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
        buttonRemove.setOnClickListener{
            currentText.setText(viewModel.remove(currentText.text.toString()))
            currentText.setSelection(currentText.text.length)
        }

        buttonShow.setOnClickListener {
            when(buttonBaseCurrency.text){
                "USD" -> viewModel.refreshData("USD")
                "EURO" -> viewModel.refreshData("EUR")
                "KZT" -> viewModel.refreshData("KZT")
                "RUB" -> viewModel.refreshData("RUB")
            }
        }

        buttonRepeat.setOnClickListener {
            if(currencyData == null)
                when(buttonBaseCurrency.text){
                    "USD" -> viewModel.refreshData("USD")
                    "EURO" -> viewModel.refreshData("EUR")
                    "KZT" -> viewModel.refreshData("KZT")
                    "RUB" -> viewModel.refreshData("RUB")
                }
            else viewModel.setOldData()
        }
    }

    private fun setCurrencies(buttonBaseCurrency : MaterialButton, buttonResultCurrency : MaterialButton) {
        buttonBaseCurrency.text = viewModel.getBaseCurrency()
        buttonResultCurrency.text = viewModel.getResultCurrency()
    }


}