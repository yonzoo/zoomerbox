package com.zoomerbox.presentation.view.util

import io.reactivex.Scheduler

interface ISchedulersProvider {


    fun io(): Scheduler

    fun ui(): Scheduler
}
