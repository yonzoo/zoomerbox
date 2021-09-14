package com.zoomerbox.di.fragment

import com.zoomerbox.presentation.view.fragment.ShopFragment
import com.zoomerbox.presentation.view.fragment.ShoppingCartFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface FragmentComponent {

    fun inject(fragment: ShopFragment)

    fun inject(fragment: ShoppingCartFragment)
}
