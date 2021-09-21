package com.zoomerbox.di.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.zoomerbox.ZoomerboxApplication
import com.zoomerbox.data.repository.*
import com.zoomerbox.data.repository.impl.*
import com.zoomerbox.domain.ISeasonDropInteractor
import com.zoomerbox.domain.IUserInteractor
import com.zoomerbox.domain.impl.SeasonDropInteractor
import com.zoomerbox.domain.impl.UserInteractor
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
        fun bindSeasonDropRepository(impl: SeasonDropRepository): ISeasonDropRepository

        @Binds
        fun bindUserRepository(impl: UserRepository): IUserRepository

        @Binds
        fun bindShoppingCartRepository(impl: ShoppingCartRepository): IShoppingCartRepository

        @Binds
        fun bindFavouriteRepository(impl: FavouriteRepository): IFavouriteRepository

        @Binds
        fun bindCacheRepository(impl: CacheRepository): ICacheRepository

        @Binds
        fun bindOrdersRepository(impl: OrdersRepository): IOrdersRepository

        @Binds
        fun bindScheduler(impl: SchedulersProvider): ISchedulersProvider

        @Binds
        fun bindUserInteractor(impl: UserInteractor): IUserInteractor

        @Binds
        fun bindSeasonDropInteractor(impl: SeasonDropInteractor): ISeasonDropInteractor
    }
}
