package co.hmtamim.survey.data.repositoryimpl

import co.hmtamim.survey.data.constant.ACCESS_TOKEN
import co.hmtamim.survey.data.constant.REFRESH_TOKEN
import co.hmtamim.survey.data.storage.EncryptedSharedPreferences
import co.hmtamim.survey.domain.repository.PrefRepository
import javax.inject.Inject

class PrefRepositoryImpl @Inject constructor(
    private val pref: EncryptedSharedPreferences,
) : PrefRepository {
    override fun getAccessToken(): String? {
        return pref.get(ACCESS_TOKEN)
    }

    override suspend fun saveAccessToken(token: String) {
        pref.set(ACCESS_TOKEN, token)
    }

    override fun getRefreshToken(): String? {
        return pref.get(REFRESH_TOKEN)
    }

    override suspend fun saveRefreshToken(token: String) {
        pref.set(REFRESH_TOKEN, token)
    }

    override suspend fun clearStorage() {
        pref.clearAll()
    }
}