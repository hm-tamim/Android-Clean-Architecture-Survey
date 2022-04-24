package co.hmtamim.survey.test

import android.content.Context
import co.hmtamim.survey.data.storage.EncryptedSharedPreferences
import co.hmtamim.survey.di.modules.StorageModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [StorageModule::class]
)
class FakeStorageModule {

    @Provides
    fun provideSecuredLocalStorage(@ApplicationContext context: Context) =
        EncryptedSharedPreferences(context, "testPref")

}