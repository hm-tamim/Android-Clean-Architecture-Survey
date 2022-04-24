package co.hmtamim.survey.test

import android.content.Context
import androidx.room.Room
import co.hmtamim.survey.data.database.AppDatabase
import co.hmtamim.survey.data.database.dao.SurveysDao
import co.hmtamim.survey.di.modules.DatabaseModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
class FakeDatabaseModule {

    @Provides
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @Provides
    @Singleton
    fun provideSurveysDao(appDatabase: AppDatabase): SurveysDao {
        return appDatabase.surveysDao();
    }

}