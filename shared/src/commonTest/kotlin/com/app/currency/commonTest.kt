package com.app.currency

import com.app.currency.dao.AppDao
import com.app.currency.repo.AppRepo
import com.app.currency.service.AppService
import com.app.currency.setting.PreferenceSettings
import com.app.currency.utils.isApiHitNeeded
import com.app.currency.utils.saveThisTime
import io.ktor.util.logging.*
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class CommonGreetingTest {



    @Test
    fun testExample() {
        assertTrue(Greeting().greeting().contains("Hello"), "Check 'Hello' is mentioned")
    }

    // only when api testing needed
    @Test
     fun testApiToGetAllCurrency(){
        runBlocking {
         var data=  AppService().getLatestCurrencies()
            assertTrue((data.keys.contains("AED")))
        }
     }

    // only when api testing needed
    @Test
    fun testApiToGetAllCurrencyPrice(){
        runBlocking {
            var data=  AppService().getLatestRates()
            assertTrue((data.rates.size) > 0)
        }
    }






}