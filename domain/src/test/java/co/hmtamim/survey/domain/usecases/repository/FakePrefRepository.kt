package co.hmtamim.survey.domain.usecases.repository

import co.hmtamim.survey.domain.repository.PrefRepository

class FakePrefRepository : PrefRepository {

    private val storage = hashMapOf<String, String?>()

    override fun getAccessToken(): String? {
        return storage["access_token"]
    }

    override suspend fun saveAccessToken(token: String) {
        storage["access_token"] = token
    }

    override fun getRefreshToken(): String? {
        return storage["refresh_token"]
    }

    override suspend fun saveRefreshToken(token: String) {
        storage["refresh_token"] = token
    }

    override suspend fun clearStorage() {
        storage.clear()
    }
}