package com.final_project.calculator.mvvm.screens.converter

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.final_project.calculator.mvvm.model.Currency
import com.final_project.calculator.mvvm.repository.Repository
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.ceil
import kotlin.math.floor

class ConverterViewModel : ViewModel() {
    private val repository = Repository()

    private val _currenciesLiveData = MutableLiveData<Currency?>()
    val currenciesLiveData : LiveData<Currency?> = _currenciesLiveData

    private var canAddDecimal = true

    private val baseCurrencies = listOf("EURO", "KZT", "RUB", "USD")
    private val resultCurrencies = listOf("EURO", "KZT", "RUB", "USD")
    private var baseIndex : Int = 3
    private var resultIndex : Int = 1

    private var oldResponse : Currency? = null

    fun changeBaseCurrency(){
        baseIndex++
    }

    fun changeResultCurrency(){
        resultIndex++
    }

    fun getBaseCurrency() : String{
        return baseCurrencies[baseIndex % 4]
    }

    fun getResultCurrency() : String{
        return resultCurrencies[resultIndex % 4]
    }

    fun refreshData(baseCurrency: String){
        viewModelScope.launch {
            withContext(IO){
                val response = repository.getData(baseCurrency)
                oldResponse = response
                _currenciesLiveData.postValue(response)
            }
        }
    }

    fun setOldData(){
        _currenciesLiveData.postValue(oldResponse)
    }

    fun addDigit(view : View, currentText : String) : String{
        var result : String = currentText
        if ((view as MaterialButton).text == ".") {
            if(canAddDecimal){
                canAddDecimal = false
                if(currentText.isNotEmpty()) result += view.text
                else result += "0" + view.text
            }
        }
        else {
            if(result != "0")
                result += view.text
            else
                result = view.text.toString()
        }
        return result
    }

    fun remove(currentText: String) : String {
        var result = currentText

        if(result.isNotEmpty()) {
            if(result.last() == '.') canAddDecimal = true
            result = result.slice(0 until result.lastIndex)
        }

        return result
    }

    fun getResult(currencyValue : Double, currentText: String) : String {
        val result = currencyValue * currentText.toDouble()

        if(ceil(result) == floor(result)) return result.toInt().toString()

        return result.toString()
    }

}