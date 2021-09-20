package com.zoomerbox.di.activity

import android.content.Context
import com.zoomerbox.presentation.view.activity.*
import dagger.Subcomponent


@ActivityScope
@Subcomponent
interface ActivityComponent {

    fun context(): Context

    fun inject(activity: SignInActivity)

    fun inject(activity: SignInCodeActivity)

    fun inject(activity: FavouriteActivity)

    fun inject(activity: OrdersActivity)

    fun inject(activity: CreateOrderActivity)

    fun inject(activity: AppSettingsActivity)

    fun inject(activity: DefaultActivity)

    fun inject(activity: ZoomerBoxActivity)
}
