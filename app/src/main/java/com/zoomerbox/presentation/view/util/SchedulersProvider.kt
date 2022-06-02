package com.zoomerbox.presentation.view.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Реализация интерфейса для предоставления доступа к потокам
 *
 * @see ISchedulersProvider
 */
@Singleton
class SchedulersProvider @Inject constructor() : ISchedulersProvider {

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
