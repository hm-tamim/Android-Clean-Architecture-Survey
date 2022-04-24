package co.hmtamim.survey.di.modules

import co.hmtamim.survey.data.repositoryimpl.AuthRepositoryImpl
import co.hmtamim.survey.data.repositoryimpl.PrefRepositoryImpl
import co.hmtamim.survey.data.repositoryimpl.SurveyRepositoryImpl
import co.hmtamim.survey.domain.repository.AuthRepository
import co.hmtamim.survey.domain.repository.PrefRepository
import co.hmtamim.survey.domain.repository.SurveyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun prefRepository(prefRepositoryImpl: PrefRepositoryImpl): PrefRepository

    @Binds
    abstract fun authRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun surveyRepository(surveyRepositoryImpl: SurveyRepositoryImpl): SurveyRepository

}
