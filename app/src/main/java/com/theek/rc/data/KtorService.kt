package com.theek.rc.data

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.utils.io.toByteArray

class KtorService(private val httpClient: HttpClient) {

    suspend fun getSubscriptionUi(): Result<ByteArray> {
        return try {
            val response = httpClient.get {
                url {
                    protocol = URLProtocol.HTTP
                    host = "10.0.2.2"
                    port = 8080
                    path("subscription")
                }
            }

            if (response.status.value in 200..299) {
                val bytes = response.bodyAsChannel().toByteArray()
                Result.success(bytes)

            } else {
                Result.failure(Exception("Network request failed with status code: ${response.status.value}"))
            }

        } catch (e: Exception) {
            Result.failure(Exception("Request failed ${e.localizedMessage}"))
        }
    }
}