package com.app.currency.android.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.currency.cache.CurrencyName
import com.app.currency.cache.CurrencyValue
import com.app.currency.model.LatestRatesRes
import com.app.currency.repo.AppRepo
import com.app.currency.setting.PreferenceSettings
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainVm(val repo: AppRepo) : ViewModel() {
    val data = mutableStateOf<LatestRatesRes?>(null)

    private val _dataCurrencyName = mutableStateOf<MutableList<CurrencyName>?>(null)
    val dataCurrency=_dataCurrencyName

    private val _dataCurrencyValue = mutableStateOf<MutableList<CurrencyValue>?>(null)
    val dataCurrencyValue=_dataCurrencyValue

    val mdate= mutableStateOf("")

    val error = mutableStateOf("")

     val isLoading= mutableStateOf<Boolean>(false)

    init {
        getAllCurrency()
        getAllPrices()
        getUpdatedTime()
    }

    private fun getUpdatedTime() {
      val date=  PreferenceSettings.getDateLocals()
        mdate.value=date
    }

    private fun getAllPrices() {
        viewModelScope.launch {
            isLoading.value=true
            val list= mutableListOf<CurrencyValue>()
            repo.getAllLatestPrices().collectLatest {
                list.add(it)
                _dataCurrencyValue.value=list
            }
            isLoading.value=false

        }
    }

    private fun getAllCurrency() {
        viewModelScope.launch {
            isLoading.value=true
            val list= mutableListOf<CurrencyName>()
            repo.getAllLatestName().collectLatest {
                list.add(it)
                _dataCurrencyName.value=list
            }
            isLoading.value=false

        }
    }

}
