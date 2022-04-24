package co.hmtamim.survey.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshTokenOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshTokenRetrofit