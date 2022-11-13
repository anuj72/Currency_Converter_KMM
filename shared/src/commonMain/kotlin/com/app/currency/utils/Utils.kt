package com.app.currency.utils

import com.app.currency.setting.PreferenceSettings
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant
import kotlin.time.Duration

fun saveThisTime() {
    val now = Clock.System.now()
    PreferenceSettings.putDate(now.toString())
}

fun isApiHitNeeded(): Boolean {
    return try {
        val now = Clock.System.now()
        val instantInThePast: Instant? = PreferenceSettings.getDate().toString().toInstant()
        if (instantInThePast != null) {
            val durationSinceThen: Duration = now - instantInThePast!!
            durationSinceThen.inWholeMinutes > 30
        } else {
            true
        }
    } catch (e: Exception) {
        true
    }
}

