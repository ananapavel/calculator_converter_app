package com.final_project.calculator.mvvm.screens.calculator

import android.view.View
import androidx.lifecycle.ViewModel
import com.google.android.material.button.MaterialButton
import java.math.RoundingMode
import kotlin.math.ceil
import kotlin.math.floor

class CalculatorViewModel: ViewModel() {

    private var canAddOperation = false
    private var canAddDecimal = true
    private var previousText = ""

    fun addDigit(view: View, currentText : String) : String {
        var addition = ""
        if(currentText.isNotEmpty())
            if(currentText[currentText.length-1] == ')'){
                canAddOperation = false
                addition = "×"
            }

        if ((view as MaterialButton).text == ".") {
            if (canAddDecimal) {
                canAddDecimal = false
                return if (currentText.isNotEmpty() && canAddOperation && currentText[currentText.length-1] != '%')
                    currentText + addition + view.text.toString()
                else{
                    canAddOperation = true
                    currentText + addition + "0" + view.text.toString()
                }
            } else
                canAddOperation = true
                return currentText
        } else {
            canAddOperation = true
            if(currentText.isNotEmpty()){
                if(currentText.last() == '0'){
                    if(currentText.length == 1) return view.text.toString()
                    else
                        if(!currentText[currentText.lastIndex - 1].isDigit() && currentText[currentText.lastIndex - 1]!='.')
                            return currentText.slice(0 until currentText.lastIndex) + addition + view.text.toString()
                }
            }
            return currentText + addition + view.text.toString()
        }
    }

    fun addPercent(view: View, currentText: String) : String{
        var addition = ""
        if(currentText.isNotEmpty())
            if(currentText[currentText.length-1] == ')'){
                canAddOperation = false
                addition = "×"
            }
        if ((view as MaterialButton).text == "%") {
            if(currentText.isNotEmpty())
                if(currentText.last() == '%') return currentText
            return if (currentText.isEmpty() || !canAddOperation) {
                canAddDecimal = true
                currentText
            } else {
                canAddDecimal = true
                if (currentText[currentText.length - 1] == '.'){
                    canAddOperation = true
                    currentText.slice(0 until currentText.lastIndex) + addition + view.text.toString()
                } else{
                    canAddOperation = true
                    currentText + addition + view.text.toString()
                }
            }
        }
        else return currentText
    }

    fun addOperation(view: View, currentText: String) : String {
        return if(canAddOperation){
            canAddOperation = false
            canAddDecimal = true
            if (currentText[currentText.length-1] != '.')
                currentText + (view as MaterialButton).text.toString()
            else currentText.slice(0..currentText.length-2) + (view as MaterialButton).text.toString()
        } else
            if (currentText.isNotEmpty()){
                canAddDecimal = true
                canAddOperation = false
                currentText.slice(0..currentText.length-2) + (view as MaterialButton).text.toString()
            }
            else currentText
    }

    fun remove(currentText: String) : String {

        if(currentText.length <= 1) return clear()

        canAddOperation = currentText[currentText.length-2] == ')' || currentText[currentText.length-2].isDigit() || currentText[currentText.length-2] == '.' || !currentText[currentText.length-1].isDigit()

        if(currentText[currentText.length-1] == ')')
            return changeSign(currentText)

        if(currentText[currentText.length-1] == '.')
            canAddDecimal = true
        else
            if(!currentText[currentText.length-1].isDigit() && currentText[currentText.length-1] != '%')
                for(i in currentText.length-2 downTo 0) {
                    if(currentText[i] == '.'){
                        canAddDecimal = false
                        break
                    }
                    if (!currentText[i].isDigit() && currentText[i] != '%') {
                        canAddDecimal = true
                        break
                    }
                }

        return currentText.slice(0..currentText.length-2)
    }

    fun changeSign(currentText: String) : String {

        if(currentText == "") return ""

        var result = ""
        var textSize = currentText.length-1
        if(!canAddOperation) textSize--
        if(currentText[textSize] == '.') {
            textSize--
            canAddDecimal = true
        }
        if(currentText[textSize] == ')') {
            for(i in textSize downTo 0){
                if(currentText[i] == '('){
                    result = currentText.slice(0 until i) + currentText.slice(i+2 until textSize)
                    break
                }
            }
            canAddOperation = true
        }
        else {
            var isFirst = true
            for(i in textSize downTo 0){
                if(!currentText[i].isDigit() && currentText[i] != '.' && currentText[i] != '%'){
                    result = currentText.slice(0..i) + "(–" + currentText.slice(i+1..textSize)
                    isFirst = false
                    break
                }
            }
            if(isFirst)
                result = "(–" + currentText.slice(0..textSize)
            result+=')'
            canAddDecimal = true
            canAddOperation = true
        }
        return result
    }

    fun clear() : String {
        canAddOperation = false
        canAddDecimal = true
        return ""
    }

    fun equals(currentText: String) : String{
        if(currentText == "") return ""
        if(currentText.contains("÷0")) return ""
        previousText = currentText
        canAddOperation = true
        return calculate(currentText)
    }

    fun getPreviousText(): String {
        return previousText
    }

    private fun calculate(currentText: String) : String{
        val digitOperator = digitOperators(currentText)
        if (digitOperator.isEmpty()) return ""

        val list = mutableListOf<Any>()

        for (i in digitOperator.indices) {
            if (digitOperator[i] !is String)
                list.add(digitOperator[i])
            else {
                val percent = digitOperator[i].toString()
                val number = percent.slice(0 until percent.lastIndex).toDouble()
                val operation = if(list.isNotEmpty()) {
                    list.last()
                }
                else '_'

                var result = 0.0

                if(list.isNotEmpty()) {
                    list.removeLast()
                    val timesDivision = timesDivisionCalculate(list)
                    result = addSubtractCalculate(timesDivision).toBigDecimal().setScale(7, RoundingMode.CEILING).toDouble()
                }
                when (operation) {
                    '×' -> result = result / 100 * number
                    '÷' -> result /= (number / 100)
                    '+' -> result += result / 100 * number
                    '–' -> result -= result / 100 * number
                    else -> result = ((number / 100).toBigDecimal().setScale(7, RoundingMode.CEILING).toDouble())
                }

                list.clear()
                list.add(result)
            }
        }



        val timesDivision = timesDivisionCalculate(list)
        list.add(addSubtractCalculate(timesDivision).toBigDecimal().setScale(7, RoundingMode.CEILING).toDouble())

        val resultFormatted = list.last() as Double

        var result = if(ceil(resultFormatted) == floor(resultFormatted))
            resultFormatted.toInt().toString()
        else resultFormatted.toString()

        if(result[0] == '-') result = "(–" + result.slice(1..result.lastIndex) + ')'
        return result
    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Double {
        var result = passedList[0] as Double

        for(i in passedList.indices){
            if(passedList[i] is Char && i != passedList.lastIndex){
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Double
                if (operator == '+')
                    result += nextDigit
                if (operator == '–')
                    result -= nextDigit
            }
            else if(passedList[i] is String) continue
        }

        return result
    }

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any> {
        var list = passedList
        while (list.contains('×') || list.contains('÷')){
            list = calcTimesDiv(list)
        }
        return list
    }

    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any> {
        val list = mutableListOf<Any>()
        var restartIndex = passedList.size

        for(i in passedList.indices){
            if(passedList[i] is Char && i != passedList.lastIndex && i < restartIndex){
                val operator = passedList[i]
                val prevDigit = passedList[i - 1] as Double
                val nextDigit = passedList[i + 1] as Double

                when(operator){
                    '×' -> {
                        list.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '÷' -> {
                        list.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    else -> {
                        list.add(prevDigit)
                        list.add(operator)
                    }
                }
            }
            else if(passedList[i] is String) continue
            if(i > restartIndex)
                list.add(passedList[i])
        }

        return list
    }

    private fun digitOperators(currentText: String): MutableList<Any> {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        var isMinus = false
        var isPercent = false
        var number = 1.0
        for(i in currentText.indices){
            if(isMinus){
                isMinus = false
                continue
            }
            if(currentText[i] == '('){
                isMinus = true
                currentDigit += '-'
                continue
            }
            if(currentText[i].isDigit() || currentText[i] == '.')
                currentDigit += currentText[i]
            else {
                if(currentDigit != ""){
                    if(currentText[i] != '%'){
                        if(!isPercent)
                            list.add(currentDigit.toDouble())
                        else {
                            list.removeLast()
                            list.add(number/100*(currentDigit.toDouble()))
                            isPercent = false
                        }
                    }
                    else {
                        list.add("$currentDigit%")
                        if(currentText.lastIndex != i){
                            if(currentText[i + 1].isDigit()){
                                isPercent = true
                                number = currentDigit.toDouble()
                            }
                        }
                    }
                }
                currentDigit = ""
                isMinus = false
                if(currentText[i]!=')' && currentText[i]!= '(' && currentText[i] != '%')
                    list.add(currentText[i])
            }
        }

        if(isPercent){
            list.removeLast()
            list.add(number/100*(currentDigit.toDouble()))
        }
        else if(currentDigit != "")
            list.add(currentDigit.toDouble())

        if(list.last() == '+' || list.last() == '–' || list.last() == '÷' || list.last() == '×')
            list.removeLast()
        return list
    }
}