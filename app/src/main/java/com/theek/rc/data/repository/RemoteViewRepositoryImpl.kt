package com.theek.rc.data.repository

import com.theek.rc.data.KtorService
import com.theek.rc.domain.repository.RemoteViewRepository

class RemoteViewRepositoryImpl(
    private val ktorService: KtorService
) : RemoteViewRepository {

    override suspend fun getSubscriptionUi(): Result<ByteArray> {
        return ktorService.getSubscriptionUi()
    }
}