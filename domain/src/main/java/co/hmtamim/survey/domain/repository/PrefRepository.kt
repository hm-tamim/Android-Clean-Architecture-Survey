package co.hmtamim.survey.domain.repository

interface PrefRepository {
    fun getAccessToken(): String?
    suspend fun saveAccessToken(token: String)
    fun getRefreshToken(): String?
    suspend fun saveRefreshToken(token: String)
    suspend fun clearStorage()
}
