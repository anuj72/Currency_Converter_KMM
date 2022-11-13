package com.app.currency

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class Ktor {
    object Client {
        val httpClient = HttpClient {
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        //      Napier.v(tag = "HTTP Client", message = message)
                    }
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
                .also { initLogger() }
            defaultRequest {
                header(HttpHeaders.ContentType, "application/json")
                header(HttpHeaders.Accept, "application/json")

            }
        }

    }
}