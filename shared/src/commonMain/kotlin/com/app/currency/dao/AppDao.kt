package com.app.currency.dao
import com.app.currency.DatabaseDriverFactory
import com.app.currency.cache.CurrencyName
import com.app.currency.cache.CurrencyValue
import com.app.currency.cache.MyAppDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AppDao(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = MyAppDb(databaseDriverFactory.createDriver())
    private val dbQuery = database.myAppDbQueries

    fun getAllLatestName(): Flow<CurrencyName> {
        return dbQuery.getAllCurrencyName().executeAsList().asFlow()
    }


    fun getAllLatestPrices(): Flow<CurrencyValue> {
        return dbQuery.getAllCurrencyValue().executeAsList().asFlow()
    }




  suspend  fun insertInCurrencyName(key: String, name: String) {
      dbQuery.insertAllCurrencyName(key, name)
  }

    suspend  fun insertInCurrencyValue(key: String, name: String) {
      dbQuery.insertAllCurrencyValue(key, name)

    }







}

