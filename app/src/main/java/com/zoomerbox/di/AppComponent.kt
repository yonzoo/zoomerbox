package com.zoomerbox.di

import com.zoomerbox.di.activity.ActivityComponent
import com.zoomerbox.di.fragment.FragmentComponent
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun getActivityComponent(): ActivityComponent

    fun getFragmentComponent(): FragmentComponent
}
