package co.hmtamim.survey.di.modules

import co.hmtamim.survey.BuildConfig
import co.hmtamim.survey.data.service.providers.ApiServiceProvider
import co.hmtamim.survey.data.service.providers.ConverterFactoryProvider
import co.hmtamim.survey.data.service.providers.RefreshTokenServiceProvider
import co.hmtamim.survey.data.service.providers.RetrofitProvider
import co.hmtamim.survey.di.RefreshTokenOkHttpClient
import co.hmtamim.survey.di.RefreshTokenRetrofit
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    fun provideBaseApiUrl() = BuildConfig.BASE_API_URL

    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): Converter.Factory =
        ConverterFactoryProvider.getMoshiConverterFactory(moshi)

    @Provides
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit = RetrofitProvider
        .getRetrofitBuilder(baseUrl, okHttpClient, converterFactory)
        .build()

    @RefreshTokenRetrofit
    @Provides
    fun provideRefreshTokenRetrofit(
        baseUrl: String,
        @RefreshTokenOkHttpClient
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit = RetrofitProvider
        .getRetrofitBuilder(baseUrl, okHttpClient, converterFactory)
        .build()

    @Provides
    fun provideApiService(retrofit: Retrofit) = ApiServiceProvider.getApiService(retrofit)

    @Provides
    fun provideRefreshTokenService(
        @RefreshTokenRetrofit retrofit: Retrofit
    ) = RefreshTokenServiceProvider.getRefreshTokenService(retrofit)

}
