package com.app.currency.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LatestRatesRes(
    @SerialName("base")
    val base: String,
    @SerialName("disclaimer")
    val disclaimer: String,
    @SerialName("license")
    val license: String,
    @SerialName("rates")
    val rates: Map<String,String>,
    @SerialName("timestamp")
    val timestamp: Int
)