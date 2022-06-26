package com.final_project.calculator.mvvm.repository

import com.final_project.calculator.mvvm.api.RetrofitBuilder
import com.final_project.calculator.mvvm.model.Currency

class Repository {

    suspend fun getData(baseCurrency: String) : Currency?{
        val request = RetrofitBuilder.apiClient.getData(baseCurrency)

        if (request.isSuccessful)
            return request.body()!!

        return null
    }

}
