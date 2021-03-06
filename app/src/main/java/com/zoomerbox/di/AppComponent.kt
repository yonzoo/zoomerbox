package com.zoomerbox.di

import com.zoomerbox.di.activity.ActivityComponent
import com.zoomerbox.di.fragment.FragmentComponent
import com.zoomerbox.di.module.ApiModule
import com.zoomerbox.di.module.AppModule
import com.zoomerbox.di.module.DbModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, ApiModule::class, DbModule::class])
interface AppComponent {

    fun getActivityComponent(): ActivityComponent

    fun getFragmentComponent(): FragmentComponent
}
