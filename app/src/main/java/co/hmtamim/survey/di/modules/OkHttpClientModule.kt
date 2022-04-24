package co.hmtamim.survey.di.modules

import co.hmtamim.survey.BuildConfig
import co.hmtamim.survey.data.observer.GlobalObserver
import co.hmtamim.survey.data.service.AuthenticationRefreshToken
import co.hmtamim.survey.data.service.RefreshTokenService
import co.hmtamim.survey.data.service.TokenInterceptor
import co.hmtamim.survey.di.RefreshTokenOkHttpClient
import co.hmtamim.survey.domain.usecase.TokenUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OkHttpClientModule {

    @Provides
    fun provideOkHttpClient(
        tokenInterceptor: TokenInterceptor,
        authenticationRefreshToken: AuthenticationRefreshToken
    ) = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        addInterceptor(tokenInterceptor)
        authenticator(authenticationRefreshToken)
    }.build()

    @RefreshTokenOkHttpClient
    @Provides
    fun provideTokenOkHttpClient() = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
    }.build()

    @Provides
    @Singleton
    fun provideGlobalObserver(): GlobalObserver = GlobalObserver()

    @Provides
    fun provideTokenInterceptor(tokenUseCases: TokenUseCases) =
        TokenInterceptor(tokenUseCases)

    @Provides
    fun provideAuthenticationRefreshToken(
        tokenUseCases: TokenUseCases,
        refreshTokenService: RefreshTokenService,
        globalObserver: GlobalObserver
    ) = AuthenticationRefreshToken(tokenUseCases, refreshTokenService, globalObserver)

}
