package co.hmtamim.survey.data.service.providers

import co.hmtamim.survey.data.service.RefreshTokenService
import retrofit2.Retrofit

object RefreshTokenServiceProvider {

    fun getRefreshTokenService(retrofit: Retrofit): RefreshTokenService {
        return retrofit.create(RefreshTokenService::class.java)
    }
}
