package com.final_project.calculator.mvvm.api

import com.final_project.calculator.mvvm.model.Currency
import retrofit2.Response

class ApiClient(
    private val apiService: ApiService
) {
    suspend fun getData(baseCurrency: String) : Response<Currency> {
        return apiService.getData(baseCurrency)
    }
}
