package com.zoomerbox.di.fragment

import com.zoomerbox.presentation.view.fragment.ShopFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface FragmentComponent {

    fun inject(fragment: ShopFragment)
}
