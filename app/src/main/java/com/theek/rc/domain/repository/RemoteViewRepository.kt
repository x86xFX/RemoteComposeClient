package com.theek.rc.domain.repository

interface RemoteViewRepository {
    suspend fun getSubscriptionUi(): Result<ByteArray>
}