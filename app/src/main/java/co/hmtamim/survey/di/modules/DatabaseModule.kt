package co.hmtamim.survey.di.modules

import android.content.Context
import androidx.room.Room
import co.hmtamim.survey.data.database.AppDatabase
import co.hmtamim.survey.data.database.dao.SurveysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideSurveysDao(appDatabase: AppDatabase): SurveysDao {
        return appDatabase.surveysDao();
    }

}