package co.hmtamim.survey.di.modules

import android.content.Context
import co.hmtamim.survey.data.constant.APP_SECRET_SHARED_PREFS
import co.hmtamim.survey.data.storage.EncryptedSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    fun provideSecuredLocalStorage(@ApplicationContext context: Context) =
        EncryptedSharedPreferences(context, APP_SECRET_SHARED_PREFS)

}
