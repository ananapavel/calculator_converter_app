package com.final_project.calculator.mvvm.model

import com.google.gson.annotations.SerializedName

data class Currency (

    @SerializedName("meta" ) var meta : Meta,
    @SerializedName("data" ) var data : Data

)

data class Meta (

    @SerializedName("last_updated_at" ) var lastUpdatedAt : String

)


data class CurrencyName(

    @SerializedName("code"  ) var code  : String,
    @SerializedName("value" ) var value : Double

)


data class Data(

    @SerializedName("EUR" ) var EUR : CurrencyName,
    @SerializedName("KZT" ) var KZT : CurrencyName,
    @SerializedName("RUB" ) var RUB : CurrencyName,
    @SerializedName("USD" ) var USD : CurrencyName

)

