package com.zoomerbox.di.activity

import android.content.Context
import com.zoomerbox.presentation.view.activity.FavouriteActivity
import com.zoomerbox.presentation.view.activity.OrdersActivity
import com.zoomerbox.presentation.view.activity.SignInActivity
import com.zoomerbox.presentation.view.activity.SignInCodeActivity
import dagger.Subcomponent


@ActivityScope
@Subcomponent
interface ActivityComponent {

    fun context(): Context

    fun inject(activity: SignInActivity)

    fun inject(activity: SignInCodeActivity)

    fun inject(activity: FavouriteActivity)

    fun inject(activity: OrdersActivity)
}
