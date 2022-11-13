package com.app.currency.service

import com.app.currency.AppId
import com.app.currency.BaseUrl
import com.app.currency.Ktor
import com.app.currency.model.LatestRatesRes
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class AppService {
    private val httpClient = Ktor.Client.httpClient
    suspend fun getLatestRates(): LatestRatesRes {
        val url = BaseUrl.plus("/api/latest.json")
        val response: HttpResponse = httpClient.get(url) {
            parameter("app_id", AppId)
        }
        return response.body()
    }

    suspend fun getLatestCurrencies(): Map<String,String> {
        val url = BaseUrl.plus("/api/currencies.json")
        val response: HttpResponse = httpClient.get(url) {
            parameter("app_id", AppId)
        }
        return response.body()
    }



}