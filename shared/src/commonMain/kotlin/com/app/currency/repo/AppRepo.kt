package com.app.currency.repo

import com.app.currency.DatabaseDriverFactory
import com.app.currency.cache.CurrencyName
import com.app.currency.cache.CurrencyValue
import com.app.currency.dao.AppDao
import com.app.currency.model.Resource
import com.app.currency.service.AppService
import com.app.currency.setting.PreferenceSettings
import com.app.currency.utils.isApiHitNeeded
import com.app.currency.utils.saveThisTime
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant
import kotlin.time.Duration

class AppRepo(databaseDriverFactory: DatabaseDriverFactory) {

    lateinit  var dao: AppDao
    init {
        dao=  AppDao(databaseDriverFactory)
    }


    @Throws(Exception::class)
    suspend fun getAllCurrencyPrices(): Resource<Map<String,String>> = withContext(Dispatchers.Default) {
        try {
            val response = AppService().getLatestRates().rates
            response.forEach {
                dao.insertInCurrencyValue(it.key,it.value)
            }
            saveThisTime() //to save time
            Resource.Success(response)
        }  catch (e: Exception) {
            Resource.Error(error = e.message ?: "")
        }
    }


    @Throws(Exception::class)
    suspend fun getAllCurrency(): Resource<Map<String,String>> = withContext(Dispatchers.Default) {
        try {
            val response = AppService().getLatestCurrencies()
            response.forEach {
                dao.insertInCurrencyName(it.key,it.value)
            }
            saveThisTime() //to save time
            Resource.Success(response)
        }  catch (e: Exception) {
            Resource.Error(error = e.message ?: "")
        }
    }




    suspend fun getAllLatestName(): Flow<CurrencyName> {
        if(isApiHitNeeded()){
          getAllCurrency()
        }
        return dao.getAllLatestName()
    }


    suspend fun getAllLatestPrices(): Flow<CurrencyValue> {
        if(isApiHitNeeded()){
           getAllCurrencyPrices()
        }
        return dao.getAllLatestPrices()
    }






}