package com.zoomerbox.di.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.zoomerbox.ZoomerboxApplication
import com.zoomerbox.data.repository.ISeasonDropRepository
import com.zoomerbox.data.repository.IUserRepository
import com.zoomerbox.data.repository.impl.real.SeasonDropRepository
import com.zoomerbox.data.repository.impl.real.UserRepository
import com.zoomerbox.data.store.ICartItemsStore
import com.zoomerbox.data.store.impl.CartItemsStorePrefsImpl
import com.zoomerbox.presentation.view.util.ISchedulersProvider
import com.zoomerbox.presentation.view.util.SchedulersProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule.BindsModule::class])
class AppModule(
    private val application: ZoomerboxApplication,
    private val auth: FirebaseAuth
) {

    @Provides
    @Singleton
    fun provideApplication(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideAuth(): FirebaseAuth {
        return auth
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Module
    interface BindsModule {

        @Binds
        fun bindStore(impl: CartItemsStorePrefsImpl): ICartItemsStore

        @Binds
        fun bindSeasonDropRepository(impl: SeasonDropRepository): ISeasonDropRepository

        @Binds
        fun bindUserRepository(impl: UserRepository): IUserRepository

        @Binds
        fun bindScheduler(impl: SchedulersProvider): ISchedulersProvider
    }
}
