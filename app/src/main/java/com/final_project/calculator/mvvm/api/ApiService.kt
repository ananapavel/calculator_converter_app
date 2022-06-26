package com.final_project.calculator.mvvm.api

import com.final_project.calculator.mvvm.model.Currency
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("latest?apikey=W8bVva9gvMt03HC3Sn1qQ7Gm4VuBlrOhvRTSpKMv&currencies=EUR%2CKZT%2CRUB%2CUSD")
    suspend fun getData(
        @Query("base_currency") baseCurrency: String
    ) : Response<Currency>

}