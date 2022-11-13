package com.app.currency.setting

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalDateTime

object PreferenceSettings {
    private const val dateTime = "dateTime"
    private val preferences = Settings()

    fun putDate(dt:String){
        preferences.putString(dateTime,dt)
    }

    fun getDate():String{
       return preferences.getString(dateTime,"")
    }

    fun getDateLocals():String{
        return preferences.getString(dateTime,"").toString()
    }

}