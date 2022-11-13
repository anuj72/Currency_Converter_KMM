package com.app.currency

class Greeting {
    private val platform: Platform = getPlatform()

    fun greeting(): String {
        return "Hello, ${platform.name}!"
    }


}