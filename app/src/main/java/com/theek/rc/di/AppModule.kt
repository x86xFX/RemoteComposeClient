package com.theek.rc.di

import com.theek.rc.data.KtorService
import com.theek.rc.data.repository.RemoteViewRepositoryImpl
import com.theek.rc.domain.repository.RemoteViewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRemoteViewRepository(ktorService: KtorService): RemoteViewRepository {
        return RemoteViewRepositoryImpl(ktorService)
    }

    @Provides
    @Singleton
    fun provideKtorService(httpClient: HttpClient): KtorService {
        return KtorService(httpClient)
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(engineFactory = Android) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        isLenient = true
                        prettyPrint = true
                    }
                )
            }

            install(Logging) {
                logger = Logger.ANDROID
            }
        }
    }
}