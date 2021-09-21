package com.zoomerbox.di.module

import android.content.Context
import androidx.room.Room
import com.zoomerbox.data.provider.local.room.AppDatabase
import com.zoomerbox.data.provider.local.room.dao.BoxItemDao
import com.zoomerbox.data.provider.local.room.dao.CollectionDao
import com.zoomerbox.data.provider.local.room.dao.SeasonDropDao
import com.zoomerbox.data.provider.local.room.dao.ZoomerBoxDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class DbModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Context): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "zoomerbox-local-db")
            .allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    internal fun provideBoxItemDao(appDatabase: AppDatabase): BoxItemDao {
        return appDatabase.boxItemDao()
    }

    @Provides
    @Singleton
    internal fun provideCollectionDao(appDatabase: AppDatabase): CollectionDao {
        return appDatabase.collectionDao()
    }

    @Provides
    @Singleton
    internal fun provideSeasonDropDao(appDatabase: AppDatabase): SeasonDropDao {
        return appDatabase.seasonDropDao()
    }

    @Provides
    @Singleton
    internal fun provideZoomerBoxDao(appDatabase: AppDatabase): ZoomerBoxDao {
        return appDatabase.zoomerBoxDao()
    }
}
