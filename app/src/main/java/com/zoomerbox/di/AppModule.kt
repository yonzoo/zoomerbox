package com.zoomerbox.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.zoomerbox.ZoomerboxApplication
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

//        @Binds
//        fun bindRepository(impl: NewsRepository): INewsRepository
//
//        @Binds
//        fun bindApi(impl: NewsApiImpl): INewsApi
//
//        @Binds
//        fun bindStore(impl: NewsStoreImpl): NewsStore
//
//        @Binds
//        fun bindScheduler(impl: SchedulersProvider): ISchedulersProvider
    }
}
