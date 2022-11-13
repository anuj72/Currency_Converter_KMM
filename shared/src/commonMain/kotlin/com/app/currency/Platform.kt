package com.app.currency

import com.squareup.sqldelight.db.SqlDriver

interface Platform {
    val name: String
}
interface Factory {
    val factory: DatabaseDriverFactory
}

expect fun getPlatform(): Platform

expect fun initLogger()

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}
